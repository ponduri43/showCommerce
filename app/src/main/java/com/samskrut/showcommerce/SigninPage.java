package com.samskrut.showcommerce;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.samskrut.showcommerce.database.DBAdapter;
import com.samskrut.showcommerce.threadpool.ImageLoader;
import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.SharedPrefencers;

public class SigninPage extends ActionBarActivity
{

	EditText username, password;
	Button signin;
	Context context;
	SharedPrefencers pref;
	String un, pwd;
	DBAdapter dba;

	protected ProgressBar pbDownloadProgress;

	TextView statustv;
	TextView downloadtv;

	private Handler guiThread;
	private Runnable updateTask;

	LinearLayout signin_ll;
	ActionBar mActionBar;

	public ProgressDialog dialog;

	int maxProgress;
	ImageLoader imageLoader;
	int customer_id;
	
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_signin_page);

		//mActionBar = getActionBar();
	//	mActionBar.setTitle(R.string.app_name);
	//	mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0099cc")));
		
		toolbar = (Toolbar) findViewById(R.id.app_bar);
		
		toolbar.setTitle(R.string.app_name);
		//toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0099cc")));
        setSupportActionBar(toolbar);

		toolbar.setLogo(R.drawable.ic_launcher);


		xmlfileinitilization();
		pref = new SharedPrefencers(context);
		imageLoader = new ImageLoader(context);

	}

	private void xmlfileinitilization()
	{
		context = this;
		dba = new DBAdapter(context);
		username = (EditText) findViewById(R.id.signin_username);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		password = (EditText) findViewById(R.id.signin_password);
		signin = (Button) findViewById(R.id.signin_button);
		// =========
		statustv = (TextView) findViewById(R.id.statustv);
		downloadtv = (TextView) findViewById(R.id.downloadtv);
		downloadtv.setText(R.string.downloadtv1);

		pbDownloadProgress = (ProgressBar) findViewById(R.id.pbDownloadProgress);
		pbDownloadProgress.setVisibility(View.GONE);

		pbDownloadProgress.setProgressDrawable(context.getResources().getDrawable(R.drawable.greenprogressbar));

		signin_ll = (LinearLayout) findViewById(R.id.signin_ll);
	}

	public void makeparsecall(View v)
	{

		InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(password.getWindowToken(), 0);



		// make parse call
		if (username.getText().toString().trim().length() > 0)
		{
			un = username.getText().toString().trim();

			if (password.getText().toString().trim().length() > 0)
			{
				// make parse call
				pwd = password.getText().toString().trim();

				//=================
				if(pref.getPrefe().contentEquals("alreadysignedin")){
				ArrayList<String> al=pref.getuserCredentails();
					if(un.contentEquals(al.get(0))&&pwd.contentEquals(al.get(1))){
						Toast.makeText(getApplicationContext(),"You have successfully logged in",Toast.LENGTH_LONG).show();
						pref.setloggedinstatus(true);


						Constants.customerid = pref.getCustomerId();
						Intent ii = new Intent(context, CategoryGridView.class);
						//ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(ii);
						finish();


					}else
					{
						Toast.makeText(getApplicationContext(),"Wrong credentials",Toast.LENGTH_LONG).show();

					}

					return;
				}
				//=================

				ParseUser.logInInBackground(un, pwd, new LogInCallback()
				{

					@Override
					public void done(ParseUser user, com.parse.ParseException e)
					{
						if (user != null)
						{

							if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
							{

								customer_id = user.getInt("CustomerId");
								Log.d("bis", "customerId=" + customer_id);
								new LongOperation().execute();
							}
							else
							{

								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
								alertDialogBuilder.setTitle("Storage Error");
								alertDialogBuilder.setMessage("Please check sdcard!");
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
							/*
							 * Intent nextPageIntent = new Intent(context,
							 * parseLongOperation.class);
							 * nextPageIntent.setFlags
							 * (Intent.FLAG_ACTIVITY_NEW_TASK |
							 * Intent.FLAG_ACTIVITY_CLEAR_TASK);
							 * startActivity(nextPageIntent); finish();
							 * overridePendingTransition(R.anim.trans_left_in,
							 * R.anim.trans_left_out);
							 */

						}
						else
						{
							Toast.makeText(context, "Incorrect Password. Please try again.", Toast.LENGTH_LONG).show();

							username.setText("");
							password.setText("");
							username.requestFocus();
						}
					}

				});
			}
			else
			{
				Toast.makeText(context, "Enter Password ", Toast.LENGTH_SHORT).show();
			}

		}
		else
		{
			Toast.makeText(context, "Enter User Name ", Toast.LENGTH_SHORT).show();
		}
	}

	// -================
	private void initThreading()
	{
		guiThread = new Handler();
		updateTask = new Runnable()
		{
			public void run()
			{

				downloadtv.setText(R.string.downloadtv3);
				pbDownloadProgress.setProgress(Constants.mProgressStatus);
				statustv.setText(" " + Constants.mProgressStatus + "/" + maxProgress);
				int percent = 0;
				try
				{
					percent = Constants.mProgressStatus * 100 / maxProgress;
					Log.d("bis", "% is " + percent);
				}
				catch (Exception e)
				{
					// TODO: handle exception
					percent = 100;
				}

				if (maxProgress == Constants.mProgressStatus || percent > 95)
				{
					// dialog.dismiss();
					downloadtv.setText(R.string.downloadtv4);

					pref.savePrefs(un, pwd, customer_id);
					Constants.customerid = customer_id;
					Intent ii = new Intent(context, CategoryGridView.class);
					//ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(ii);
					finish();

					// overridePendingTransition(R.anim.trans_left_in,
					// R.anim.trans_left_out);
				}
				else
				{

					guiThread.postDelayed(this, 3000);
				}

			}
		};

		guiThread.postDelayed(updateTask, 3000);
	}




	private class LongOperation extends AsyncTask<String, Integer, String>
	{

		List<ParseObject> categories_listOb;
		List<ParseObject> products_listOb;
		List<ParseObject> productsimages_listOb;
		List<ParseObject> productsimages_listOb2;

		ArrayList<String> caturls;
		ArrayList<String> productThimageurls;
		ArrayList<String> productimageurls;

		ArrayList<String> p360imageurl;

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			signin_ll.setVisibility(View.GONE);
			pbDownloadProgress.setVisibility(View.VISIBLE);
			downloadtv.setText(R.string.downloadtv2);
			caturls = new ArrayList<String>();
			productThimageurls = new ArrayList<String>();
			productimageurls = new ArrayList<String>();

			p360imageurl = new ArrayList();

		}

		@Override
		protected void onPostExecute(String result)
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			maxProgress = p360imageurl.size() + caturls.size() + productThimageurls.size() + productimageurls.size();
			pbDownloadProgress.setMax(maxProgress);

			for (int i = 0; i < p360imageurl.size(); i++)
			{
				imageLoader.DisplayImage(p360imageurl.get(i).toString(), "p360");

			}

			for (int i = 0; i < productThimageurls.size(); i++)
			{
				imageLoader.DisplayImage(productThimageurls.get(i).toString(), "productsTh");

			}

			for (int i = 0; i < caturls.size(); i++)
			{
				imageLoader.DisplayImage(caturls.get(i).toString(), "categories");

			}

			for (int i = 0; i < productimageurls.size(); i++)
			{
				imageLoader.DisplayImage(productimageurls.get(i).toString(), "products");

			}

			initThreading();

		}

		@Override
		protected String doInBackground(String... params)
		{

			// categories============

			try
			{
				// Locate the class table named "TestLimit" in Parse.com
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("categories");
				query.setLimit(1000);
				query.whereEqualTo("customer_id", customer_id);
				query.orderByDescending("createdAt");
				// Set the limit of objects to show

				categories_listOb = query.find();
				dba.open();
				dba.delteCategories();

				Log.d("bis", "query size" + categories_listOb.size());
				for (ParseObject singleob : categories_listOb)
				{
					int cid = singleob.getInt("category_id");
					String cname = singleob.getString("category_name");
					// String curl = singleob.getString("curl");

					final ParseFile pfile = (ParseFile) singleob.get("category_thumbnail");
					String[] strArray = pfile.getUrl().split("/");
					String curl = strArray[4].substring(42);

					dba.insertCategories(cid, cname, curl);
					caturls.add(pfile.getUrl());

				}

				dba.close();

				// categories_listOb.clear();
			}
			catch (ParseException e)
			{
				Log.e("bis", e.getMessage() + ", " + "at categoriece");

			}

			// products ===========================
			try
			{
				// Locate the class table named "TestLimit" in Parse.com
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("products");
				query.setLimit(1000);
				query.whereEqualTo("customer_id", customer_id);
				query.orderByDescending("createdAt");
				// Set the limit of objects to show

				products_listOb = query.find();
				dba.open();
				dba.delteproducts();
				Log.d("bis", "size=" + products_listOb.size());
				for (ParseObject singleob : products_listOb)
				{
					int pid = singleob.getInt("product_id");
					int cid = singleob.getInt("category_id");
					String pname = singleob.getString("product_name");
					String pinfo = singleob.getString("product_info");
					String pcost = singleob.getString("product_cost");
					// String purlTh = singleob.getString("product_thumbnail");
					final ParseFile pfileth = (ParseFile) singleob.get("product_thumbnail");

					String purlTh = "";
					if (pfileth != null)
					{

						productThimageurls.add(pfileth.getUrl());
						String[] strArrayth = pfileth.getUrl().split("/");
						purlTh = strArrayth[4].substring(42);
					}

					final ParseFile pfile = (ParseFile) singleob.get("product_panorama");

					int visit_status_count = 0;
					if (pfile != null)
					{

						p360imageurl.add(pfile.getUrl());
					}
					productThimageurls.add(pfileth.getUrl());

					dba.insertproducts(pid, cid, pname, pinfo, purlTh, visit_status_count, pcost);

				}
				dba.close();

			}
			catch (ParseException e)
			{
				Log.e("bis", e.getMessage() + ", " + "at categoriece");

			}

			// productimages =========== part 1
			try
			{
				// Locate the class table named "TestLimit" in Parse.com
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("productImages");
				query.setLimit(1000);
				query.whereEqualTo("customer_id", customer_id);
				query.orderByDescending("createdAt");
				// Set the limit of objects to show

				productsimages_listOb = query.find();
				dba.open();
				dba.delteproductImages();
				for (ParseObject singleob : productsimages_listOb)
				{
					int pid = singleob.getInt("product_id");

					// String ppurl = singleob.getString("product_image");

					final ParseFile pfileth = (ParseFile) singleob.get("product_image");

					String ppurl = "";

					if (pfileth != null)
					{
						productimageurls.add(pfileth.getUrl());
						String[] strArrayth = pfileth.getUrl().split("/");
						ppurl = strArrayth[4].substring(42);
					}

					dba.insertproductImages(pid, ppurl);

				}
				dba.close();

			}
			catch (ParseException e)
			{
				Log.e("bis", e.getMessage() + ", " + "at product images");

			}

			// pref.savePrefs(un, pwd);

			return "";
		}

	}

}
