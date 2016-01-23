package com.samskrut.showcommerce;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.samskrut.showcommerce.database.DBAdapter;
import com.samskrut.showcommerce.database.ImportDatabase;
import com.samskrut.showcommerce.utilities.ConnectionDetector;
import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.SharedPrefencers;

public class SplashScreen extends Activity
{

	SharedPrefencers pref;
	Context context;
	ConnectionDetector cd;
	Intent nextPageIntent;

	public static InputStream databaseInputStream1;

	final DBAdapter dba = new DBAdapter(this);

	private Handler guiThread;
	private Runnable updateTask;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);

		setContentView(R.layout.activity_spalas_screen);
		context = this;
		pref = new SharedPrefencers(context);
		cd = new ConnectionDetector(context);

		new InsertTask().execute("");

	}

	// =================
	private class InsertTask extends AsyncTask<String, Void, Boolean>
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			// ////////////////////DATABASE STUFF/////////////////////

			try
			{
				File f = new File("/data/data/com.samskrut.showcommerce/databases/commerceDB.sql");
				if (f.exists())
				{

				}
				else
				{
					try
					{

						System.out.println("copying database .... ");
						Log.d("bis", "copying");

						databaseInputStream1 = getAssets().open("commerceDB.sql");

						dba.open();
						dba.close();

						ImportDatabase ipd = new ImportDatabase(databaseInputStream1);
						ipd.copyDataBase();

						// delete folder
						File folder = new File(Environment.getExternalStorageDirectory() + "/showcommerce");
						if (folder.exists())
						{
							folder.delete();
							Log.d("bis", "folder deleted");
						}

					}
					catch (IOException e)
					{
						System.out.println("copying database  failed at splash.... ");
						e.printStackTrace();
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();

			}

		}

		@Override
		protected Boolean doInBackground(String... params)
		{

			Boolean success = false;

			try
			{

				success = true;
			}
			catch (Exception e)
			{
				if (e.getMessage() != null)
					e.printStackTrace();
			}
			return success;
		}

		@Override
		protected void onPostExecute(Boolean success)
		{
			super.onPostExecute(success);

			initThreading();
			guiThread.postDelayed(updateTask, 3000);

		}

	}

	private void initThreading()
	{
		guiThread = new Handler();
		updateTask = new Runnable()
		{
			public void run()
			{

				authenticate();

			}
		};
	}

	// ================

	private void authenticate()
	{
		String status = pref.getPrefe();
		if (status.contentEquals("firstime"))
		{
			// check internet
			if (cd.isConnectingToInternet())
			{
				// call signin page

				gotoNextScreen("siginPage");

			}
			else
			{

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				alertDialogBuilder.setTitle("Connection Error");
				alertDialogBuilder.setMessage("Please check internet connection!");
				alertDialogBuilder.setCancelable(false);
				alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						// dismiss the dialog
						dialog.dismiss();
						finish();
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();

				alertDialog.show();

			}
		}
		else
		{
			// goto gallry

			Constants.customerid = pref.getCustomerId();

			Log.d("bis","pref.getloggedinstatus()= "+pref.getloggedinstatus());
			if (pref.getloggedinstatus()){
				gotoNextScreen("mainGallery");
			}else {
				gotoNextScreen("siginPage");
			}
		}
	}

	private void gotoNextScreen(final String pageTag)
	{

		if (pageTag.contentEquals("siginPage"))
		{
			nextPageIntent = new Intent(context, SigninPage.class);
		}
		else if (pageTag.contentEquals("mainGallery"))
		{
			nextPageIntent = new Intent(context, CategoryGridView.class);
		}
		//nextPageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(nextPageIntent);
		//overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		finish();
	}

}
