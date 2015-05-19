package com.wh.victorwei.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wh.frame.push.GetuiSdkDemoActivity;
import com.wh.victorwei.R;
import com.wh.victorwei.test.ImageTestActivity;
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
	Button zhuanpan_btn;
	Button xiaomipush_btn;
	public static List<String> logList = new ArrayList<String>();
	 public static MainActivity sMainActivity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sMainActivity = this;
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
		xiaomipush_btn = (Button) findViewById(R.id.xiaomipush_btn);
		xiaomipush_btn.setOnClickListener(MainActivity.this);
		zhuanpan_btn = (Button) findViewById(R.id.zhuanpan_btn);
		zhuanpan_btn.setOnClickListener(MainActivity.this);
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
		case R.id.xiaomipush_btn:
			Intent xiaomipush_intent = new Intent();
			xiaomipush_intent.setClass(context, LoginActivity.class);
			startActivity(xiaomipush_intent);
			break;
		case R.id.zhuanpan_btn:
			Intent zhuanpan_intent = new Intent();
			zhuanpan_intent.setClass(context,ImageTestActivity.class);
			startActivity(zhuanpan_intent);
			break;
		default:
			break;
		}
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        refreshLogInfo();
    }

    public void refreshLogInfo() {
        String AllLog ="";
        for(String log : logList) {
            AllLog = AllLog + log + "\n\n";
        }
        Log.i(MyApplication.TAG, AllLog+"  allLog");
    }
}
