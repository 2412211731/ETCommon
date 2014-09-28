package com.example.mycommon.bean;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProvinceBean implements Serializable {
	@SerializedName("state")
	private String proviceName;

	@SerializedName("cities")
	private List<CityBean> cityList;

	public String getProviceName() {
		return proviceName;
	}

	public void setProviceName(String proviceName) {
		this.proviceName = proviceName;
	}

	public List<CityBean> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityBean> cityList) {
		this.cityList = cityList;
	}

}