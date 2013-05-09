package com.zenobase.mindlog;

import org.apache.http.client.methods.HttpDelete;
import android.content.Context;

public class SignOutTask extends HttpClientTask {

	public SignOutTask(Context context, Authorization authorization) {
		super(context, authorization);
	}

	@Override
	public Void call() throws Exception {
		call(new HttpDelete(context.getText(R.string.host) + "/authorizations/" + authorization.getToken()));
		return null;
	}
}
