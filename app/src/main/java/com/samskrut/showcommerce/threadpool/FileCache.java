package com.samskrut.showcommerce.threadpool;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

public class FileCache
{

	private File cacheDir;
	private File cacheDir2;
	Context ctx;

	public FileCache(Context context)
	{

		// Find the dir at SDCARD to save cached images

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{

			ctx = context;
			// make directries
			File folder = new File(Environment.getExternalStorageDirectory() + "/showcommerce");
			if (!folder.exists())
			{
				folder.mkdir();
			}

			File folder2 = new File(Environment.getExternalStorageDirectory() + "/showcommerce/categories" + "");
			if (!folder2.exists())
			{
				folder2.mkdir();

			}
			File folder3 = new File(Environment.getExternalStorageDirectory() + "/showcommerce/productsTh" + "");
			if (!folder3.exists())
			{
				folder3.mkdir();

			}
			File folder4 = new File(Environment.getExternalStorageDirectory() + "/showcommerce/products" + "");
			if (!folder4.exists())
			{
				folder4.mkdir();

			}

		}
		else
		{
			// if checking on simulator the create cache dir in your application
			// context
			cacheDir = context.getCacheDir();
		}

		/*
		 * if(!cacheDir.exists()){ // create cache dir in your application
		 * context //cacheDir.mkdirs(); }
		 */
	}

	public File getFile(String url, String ffname)
	{
		String filename1 = "";

		String[] strArray = url.split("/");
		filename1 = strArray[4].substring(42);

		File folder = new File(Environment.getExternalStorageDirectory() + "/showcommerce");
		if (!folder.exists())
		{
			folder.mkdir();
		}
		File folder2 = new File(Environment.getExternalStorageDirectory() + "/showcommerce/" + ffname + "");
		if (!folder2.exists())
		{
			folder2.mkdir();

		}
		File f = new File(folder2, filename1);

		// Log.d("bis", filename1+", add= "+f.getAbsolutePath());
		return f;

	}

	public void clear()
	{
		// list all files inside cache directory
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		// delete all cache directory files
		for (File f : files)
			f.delete();
	}

}