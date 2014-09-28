package com.example.mycommon.ui.service;

import java.io.Serializable;

public class SMSInfo implements Serializable {
	public String _id = "";
	public String thread_id = "";
	public String smsAddress = "";
	public String smsBody = "";
	public String read = "";
	public int action = 0;// 1代表设置为已读，2表示删除短信
}