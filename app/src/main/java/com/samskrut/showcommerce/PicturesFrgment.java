package com.samskrut.showcommerce;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imagezoom.ImageAttacher;
import com.imagezoom.ImageAttacher.OnMatrixChangedListener;
import com.imagezoom.ImageAttacher.OnPhotoTapListener;
import com.samskrut.showcommerce.database.DBAdapter;
import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.InfoTracker;

public class PicturesFrgment extends Fragment
{

	static ArrayList<String> purls;
	ViewPager mViewPager;
	static LinearLayout myGallery;
	// static int lastid = 99;

	TextView pname, pcost, hidden_tv;
	LoadimagesInBackground loadimagesInBackground;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.pictures_fragment, container, false);
		myGallery = (LinearLayout) view.findViewById(R.id.mygallery);
		mViewPager = (ViewPager) view.findViewById(R.id.pager);

		hidden_tv = (TextView) view.findViewById(R.id.hidden_tv);

		pname = (TextView) view.findViewById(R.id.pnametv);
		pname.setText(Constants.pname);
		pcost = (TextView) view.findViewById(R.id.ppricetv);
		pcost.setText("Rs." + Constants.pcost);

		dbcall();

		// working on horizontal scrool view ==============================

		/*
		 * Handler h1 = new Handler(); Runnable r = new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * // if(myGallery.) for (int i = 0; i < purls.size(); i++) {
		 * 
		 * String fileExtenstion_h =
		 * MimeTypeMap.getFileExtensionFromUrl(purls.get(i)); String filename_h
		 * = URLUtil.guessFileName(purls.get(i), null, fileExtenstion_h);
		 * 
		 * String imageInSD_h =
		 * Environment.getExternalStorageDirectory().toString() +
		 * "/showcommerce/products/" + filename_h + "";
		 * 
		 * myGallery.addView(insertPhoto(imageInSD_h, i));
		 * 
		 * }
		 * 
		 * } }; h1.postDelayed(r, 2000);
		 */

		// =========================================

		if (purls.size() > 0)
		{

			mViewPager.setAdapter(new PictureListFragmentAdapter(getActivity(), getChildFragmentManager()));
			// mViewPager.setPageTransformer(true, new
			// ZoomOutPageTransformer());
			mViewPager.setPageTransformer(true, new DepthPageTransformer());

			loadimagesInBackground = new LoadimagesInBackground();
			loadimagesInBackground.execute();
		}
		else
		{

			mViewPager.setVisibility(View.GONE);
			hidden_tv.setVisibility(View.VISIBLE);

		}

		return view;
	}

	// animation classess=================

	public class DepthPageTransformer implements ViewPager.PageTransformer
	{
		private static final float MIN_SCALE = 0.75f;

		public void transformPage(View view, float position)
		{
			int pageWidth = view.getWidth();

			if (position < -1)
			{ // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);

			}
			else if (position <= 0)
			{ // [-1,0]
				// Use the default slide transition when moving to the left page
				view.setAlpha(1);
				view.setTranslationX(0);
				view.setScaleX(1);
				view.setScaleY(1);

			}
			else if (position <= 1)
			{ // (0,1]
				// Fade the page out.
				view.setAlpha(1 - position);

				// Counteract the default slide transition
				view.setTranslationX(pageWidth * -position);

				// Scale the page down (between MIN_SCALE and 1)
				float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
				view.setScaleX(scaleFactor);
				view.setScaleY(scaleFactor);

			}
			else
			{ // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}
	}

	// ===================================
	private void dbcall()
	{
		DBAdapter dab = new DBAdapter(getActivity());
		dab.open();
		Cursor mcursor = dab.retriveProductImages(Constants.pid);
		purls = new ArrayList<String>();

		if (mcursor.moveToFirst())
		{
			do
			{

				purls.add(mcursor.getString(1));
				// Log.d("bis", "while adding" + mcursor.getString(1));
			} while (mcursor.moveToNext());
		}

		if (mcursor != null && !mcursor.isClosed())
		{
			mcursor.close();
		}
		dab.close();
	}

	/*
	 * View insertPhoto(String path, int j) { Bitmap bm =
	 * decodeSampledBitmapFromUri(path, 220, 220);
	 * 
	 * final LinearLayout layout = new LinearLayout(getActivity());
	 * layout.setLayoutParams(new LayoutParams(80, 80));
	 * layout.setClickable(true); layout.setGravity(Gravity.RIGHT);
	 * 
	 * final ImageView imageView = new ImageView(getActivity());
	 * 
	 * imageView.setLayoutParams(new LayoutParams(77, 77));
	 * imageView.setPadding(10, 2, 10, 2);
	 * imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	 * 
	 * imageView.setId(j);
	 * 
	 * imageView.setImageBitmap(bm); imageView.setBackgroundColor(Color.rgb(255,
	 * 255, 255));
	 * 
	 * imageView.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) {
	 * 
	 * mViewPager.setCurrentItem(imageView.getId());
	 * 
	 * } });
	 * 
	 * layout.addView(imageView);
	 * 
	 * return layout; }
	 */

	View insertPhoto(int j, Bitmap bm)
	{
		if (getActivity() != null&&!getActivity().isFinishing())
		{
			final LinearLayout layout = new LinearLayout(getActivity());
			layout.setLayoutParams(new LayoutParams(120, 120));
			layout.setClickable(true);
			layout.setGravity(Gravity.RIGHT);

			final ImageView imageView = new ImageView(getActivity());

			imageView.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			imageView.setPadding(5, 2, 5, 2);
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

			imageView.setId(j);

			imageView.setImageBitmap(bm);
			imageView.setBackgroundColor(Color.rgb(255, 255, 255));

			imageView.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{

					mViewPager.setCurrentItem(imageView.getId());

					// Log.d("bis", "imageView.getId()2=" + imageView.getId());

				}
			});

			layout.addView(imageView);
			// Log.d("bis", "imageView.getId()1=" + imageView.getId());

			return layout;

		}
		return null;

	}

	// ====

	public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight)
	{
		Bitmap bm = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(path, options);

		return bm;
	}

	public int calculateInSampleSize(

	BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth)
		{
			if (width > height)
			{
				inSampleSize = Math.round((float) height / (float) reqHeight);
			}
			else
			{
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}

	// =============

	public class PictureListFragmentAdapter extends FragmentPagerAdapter
	{
		Context ctxt = null;

		public PictureListFragmentAdapter(Context ctxt, FragmentManager mgr)
		{
			super(mgr);
			this.ctxt = ctxt;
		}

		@Override
		public int getCount()
		{
			return (purls.size());
		}

		@Override
		public Fragment getItem(int position)
		{
			return (SinglePictureFragment.newInstance(position));
		}

		@Override
		public String getPageTitle(int position)
		{
			return (SinglePictureFragment.getTitle(ctxt, position));
		}

		/*
		 * @Override public void destroyItem(ViewGroup container, int position,
		 * Object object) { Log.d("bis", "position frg " + position);
		 * FragmentManager manager = ((Fragment) object).getFragmentManager();
		 * FragmentTransaction trans = manager.beginTransaction();
		 * trans.remove((Fragment) object); trans.commit();
		 * 
		 * }
		 */

	}

	public static class SinglePictureFragment extends Fragment
	{
		private static final String KEY_POSITION = "position";

		static Context ctx;

		ImageView mImage;
		ImageButton mbutton;
		Bitmap bmp;
		TextView product_info_tv;

		static SinglePictureFragment newInstance(int position)
		{
			SinglePictureFragment frag = new SinglePictureFragment();
			Bundle args = new Bundle();

			args.putInt(KEY_POSITION, position);
			frag.setArguments(args);

			return (frag);
		}

		static String getTitle(Context ctxt, int position)
		{
			ctx = ctxt;
			return (String.format(ctxt.getString(R.string.hint), position + 1));
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View result = inflater.inflate(R.layout.single_picture_ov, container, false);
			mbutton = (ImageButton) result.findViewById(R.id.editor1);

			// mImage = (TouchImageView) result.findViewById(R.id.flag);
			mImage = (ImageView) result.findViewById(R.id.flag);

			int position = getArguments().getInt(KEY_POSITION, -1);

			String url = purls.get(position);
			if (InfoTracker.usersessionid > 0)
			{

				InfoTracker.imageid = purls.get(position);
			}

			//String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(url);
			//String filename = URLUtil.guessFileName(url, null, fileExtenstion);
			// String imageInSD = getActivity().getExternalCacheDir().toString()
			// + "/commerce/products/" + filename + "";
			String imageInSD = Environment.getExternalStorageDirectory().toString() + "/showcommerce/products/" + url + "";

			// BitmapFactory.Options options = new BitmapFactory.Options();
			// options.inSampleSize = 8;
			// options.inPurgeable = true;
			// options.inScaled = false;
			// Bitmap bmp = BitmapFactory.decodeFile(imageInSD, options);

			/*
			 * options.inJustDecodeBounds = false; options.inDither = false;
			 * options.inSampleSize = 4; options.inScaled = false;
			 * options.inPreferredConfig = Bitmap.Config.ARGB_8888; Bitmap bmp =
			 * BitmapFactory.decodeFile(imageInSD, options);
			 */
			bmp = BitmapFactory.decodeFile(imageInSD);
			mImage.setImageBitmap(bmp);
			// original = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
			// bm.getHeight(), matrix, true);

			// ===============

			// imageView.setBackgroundColor(Color.rgb(100, 100, 50));
			// ==================

			usingSimpleImage(mImage);

			System.gc();
			// editor.setHint(getTitle(getActivity(), position));

			return (result);
		}

		/*
		 * @Override public void onDestroy() { // TODO Auto-generated method
		 * stub super.onDestroy(); bmp.recycle(); bmp = null; mImage = null; }
		 */

		public void usingSimpleImage(ImageView imageView)
		{
			ImageAttacher mAttacher = new ImageAttacher(imageView);
			ImageAttacher.MAX_ZOOM = 3.0f; // Double the current Size
			ImageAttacher.MIN_ZOOM = 1.0f; // Half the current Size
			MatrixChangeListener mMaListener = new MatrixChangeListener();
			mAttacher.setOnMatrixChangeListener(mMaListener);
			PhotoTapListener mPhotoTap = new PhotoTapListener();
			mAttacher.setOnPhotoTapListener(mPhotoTap);
		}

		private class PhotoTapListener implements OnPhotoTapListener
		{

			@Override
			public void onPhotoTap(View view, float x, float y)
			{

				/*if (getActivity().getActionBar().isShowing())
				{

					PicturesFrgment.myGallery.setVisibility(8);
					getActivity().getActionBar().hide();
					// int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN |
					// View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
					ProductOverView.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
					
					
					
				}
				else
				{

					PicturesFrgment.myGallery.setVisibility(0);
					getActivity().getActionBar().show();

					ProductOverView.decorView.setSystemUiVisibility(0);

				}*/
				
				//materail theme
				
				if (((ActionBarActivity)getActivity()).getSupportActionBar().isShowing())
				{

					PicturesFrgment.myGallery.setVisibility(View.GONE);
					((ActionBarActivity)getActivity()).getSupportActionBar().hide();
					
					//ProductOverView.toolbar.animate().translationY(-ProductOverView.toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();

					// int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN |
					// View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
					ProductOverView.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
					
					
					
				}
				else
				{
					PicturesFrgment.myGallery.setVisibility(View.VISIBLE);
					((ActionBarActivity)getActivity()).getSupportActionBar().show();
					//ProductOverView.toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();


					ProductOverView.decorView.setSystemUiVisibility(0);

				}
				

			}
		}

		private class MatrixChangeListener implements OnMatrixChangedListener
		{

			@Override
			public void onMatrixChanged(RectF rect)
			{

			}
		}

	}

	// =================
	private class LoadimagesInBackground extends AsyncTask<String, Integer, String>
	{
		ArrayList<Bitmap> alb = new ArrayList<Bitmap>();

		@Override
		protected String doInBackground(String... params)
		{

			// if(myGallery.)
			for (int i = 0; i < purls.size(); i++)
			{

				//String fileExtenstion_h = MimeTypeMap.getFileExtensionFromUrl(purls.get(i));
				//String filename_h = URLUtil.guessFileName(purls.get(i), null, fileExtenstion_h);

				String imageInSD_h = Environment.getExternalStorageDirectory().toString() + "/showcommerce/products/" + purls.get(i) + "";

				Bitmap bm = decodeSampledBitmapFromUri(imageInSD_h, 220, 220);

				alb.add(bm);

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{

			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (getActivity() != null)
			{

				for (int j = 0; j < alb.size(); j++)
				{
					if (getActivity() != null)
					{
						if (!getActivity().isFinishing())
						{
							myGallery.addView(insertPhoto(j, alb.get(j)));
						}
						else
						{
							loadimagesInBackground.cancel(true);
						}
					}
					else
					{
						loadimagesInBackground.cancel(true);
						// getActivity().finish();

					}

				}

			}

		}

		// ================
	}

}