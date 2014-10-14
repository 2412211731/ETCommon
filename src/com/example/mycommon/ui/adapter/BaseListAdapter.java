package com.example.mycommon.ui.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class BaseListAdapter<T> extends BaseAdapter {

	protected List<T> list;
	protected LayoutInflater inflater;

	public BaseListAdapter(final Context context, List<T> list) {
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
