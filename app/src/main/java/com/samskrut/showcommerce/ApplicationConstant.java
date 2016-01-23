package com.samskrut.showcommerce;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

public class ApplicationConstant extends Application
{
	Context context;

	public static final String TAG = ApplicationConstant.class.getSimpleName();
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private static ApplicationConstant mInstance;

	private JobManager jobManager;

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();

		context = this;
		mInstance = this;
		Parse.initialize(this, "WmBh3MLC4HXFKlEtagXCsmOQyBcJTEqHR1llaLC9", "F454edm5iv8K54F7vm74YiQEEgTIGkdlaPe6GLtc");
		// old Parse.initialize(this, "28rzQwSoD7MFQOOViu9awAI0giaUDK8E7ADYbXAz", "jbYQAqhT1jcRiIUrS3UwuFuFOipjv04kUYhZpkEN");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);

		configureJobManager();
	}

	public static synchronized ApplicationConstant getInstance()
	{
		return mInstance;
	}

	public RequestQueue getRequestQueue()
	{
		if (mRequestQueue == null)
		{
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader()
	{
		getRequestQueue();
		if (mImageLoader == null)
		{
			mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag)
	{
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req)
	{
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag)
	{
		if (mRequestQueue != null)
		{
			mRequestQueue.cancelAll(tag);
		}
	}

	private void configureJobManager()
	{
		Configuration configuration = new Configuration.Builder(this).customLogger(new CustomLogger()
		{
			private static final String TAG = "JOBS";

			@Override
			public boolean isDebugEnabled()
			{
				return true;
			}

			@Override
			public void d(String text, Object... args)
			{
				Log.d(TAG, String.format(text, args));
			}

			@Override
			public void e(Throwable t, String text, Object... args)
			{
				Log.e(TAG, String.format(text, args), t);
			}

			@Override
			public void e(String text, Object... args)
			{
				Log.e(TAG, String.format(text, args));
			}
		}).minConsumerCount(1)// always keep at least one consumer alive
				.maxConsumerCount(3)// up to 3 consumers at a time
				.loadFactor(3)// 3 jobs per consumer
				.consumerKeepAlive(120)// wait 2 minute
				.build();
		jobManager = new JobManager(this, configuration);
	}

	public JobManager getJobManager()
	{
		return jobManager;
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();

		// myDbHelper.close();
	}
}
