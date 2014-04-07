package com.lcq.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lcq.dao.IMemoDao;
import com.lcq.dao.impl.MemoDaoImpl;
import com.lcq.exercise.R;
import com.lcq.model.Memo;

public class Memo_Activity extends Activity {
	
	private ListView lv_meno;
	private Button bt_new;
	private List<Memo> memos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memo);
		
		//�����б��ʼ��
		lv_meno = (ListView) this.findViewById(R.id.lv_memo);
		lv_meno.setAdapter(new MyAdapter());
		lv_meno.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.putExtra("build", memos.get(position).getBuildTime());
				startActivity(intent);
			}
		});
		
		
		//���ఴť��ʼ��
		bt_new = (Button) this.findViewById(R.id.bt_new);
		bt_new.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.putExtra("build", "");
				startActivity(intent);
			}
		});
		
		//�����ݿ��ȡ����
		initData();
		
	}
	
	
	
	/**
	 * �����ݿ��ȡ�����б���Ϣ
	 */
	private void initData() {
		IMemoDao memoDaoImpl = new MemoDaoImpl(getApplicationContext());
		memos = memoDaoImpl.getTitle();
	}

	/**
	 * ��������ʾ��ʱ��ִ�еĴ���
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
		initData();
		//���ListView�Ѿ���ʼ������ListView����ˢ�²���
		if (lv_meno != null) {
			MyAdapter myAdapter = (MyAdapter) lv_meno.getAdapter();
			myAdapter.notifyDataSetChanged();
		}
		
	}
	
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (memos != null) {
				return memos.size();
			}else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(), R.layout.list_item, null);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_item_title);
			TextView tv_modify = (TextView) view.findViewById(R.id.tv_item_modify);
			
			tv_title.setText(memos.get(position).getTitle());
			String time = DateFormat.format("MM �� dd ��\nkk:mm", Long.parseLong(memos.get(position).getModifyTime())).toString();
			tv_modify.setText(time);
			
			return view;
		}
		
	}
}
