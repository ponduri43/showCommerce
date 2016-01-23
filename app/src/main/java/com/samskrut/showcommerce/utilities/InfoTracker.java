package com.samskrut.showcommerce.utilities;

public class InfoTracker
{

	public static String tableusersessionid;

	public static int usersessionid = 0;

	// public static int visitedProductCount = 0;

	// public static int visitedCategoryCount=0;

	public static int cid = 0;

	public static int pid = 0;

	public static String videoid;

	public static String imageid;

	public static int view360 = 0;

	public static long time = 0;

	public static String pname;

	public static void totalresetValues()
	{
		usersessionid = 0;
		
		tableusersessionid = null;

		cid = 0;

		pid = 0;

		videoid = null;

		imageid = null;

		view360 = 0;

		time = 0;

	}

	public static void resetValuesAtCategoryLevel()
	{

		// visitedProductCount = 0;

		cid = 0;

		pid = 0;

		videoid = null;

		imageid = null;

		view360 = 0;

		time = 0;

	}

	public static void resetValuesAtProductLevel()
	{

		// visitedProductCount = 0;

		// cid = 0;

		pid = 0;

		videoid = null;

		imageid = null;

		view360 = 0;

		time = 0;

	}

}
