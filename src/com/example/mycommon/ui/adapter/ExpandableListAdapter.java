package com.example.mycommon.ui.adapter;

import java.lang.reflect.Type;
import java.util.List;
import com.example.mycommon.R;
import com.example.mycommon.bean.ProvinceBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 折叠列表适配器
 * 
 * @author kaka
 * 
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<ProvinceBean> dataList;

	public ExpandableListAdapter(Context context, String jsonData) {
		this.context = context;
		inflater = LayoutInflater.from(context);

		Type type = new TypeToken<List<ProvinceBean>>() {
		}.getType();
		Gson gson = new Gson();
		dataList = gson.fromJson(jsonData, type);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return dataList.get(groupPosition).getCityList().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = inflater.inflate(
				context.getResources().getLayout(
						R.layout.city_list_expand_content_item), null);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(dataList.get(groupPosition).getCityList()
				.get(childPosition).getCityName());

		view.setTag(dataList.get(groupPosition).getCityList()
				.get(childPosition).getCityId());
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "点击了", Toast.LENGTH_LONG).show();
			}
		});
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return dataList.get(groupPosition).getCityList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return dataList.get(groupPosition).getProviceName();
	}

	@Override
	public int getGroupCount() {
		return dataList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = inflater.inflate(
				context.getResources().getLayout(
						R.layout.city_list_expand_title_item), null);
		TextView title = (TextView) view.findViewById(R.id.history_text);
		title.setText(dataList.get(groupPosition).getProviceName());

		ImageView arrow = (ImageView) view.findViewById(R.id.arrow);
		if (isExpanded) {
			arrow.setImageResource(R.drawable.arrow_down1);
		} else {
			arrow.setImageResource(R.drawable.arrow_right1);
		}
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
