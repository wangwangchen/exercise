package com.lcq.dao;

import java.util.List;

import com.lcq.model.Memo;



public interface IMemoDao {
	
	/**
	 * �����ݿ������û���Ϣ
	 * @param user �û���ϢJavaBean����
	 */
	public boolean addMemo(Memo user);
	
	/**
	 * ��ȡһ��Memo��Ϣ
	 * @param build memo����ʱ��
	 * @return Memo����
	 */
	public Memo getMemo(String build);
	
	/**
	 * ��ȡ���б���
	 * @return ���б�����List����
	 */
	public List<Memo> getMemos();

	/**
	 * ��ȡTitle������޸�ʱ��
	 * @return Memo����
	 */
	public List<Memo> getTitle();
	
	/**
	 * ����һ��Memo
	 * @param memo ����¼��Ϣ
	 * @return ���½��
	 */
	public boolean updateMemo(Memo memo);
}
