package com.example.mycommon.utils.web;

import com.example.mycommon.widget.WebViewController;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebWithControllBarUtil {
	public static void wrapWebView(final WebView webView, WebViewController ctrl) {
		setUpWebView(webView, ctrl);
		setBtnOnState(webView, ctrl);
	}

	private static void setBtnOnState(WebView webView, WebViewController ctrl) {
		ctrl.getBackBtn().setEnabled(webView.canGoBack());
		ctrl.getForwardBtn().setEnabled(webView.canGoForward());
		int progress = webView.getProgress();
		boolean finish = progress == 100;
		ctrl.getStopBtn().setVisibility(finish ? View.GONE : View.VISIBLE);
		ctrl.getRefreshBtn().setVisibility(finish ? View.VISIBLE : View.GONE);

		if (progress != 100) {
			if (ctrl.getProgressBar() != null) {
				ctrl.getProgressBar().setVisibility(View.VISIBLE);
				ctrl.getProgressBar().setMax(100);
				ctrl.getProgressBar().setProgress(progress);
			}
		} else {
			if (ctrl.getProgressBar() != null) {
				ctrl.getProgressBar().setVisibility(View.GONE);
			}
		}
	}

	private static void setUpWebView(final WebView webView,
			final WebViewController ctrl) {

		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setDisplayZoomControls(false);
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setAllowFileAccess(true);
		settings.setPluginState(PluginState.ON);
		settings.setAppCacheEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onReceivedTitle(WebView view, String title) {
				Context ctx = view.getContext();
//				if (ctx instanceof BaseActionBarActivity) {
//					BaseActionBarActivity act = (BaseActionBarActivity) ctx;
//					act.getSupportActionBar().setTitle(title);
//				}
				super.onReceivedTitle(view, title);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				setBtnOnState(webView, ctrl);
			}
		});
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				Toast.makeText(
						view.getContext(),
						"出错啦\r\n" + failingUrl + "\r\n" + errorCode + "\r\n"
								+ description, Toast.LENGTH_LONG).show();
			}
		});

		webView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				Log.i("tag", "url=" + url);
				Log.i("tag", "userAgent=" + userAgent);
				Log.i("tag", "contentDisposition=" + contentDisposition);
				Log.i("tag", "mimetype=" + mimetype);
				Log.i("tag", "contentLength=" + contentLength);
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				webView.getContext().startActivity(intent);
			}
		});

	}
	 
}
