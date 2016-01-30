package com.samskrut.showcommerce;

import java.io.File;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samskrut.showcommerce.database.DBAdapter;
import com.samskrut.showcommerce.panorama.PanoramaActivity;
import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.InfoTracker;

public class ProductOverView extends ActionBarActivity
{
	// TextView tv;
	String text;

	DBAdapter dba;
	// public static LinearLayout navi;
	static View decorView;
	// static Boolean fine = true;
	ActionBar mActionBar;
	Context context;

	static Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_product_over_view);

		// mActionBar = getActionBar();
		// mActionBar.setBackgroundDrawable(new
		// ColorDrawable(Color.parseColor("#ff0099cc")));
		// mActionBar.setDisplayShowHomeEnabled(true);
		// -- mActionBar.setDisplayHomeAsUpEnabled(true);
		// mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
		// ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
		// mActionBar.setIcon(R.drawable.ic_action_back);
		// mActionBar.setTitle(Constants.pname);

		toolbar = (Toolbar) findViewById(R.id.app_bar);

		toolbar.setTitle(Constants.pname);
		//toolbar.setSubtitle("Rs." + Constants.pcost);
		// toolbar.setBackgroundDrawable(new
		// ColorDrawable(Color.parseColor("#ff0099cc")));
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowHomeEnabled(false);

		toolbar.setNavigationIcon(R.drawable.ic_action_back);
		//toolbar.setLogo(R.drawable.ic_launcher);
		initializeTabBarUIElements();

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();

		Fragment fr = new PicturesFrgment();
		fragmentTransaction.replace(R.id.myfrag, fr);
		fragmentTransaction.commit();

	}

	public void initializeTabBarUIElements()
	{
		context = this;
		decorView = getWindow().getDecorView();
		dba = new DBAdapter(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.product_overview, menu);

		getMenuInflater().inflate(R.menu.product_overview, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

	

		Fragment fr = null;
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();

		Fragment currentfragment = fm.findFragmentById(R.id.myfrag);
		switch (item.getItemId())
		{

		case R.id.video_mi:
			if (currentfragment instanceof VideoFragment ){
				Toast.makeText(context, R.string.msg, Toast.LENGTH_SHORT).show();
			}else {
				fr = new VideoFragment();
				fragmentTransaction.replace(R.id.myfrag, fr);

				fragmentTransaction.commit();
			}
			return true;
		case R.id.rotaion_mi:
			
			//36 images swipe code
			
			// fr = new RotationFragment();
			// fragmentTransaction.replace(R.id.myfrag, fr);
			// fragmentTransaction.commit();

			// File filw = new File(Environment.getExternalStorageDirectory() +
			// "/showcommerce/p" + Constants.pid);
			// if (folder2.exists())
			// {

			// }
			// String imageInSD =
			// Environment.getExternalStorageDirectory().toString() +
			// "/showcommerce/products/" + filename + "";
           
			// panorama code
			
			/*final File file = new File(Environment.getExternalStorageDirectory() + "/showcommerce/p360/", "p360" + Constants.pid + ".jpg");

			Log.d("bis", "path is " + file.getAbsolutePath());

			if (file.exists())
			{

				Intent ii = new Intent(this, PanoramaActivity.class);
				ii.setData(Uri.fromFile(file));
				startActivity(ii);

			}// finish();
			else
			{
				Toast.makeText(context, "Image Not Availble", 1000).show();
			}*/
			
			// webview code
			
			//Intent ii = new Intent(this, WebviewPanorama.class);
			//startActivity(ii);
			
			fr = new WebviewPanorama();
			fragmentTransaction.replace(R.id.myfrag, fr);

			fragmentTransaction.commit();


			return true;
		case R.id.swipe_mi:


			if (currentfragment instanceof PicturesFrgment)
			{
				Toast.makeText(context, R.string.msg, Toast.LENGTH_SHORT).show();
			}else {
				fr = new PicturesFrgment();
				fragmentTransaction.replace(R.id.myfrag, fr);

				fragmentTransaction.commit();
			}

			return true;
		case R.id.survey_mi:
			if (InfoTracker.usersessionid > 0)
			{
				storeSessionForThisProduct("survey");

			}
			else
			{
				Toast.makeText(context, R.string.casual_mode_toast, Toast.LENGTH_SHORT).show();

			}

			return true;
		case android.R.id.home:
			if (InfoTracker.cid != 0 && InfoTracker.pid != 0)
			{
				storeSessionForThisProduct("home");
			}
			else
			{
				finish();

			}
			return true;

			case R.id.product_info_mi:

				showPinfo();
				return true;
		default:
			Log.d("bis", "default");

			break;

		}

		return true;
	}

	private  void showPinfo(){
		// Create custom dialog object
		final Dialog dialog = new Dialog(ProductOverView.this);
		// Include dialog.xml file
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		Display display =((WindowManager) getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		int height=display.getHeight();
		dialog.setContentView(R.layout.product_info_layout);
		dialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 5);
		dialog.setCancelable(true);


		// Set dialog title
		//dialog.setTitle("short info");

		// set values for custom dialog components - text, image and button
		TextView pi_name = (TextView) dialog.findViewById(R.id.pi_name);
		pi_name.setText(Constants.pname);
		TextView pi_body = (TextView) dialog.findViewById(R.id.pi_body);
		pi_body.setText(Constants.pname);
		TextView pi_cost = (TextView) dialog.findViewById(R.id.pi_cost);
		pi_cost.setText("RS. " + Constants.pcost);
		ImageView piclose = (ImageView) dialog.findViewById(R.id.pi_close);
		piclose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();

		/*Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
		// if decline button is clicked, close the custom dialog
		declineButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Close dialog
				dialog.dismiss();
			}
		});*/
	}
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		storeSessionForThisProduct("back");
	}

	private void storeSessionForThisProduct(String actionMsg)
	{

		if (InfoTracker.cid != 0 && InfoTracker.pid != 0)
		{

			dba.open();
			Cursor mcursor2 = dba.checkduplicateproductsforbuyer(InfoTracker.pid, InfoTracker.tableusersessionid.trim());

			if (mcursor2.getCount() > 0)
			{
				// ========= updtaing with new existing values

				// videoid

				if (InfoTracker.videoid == null)
				{
					InfoTracker.videoid = mcursor2.getString(3);
				}

				// Image id

				if (InfoTracker.imageid == null)
				{
					InfoTracker.imageid = mcursor2.getString(4);
				}

				// rotaionflag

				if (InfoTracker.view360 == 0)
				{
					InfoTracker.view360 = mcursor2.getInt(5);
				}

				// pname

				if (InfoTracker.pname == null)
				{
					InfoTracker.pname = mcursor2.getString(10);
				}

				// time
				int old_millis = mcursor2.getInt(6);
				int millis = (int) (System.currentTimeMillis() - InfoTracker.time);
				int new_millis = old_millis + millis;

				// no off time
				int count = mcursor2.getInt(11);

				// =========

				dba.updatetbuyer(InfoTracker.cid, InfoTracker.pid, InfoTracker.videoid, InfoTracker.imageid, InfoTracker.view360, new_millis, " User", 0,
						InfoTracker.tableusersessionid.trim(), InfoTracker.pname, count + 1);

				// Log.d("bis", "id= " + InfoTracker.tableusersessionid.trim() +
				// ", updating cid and pid " + InfoTracker.cid + " ," +
				// InfoTracker.pid);
			}
			else
			{

				if (InfoTracker.videoid == null)
				{
					InfoTracker.videoid = "";
				}

				if (InfoTracker.imageid == null)
				{
					InfoTracker.imageid = "";
				}

				if (InfoTracker.pname == null)
				{
					InfoTracker.pname = "";
				}

				int millis = (int) (System.currentTimeMillis() - InfoTracker.time);

				dba.insertbuyer(InfoTracker.cid, InfoTracker.pid, InfoTracker.videoid, InfoTracker.imageid, InfoTracker.view360, millis, " User", 0,
						InfoTracker.tableusersessionid.trim(), InfoTracker.pname, 1, "");
				// Log.d("bis", "id= " + InfoTracker.tableusersessionid.trim() +
				// ", inserting  cid and pid " + InfoTracker.cid + " ," +
				// InfoTracker.pid);
			}

			dba.close();

			if (actionMsg.contentEquals("home"))
			{
				Constants.pid = 0;
				Constants.pcost = "";
				Constants.pname = "";
				finish();
			}
			else if (actionMsg.contentEquals("back"))
			{
				Constants.pid = 0;
				Constants.pcost = "";
				Constants.pname = "";
				finish();

			}
			else if (actionMsg.contentEquals("survey"))
			{
				Intent intent = new Intent(this, QuickSurvey.class);
				startActivity(intent);
				//finish();

			}

		}

	}
}
