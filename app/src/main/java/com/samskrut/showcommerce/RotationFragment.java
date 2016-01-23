package com.samskrut.showcommerce;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.InfoTracker;

public class RotationFragment extends Fragment implements View.OnTouchListener
{

	private int PID;
	public ProgressDialog dialog;
	private ImageView imageview;
	private int currentimagenumber = 1;
	int x, y, cx, cy, fsx, fsy, fspx, fspy;

	boolean flag = true;

	Context ctx;

	int total;
	ArrayList<String> al;

	TextView hidden_tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.roation_fragment, container, false);

		ctx = getActivity();
		PID = Constants.pid;

		File folder = new File(Environment.getExternalStorageDirectory() + "/showcommerce");
		if (folder.exists())
		{
			File folder2 = new File(Environment.getExternalStorageDirectory() + "/showcommerce/p" + PID);
			if (folder2.exists())
			{
				File[] list = folder2.listFiles();
				total = list.length;
				al = new ArrayList<String>();
				// Log.d("bis","total= "+ total);
				imageview = (ImageView) view.findViewById(R.id.image);

				Bitmap bmp = BitmapFactory.decodeFile(list[0].getAbsolutePath());
				currentimagenumber = 1;
				imageview.setImageBitmap(bmp);
				imageview.setOnTouchListener(this);

				for (int i = 0; i < total; i++)
				{

					al.add(list[i].getAbsolutePath());
					Collections.sort(al);

					if (InfoTracker.usersessionid > 0)
					{

						InfoTracker.view360 = 1;
					}

				}

			}
			else
			{

				hidden_tv = (TextView) view.findViewById(R.id.hidden_tv);
				hidden_tv.setVisibility(0);
			}
		}

		return view;
	}

	private void toggleFullscreen(boolean fullscreen)
	{
		WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
		if (fullscreen)
		{
			attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			if (getActivity().getActionBar().isShowing())
			{
				getActivity().getActionBar().hide();
			}
		}
		else
		{
			attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
			if (!getActivity().getActionBar().isShowing())
			{
				getActivity().getActionBar().show();
			}
		}
		getActivity().getWindow().setAttributes(attrs);
		flag = !flag;
	}

	public void moveRight()
	{

		currentimagenumber--;
		if (currentimagenumber == 0)
			currentimagenumber = total;

		Bitmap bmp = BitmapFactory.decodeFile(al.get(currentimagenumber - 1));
		// Bitmap bmp =
		// BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString()
		// + "/showcommerce/p" + PID + "/" + PID + "_" + currentimagenumber +
		// ".jpg");
		imageview.setImageBitmap(bmp);

	}

	public void moveLeft()
	{

		currentimagenumber++;
		if (currentimagenumber == total + 1)
			currentimagenumber = 1;
		Bitmap bmp = BitmapFactory.decodeFile(al.get(currentimagenumber - 1));
		// Bitmap bmp =
		// BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString()
		// + "/showcommerce/p" + PID + "/" + PID + "_" + currentimagenumber +
		// ".jpg");

		imageview.setImageBitmap(bmp);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{

		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			fspx = (int) event.getX();
			fspy = (int) event.getY();
			cx = (int) event.getX();
			cy = (int) event.getY();
			x = (int) event.getX();
			y = (int) event.getY();
			return true;
		}
		case MotionEvent.ACTION_UP:
		{
			x = (int) event.getX();
			y = (int) event.getY();
			fsx = x;
			fsy = y;
			if (Math.abs(fsx - fspx) <= 5 && Math.abs(fsy - fspy) <= 5)
			{
				toggleFullscreen(flag);
			}
			return true;
		}
		case MotionEvent.ACTION_MOVE:
		{
			x = (int) event.getX();
			y = (int) event.getY();

			if (x >= cx + 10)
			{
				cx = x;
				moveRight();
				return true;
			}
			else if (x <= cx - 10)
			{
				cx = x;
				moveLeft();
				return true;
			}
		}
		}
		return false;
	}

}
