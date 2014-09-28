package com.example.mycommon.bean;

import java.io.Serializable;

/**
 * app基础信息
 */
public class AppInfo implements Serializable {
	private static final long serialVersionUID = 2014090111L;

	private String token;
	private int versionCode;
	private String versionName;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

}
