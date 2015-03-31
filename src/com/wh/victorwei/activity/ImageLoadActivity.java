package com.wh.victorwei.activity;

import com.squareup.picasso.Picasso;
import com.wh.victorwei.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageLoadActivity extends Activity {
	
	ImageView imageView ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_test);
		imageView = (ImageView) findViewById(R.id.netImage);
		Picasso picasso = Picasso.with(this);
		picasso.load("http://i.imgur.com/WoZXWkP.jpg").error(R.drawable.error).into(imageView);
	}
}
