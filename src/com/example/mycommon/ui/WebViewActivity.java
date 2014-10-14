package com.example.mycommon.ui;

import com.example.mycommon.R;
import com.example.mycommon.utils.web.WebUtil;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * 显示网页的界面
 * 
 * @author kaka
 */
public class WebViewActivity extends BaseActivity {

	private WebView webView;
	private String url;
	private String title = "";

	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.web_simple);
		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		super.onCreate(savedInstanceState);

		url = intent.getStringExtra("url");
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		webView = (WebView) findViewById(R.id.webview);

		WebUtil.wrapWebView(webView, progressBar);
		webView.loadUrl(url);
	}

	@Override
	public void initListener() {
	}

	@Override
	public void initView() {
	}

	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public String getTitleStr() {
		return title;
	}
}
