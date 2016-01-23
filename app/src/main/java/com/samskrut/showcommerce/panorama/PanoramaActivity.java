package com.samskrut.showcommerce.panorama;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.panorama.PanoramaClient;
import com.google.android.gms.panorama.PanoramaClient.OnPanoramaInfoLoadedListener;
import com.samskrut.showcommerce.R;
import com.samskrut.showcommerce.utilities.Constants;

public class PanoramaActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, OnPanoramaInfoLoadedListener
{

	public static final String TAG = PanoramaActivity.class.getSimpleName();

	private PanoramaClient mClient;

	int count = 0;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_panorama_sample);
		mClient = new PanoramaClient(this, this, this);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		mClient.connect();
	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		// Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" +
		// R.raw.glass);

		// final File file = new File(Environment.getExternalStorageDirectory(),
		// "pano1.jpg");
		File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "showcommerce" + File.separator + "p360" + File.separator + "p360"
				+ Constants.pid + ".jpg");
		Log.d("bis", "before uri " + file.getAbsolutePath());
		Uri uri = Uri.fromFile(file);
		Log.d("bis", "after uri " + uri.getPath());

		mClient.loadPanoramaInfo(this, uri);

	}

	@Override
	public void onPanoramaInfoLoaded(ConnectionResult result, Intent viewerIntent)
	{

		if (result.isSuccess() && count < 2)
		{

			Log.i(TAG, "found viewerIntent: " + viewerIntent);
			Log.d("bis", "count= " + count);
			if (viewerIntent != null)
			{
				startActivity(viewerIntent);

			}
		}
		else
		{
			Log.e(TAG, "error: " + result);
		}
	}

	@Override
	public void onDisconnected()
	{
		// Do nothing
	}

	@Override
	public void onConnectionFailed(ConnectionResult status)
	{
		Log.e(TAG, "connection failed: " + status);
		// TODO fill in
	}

	@Override
	public void onStop()
	{
		super.onStop();
		mClient.disconnect();
	}

	@Override
	protected void onRestart()
	{ // TODO Auto-generated method stub
		super.onRestart();
		Log.d("bis", "finshed at on resume");
		count = 2;

		finish();
	}

}
