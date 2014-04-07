package com.lcq.dao;

import java.util.List;

import com.lcq.model.Memo;



public interface IMemoDao {
	
	/**
	 * 向数据库新增用户信息
	 * @param user 用户信息JavaBean对象
	 */
	public boolean addMemo(Memo user);
	
	/**
	 * 获取一个Memo信息
	 * @param build memo创建时间
	 * @return Memo对象
	 */
	public Memo getMemo(String build);
	
	/**
	 * 获取所有备忘
	 * @return 所有备忘的List集合
	 */
	public List<Memo> getMemos();

	/**
	 * 获取Title和最后修改时间
	 * @return Memo集合
	 */
	public List<Memo> getTitle();
	
	/**
	 * 更新一条Memo
	 * @param memo 备忘录信息
	 * @return 更新结果
	 */
	public boolean updateMemo(Memo memo);
}
