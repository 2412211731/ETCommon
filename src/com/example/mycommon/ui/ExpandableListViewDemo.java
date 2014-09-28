package com.example.mycommon.ui;

import com.example.mycommon.R;
import com.example.mycommon.ui.adapter.ExpandableListAdapter;
import com.example.mycommon.utils.ResourceUtils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class ExpandableListViewDemo extends Activity {
	private ExpandableListView expandableListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandablelistview_demo);
		
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
		
		 String jsonData = ResourceUtils.geFileFromAssets(this, "provinceCitys.json");
		 ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(this,jsonData);
		 expandableListView.setAdapter(expandableListAdapter);
	}

}
