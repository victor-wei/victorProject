package com.wh.victorwei;

import com.squareup.picasso.Picasso;
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
	ImageView imageView ;
	TextView  picassoTv;
	Button jumpBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		initComponent();
		
		Picasso picasso = Picasso.with(this);
		picasso.load("http://imgur.com/gallery/WoZXWkP").error(R.drawable.error).into(imageView);
	}
    
	private void initComponent() {
		imageView = (ImageView) findViewById(R.id.picassoImg);
		jumpBtn = (Button) findViewById(R.id.jumpBtn);
		
		jumpBtn.setOnClickListener(MainActivity.this);
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

		default:
			break;
		}
	}
}
