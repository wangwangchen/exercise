package com.lcq.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DaoHalper extends SQLiteOpenHelper{

	public DaoHalper(Context context) {
		super(context, "memo.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table memo (build varchar(50) primary key not null, "
				+ "modify varchar(50) not null, content varchar(500), background varchar(50),"
				+ "alarm varchar(50), title varchar(50) not null)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
