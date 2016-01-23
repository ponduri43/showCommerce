package com.samskrut.showcommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class WebviewPanorama extends Fragment
{

	WebView webView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View view = inflater.inflate(R.layout.webview_activity, container, false);

		String url = "http://img1.cfcdn.com/emodel/vt_complete/554f46822262c/tour.html";
		final ProgressDialog dialog = ProgressDialog.show(getActivity(), null, "Loading - 0%", true);

		WebView webView = (WebView) view.findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient()
		{
			public void onProgressChanged(WebView view, int progress)
			{
				dialog.setMessage("Loading - " + progress + "%");
				if (progress == 100)
				{
					dialog.dismiss();
				}
			}
		});
		webView.loadUrl(url);

		return view;
	}

	private void setFullscreen(boolean fullscreen)
	{
		WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
		if (fullscreen)
		{
			attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		}
		else
		{
			attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
		}
		getActivity().getWindow().setAttributes(attrs);
	}
}