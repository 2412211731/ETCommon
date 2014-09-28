package com.example.mycommon.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class CityBean implements Serializable {
	private int cityId;

	@SerializedName("city")//序列话为json时候的名字，从json中解析的对应字段名
	private String cityName;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}