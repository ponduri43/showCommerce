package com.samskrut.showcommerce.jobs;

import java.net.URLEncoder;

import android.util.Log;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.samskrut.showcommerce.ApplicationConstant;
import com.samskrut.showcommerce.database.DBAdapter;
import com.samskrut.showcommerce.database.buyerClass;
import com.samskrut.showcommerce.events.RertyCountPojo;
import com.samskrut.showcommerce.events.SyncDataPojoSaved;
import com.samskrut.showcommerce.events.SyncDataPojoSynFailed;
import com.samskrut.showcommerce.events.SyncDataPojoSynced;
import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.HttpRequest;

import de.greenrobot.event.EventBus;

public class SyncDataJob extends Job
{

	String response;

	private String sid;
	private String pid;
	buyerClass bc;

	String LOG_TAG = "SyncDataJob";

	public SyncDataJob(buyerClass _bc)
	{
		super(new Params(Priority.HIGH).requireNetwork().persist());
		// TODO Auto-generated constructor stub
		bc = _bc;

		Log.d(LOG_TAG, "SyncDataJobConstructor");
	}

	@Override
	public void onAdded()
	{
		// Saving the todo text to DB, this is only for Updating UI for better
		// UX

		/*
		 * DBAdapter dao = new
		 * DBAdapter(ApplicationConstant.getInstance().getApplicationContext());
		 * dao.open(); dao.updateBuyerinfoStatus(bc.getSid(),
		 * Integer.parseInt(bc.getPid()), "android_add"); dao.close();
		 */

		EventBus.getDefault().post(new SyncDataPojoSaved(bc));
		Log.d(LOG_TAG, "onAdded");

	}

	@Override
	public void onRun() throws Throwable
	{
		Log.d(LOG_TAG, "onRun");

		String fullUrl = "https://docs.google.com/forms/d/1KMT-N4qWGfQxv_BtxWXKIZ05FPsRXNUs6PvynGPxcDg/formResponse";

		// ProTip:Always Use Volley,Retrofit with okhttp
		HttpRequest mReq = new HttpRequest();
		String ccid=Constants.customerid + "";
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
		Log.i("HttpResponseSC", response);
		if (responsecode == 200)
		{

			DBAdapter dao = new DBAdapter(ApplicationConstant.getInstance().getApplicationContext());
			dao.open();
			// dao.updateBuyerinfoStatus(bc.getSid(),
			// Integer.parseInt(bc.getPid()), "web");

			dao.delete_buyer(bc.getSid(), Integer.parseInt(bc.getPid()));
			dao.close();

			EventBus.getDefault().post(new SyncDataPojoSynced(bc));

		}
		else
		{

			Log.d(LOG_TAG, "Exception");
			throw new Exception(response);
		}

	}

	@Override
	protected boolean shouldReRunOnThrowable(Throwable throwable)
	{
		// TODO Auto-generated method stub

		Log.d(LOG_TAG, "shouldReRunOnThrowable");
		int count = 1;

		if (throwable instanceof Exception)
		{

			Exception todoException = (Exception) throwable;

			Log.d(LOG_TAG, "throwable instanceof Exception");
			// if it is a 4xx error, stop
			// System.out.println(Integer.parseInt(todoException.getMessage()) <
			// 400 || Integer.parseInt(todoException.getMessage()) > 499);
			// return Integer.parseInt(todoException.getMessage()) < 400 ||
			// Integer.parseInt(todoException.getMessage()) > 499;

			// Hard coded to true for Testing Retry Logic, if it's true Jobs run
			// method will be restarted.
			count = count++;
			EventBus.getDefault().post(new RertyCountPojo(count));

			return true;

		}

		// Hard coded to true for Testing Retry Logic
		return true;
		// return true;
	}

	@Override
	protected void onCancel()
	{
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "onCancel");

		DBAdapter dao = new DBAdapter(ApplicationConstant.getInstance().getApplicationContext());
		dao.open();
		dao.updateBuyerinfoStatus(bc.getSid(), Integer.parseInt(bc.getPid()), "android");

		dao.close();

		int count = 1;
		EventBus.getDefault().post(new RertyCountPojo(count));

		EventBus.getDefault().post(new SyncDataPojoSynFailed(bc));

	}

}
