package com.lcq.ui;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lcq.alarm.AlarmReceiver;
import com.lcq.alarm.DateTimePickerDialog;
import com.lcq.alarm.DateTimePickerDialog.OnDateTimeSetListener;
import com.lcq.dao.IMemoDao;
import com.lcq.dao.impl.MemoDaoImpl;
import com.lcq.exercise.R;
import com.lcq.model.Memo;
import com.lcq.quickmenu.ActionItem;
import com.lcq.quickmenu.QuickAction;

/**
 * �����༭����
 * @author chen
 */
public class MainActivity extends Activity {

	private EditText et_content;
	private TextView tv_alarm_time;
	private ImageView iv_attachment;
	private Long modifyTime;
	private Intent intent;
	private String build;
	private String alarmTime;
	
	private static final int ID_PICTURE = 1;
	private static final int ID_PHOTO = 2;
	private static final int ID_RADIO = 3;
	private static final int ID_RELATIVES = 4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		intent = getIntent();
		
		//���ؼ������ر�����ʷ���棩
		Button bt_back = (Button) findViewById(R.id.bt_back);
		bt_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.this.finish();
				Intent intent=new Intent(getApplicationContext(), Memo_Activity.class);
				startActivity(intent);
			}
		});
		
		//��ɫѡ��ť��������ɫѡ���
		ImageView iv_color = (ImageView) this.findViewById(R.id.iv_colorbox);
		iv_color.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(), ColorActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		
		//�󶨱༭��
		et_content = (EditText) this.findViewById(R.id.et_content);
		et_content.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				saveMemo();
			}
		});
		
		//��ʼ��tv_alarm_time
		tv_alarm_time = (TextView) this.findViewById(R.id.tv_alarm_time);
		tv_alarm_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		//��������
		ImageView iv_alarm = (ImageView) this.findViewById(R.id.iv_alarm);
		iv_alarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePickerDialog dialog = new DateTimePickerDialog(MainActivity.this, System.currentTimeMillis());
		        dialog.setOnDateTimeSetListener(new OnDateTimeSetListener() {
					@Override
					public void OnDateTimeSet(AlertDialog dialog, long date) {
						//����㲥
						Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
			            final PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
			            final AlarmManager alarmManager = ((AlarmManager) getSystemService(ALARM_SERVICE));
						alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);
						//�������ӱ�����ֵ�����㱣�浽���ݿ⣩
						alarmTime = date + "";
						tv_alarm_time.setText(DateFormat.format("MM �� dd ��\n kk:mm", date));
					}
				});
				dialog.show();
			}
		});
		
		//����ImageView��ʼ��
		iv_attachment = (ImageView) this.findViewById(R.id.iv_attachment);
		iv_attachment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popMenu();
			}
		});
		
		//����InitDate()����ʼ������������Ӧ��һЩ����
		InitDate();
	}

	protected void popMenu() {
		ActionItem pictureItem 		= new ActionItem(ID_PICTURE, "ͼƬ", getResources().getDrawable(R.drawable.picture));
		ActionItem photoItem 	= new ActionItem(ID_PHOTO, "����", getResources().getDrawable(R.drawable.photo));
        ActionItem radioItem 	= new ActionItem(ID_RADIO, "¼��", getResources().getDrawable(R.drawable.radio));
        ActionItem relativesItem 	= new ActionItem(ID_RELATIVES, "��ϵ��", getResources().getDrawable(R.drawable.relatives));
		
		
		final QuickAction mQuickAction 	= new QuickAction(this);
		
		mQuickAction.addActionItem(pictureItem);
		mQuickAction.addActionItem(photoItem);
		mQuickAction.addActionItem(radioItem);
		mQuickAction.addActionItem(relativesItem);
		
		mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				//ActionItem actionItem = quickAction.getActionItem(pos);
				switch (actionId) {
				case ID_PICTURE:
					Toast.makeText(getApplicationContext(), "��Ҫȥͼ��ѡ��ͼƬ��", Toast.LENGTH_SHORT).show();
					break;
				case ID_PHOTO:
					Toast.makeText(getApplicationContext(), "��Ҫȥ������", Toast.LENGTH_SHORT).show();
					break;
				case ID_RADIO:
					Toast.makeText(getApplicationContext(), "��Ҫȥ¼����", Toast.LENGTH_SHORT).show();
					break;
				case ID_RELATIVES:
					Toast.makeText(getApplicationContext(), "��Ҫȥѡ����ϵ����", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		});
		
		//setup on dismiss listener, set the icon back to normal
		mQuickAction.setOnDismissListener(new PopupWindow.OnDismissListener() {			
			@Override
			public void onDismiss() {
				//����ʽ�˵���ʧ
			}
		});
		mQuickAction.show(iv_attachment);
	}

	/**
	 * ��ʼ����Activity�����ݣ����ӡ�������ɫ�������������ݣ�
	 */
	private void InitDate() {
		//��ʼ����ǰʱ��
		modifyTime = System.currentTimeMillis();
		TextView tv_current_time = (TextView) this.findViewById(R.id.tv_current_time);
		tv_current_time.setText(DateFormat.format("MM �� dd ��\n kk:mm", modifyTime));
		
		//��������
		build = intent.getStringExtra("build");
		
		//���build��Ϊ�գ���ô�û�ϣ���޸�һ��Memo
		if (!"".equals(build)) {
			//�����ݿ��ȡ��memo�����ݣ�����ʼ��
			IMemoDao memoDaoImpl = new MemoDaoImpl(getApplicationContext());
			Memo memo = memoDaoImpl.getMemo(build.trim());
			if (memo == null) {
				return;
			}
			et_content.setText(memo.getContent());
			if (memo.getAlarmTime() != null) {
				alarmTime = DateFormat.format("MM �� dd ��\n kk:mm", Long.parseLong(memo.getAlarmTime())).toString();
				tv_alarm_time.setText(alarmTime);
			}
			
			String colorString = memo.getBackgroundColor();
			if (!"".equals(colorString)) {
				int color = Integer.parseInt(colorString);
				if (color != 0) {
					et_content.setBackgroundColor(color);
				}
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1 && resultCode == 1) {
			int color = data.getIntExtra("color", 0);
			et_content.setBackgroundColor(color);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * ͨ�����ô˺��������Ա���Memo�����ݿ�
	 */
	private void saveMemo() {
		
		//�½�һ���߳�ȥ����Memo
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//����ر�ʱ���汸�������ݿ�
				String content = et_content.getText().toString();
				
				//����༭�����û�������ݣ��Ҵ�ʱ������Ϊ�½��ı������򲻽��б������
				if ("".equals(content) && "".equals(build)) {
					return;
				}
				
				String title;   //���ݵı���
				
				// �������̫��������˻��з�,��������·�ʽ����ȡTitle
				int count = content.indexOf("\n");
				if (count > -1 && count < 12) {
					title = content.substring(0, count) + "...";
				} else if (content.length() > 12) {
					title = content.substring(0, 12) + "...";
				} else {
					title = content;
				}
				
				//�½�һ��Memo�������������ݿⱣ�汸����Ϣ��
				Memo memo;
				
				//����Dao��������ݿ��һ������
				if ("".equals(build)) {
					//�������½�ʱ��
					build = System.currentTimeMillis() + "";     
					//�½�һ������
					memo = new Memo(build, modifyTime + "", content, "", alarmTime, title);
					new MemoDaoImpl(getApplicationContext()).addMemo(memo);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else {
					memo = new Memo(build, modifyTime + "", content, "", alarmTime, title);
					new MemoDaoImpl(getApplicationContext()).updateMemo(memo);
				}
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
