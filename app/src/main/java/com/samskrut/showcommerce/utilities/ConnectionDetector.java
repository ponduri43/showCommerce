package com.samskrut.showcommerce.utilities;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector
{

	private Context _context;
	int statuscode = 0;

	public ConnectionDetector(Context context)
	{
		this._context = context;
	}

	public boolean isConnectingToInternet()
	{
		ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null)
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}

		}
		return false;
	}

	public int getStatusCode()
	{

		try
		{
			URL url = new URL("https://www.google.co.in/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			statuscode = conn.getResponseCode();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return statuscode;
	}
}