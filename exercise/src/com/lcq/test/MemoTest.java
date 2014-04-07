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
				"第一个memo", "", "", "第一个memo");
		assertEquals(true, memoDaoImpl.addMemo(memo));
	}
	
	public void getMemos() {
		MemoDaoImpl memoDaoImpl = new MemoDaoImpl(getContext());
		List<Memo> memos = memoDaoImpl.getMemos();
		assertEquals(1, memos.size());
		if (memos != null) {
			Memo memo = memos.get(0);
			System.out.println("创建日期：" + memo.getBuildTime() + "修改日期：" + memo.getModifyTime()
					+ "备忘内容:" + memo.getContent());
		}
		//System.out.println("email: " + user.getEmail() + " code: " + user.getCode());
	}
}
