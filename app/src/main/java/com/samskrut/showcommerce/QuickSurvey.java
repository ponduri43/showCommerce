package com.samskrut.showcommerce;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.path.android.jobqueue.JobManager;
import com.samskrut.showcommerce.database.DBAdapter;
import com.samskrut.showcommerce.database.buyerClass;
import com.samskrut.showcommerce.events.RertyCountPojo;
import com.samskrut.showcommerce.events.SyncDataPojoSaved;
import com.samskrut.showcommerce.events.SyncDataPojoSynFailed;
import com.samskrut.showcommerce.events.SyncDataPojoSynced;
import com.samskrut.showcommerce.jobs.SyncDataJob;
import com.samskrut.showcommerce.utilities.ConnectionDetector;
import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.HttpRequest;
import com.samskrut.showcommerce.utilities.InfoTracker;
import com.samskrut.showcommerce.utilities.SharedPrefencers;
import com.samskrut.showcommerce.utilities.TYPEFACE;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;

public class QuickSurvey extends ActionBarActivity
{

	DBAdapter dba = new DBAdapter(this);
	SeekBar volumeControl;
	LinearLayout ll;
	EditText bname, bphone, bmail;
	RadioGroup ageRGB, genderRGB;
	Intent intent;
	ActionBar mActionBar;
	Context context;
	CheckBox checksms, checkmail;

	JobManager jobManager;
	int jm_count;

	// -------------------------------
	TextView product_pruchase_count_displytv;
	String[] productInterest, productInterestId;

	private LayoutInflater linfPI;
	private LinearLayout lnrProductInterest;
	private Dialog diaPI;

	int productInterestCount = 0;

	ArrayList<String> arrayProductInterest, arrayProductInterestID;
	ArrayList<String> cummlative_arrayProductInterestID;
	ArrayList<String> doesnotPurchasedItems;

	// -------------------------
	int purchasecount = 0;

	Cursor mCursor_retriveByerInfobyseesionid;

	Boolean productIntrestClikedorNot = false;
	
	SharedPrefencers pref;
	
	Toolbar toolbar;

