package com.lcq.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.lcq.colorpicker.ColorPicker;
import com.lcq.colorpicker.OpacityBar;
import com.lcq.colorpicker.SVBar;
import com.lcq.colorpicker.SaturationBar;
import com.lcq.colorpicker.ValueBar;
import com.lcq.exercise.R;

public class ColorActivity extends Activity {

	private ColorPicker picker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//去除title   
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		
		setContentView(R.layout.activity_color);
		
		picker = (ColorPicker) findViewById(R.id.picker);
		SVBar svBar = (SVBar) findViewById(R.id.svbar);
		OpacityBar opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
		SaturationBar saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
		ValueBar valueBar = (ValueBar) findViewById(R.id.valuebar);
		
		picker.addSVBar(svBar);
		picker.addOpacityBar(opacityBar);
		picker.addSaturationBar(saturationBar);
		picker.addValueBar(valueBar);
		
		//picker.setOnColorChangedListener(this);
		
		//确定按钮点击事件
		Button bt_ok = (Button) this.findViewById(R.id.bt_ok);
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("color", picker.getColor());
				ColorActivity.this.setResult(1, intent);
				ColorActivity.this.finish();
			}
		});

		
	}

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.color, menu);
		return true;
	}

}
