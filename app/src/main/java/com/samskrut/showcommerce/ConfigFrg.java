package com.samskrut.showcommerce;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ConfigFrg extends Fragment
{
	Context ctx;

	// GridView gvColors, gvImages;
	ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7;
	String[] web;
	ImageView imageView, hiv, imageView2;

	ArrayList<String> alColors;
	ArrayList<String> alChairImages;
	ArrayList<String> alChairlegImages;
	ArrayList<String> alChairlegBigImages;

	ViewGroup container;
	LayoutInflater inflater;
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		ctx = getActivity();

		this.container = container;
		this.inflater = inflater;
		if (rootView == null)
		{
			rootView = inflater.inflate(R.layout.config_fragment_layout_land, container, false);

		}

		if (rootView != null)
			Initialize(rootView);

		return rootView;

	}

	private void Initialize(View view)
	{

		File chairImages = new File(Environment.getExternalStorageDirectory() + "/sc2/cc/ci");

		File file1 = new File(Environment.getExternalStorageDirectory() + "/sc2/cc/leg01.jpg");
		File file2 = new File(Environment.getExternalStorageDirectory() + "/sc2/cc/leg02.jpg");
		alChairlegImages = new ArrayList<String>();
		alChairlegImages.add(file1.getAbsolutePath());
		alChairlegImages.add(file2.getAbsolutePath());

		File file4 = new File(Environment.getExternalStorageDirectory() + "/sc2/cc/bigleg02.png");
		File file3 = new File(Environment.getExternalStorageDirectory() + "/sc2/cc/bigleg01.png");
		alChairlegBigImages = new ArrayList<String>();
		alChairlegBigImages.add(file3.getAbsolutePath());
		alChairlegBigImages.add(file4.getAbsolutePath());

		if (chairImages.exists())
		{

			File[] listchairs = chairImages.listFiles();

			alChairImages = new ArrayList<String>();

			for (int i = 0; i < listchairs.length; i++)
			{

				alChairImages.add(listchairs[i].getAbsolutePath());
				Collections.sort(alChairImages);

			}
			hiv = (ImageView) view.findViewById(R.id.hiv);

			int ori = getScreenOrientation();
			if (ori == 1)
			{
				hiv.setImageResource(R.drawable.chair_base_p);
			}
			else
			{
				hiv.setImageResource(R.drawable.chair_base_l);

			}
		}

		File colors = new File(Environment.getExternalStorageDirectory() + "/sc2/cc/color");
		if (colors.exists())
		{
			File[] listcolors = colors.listFiles();
			alColors = new ArrayList<String>();
			for (int i = 0; i < listcolors.length; i++)
			{

				alColors.add(listcolors[i].getAbsolutePath());
				Collections.sort(alColors);

			}
		}
		Log.d("bis", "colors" + alColors.size() + "images list" + alChairImages.size());

		imageView = (ImageView) view.findViewById(R.id.imageView1);
		Bitmap bmp8 = BitmapFactory.decodeFile(alChairImages.get(1));
		imageView.setImageBitmap(bmp8);

		imageView2 = (ImageView) view.findViewById(R.id.imageView2);
		Bitmap bmp9 = BitmapFactory.decodeFile(alChairlegBigImages.get(0));
		imageView2.setImageBitmap(bmp9);

		iv1 = (ImageView) view.findViewById(R.id.iv1);
		final Bitmap bmp1 = BitmapFactory.decodeFile(alColors.get(0));
		iv1.setImageBitmap(bmp1);

		iv2 = (ImageView) view.findViewById(R.id.iv2);
		final Bitmap bmp2 = BitmapFactory.decodeFile(alColors.get(1));
		iv2.setImageBitmap(bmp2);

		iv3 = (ImageView) view.findViewById(R.id.iv3);
		final Bitmap bmp3 = BitmapFactory.decodeFile(alColors.get(2));
		iv3.setImageBitmap(bmp3);

		iv4 = (ImageView) view.findViewById(R.id.iv4);
		final Bitmap bmp4 = BitmapFactory.decodeFile(alColors.get(3));
		iv4.setImageBitmap(bmp4);

		iv5 = (ImageView) view.findViewById(R.id.iv5);
		final Bitmap bmp5 = BitmapFactory.decodeFile(alColors.get(4));
		iv5.setImageBitmap(bmp5);

		iv1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Bitmap bmp = BitmapFactory.decodeFile(alChairImages.get(1));
				imageView.setImageBitmap(bmp);
				imageView.setImageBitmap(bmp);

			}
		});
		iv2.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Bitmap bmp = BitmapFactory.decodeFile(alChairImages.get(2));
				imageView.setImageBitmap(bmp);
				imageView.setImageBitmap(bmp);

			}
		});
		iv3.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Bitmap bmp = BitmapFactory.decodeFile(alChairImages.get(3));
				imageView.setImageBitmap(bmp);
				imageView.setImageBitmap(bmp);

			}
		});
		iv4.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Bitmap bmp = BitmapFactory.decodeFile(alChairImages.get(4));
				imageView.setImageBitmap(bmp);
				imageView.setImageBitmap(bmp);

			}
		});
		iv5.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Bitmap bmp = BitmapFactory.decodeFile(alChairImages.get(5));
				imageView.setImageBitmap(bmp);
				imageView.setImageBitmap(bmp);

			}
		});

		// =================

		iv6 = (ImageView) view.findViewById(R.id.iv6);
		Bitmap bmp6 = BitmapFactory.decodeFile(alChairlegImages.get(0));
		iv6.setImageBitmap(bmp6);
		iv6.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Bitmap bmp = BitmapFactory.decodeFile(alChairlegBigImages.get(1));
				imageView2.setImageBitmap(bmp);
				imageView2.setImageBitmap(bmp);

			}
		});

		iv7 = (ImageView) view.findViewById(R.id.iv7);
		Bitmap bmp7 = BitmapFactory.decodeFile(alChairlegImages.get(1));
		iv7.setImageBitmap(bmp7);
		iv7.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Bitmap bmp = BitmapFactory.decodeFile(alChairlegBigImages.get(0));
				imageView2.setImageBitmap(bmp);
				imageView2.setImageBitmap(bmp);

			}
		});
		// TODO Auto-generated method stub

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		container.removeAllViews();
		switch (newConfig.orientation)
		{
		case Configuration.ORIENTATION_PORTRAIT:
			rootView = inflater.inflate(R.layout.config_fragment_layout_land, container, true);
			break;
		case Configuration.ORIENTATION_LANDSCAPE:
			rootView = inflater.inflate(R.layout.config_fragment_layout_land, container, true);
			break;
		}

		if (rootView != null)
		{
			Initialize(rootView);

		}
	}

	public int getScreenOrientation()
	{

		// Query what the orientation currently really is.
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			return 1; // Portrait Mode

		}
		else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			return 2; // Landscape mode
		}
		return 0;
	}

}
