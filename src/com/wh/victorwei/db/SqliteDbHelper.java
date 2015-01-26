package com.wh.victorwei.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author weihe
 * 数据库版本升级，根据version判断，在onUpgrade中执行相应的升级代码
 * 
 */
public class SqliteDbHelper extends SQLiteOpenHelper {
    
	private static String dbName = "victor.db";
	private static final int VERSION = 1;
	private Context context;
	public SqliteDbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.context = context;
	}
	public SqliteDbHelper(Context context) {
		this(context,dbName, null, VERSION);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
