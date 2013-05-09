package com.zenobase.mindlog;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MainActivity extends RoboActivity {

	@InjectView(R.id.sourceValue)
	private TextView sourceView;

	@InjectView(R.id.scorePlot)
	private ScoreChartView scoreChartView;

	@InjectView(R.id.scoreValue)
	private TextView scoreView;

	@InjectView(R.id.timerValue)
	private TimerView timerView;

	@Inject
	private Bus bus;

	@Inject
	private Headset headset;

	@Inject
	private Scoreboard scoreboard;

	@Inject
	private Preferences preferences;

	private Menu menu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		bus.register(this);
    	setContentView(R.layout.main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		onSignal(new Signal(null, 0));
		onScore(new Score(scoreboard.getLast()));
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Authorization authorization = Authorization.parse(intent.getData());
		if (authorization != null) {
			preferences.setAuthorization(authorization);
			invalidateOptionsMenu();
			Toast.makeText(this, R.string.toast_sign_in_success, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, R.string.toast_sign_in_error, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		headset.connect();
	}

	@Override
	protected void onPause() {
		super.onPause();
		pause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		headset.disconnect();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		bus.unregister(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
    	getMenuInflater().inflate(R.menu.main, menu);
		setVisible(R.id.menu_start, !scoreboard.isEnabled());
		setVisible(R.id.menu_pause, scoreboard.isEnabled());
	    setEnabled(R.id.menu_stop, scoreboard.isEnabled() || scoreboard.getCount() > 0);
		setVisible(R.id.menu_sign_in, !preferences.hasAuthorization());
		setVisible(R.id.menu_sign_out, preferences.hasAuthorization());
    	return super.onCreateOptionsMenu(menu);
	}

	private void setVisible(int id, boolean enabled) {
		menu.findItem(id).setVisible(enabled);
	}

	private void setEnabled(int id, boolean enabled) {
		MenuItem item = menu.findItem(id);
		item.setEnabled(enabled);
	    item.getIcon().setAlpha(enabled ? 255 : 64);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_start:
				start();
				return true;
			case R.id.menu_pause:
				pause();
				return true;
			case R.id.menu_stop:
				stop();
				return true;
			case R.id.menu_sign_in:
				signIn();
				return true;
			case R.id.menu_sign_out:
				signOut();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void signIn() {
		new AuthorizeIntent(this).start();
	}

	private void signOut() {
		new SignOutTask(this, preferences.getAuthorization()).execute();
		preferences.setAuthorization(null);
		invalidateOptionsMenu();
	}

	@Subscribe
    public void onSignal(Signal event) {
		sourceView.setText(event.getSource());
    	sourceView.setTextColor(signalToColor(event.getStrength()));
    }

	private static int signalToColor(int strength) {
    	if (strength == 100) {
        	return Color.GREEN;
		} else if (strength > 1) {
        	return Color.YELLOW;
		} else {
        	return Color.RED;
		}
	}

	@Subscribe
	public void onScore(Score event) {
    	timerView.setValue(scoreboard.getCount());
		scoreChartView.setScores(scoreboard.getScores());
		setValue(scoreView, event.getValue());
    }

    private static void setValue(TextView view, int value) {
		view.setText(value + "%");
    }

	private void start() {
		scoreboard.setEnabled(true);
		invalidateOptionsMenu();
	}

	private void pause() {
		scoreboard.setEnabled(false);
		invalidateOptionsMenu();
	}

	private void stop() {
		Event event = createEvent();
		scoreboard.setEnabled(false);
		scoreboard.reset();
		invalidateOptionsMenu();
		new EventDialog(this, preferences).show(event);
	}

	private Event createEvent() {
		String tag = getText(R.string.event_tag).toString();
		return new Event(tag, scoreboard.getCount() * 1000, scoreboard.getMean());
	}
}
