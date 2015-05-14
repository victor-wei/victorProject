package com.wh.victorwei.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wh.frame.push.GetuiSdkDemoActivity;
import com.wh.victorwei.R;
import com.wh.victorwei.test.TreeViewTest;

public class MainActivity extends Activity implements OnClickListener {

	private Context context;
	TextView picassoTv;
	Button jumpBtn;
	Button drawer_btn;
	Button image_btn;
	Button push_show;
	Button push_demo;
	Button login_show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		initComponent();
	}

	private void initComponent() {
		jumpBtn = (Button) findViewById(R.id.jumpBtn);
		jumpBtn.setOnClickListener(MainActivity.this);
		drawer_btn = (Button) findViewById(R.id.drawer_btn);
		drawer_btn.setOnClickListener(MainActivity.this);
		image_btn = (Button) findViewById(R.id.image_btn);
		image_btn.setOnClickListener(MainActivity.this);
		push_show = (Button) findViewById(R.id.push_show);
		push_show.setOnClickListener(MainActivity.this);
		push_demo = (Button) findViewById(R.id.push_demo);
		push_demo.setOnClickListener(MainActivity.this);
	    login_show = (Button) findViewById(R.id.login_show);
		login_show.setOnClickListener(MainActivity.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		switch (viewId) {
		case R.id.jumpBtn:
			Intent intent = new Intent();
			intent.setClass(context, TreeViewTest.class);
			startActivity(intent);
			break;
		case R.id.drawer_btn:
			Intent intentDrawer = new Intent();
			intentDrawer.setClass(context, DrawLayoutActivity.class);
			startActivity(intentDrawer);
			break;
		case R.id.image_btn:
			Intent imageIntent = new Intent();
			imageIntent.setClass(context, ImageLoadActivity.class);
			startActivity(imageIntent);
			break;
		case R.id.push_show:
			Intent push_show = new Intent();
			push_show.setClass(context, PushShowActivity.class);
			startActivity(push_show);
			break;
		case R.id.push_demo:
			Intent push_demo = new Intent();
			push_demo.setClass(context, GetuiSdkDemoActivity.class);
			startActivity(push_demo);
			break;
		case R.id.login_show:
			Intent login_show = new Intent();
			login_show.setClass(context, LoginActivity.class);
			startActivity(login_show);
			break;
		default:
			break;
		}
	}
}
