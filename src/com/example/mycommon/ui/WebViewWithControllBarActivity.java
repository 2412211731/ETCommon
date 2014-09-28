//package com.example.mycommon.ui;
//
//import com.example.mycommon.R;
//import com.example.mycommon.utils.web.WebUtil;
//import com.example.mycommon.utils.web.WebWithControllBarUtil;
//import com.example.mycommon.widget.WebViewController;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.webkit.WebView;
//import android.widget.ProgressBar;
//
///**
// * 带网页控制条的activity
// * @author kaka
// *
// */
//public class WebViewWithControllBarActivity extends BaseActivity {
//
//	private WebView webView;
//	private String url;
//	private String title = "";
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		setContentView(R.layout.web_simple);
//		super.onCreate(savedInstanceState);
//	}
//
//	@Override
//	public void initListener() {
//	}
//
//	@Override
//	public void initView() {
//		Intent intent = getIntent();
//		title = intent.getStringExtra("title");
//		url = intent.getStringExtra("url");
//		webView = (WebView) findViewById(R.id.webview);
//		
//		WebViewController ctrl = (WebViewController) findViewById(R.id.more_controller);
//		WebWithControllBarUtil.wrapWebView(webView, ctrl);
//		ctrl.setWebView(webView, url);
//
//		webView.loadUrl(url);
//	}
//
//	@Override
//	public void onBackPressed() {
//		if (webView.canGoBack()) {
//			webView.goBack();
//		} else {
//			super.onBackPressed();
//		}
//	}
//
//	@Override
//	public String getTitle(String title) {
//		return title;
//	}
//}
