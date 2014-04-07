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
 * 备忘编辑界面
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
		
		//返回键（返回备忘历史界面）
		Button bt_back = (Button) findViewById(R.id.bt_back);
		bt_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.this.finish();
				Intent intent=new Intent(getApplicationContext(), Memo_Activity.class);
				startActivity(intent);
			}
		});
		
		//颜色选择按钮（弹出颜色选择框）
		ImageView iv_color = (ImageView) this.findViewById(R.id.iv_colorbox);
		iv_color.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(), ColorActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		
		//绑定编辑框
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
		
		//初始化tv_alarm_time
		tv_alarm_time = (TextView) this.findViewById(R.id.tv_alarm_time);
		tv_alarm_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		//闹钟设置
		ImageView iv_alarm = (ImageView) this.findViewById(R.id.iv_alarm);
		iv_alarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePickerDialog dialog = new DateTimePickerDialog(MainActivity.this, System.currentTimeMillis());
		        dialog.setOnDateTimeSetListener(new OnDateTimeSetListener() {
					@Override
					public void OnDateTimeSet(AlertDialog dialog, long date) {
						//定义广播
						Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
			            final PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
			            final AlarmManager alarmManager = ((AlarmManager) getSystemService(ALARM_SERVICE));
						alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);
						//设置闹钟变量的值（方便保存到数据库）
						alarmTime = date + "";
						tv_alarm_time.setText(DateFormat.format("MM 月 dd 日\n kk:mm", date));
					}
				});
				dialog.show();
			}
		});
		
		//附件ImageView初始化
		iv_attachment = (ImageView) this.findViewById(R.id.iv_attachment);
		iv_attachment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popMenu();
			}
		});
		
		//调用InitDate()来初始化该条备忘对应的一些数据
		InitDate();
	}

	protected void popMenu() {
		ActionItem pictureItem 		= new ActionItem(ID_PICTURE, "图片", getResources().getDrawable(R.drawable.picture));
		ActionItem photoItem 	= new ActionItem(ID_PHOTO, "拍照", getResources().getDrawable(R.drawable.photo));
        ActionItem radioItem 	= new ActionItem(ID_RADIO, "录音", getResources().getDrawable(R.drawable.radio));
        ActionItem relativesItem 	= new ActionItem(ID_RELATIVES, "联系人", getResources().getDrawable(R.drawable.relatives));
		
		
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
					Toast.makeText(getApplicationContext(), "我要去图库选择图片了", Toast.LENGTH_SHORT).show();
					break;
				case ID_PHOTO:
					Toast.makeText(getApplicationContext(), "我要去拍照了", Toast.LENGTH_SHORT).show();
					break;
				case ID_RADIO:
					Toast.makeText(getApplicationContext(), "我要去录音了", Toast.LENGTH_SHORT).show();
					break;
				case ID_RELATIVES:
					Toast.makeText(getApplicationContext(), "我要去选择联系人了", Toast.LENGTH_SHORT).show();
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
				//弹出式菜单消失
			}
		});
		mQuickAction.show(iv_attachment);
	}

	/**
	 * 初始化本Activity的数据（闹钟、背景颜色、本备忘的内容）
	 */
	private void InitDate() {
		//初始化当前时间
		modifyTime = System.currentTimeMillis();
		TextView tv_current_time = (TextView) this.findViewById(R.id.tv_current_time);
		tv_current_time.setText(DateFormat.format("MM 月 dd 日\n kk:mm", modifyTime));
		
		//加载数据
		build = intent.getStringExtra("build");
		
		//如果build不为空，那么用户希望修改一个Memo
		if (!"".equals(build)) {
			//从数据库获取该memo的数据，并初始化
			IMemoDao memoDaoImpl = new MemoDaoImpl(getApplicationContext());
			Memo memo = memoDaoImpl.getMemo(build.trim());
			if (memo == null) {
				return;
			}
			et_content.setText(memo.getContent());
			if (memo.getAlarmTime() != null) {
				alarmTime = DateFormat.format("MM 月 dd 日\n kk:mm", Long.parseLong(memo.getAlarmTime())).toString();
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
	 * 通过调用此函数，可以保存Memo到数据库
	 */
	private void saveMemo() {
		
		//新建一个线程去保存Memo
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//界面关闭时保存备忘到数据库
				String content = et_content.getText().toString();
				
				//如果编辑框根本没输入内容，且此时本备忘为新建的备忘，则不进行保存操作
				if ("".equals(content) && "".equals(build)) {
					return;
				}
				
				String title;   //内容的标题
				
				// 如果内容太长或出现了换行符,则采用如下方式来获取Title
				int count = content.indexOf("\n");
				if (count > -1 && count < 12) {
					title = content.substring(0, count) + "...";
				} else if (content.length() > 12) {
					title = content.substring(0, 12) + "...";
				} else {
					title = content;
				}
				
				//新建一个Memo对象（用来往数据库保存备忘信息）
				Memo memo;
				
				//调用Dao借口往数据库存一个备忘
				if ("".equals(build)) {
					//设置器新建时间
					build = System.currentTimeMillis() + "";     
					//新建一个备忘
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
