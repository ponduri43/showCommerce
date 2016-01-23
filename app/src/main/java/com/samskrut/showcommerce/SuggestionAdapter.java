package com.samskrut.showcommerce;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
//import android.widget.CursorAdapter;
//import android.support.v4.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.samskrut.showcommerce.database.ActionbarProductObject;
import com.samskrut.showcommerce.utilities.BitmapImageLoader;

public class SuggestionAdapter extends CursorAdapter
{

	// private List<String> items;
	// private List<String> puls;

	private TextView text;
	private ImageView image;
	ArrayList<ActionbarProductObject> apoList;

	public SuggestionAdapter(Context context, Cursor cursor, ArrayList<ActionbarProductObject> items)
	{

		super(context, cursor, false);

		// this.items = items;
		this.apoList = items;

	}

	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{

		// Show list item data from cursor
		
		text.setText(apoList.get(cursor.getPosition()).getPname());

		// Alternatively show data direct from database
		// text.setText(cursor.getString(cursor.getColumnIndex("column_name")));

		String url = apoList.get(cursor.getPosition()).getPurl();
		// Log.d("bis", "url==" + url);

		//String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(url);
		//String filename = URLUtil.guessFileName(url, null, fileExtenstion);
		// Log.d("bis", "filename= " + filename);
		// String imageInSD = context.getExternalCacheDir().toString() +
		// "/commerce/productsTh/" + filename + "";
		String imageInSD = Environment.getExternalStorageDirectory().toString() + "/showcommerce/productsTh/" + url + "";
		BitmapImageLoader loadImage = new BitmapImageLoader(context, "Speaker");
		loadImage.loadBitmap(imageInSD, image);

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.search_item, parent, false);

		text = (TextView) view.findViewById(R.id.text);
		image = (ImageView) view.findViewById(R.id.actionbariv);

		return view;

	}

}