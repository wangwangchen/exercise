package com.lcq.model;


/**
 * 存放用户账号密码的javaBean
 * @author JerryLiu
 *
 */
public class Memo {
	private String buildTime;
	private String modifyTime;
	private String content;
	private String backgroundColor;
	private String alarmTime;
	private String title;
	
	public Memo(String buildTime, String modifyTime, String content,
			String backgroundColor, String alarmTime, String title) {
		super();
		this.buildTime = buildTime;
		this.modifyTime = modifyTime;
		this.content = content;
		this.backgroundColor = backgroundColor;
		this.alarmTime = alarmTime;
		this.title = title;
	}
	
	public Memo() {
	}

	public String getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
