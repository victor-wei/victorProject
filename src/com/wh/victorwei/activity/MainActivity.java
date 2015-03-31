package com.wh.victorwei.activity;

import com.squareup.picasso.Picasso;
import com.wh.victorwei.R;
import com.wh.victorwei.R.drawable;
import com.wh.victorwei.R.id;
import com.wh.victorwei.R.layout;
import com.wh.victorwei.R.menu;
import com.wh.victorwei.test.TreeViewTest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity  implements OnClickListener{
	
	private Context context;
	TextView  picassoTv;
	Button jumpBtn;
	Button drawer_btn;
	Button image_btn;

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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
      	int viewId  = v.getId();
      	switch (viewId) {
		case R.id.jumpBtn:
			Intent intent  = new Intent();
			intent.setClass(context, TreeViewTest.class);
			startActivity(intent);
			break;
		case R.id.drawer_btn:
			Intent intentDrawer  = new Intent();
			intentDrawer.setClass(context, DrawLayoutActivity.class);
			startActivity(intentDrawer);
			break;
		case R.id.image_btn:
			Intent imageIntent  = new Intent();
			imageIntent.setClass(context, ImageLoadActivity.class);
			startActivity(imageIntent);
			break;

		default:
			break;
		}
	}
}
