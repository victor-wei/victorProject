package com.wh.victorwei.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.wh.victorwei.R;

/**
 * 推送展示页
 * 
 * @author weihe
 *
 */
public class PushShowActivity extends Activity {

	TextView title;
	TextView content;
	Button bind;
	String alias;
	EditText aliasEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.push_show_layout);
		initComponent();
	}

	private void initComponent() {
		title = (TextView) findViewById(R.id.title);
		content = (TextView) findViewById(R.id.content);
		aliasEdit = (EditText) findViewById(R.id.aliasEdit);
		bind = (Button) findViewById(R.id.bind);
		bind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String alias = aliasEdit.getEditableText().toString();
				if (alias != null && alias.length() > 0) {
					boolean bindSuccess = PushManager.getInstance().bindAlias(PushShowActivity.this,
							alias);
					System.out.println(bindSuccess);
					return;
				}
			}
		});
	}

}