	//=========
	String response;
    ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);

		setContentView(R.layout.quick_survey);

	//	mActionBar = getActionBar();
	//	mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0099cc")));
	//	mActionBar.setDisplayShowHomeEnabled(true);
		//-- mActionBar.setDisplayHomeAsUpEnabled(true);
		//mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
		//mActionBar.setIcon(R.drawable.ic_action_back);
		//mActionBar.setTitle("Quick Survey");

		
		toolbar = (Toolbar) findViewById(R.id.app_bar);

		toolbar.setTitle("Quick Survey");
	
		//toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0099cc")));
		setSupportActionBar(toolbar);
		
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		toolbar.setNavigationIcon(R.drawable.ic_action_back); 
		toolbar.setLogo(R.drawable.ic_launcher);

		
		intilizexmlvaribles();

		// get db data

		// --------------------------------------------------

		dba.open();
		if (InfoTracker.tableusersessionid != null)
		{
			dba.open();
			mCursor_retriveByerInfobyseesionid = dba.retriveByerInfobyseesionid(InfoTracker.tableusersessionid);
			dba.close();

			if (mCursor_retriveByerInfobyseesionid.getCount() > 0)
			{

				mCursor_retriveByerInfobyseesionid.moveToFirst();

				productInterest = new String[mCursor_retriveByerInfobyseesionid.getCount()];
				productInterestId = new String[mCursor_retriveByerInfobyseesionid.getCount()];
				product_pruchase_count_displytv.setText("Select Products Purchased");

				int count = 0;

				do
				{
					String productName = mCursor_retriveByerInfobyseesionid.getString(0);
					String idproduct = Integer.toString(mCursor_retriveByerInfobyseesionid.getInt(1));

					productInterest[count] = productName;
					productInterestId[count] = idproduct.trim();
					cummlative_arrayProductInterestID.add(idproduct.trim());
					count++;

				} while (mCursor_retriveByerInfobyseesionid.moveToNext());

			}
			else
			{
				productInterest = new String[] {};
				product_pruchase_count_displytv.setText("Products Not Found");
			}

			mCursor_retriveByerInfobyseesionid.close();

			//

			ll = (LinearLayout) findViewById(R.id.ll2);
			ll.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// addItemsOnSpinner2();
					if (mCursor_retriveByerInfobyseesionid.getCount() > 0)
					{

						showDialogProdctInterest();
					}

				}
			});

		}
	}

	// ----------------- PRODUCT INTEREST START
	// --------------------------------------

	private void showDialogProdctInterest()
	{

		productInterestCount = productInterest.length;

		diaPI = new Dialog(QuickSurvey.this);
		diaPI.getWindow();
		diaPI.requestWindowFeature(Window.FEATURE_NO_TITLE);

		diaPI.setContentView(R.layout.list_wcheck);
		diaPI.setCancelable(false);

		lnrProductInterest = (LinearLayout) diaPI.findViewById(R.id.lnrList);
		lnrProductInterest.removeAllViews();

		ImageButton list_wcheck_btnDone = (ImageButton) diaPI.findViewById(R.id.list_wcheck_btnDone);
		list_wcheck_btnDone.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				diaPI.dismiss();

				if (mCursor_retriveByerInfobyseesionid.getCount() > 0)
				{
					if (purchasecount == 1)
					{
						product_pruchase_count_displytv.setText(purchasecount + " Product Purchased");
					}
					else
					{
						product_pruchase_count_displytv.setText(purchasecount + " Products Purchased");
					}

				}

				if (arrayProductInterestID.size() > 0)
				{
					doesnotPurchasedItems.clear();

					for (int j = 0; j < cummlative_arrayProductInterestID.size(); j++)
					{

						if (arrayProductInterestID.contains(cummlative_arrayProductInterestID.get(j)))
						{
							Log.d("bis", "intrested list item" + cummlative_arrayProductInterestID.get(j));
						}
						else
						{
							if (!doesnotPurchasedItems.contains(cummlative_arrayProductInterestID.get(j)))
							{
								doesnotPurchasedItems.add(cummlative_arrayProductInterestID.get(j));
								Log.d("bis", "added in the  intrested list item" + cummlative_arrayProductInterestID.get(j));

							}
							else
							{
								Log.d("bis", "already added in the  intrested list item");
							}
						}

					}

				}
				else
				{
					doesnotPurchasedItems.addAll(cummlative_arrayProductInterestID);
				}
				int total = arrayProductInterestID.size() + doesnotPurchasedItems.size();
				//Log.d("bis", "like" + arrayProductInterestID.size());
				//Log.d("bis", "does not like" + doesnotPurchasedItems.size());
				//Log.d("bis", "does not like" + cummlative_arrayProductInterestID.size());

			}
		});

		for (int i = 0; i < productInterest.length; i++)
		{

			rowProductInterest(i, productInterest[i], productInterestId[i]);

		}

		diaPI.show();
	}

	public void rowProductInterest(final int idx, final String val, final String productInterestIdVal)
	{

		final View vPTF = linfPI.inflate(R.layout.list_row_wcheck, null);

		TextView tv = (TextView) vPTF.findViewById(R.id.row_val);
		tv.setTypeface(TYPEFACE.Roboto_Regular(this));
		tv.setText(val);

		LinearLayout list_row_sep = (LinearLayout) vPTF.findViewById(R.id.list_row_sep);

		if (productInterestCount - 1 == idx)
		{
			list_row_sep.setVisibility(View.GONE);
		}
		else
		{
			list_row_sep.setVisibility(View.VISIBLE);
		}

		final CheckBox wcheck_chkbox = (CheckBox) vPTF.findViewById(R.id.wcheck_chkbox);
		wcheck_chkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                productIntrestClikedorNot = true;

                if (isChecked) {

                    if (!arrayProductInterest.contains(val)) {
                        arrayProductInterest.add(val);
                        arrayProductInterestID.add(productInterestIdVal);

                        purchasecount++;
                        dba.open();
                        Cursor mCursor = dba.updateProductPurchaseStatus(InfoTracker.tableusersessionid, Integer.parseInt(productInterestIdVal.trim()), 1);

                        dba.close();
                    }

                } else {

                    arrayProductInterest.remove(val);
                    arrayProductInterestID.remove(productInterestIdVal);
                    purchasecount--;
                    dba.open();
                    Cursor mCursor = dba.updateProductPurchaseStatus(InfoTracker.tableusersessionid, Integer.parseInt(productInterestIdVal.trim()), 0);
                    dba.close();

                }

                // Log.d("bis", " check details" + arrayProductInterest + ";" +
                // arrayProductInterestID);

                if (arrayProductInterestID.size() == 0) {

                    Log.d("bis", "total intrest items has been deleted");
                    doesnotPurchasedItems.clear();
                    arrayProductInterest.clear();
                    arrayProductInterestID.clear();
                    // productIntrestClikedorNot = false;
                }

            }

        });

		if (arrayProductInterest.contains(val))
		{
			wcheck_chkbox.setChecked(true);
		}

		vPTF.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				if (wcheck_chkbox.isChecked())
				{
					wcheck_chkbox.setChecked(false);
				}
				else
				{
					wcheck_chkbox.setChecked(true);
				}

			}
		});

		lnrProductInterest.addView(vPTF);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.quick_servey_actionbar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent intent;
		switch (item.getItemId())
		{
		case android.R.id.home:
			/*intent = new Intent(getApplicationContext(), CategoryGridView.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);*/
			finish();

		}

		return true;
	}

	private void intilizexmlvaribles()
	{

		context = this;

		product_pruchase_count_displytv = (TextView) findViewById(R.id.signup_txtTradeShow);

		ll = (LinearLayout) findViewById(R.id.ll2);
		bname = (EditText) findViewById(R.id.bname);
		bname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

		bphone = (EditText) findViewById(R.id.bphone);
		bmail = (EditText) findViewById(R.id.bmail);
		ageRGB = (RadioGroup) findViewById(R.id.radioGroup);
		genderRGB = (RadioGroup) findViewById(R.id.radioGroupGender);

		checksms = (CheckBox) findViewById(R.id.checksms);

		checkmail = (CheckBox) findViewById(R.id.checkmail);

		// job
		jobManager = ApplicationConstant.getInstance().getJobManager();
		EventBus.getDefault().register(this);
		// dailog
		linfPI = (LayoutInflater) getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
		linfPI = LayoutInflater.from(QuickSurvey.this);

		arrayProductInterest = new ArrayList<String>();
		arrayProductInterestID = new ArrayList<String>();
		doesnotPurchasedItems = new ArrayList<String>();
		cummlative_arrayProductInterestID = new ArrayList<String>();
		
		pref = new SharedPrefencers(context);

        cd=new ConnectionDetector(QuickSurvey.this);

	}

	public void updateBuyer(View v)
	{


		String buyerName = bname.getText().toString();
		String buyerPhone = bphone.getText().toString();
		String buyerMail = bmail.getText().toString();
		int r1 = ageRGB.getCheckedRadioButtonId();
		RadioButton buyerAge = (RadioButton) findViewById(r1);
		int r2 = genderRGB.getCheckedRadioButtonId();
		RadioButton buyerGender = (RadioButton) findViewById(r2);

		dba.open();

		dba.updateBuyerprofile(InfoTracker.tableusersessionid, buyerName, buyerPhone, buyerMail, buyerAge.getText().toString(), buyerGender.getText().toString(), "android");

		dba.close();

		if (checkmail.isChecked() || checksms.isChecked())
		{

			final ParseObject po = new ParseObject("MandrillEmaliList");

			po.put("usersessionid", InfoTracker.tableusersessionid);
			po.put("buyerName", buyerName);

			if (productIntrestClikedorNot)
			{
				ArrayList<String> unique = removeDuplicates(doesnotPurchasedItems);

				po.put("pids", unique);

			}
			else
			{
				po.put("pids", cummlative_arrayProductInterestID);
			}

			po.put("buyerPhone", buyerPhone);
			po.put("buyerMail", buyerMail);
			po.put("CustomerId", pref.getCustomerId());

			po.saveInBackground(new SaveCallback()
			{

				@Override
				public void done(ParseException e)
				{
					if (e == null)
					{
                        Log.d("bis"," parse objecct is geting inserted");
					}

				}
			});

		}

		// postDataJQ(InfoTracker.tableusersessionid);
		//postDataJQForAllRecord();




            new postASYNC().execute("");

	}

    private class postASYNC extends AsyncTask<String, Void, String> {

        ProgressDialog  progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=new ProgressDialog(QuickSurvey.this);
            progressDialog.setMessage("Syncing to cloude");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            if (cd.isConnectingToInternet())
            postDataJQForAllRecord();

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();

            InfoTracker.totalresetValues();
            InfoTracker.usersessionid = 0;
            InfoTracker.tableusersessionid = null;

            intent = new Intent(getApplicationContext(), CategoryGridView.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }
    }

	static ArrayList<String> removeDuplicates(ArrayList<String> list)
	{

		// Store unique items in result.
		ArrayList<String> result = new ArrayList<String>();

		// Record encountered Strings in HashSet.
		HashSet<String> set = new HashSet<String>();

		// Loop over argument list.
		for (String item : list)
		{

			// If String is not in set, add it to the list and the set.
			if (!set.contains(item))
			{
				result.add(item);
				set.add(item);
			}
		}
		return result;
	}

	public void postData()
	{
		dba.open();
		Cursor mcursor2 = dba.retrive();
		dba.close();

		if (mcursor2.moveToFirst())
		{
			do
			{

				/*
				 * Log.d("bis", "id=" + mcursor2.getInt(0)); Log.d("bis", "cid="
				 * + mcursor2.getInt(1)); Log.d("bis", "pid=" +
				 * mcursor2.getInt(2)); Log.d("bis", "vid=" +
				 * mcursor2.getString(3)); Log.d("bis", "imid=" +
				 * mcursor2.getString(4)); Log.d("bis", "rflag=" +
				 * mcursor2.getInt(5)); Log.d("bis", "time=" +
				 * mcursor2.getInt(6)); Log.d("bis", "bname=" +
				 * mcursor2.getString(7)); Log.d("bis", "pstatus=" +
				 * mcursor2.getInt(8)); Log.d("bis", "ssid=" +
				 * mcursor2.getString(9)); Log.d("bis", "pname=" +
				 * mcursor2.getString(10)); Log.d("bis", "count=" +
				 * mcursor2.getInt(11)); Log.d("bis", "infostatus=" +
				 * mcursor2.getString(12)); Log.d("bis", "phone=" +
				 * mcursor2.getString(13)); Log.d("bis", "mail=" +
				 * mcursor2.getString(14)); Log.d("bis", "age=" +
				 * mcursor2.getString(15)); Log.d("bis", "gender=" +
				 * mcursor2.getString(16) + "\n\n");
				 */

				String cid = "" + mcursor2.getInt(1);
				String pid = "" + mcursor2.getInt(2);
				int tempid = mcursor2.getInt(2);
				String vid = "" + mcursor2.getString(3);
				String imid = "" + mcursor2.getString(4);
				String rfalg = "" + mcursor2.getInt(5);

				long millis = mcursor2.getInt(6);

				long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
				millis -= TimeUnit.MINUTES.toMillis(minutes);
				long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
				String time;
				if (seconds > 15)
				{
					minutes++;
					time = minutes + " MIN : " + 0 + "SEC";
				}
				else
				{
					time = minutes + " MIN : " + seconds + "SEC";
				}

				String bname = "" + mcursor2.getString(7);
				String pstatus = "" + mcursor2.getInt(8);
				String sid = mcursor2.getString(9);

				String pname = "" + mcursor2.getString(10);

				String count = mcursor2.getInt(11) + "";
				String infostatus = mcursor2.getString(12);
				String phone = mcursor2.getString(13);
				String mail = mcursor2.getString(14);
				String age = mcursor2.getString(15);
				String gender = mcursor2.getString(16);

				String fullUrl = "https://docs.google.com/forms/d/1KMT-N4qWGfQxv_BtxWXKIZ05FPsRXNUs6PvynGPxcDg/formResponse";

				HttpRequest mReq = new HttpRequest();

				String data = "entry.524215702=" + URLEncoder.encode(cid) + "&" + "entry.1773214368=" + URLEncoder.encode(pid) + "&" + "entry.1667098360=" + URLEncoder.encode(vid)
						+ "&" + "entry.17553355=" + URLEncoder.encode(imid) + "&" + "entry.1827591331=" + URLEncoder.encode(rfalg) + "&" + "entry.258560354="
						+ URLEncoder.encode(time) + "&" + "entry.743090124=" + URLEncoder.encode(bname) + "&" + "entry.1853237792=" + URLEncoder.encode(pstatus) + "&"
						+ "entry.1152173085=" + URLEncoder.encode(sid) + "&" + "entry.679729860=" + URLEncoder.encode(pname) + "&" + "entry.813265161=" + URLEncoder.encode(count)
						+ "&" + "entry.1793021485=" + URLEncoder.encode(phone) + "&" + "entry.1108043609=" + URLEncoder.encode(mail) + "&" + "entry.1959002797="
						+ URLEncoder.encode(age) + "&" + "entry.1145045882=" + URLEncoder.encode(gender);

				String response = mReq.sendPost(fullUrl, data);

				if (mReq.statusCode == 200)
				{
					dba.open();
					dba.delete_buyer(sid, tempid);
					Log.d("bis", "delete ack " + sid + " " + tempid);
					dba.close();

				}

			} while (mcursor2.moveToNext());
		}

	}

	// greenrobot ================================
	// ========for current record record===
	public void postDataJQ(String ssid)
	{
		dba.open();
		Cursor mcursor2 = dba.retriveOnlyOne(ssid);
		dba.close();

		do
		{

			String cid = "" + mcursor2.getInt(1);
			String pid = "" + mcursor2.getInt(2);
			int tempid = mcursor2.getInt(2);
			String vid = "" + mcursor2.getString(3);

			String imid = "";
			if (mcursor2.getString(4).contentEquals(""))
			{
				imid = "0";
			}
			else
			{
				imid = "1";
			}

			String rflag = "" + mcursor2.getInt(5);

			long millis = mcursor2.getInt(6);

			long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
			String time = "" + seconds;

			String bname = "" + mcursor2.getString(7);
			String pstatus = "" + mcursor2.getInt(8);
			String sid = mcursor2.getString(9);

			String pname = "" + mcursor2.getString(10);

			String count = mcursor2.getInt(11) + "";
			String infostatus = mcursor2.getString(12);
			String phone = mcursor2.getString(13);
			String mail = mcursor2.getString(14);
			String age = mcursor2.getString(15);
			String gender = mcursor2.getString(16);

			buyerClass bc = new buyerClass();

			bc.setCid(cid);
			bc.setPid(pid);
			bc.setVid(vid);
			bc.setImid(imid);
			bc.setRfalg(rflag);
			bc.setTime(time);
			bc.setBname(bname);
			bc.setPstatus(pstatus);
			bc.setSid(sid);
			bc.setPname(pname);
			bc.setCount(count);
			bc.setInfostatus(infostatus);
			bc.setPhone(phone);
			bc.setMail(mail);
			bc.setAge(age);
			bc.setGender(gender);

			jobManager.addJobInBackground(new SyncDataJob(bc));

		} while (mcursor2.moveToNext());

		// calling for pending record

		Log.d("bis", "current records updated");
		postDataJQForAllRecord();
	}

	// for pending records
	public void postDataJQForAllRecord() {
        dba.open();
        Cursor mcursor2 = dba.retrive();
        dba.close();
        if (mcursor2.getCount() > 0) {
            do {

                String cid = "" + mcursor2.getInt(1);
                String pid = "" + mcursor2.getInt(2);
                int tempid = mcursor2.getInt(2);
                String vid = "" + mcursor2.getString(3);

                String imid = "";
                if (mcursor2.getString(4).contentEquals("")) {
                    imid = "0";
                } else {
                    imid = "1";
                }

                String rflag = "" + mcursor2.getInt(5);

                long millis = mcursor2.getInt(6);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
                String time = "" + seconds;

                String bname = "" + mcursor2.getString(7);
                String pstatus = "" + mcursor2.getInt(8);
                String sid = mcursor2.getString(9);

                String pname = "" + mcursor2.getString(10);

                String count = mcursor2.getInt(11) + "";
                String infostatus = mcursor2.getString(12);
                String phone = mcursor2.getString(13);
                String mail = mcursor2.getString(14);
                String age = mcursor2.getString(15);
                String gender = mcursor2.getString(16);

                buyerClass bc = new buyerClass();

                bc.setCid(cid);
                bc.setPid(pid);
                bc.setVid(vid);
                bc.setImid(imid);
                bc.setRfalg(rflag);
                bc.setTime(time);
                bc.setBname(bname);
                bc.setPstatus(pstatus);
                bc.setSid(sid);
                bc.setPname(pname);
                bc.setCount(count);
                bc.setInfostatus(infostatus);
                bc.setPhone(phone);
                bc.setMail(mail);
                bc.setAge(age);
                bc.setGender(gender);

                //jobManager.addJobInBackground(new SyncDataJob(bc));

                postData(bc);

            } while (mcursor2.moveToNext());
            Log.d("bis", "pending records updated");
        }
    }

	// ========================EventBus Callbacks

	public void onEventMainThread(SyncDataPojoSaved ignored)
	{

		// Toast.makeText(getApplicationContext(), " added!",
		// Toast.LENGTH_LONG).show();
	}

	public void onEventMainThread(SyncDataPojoSynced ignored)
	{

		// Toast.makeText(getApplicationContext(), "Synced!",
		// Toast.LENGTH_LONG).show();
	}

	public void onEventMainThread(SyncDataPojoSynFailed ignored)
	{

		// Toast.makeText(getApplicationContext(), "Sync Failed!",
		// Toast.LENGTH_LONG).show();
	}

	public void onEventMainThread(RertyCountPojo ignored)
	{

		jm_count = jm_count + ignored.getCount();
		// tv.setText("Retry Counts: "+" "+String.valueOf(count));

		// Toast.makeText(getApplicationContext(), "Retry==>" +
		// Integer.toString(jm_count), Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		try
		{
			EventBus.getDefault().unregister(this);

		}
		catch (Throwable t)
		{
			// this may crash if registration did not go through. just be safe
		}
	}


	private void postData(buyerClass bc){


		String fullUrl = "https://docs.google.com/forms/d/1KMT-N4qWGfQxv_BtxWXKIZ05FPsRXNUs6PvynGPxcDg/formResponse";

		// ProTip:Always Use Volley,Retrofit with okhttp
		HttpRequest mReq = new HttpRequest();
		String ccid= Constants.customerid + "";
		Log.d("bis", "ccid"+ccid);

		String data = "entry.524215702=" + URLEncoder.encode(bc.getCid()) + "&" + "entry.1773214368=" + URLEncoder.encode(bc.getPid()) + "&" + "entry.1667098360="
				+ URLEncoder.encode(bc.getVid()) + "&" + "entry.17553355=" + URLEncoder.encode(bc.getImid()) + "&" + "entry.1827591331=" + URLEncoder.encode(bc.getRfalg()) + "&"
				+ "entry.258560354=" + URLEncoder.encode(bc.getTime()) + "&" + "entry.743090124=" + URLEncoder.encode(bc.getBname()) + "&" + "entry.1853237792="
				+ URLEncoder.encode(bc.getPstatus()) + "&" + "entry.1152173085=" + URLEncoder.encode(bc.getSid()) + "&" + "entry.679729860=" + URLEncoder.encode(bc.getPname())
				+ "&" + "entry.813265161=" + URLEncoder.encode(bc.getCount()) + "&" + "entry.1793021485=" + URLEncoder.encode(bc.getPhone()) + "&" + "entry.1108043609="
				+ URLEncoder.encode(bc.getMail()) + "&" + "entry.1959002797=" + URLEncoder.encode(bc.getAge()) + "&" + "entry.1145045882=" + URLEncoder.encode(bc.getGender())
				+ "&" + "entry.1793147969=" + URLEncoder.encode(ccid);

		response = mReq.sendPost(fullUrl, data);
		int responsecode = mReq.statusCode;
		//Log.i("HttpResponseSC", response);
		if (responsecode == 200)
		{

			DBAdapter dao = new DBAdapter(ApplicationConstant.getInstance().getApplicationContext());
			dao.open();
			// dao.updateBuyerinfoStatus(bc.getSid(),
			// Integer.parseInt(bc.getPid()), "web");

			dao.delete_buyer(bc.getSid(), Integer.parseInt(bc.getPid()));
			dao.close();


		}
		else
		{

			Log.d("bis", "Exception while syncing not 200");
		}
	}

}
