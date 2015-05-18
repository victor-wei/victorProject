package com.wh.victorwei.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.igexin.sdk.PushManager;
import com.ta.TASyncHttpClient;
import com.ta.annotation.TAInject;
import com.ta.util.http.RequestParams;
import com.wh.victorwei.R;
import com.wh.victorwei.Setting;

public class LoginActivity extends Activity {

	private Button register_btn;
	private Button login_btn;
	private EditText password;
	private EditText username;
	@TAInject
	private TASyncHttpClient syncHttpClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initComponent();
		initControl();
	}

	private void initComponent() {
		register_btn = (Button) findViewById(R.id.register_btn);
		login_btn = (Button) findViewById(R.id.login_btn);
		password = (EditText) findViewById(R.id.password);
		username = (EditText) findViewById(R.id.username);
	}

	private void initControl() {

		register_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		login_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final String userName = username.getText().toString();
				final String passwordString = password.getText().toString();
				if (userName == null || "".equals(userName)
						|| passwordString == null || "".equals(passwordString)) {
					Toast.makeText(LoginActivity.this, "用户名或密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				new Thread(new Runnable() {

					@Override
					public void run() {
						RequestParams params = new RequestParams();
						params.put("name", userName);
						params.put("password", passwordString);
						try {
							String content="sssss"; 
							content = syncHttpClient.get(Setting.victorServerUrl);
//						String content = syncHttpClient
//								.get(Setting.victorServerUrl + "command=user");
						Log.i("TAG", content + " content");
							
//							JSONArray jsonArray = JSONArray.parseArray(content);
//							for (int i = 0; i < jsonArray.size(); i++) {
//								JSONObject json = new JSONObject(jsonArray
//										.getJSONObject(i));
//								String name = json.getString("name");
//								String password = json.getString("password");
//								if (name.equals(userName)
//										&& password.equals(passwordString)) {
									PushManager.getInstance().bindAlias(
											getApplicationContext(),
											username.getText().toString());
									String clientId = PushManager.getInstance()
											.getClientid(getApplicationContext());
									Log.i("TAG", clientId + " clientId");
//								}
//							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();

			}
		});
	}
}
