package com.wh.victorwei.activity;

import com.wh.victorwei.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DrawLayoutActivity extends Activity implements OnClickListener{
    
	private Button show_btn;
	private DrawerLayout drawerLayout;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawerlayout);
		context = this;
		initComponent();
		setListener();
	}
	
	private void initComponent(){
		show_btn = (Button) findViewById(R.id.show_btn);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		
	}
	private void setListener(){
		show_btn.setOnClickListener(this);
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.show_btn:
			drawerLayout.openDrawer(Gravity.LEFT);
			break;

		default:
			break;
		}
	}
}
