package com.lcq.test;

import java.util.Date;
import java.util.List;

import android.test.AndroidTestCase;
import android.widget.Toast;

import com.lcq.dao.impl.MemoDaoImpl;
import com.lcq.model.Memo;

public class MemoTest extends AndroidTestCase {
	
	public void addMemo() {
		MemoDaoImpl memoDaoImpl = new MemoDaoImpl(getContext());
		Memo memo = new Memo(System.currentTimeMillis() + "", 
				System.currentTimeMillis() + "", 
				"��һ��memo", "", "", "��һ��memo");
		assertEquals(true, memoDaoImpl.addMemo(memo));
	}
	
	public void getMemos() {
		MemoDaoImpl memoDaoImpl = new MemoDaoImpl(getContext());
		List<Memo> memos = memoDaoImpl.getMemos();
		assertEquals(1, memos.size());
		if (memos != null) {
			Memo memo = memos.get(0);
			System.out.println("�������ڣ�" + memo.getBuildTime() + "�޸����ڣ�" + memo.getModifyTime()
					+ "��������:" + memo.getContent());
		}
		//System.out.println("email: " + user.getEmail() + " code: " + user.getCode());
	}
}
