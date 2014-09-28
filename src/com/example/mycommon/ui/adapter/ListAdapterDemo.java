package com.example.mycommon.ui.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapterDemo extends BaseAdapter {

	private JSONArray jsonArray;
	private LayoutInflater inflater;

	public ListAdapterDemo(final Context context, JSONArray jsonArray) {
		inflater = LayoutInflater.from(context);
		this.jsonArray = jsonArray;
	}

	@Override
	public int getCount() {
		return jsonArray.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return jsonArray.get(position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
//		if (convertView == null) {
//			convertView = inflater.inflate(R.layout.help_list_item,
//					parent, false);
//			holder = new ViewHolder();
//			holder.itemLayout =  ;
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
		
		return convertView;
	}

	class ViewHolder {
		public View itemLayout;
	}

}
