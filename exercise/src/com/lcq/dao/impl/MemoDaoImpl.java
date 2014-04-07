package com.lcq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lcq.dao.IMemoDao;
import com.lcq.model.Memo;
import com.lcq.utils.DaoHalper;

public class MemoDaoImpl implements IMemoDao {

	private DaoHalper daoHalper;
	
	public MemoDaoImpl(Context context) {
		super();
		this.daoHalper = new DaoHalper(context);
	}

	@Override
	public boolean addMemo(Memo memo) {
		SQLiteDatabase db = this.daoHalper.getWritableDatabase();
		try {
			db.execSQL("insert into memo (build, modify, content, background, alarm, title) values (?,?,?,?,?,?)", 
					new Object[] {memo.getBuildTime(), memo.getModifyTime(), memo.getContent(), 
					memo.getBackgroundColor(), memo.getAlarmTime(), memo.getTitle()});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			db.close();
		}
		return true;
	}

	@Override
	public Memo getMemo(String build) {
		SQLiteDatabase db = this.daoHalper.getWritableDatabase();
		try {
			Cursor cursor = db.rawQuery("select * from memo where build = ?", new String[] {build});
			if (cursor.getCount() == 0) {
				return null;
			}
			cursor.moveToNext();
			Memo memo = new Memo(cursor.getString(0), cursor.getString(1), cursor.getString(2), 
					cursor.getString(3), cursor.getString(4), cursor.getString(5));
			return memo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			db.close();
		}
	}

	@Override
	public List<Memo> getMemos() {
		SQLiteDatabase db = this.daoHalper.getWritableDatabase();
		try {
			Cursor cursor = db.rawQuery("select * from memo", new String[] {});
			if (cursor.getCount() == 0) {
				return null;
			}
			
			List<Memo> memos = new ArrayList<Memo>();
			
			while(cursor.moveToNext()) {
				Memo memo = new Memo(cursor.getString(0), cursor.getString(1), cursor.getString(2), 
						cursor.getString(3), cursor.getString(4), cursor.getString(5));
				memos.add(memo);
			}
			
			return memos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			db.close();
		}
	}

	@Override
	public List<Memo> getTitle() {
		SQLiteDatabase db = this.daoHalper.getWritableDatabase();
		try {
			Cursor cursor = db.rawQuery("select build, modify, title from memo", new String[] {});
			if (cursor.getCount() == 0) {
				return null;
			}
			
			List<Memo> memos = new ArrayList<Memo>();
			
			while(cursor.moveToNext()) {
				Memo memo = new Memo();
				memo.setBuildTime(cursor.getString(0));
				memo.setModifyTime(cursor.getString(1));
				memo.setTitle(cursor.getString(2));
				memos.add(memo);
			}
			
			return memos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			db.close();
		}
	}

	public boolean updateMemo(Memo memo) {
		SQLiteDatabase db = this.daoHalper.getWritableDatabase();
		try {
			db.execSQL("update memo set modify = ?, content = ?, background = ?, alarm = ?, title = ? where build = ?", 
					new Object[] {memo.getModifyTime(), memo.getContent(), 
					memo.getBackgroundColor(), memo.getAlarmTime(), memo.getTitle(), memo.getBuildTime()});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			db.close();
		}
		return true;
	}

}
