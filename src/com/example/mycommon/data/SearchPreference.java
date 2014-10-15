package com.example.mycommon.data;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import android.content.Context;
import android.text.TextUtils;
import com.example.mycommon.utils.LocalPreference;

/**
 * 搜索历史
 */
public class SearchPreference extends LocalPreference {

	private final static String name = "search_hisotry";

	public SearchPreference(Context mContext) {
		super(mContext,name);
	}

	public void add(String q) {
		try {
			String jsonStr = getString("keys", null);
			JSONArray oldArr = null;
			if (TextUtils.isEmpty(jsonStr)) {
				oldArr = new JSONArray();
			} else {
				oldArr = new JSONArray(jsonStr);
			}
			JSONArray newArr = new JSONArray();
			for (int i = 0; i < oldArr.length(); i++) {
				if (!oldArr.optString(i, "").equals(q)) {
					newArr.put(oldArr.get(i));
				}
			}
			newArr.put(q);
			setString("keys", newArr.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getAll() {
		List<String> result = new ArrayList<String>();
		try {
			String jsonStr = getString("keys", null);
			JSONArray arr = null;
			if (TextUtils.isEmpty(jsonStr)) {
				arr = new JSONArray();
			} else {
				arr = new JSONArray(jsonStr);
			}
			for (int i = arr.length() - 1; i >= 0; i--) {
				result.add(arr.getString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
