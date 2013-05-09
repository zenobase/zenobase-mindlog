package com.zenobase.mindlog;

import javax.inject.Inject;

import roboguice.util.Ln;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.os.Message;
import com.neurosky.thinkgear.TGDevice;
import com.squareup.otto.Bus;

@SuppressLint("HandlerLeak")
public class BluetoothHeadset implements Headset {

	private final Bus bus;
	private final Scoreboard scoreboard;
	private final TGDevice device;

	@Inject
	public BluetoothHeadset(Bus bus, Scoreboard scoreboard) {
		Ln.d("Driver: %s, %d", TGDevice.build_title, TGDevice.version);
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter == null) {
			throw new IllegalStateException("Bluetooth is not enabled");
		}
		this.bus = bus;
		this.scoreboard = scoreboard;
		this.device = new TGDevice(adapter, handler);
    }

	private final Handler handler = new Handler() {

    	@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

				case TGDevice.MSG_STATE_CHANGE:
					switch (msg.arg1) {
    	                case TGDevice.STATE_IDLE:
    	                	break;
    	                case TGDevice.STATE_CONNECTING:
                        	Ln.d("Connecting headset...");
    	                    break;
                        case TGDevice.STATE_CONNECTED:
                        	Ln.d("Connected headset");
                            device.start();
                            break;
                        case TGDevice.STATE_DISCONNECTED:
                        	Ln.d("Disconnected headset");
                            device.start();
                            break;
                        default:
    	                	Ln.e("Couldn't connect headset: %d", msg.arg1);
    	                	break;
                    }
                    break;

                case TGDevice.MSG_POOR_SIGNAL:
                	// 0..200
                	int signal = (200 - msg.arg1) / 2;
            		bus.post(new Signal("MindWave", signal));
                    break;

                case TGDevice.MSG_RAW_DATA:
                	// -2048..2047
                	break;

                case TGDevice.MSG_EEG_POWER:
                	// TGEegPower p = (TGEegPower) msg.obj;
                	break;

                case TGDevice.MSG_ATTENTION:
                    // 0..100
                	break;

                case TGDevice.MSG_MEDITATION:
                    // 0..100
            		scoreboard.add(msg.arg1);
                	break;

                case TGDevice.MSG_BLINK:
                	// 1..255
                	break;
        	}
        }
    };

	@Override
	public void connect() {
		if (device.getState() != TGDevice.STATE_CONNECTING && device.getState() != TGDevice.STATE_CONNECTED) {
			device.connect(false);
		}
	}

	@Override
	public void disconnect() {
		device.close();
    }
}
