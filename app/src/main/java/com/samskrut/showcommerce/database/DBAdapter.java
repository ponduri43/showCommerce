package com.samskrut.showcommerce.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter
{

	private static final String DATABASE_NAME = "commerceDB.sql";

	private static final int DATABASE_VERSION = 1;

	private final Context context;

	private DatabaseHelper DBHelper;
	private static SQLiteDatabase db;

	public DBAdapter(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	public static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			// db.execSQL(DATABASE_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{

			db.execSQL("DROP TABLE IF EXISTS titles");
			onCreate(db);
		}
	}

	// ---opens the database---
	public DBAdapter open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close()
	{
		DBHelper.close();
	}

	// -------------------------------------------------------------
	// LOGIN QUERIES
	// -------------------------------------------------------------

	public Cursor getLoginWithEmailPassword(final String email, final String password) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from login where Salespersonemail='" + email + "' and Password='" + password + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getLoginWithIDClient(final String idclient) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from login where idclient='" + idclient + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor insertLogin(final String idsalesperson, final String idclient, final String Salespersonname, final String Salespersonemail, final String Password,
			final String Active, final String Admin, final String Tradeshowname, final String Place, final String StartDate, final String EndDate, final String noofsalesperson,
			final String idtradeshow) throws SQLException
	{

		Cursor mCursor = db.rawQuery(
				"INSERT INTO login (idsalesperson, idclient,Salespersonname, Salespersonemail,Password, Active, Admin,Tradeshowname,Place,StartDate,EndDate,noofsalesperson,idtradeshow) VALUES('"
						+ idsalesperson + "','" + idclient + "','" + Salespersonname + "','" + Salespersonemail + "','" + Password + "','" + Active + "','" + Admin + "','"
						+ Tradeshowname + "','" + Place + "','" + StartDate + "','" + EndDate + "','" + noofsalesperson + "','" + idtradeshow + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// -------------------------------------------------------------

	public Cursor insertTradeshow(final String idtradeshow, final String Tradeshowname, final String Place, final String StartDate, final String EndDate) throws SQLException
	{

		Cursor mCursor = db.rawQuery("INSERT INTO tbl_tradeshow (idtradeshow,Tradeshowname,Place,StartDate,EndDate) VALUES('" + idtradeshow + "','" + Tradeshowname + "','" + Place
				+ "','" + StartDate + "','" + EndDate + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getTradeshow() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from tbl_tradeshow", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor deleteTradeshow()
	{
		Cursor mCursor = db.rawQuery("delete from tbl_tradeshow", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// ----------------

	public Cursor getDashboard() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from dashboard", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getDashboard(final String idclient) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from dashboard where idclient='" + idclient + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor updateDashboard(final String idclient, final String total, final String vip, final String hot, final String junk, final String cold, final String warm)
			throws SQLException
	{

		Cursor mCursor = db.rawQuery("update dashboard set total='" + total + "',vip='" + vip + "',hot='" + hot + "',junk='" + junk + "',cold='" + cold + "',warm='" + warm
				+ "' where idclient='" + idclient + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor insertDashboard(final String idclient, final String total, final String vip, final String hot, final String junk, final String cold, final String warm)
			throws SQLException
	{

		Cursor mCursor = db.rawQuery("INSERT INTO dashboard(idclient,total,vip,hot,junk,cold,warm) VALUES('" + idclient + "','" + total + "','" + vip + "','" + hot + "','" + junk
				+ "','" + cold + "','" + warm + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// ----------------

	public Cursor getUserTimeStamp() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from timestamp", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor updateProductTimeStamp(String curr_time) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update timestamp set product_timestamp='" + curr_time + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor updateVisitorTimeStamp(String curr_time) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update timestamp set visitor_timestamp='" + curr_time + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor updateSalesTeamTimeStamp(String curr_time) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update timestamp set salesteam_timestamp='" + curr_time + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor updateTimeFrameTimeStamp(String curr_time) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update timestamp set timeframe='" + curr_time + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor updateFollowupTimeStamp(String curr_time) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update timestamp set followupaction='" + curr_time + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////

	public Cursor getUserDetail(String username, String password) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from UserMas where Username='" + username + "' and Password='" + password + "' ", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor updateSetting(String api_path, String device_id, String next_order_no) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update settings set api_path='" + api_path + "' , device_id='" + device_id + "' , next_order_no='" + next_order_no + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public boolean checkUserExists() throws SQLException
	{

		open();

		Cursor mCursor = db.rawQuery("select * from UserMas", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
			if (mCursor.getCount() > 0)
			{

				mCursor.close();

				close();

				return true;
			}
		}

		mCursor.close();

		close();

		return false;
	}

	// USER MASTER

	public Cursor insertProductMaster(final String idproduct, final String idclient, final String ProductName, final String ProductPrice, final String device) throws SQLException
	{

		Cursor mCursor = db.rawQuery("INSERT INTO Productdetails (idproduct,idclient,ProductName,ProductPrice,device) VALUES('" + idproduct + "','" + idclient + "','"
				+ ProductName + "','" + ProductPrice + "','" + device + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor updateProductMaster(String idproduct, String idclient, String ProductName, String ProductPrice, final String device)
	{

		Cursor mCursor = db.rawQuery("update Productdetails set idproduct='" + idproduct + "', idclient='" + idclient + "', ProductName='" + ProductName + "', ProductPrice='"
				+ ProductPrice + "', device='" + device + "' where idproduct='" + idproduct + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public Cursor deleteProductMaster(final String idproduct, final String device)
	{

		String query = "update Productdetails set device='" + device + "' where idproduct='" + idproduct + "'";

		Cursor mCursor = db.rawQuery(query, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor deleteProductMaster(final String idproduct)
	{

		String query = "delete from Productdetails where idproduct='" + idproduct + "'";

		Cursor mCursor = db.rawQuery(query, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getProductMaster() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from Productdetails where device != 'android-delete'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getProductwithAndroidMaster() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from Productdetails where device='android' or device='android-update' or device='android-delete'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// -------------------------------------------

	public Cursor insertTimeFrameMaster(final String idtimeframe, final String Description, final String Active, final String noofdays) throws SQLException
	{

		Cursor mCursor = db.rawQuery("INSERT INTO TimeframeDetails (idtimeframe,Description,Active,noofdays) VALUES('" + idtimeframe + "','" + Description + "','" + Active + "','"
				+ noofdays + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor updateTimeFrameMaster(final String idtimeframe, final String Description, final String Active, final String noofdays) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update TimeframeDetails set idtimeframe='" + idtimeframe + "', Description='" + Description + "',Active = '" + Active + "' , noofdays='"
				+ noofdays + "' where idtimeframe='" + idtimeframe + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor deleteTimeFrameMaster(String idtimeframe)
	{
		Cursor mCursor = db.rawQuery("delete from TimeframeDetails where idtimeframe='" + idtimeframe + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getTimeFrameMaster() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from TimeframeDetails order by idtimeframe", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// /---------------------

	public Cursor insertFollowupActionMaster(final String idfollowupaction, final String Description, final String Active) throws SQLException
	{

		Cursor mCursor = db.rawQuery("INSERT INTO FollowupactionDetails (idfollowupaction,Description,Active) VALUES('" + idfollowupaction + "','" + Description + "','" + Active
				+ "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor updateFollowupActionMaster(final String idfollowupaction, final String Description, final String Active) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update FollowupactionDetails set idfollowupaction='" + idfollowupaction + "', Description='" + Description + "',Active = '" + Active
				+ "' where idfollowupaction='" + idfollowupaction + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor deleteFollowupActionMaster(String idfollowupaction)
	{
		Cursor mCursor = db.rawQuery("delete from FollowupactionDetails where idfollowupaction='" + idfollowupaction + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getFollowupActionMaster() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from FollowupactionDetails order by idfollowupaction", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// ///

	// USER MASTER

	public Cursor updateVisitorDevice(final String vid) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update VisitorDetails set device='web' where VisitorUniqueId='" + vid + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor getVisitor(final String VisitorUniqueId) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from VisitorDetails where VisitorUniqueId='" + VisitorUniqueId + "'", null);

		if (mCursor != null && mCursor.getCount() > 0)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor insertVisitorMainMaster(final String VisitorUniqueId, final String idvisitor, final String Firstname, final String Lastname, final String Organization,
			final String phone1, final String vip, final String datetime, final String dt, final String visitorcardpath, final String idtradeshow, final String idsalesperson,
			final String scoring, final String contactmethod, final String purchaseinfluence, final String purchasetimeframe, final String productinterests,
			final String followupaction, final String followuptimeframe, final String notes, final String assignedto, final String visitedtime, final String device,
			final String sdcardimagepath) throws SQLException
	{

		Cursor mCursor = db
				.rawQuery(
						"INSERT INTO VisitorDetails (VisitorUniqueId,idvisitor,Firstname,Lastname,Organisation,phone1,vip,datetime,dt,visitorcardpath,idtradeshow,idsalesperson,scoring,contactmethod,purchaseinfluence,purchasetimeframe,productinterests,followupaction,followuptimeframe,notes,assignedto,visitedtime,device,sdcardimagepath) VALUES('"
								+ VisitorUniqueId
								+ "','"
								+ idvisitor
								+ "','"
								+ Firstname
								+ "','"
								+ Lastname
								+ "','"
								+ Organization
								+ "','"
								+ phone1
								+ "','"
								+ vip
								+ "','"
								+ datetime
								+ "','"
								+ dt
								+ "','"
								+ visitorcardpath
								+ "','"
								+ idtradeshow
								+ "','"
								+ idsalesperson
								+ "','"
								+ scoring
								+ "','"
								+ contactmethod
								+ "','"
								+ purchaseinfluence
								+ "','"
								+ purchasetimeframe
								+ "','"
								+ productinterests
								+ "','"
								+ followupaction
								+ "','"
								+ followuptimeframe
								+ "','"
								+ notes
								+ "','" + assignedto + "','" + visitedtime + "','" + device + "','" + sdcardimagepath + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor updateVisitorMainMaster(final String VisitorUniqueId, final String idvisitor, final String Firstname, final String Lastname, final String Organization,
			final String phone1, final String vip, final String datetime, final String dt, final String visitorcardpath, final String idtradeshow, final String idsalesperson,
			final String scoring, final String contactmethod, final String purchaseinfluence, final String purchasetimeframe, final String productinterests,
			final String followupaction, final String followuptimeframe, final String notes, final String assignedto, final String visitedtime, final String device,
			final String sdcardimagepath) throws SQLException
	{
		Log.d("atcount", "update VisitorDetails" + Firstname);

		Cursor mCursor = db.rawQuery("update VisitorDetails set idvisitor='" + idvisitor + "',Firstname='" + Firstname + "',Lastname='" + Lastname + "',Organisation='"
				+ Organization + "',phone1='" + phone1 + "',vip='" + vip + "',datetime='" + datetime + "',dt='" + dt + "',visitorcardpath='" + visitorcardpath + "',idtradeshow='"
				+ idtradeshow + "',idsalesperson='" + idsalesperson + "',scoring='" + scoring + "',contactmethod='" + contactmethod + "',purchaseinfluence='" + purchaseinfluence
				+ "',purchasetimeframe='" + purchasetimeframe + "',productinterests='" + productinterests + "',followupaction='" + followupaction + "',followuptimeframe='"
				+ followuptimeframe + "',notes='" + notes + "',assignedto='" + assignedto + "',visitedtime='" + visitedtime + "',device='" + device + "',sdcardimagepath='"
				+ sdcardimagepath + "' where VisitorUniqueId = '" + VisitorUniqueId + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor insertVisitorMaster(final String VisitorUniqueId, final String idvisitor, final String Firstname, final String Lastname, final String Organisation,
			final String phone1, final String vip, final String datetime, final String dt) throws SQLException
	{
		Log.d("atcount", "insertVisitorMaster" + Firstname);
		Cursor mCursor = db.rawQuery("INSERT INTO VisitorDetails (VisitorUniqueId,idvisitor,Firstname,Lastname,Organisation,phone1,vip,datetime,dt) VALUES('" + VisitorUniqueId
				+ "','" + idvisitor + "','" + Firstname + "','" + Lastname + "','" + Organisation + "','" + phone1 + "','" + vip + "','" + datetime + "','" + dt + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	private static final String DATABASE_VisitorMast = "VisitorDetails";

	private static final String KEY_ROWID5 = "VisitorUniqueId";

	public Boolean updateVisitorMaster(final String VisitorUniqueId, final String idvisitor, final String Firstname, final String Lastname, final String Organisation,
			final String phone1, final String vip, final String datetime, final String dt)
	{

		ContentValues initialValues = new ContentValues();

		initialValues.put("VisitorUniqueId", VisitorUniqueId);
		initialValues.put("idvisitor", idvisitor);
		initialValues.put("Firstname", Firstname);
		initialValues.put("Lastname", Lastname);
		initialValues.put("Organisation", Organisation);
		initialValues.put("phone1", phone1);
		initialValues.put("vip", vip);
		initialValues.put("datetime", datetime);
		initialValues.put("dt", dt);

		return db.update(DATABASE_VisitorMast, initialValues, KEY_ROWID5 + "='" + VisitorUniqueId + "'", null) > 0;

	}

	public Cursor deleteVisitorMaster(String VisitorUniqueId)
	{
		Cursor mCursor = db.rawQuery("delete from VisitorDetails where VisitorUniqueId='" + VisitorUniqueId + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getVisitorMaster(String idtradeshow) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from VisitorDetails where idtradeshow='" + idtradeshow + "' order by datetime desc", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getVisitorByDeviceMaster() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from VisitorDetails where device='android'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getVisitorMasterwithDate(String dt) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from VisitorDetails where dt='" + dt + "' order by datetime desc", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor insertTeamMaster(final String idsalesperson, final String idclient, final String Salespersonname, final String Salespersonemail, final String Password,
			final String Salespersonphone, final String device) throws SQLException
	{

		Cursor mCursor = db
				.rawQuery("INSERT INTO tbl_salesperson (idsalesperson,idclient,Salespersonname,Salespersonemail,Password,Salespersonphone,device) VALUES('" + idsalesperson + "','"
						+ idclient + "','" + Salespersonname + "','" + Salespersonemail + "','" + Password + "','" + Salespersonphone + "','" + device + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor updateTeamMaster(final String idsalesperson, final String idclient, final String Salespersonname, final String Salespersonemail, final String Password,
			final String Salespersonphone, final String device)
	{

		Cursor mCursor = db.rawQuery("update tbl_salesperson set idsalesperson='" + idsalesperson + "',idclient='" + idclient + "',Salespersonname='" + Salespersonname
				+ "',Salespersonemail='" + Salespersonemail + "',Password='" + Password + "',Salespersonphone='" + Salespersonphone + "',device='" + device
				+ "' where idsalesperson='" + idsalesperson + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public Cursor deleteTeamMaster(String idsalesperson)
	{
		Cursor mCursor = db.rawQuery("delete from tbl_salesperson where idsalesperson='" + idsalesperson + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getTeamMaster() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from tbl_salesperson where device != 'android-delete'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor postTeamMaster() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from tbl_salesperson where device='android' or device='android-update' or device='android-delete'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getTeamPasswordUpdate(final String password) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update tbl_salesperson set Password='" + password + "' where id != ''", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor deleteSalesPerson(final String Salespersonname)
	{

		Cursor mCursor = db.rawQuery("delete from tbl_salesperson where lower(Salespersonname)='" + Salespersonname + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor deleteSalesPersonDevice(final String Salespersonname)
	{

		Cursor mCursor = db.rawQuery("update tbl_salesperson set device='android-delete' where lower(Salespersonname)='" + Salespersonname + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// -------------------------------------------

	public Cursor insertTableMaster(String TblId, String TableCode, String TableName, String TableStatus) throws SQLException
	{

		Cursor mCursor = db.rawQuery("INSERT INTO TableMas (TblId,TableCode,TableName,TableStatus) VALUES('" + TblId + "','" + TableCode + "','" + TableName + "','" + TableStatus
				+ "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	private static final String DATABASE_TableMast = "TableMas";
	private static final String KEY_ROWID1 = "TblId";

	public Boolean updateTableMaster(String TblId, String TableCode, String TableName, String TableStatus)
	{
		ContentValues initialValues = new ContentValues();

		initialValues.put("TblId", TblId);
		initialValues.put("TableCode", TableCode);
		initialValues.put("TableName", TableName);
		initialValues.put("TableStatus", TableStatus);

		return db.update(DATABASE_TableMast, initialValues, KEY_ROWID1 + "='" + TblId + "'", null) > 0;

	}

	public Cursor deleteTableMaster(String TblId)
	{
		Cursor mCursor = db.rawQuery("delete from TableMas where TblId='" + TblId + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getTableMaster() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from TableMas", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// -------------------------------------------------------

	// ITEM CATEGORY

	public Cursor insertItmCatMaster(String CatID, String CatCd, String CatNm) throws SQLException
	{

		Cursor mCursor = db.rawQuery("INSERT INTO ItmCatMas (CatID,CatCd,CatNm) VALUES('" + CatID + "','" + CatCd + "','" + CatNm + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	private static final String DATABASE_ItemCatMast = "ItmCatMas";

	private static final String KEY_ROWID_ItemCat = "CatID";

	public Boolean updateItmCatMaster(String CatID, String CatCd, String CatNm)
	{
		ContentValues initialValues = new ContentValues();

		initialValues.put("CatID", CatID);
		initialValues.put("CatCd", CatCd);
		initialValues.put("CatNm", CatNm);

		return db.update(DATABASE_ItemCatMast, initialValues, KEY_ROWID_ItemCat + "='" + CatID + "'", null) > 0;

	}

	public Cursor deleteItmCatMaster(String CatID)
	{
		Cursor mCursor = db.rawQuery("delete from ItmCatMas where CatID='" + CatID + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// ---------------------------------------------------

	// ITEM MASTER

	public Cursor insertItemMaster(String ItmID, String ItmCd, String ItmNm, String ItmCatID, String RedPrice, String LrgPrice, String UOM) throws SQLException
	{

		Cursor mCursor = db.rawQuery("INSERT INTO ItemMaster (ItemID,ItemCode,ItemName,CategoryID,PriceR,PriceL,UOM) VALUES('" + ItmID + "','" + ItmCd + "','" + ItmNm + "','"
				+ ItmCatID + "','" + RedPrice + "','" + LrgPrice + "','" + UOM + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	private static final String DATABASE_ItemMast = "ItemMaster";

	private static final String KEY_ROWID_Item = "ItemID";

	public Boolean updateItemMaster(String ItemID, String ItemCode, String ItemName, String CategoryID, String PriceR, String PriceL, String UOM)
	{
		ContentValues initialValues = new ContentValues();

		initialValues.put("ItemID", ItemID);
		initialValues.put("ItemCode", ItemCode);
		initialValues.put("ItemName", ItemName);
		initialValues.put("CategoryID", CategoryID);
		initialValues.put("PriceR", PriceR);
		initialValues.put("PriceL", PriceL);
		initialValues.put("UOM", UOM);

		return db.update(DATABASE_ItemMast, initialValues, KEY_ROWID_Item + "='" + ItemID + "'", null) > 0;

	}

	public Cursor deleteItemMaster(String CatID)
	{
		Cursor mCursor = db.rawQuery("delete from ItemMaster where ItemID='" + CatID + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// ----------------------------------------------------

	public Cursor insertSubItemMethod(String ItmID, String ItmNm) throws SQLException
	{

		String query = "INSERT INTO SubItem_Method (ItmID,ItmNm) VALUES('" + ItmID + "','" + ItmNm + "')";

		Log.e("", "query: " + query);

		Cursor mCursor = db.rawQuery(query, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	private static final String DATABASE_SubItemMethod = "SubItem_Method";

	private static final String KEY_ROWID_SubItemMethod = "ItmID";

	public Boolean updateSubItemMethod(String ItmID, String ItmNm)
	{
		ContentValues initialValues = new ContentValues();

		initialValues.put("ItmID", ItmID);
		initialValues.put("ItmNm", ItmNm);

		return db.update(DATABASE_SubItemMethod, initialValues, KEY_ROWID_SubItemMethod + "='" + ItmID + "'", null) > 0;

	}

	public Cursor deleteSubItemMethod(String ItmID)
	{
		Cursor mCursor = db.rawQuery("delete from SubItem_Method where ItmID='" + ItmID + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getSubItemMethod() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from SubItem_Method", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// //

	public Cursor insertSubItemSauces(String ItmID, String ItmNm) throws SQLException
	{

		Cursor mCursor = db.rawQuery("INSERT INTO SubItem_Sauces (ItmID,ItmNm) VALUES('" + ItmID + "','" + ItmNm + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	private static final String DATABASE_SubItemSauces = "SubItem_Sauces";

	private static final String KEY_ROWID_SubItemSauces = "ItmID";

	public Boolean updateSubItemSauces(String ItmID, String ItmNm)
	{
		ContentValues initialValues = new ContentValues();

		initialValues.put("ItmID", ItmID);
		initialValues.put("ItmNm", ItmNm);

		return db.update(DATABASE_SubItemSauces, initialValues, KEY_ROWID_SubItemSauces + "='" + ItmID + "'", null) > 0;

	}

	public Cursor deleteSubItemSauces(String ItmID)
	{
		Cursor mCursor = db.rawQuery("delete from SubItem_Sauces where ItmID='" + ItmID + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getSubItemSauces() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from SubItem_Sauces", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// ////

	public Cursor insertSubItemSideDish(String ItmID, String ItmNm) throws SQLException
	{

		Cursor mCursor = db.rawQuery("INSERT INTO SubItem_SideDish (ItmID,ItmNm) VALUES('" + ItmID + "','" + ItmNm + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	private static final String DATABASE_SubItemSideDish = "SubItem_SideDish";

	private static final String KEY_ROWID_SubItemSideDish = "ItmID";

	public Boolean updateSubItemSideDish(String ItmID, String ItmNm)
	{
		ContentValues initialValues = new ContentValues();

		initialValues.put("ItmID", ItmID);
		initialValues.put("ItmNm", ItmNm);

		return db.update(DATABASE_SubItemSideDish, initialValues, KEY_ROWID_SubItemSideDish + "='" + ItmID + "'", null) > 0;

	}

	public Cursor deleteSubItemSideDish(String ItmID)
	{
		Cursor mCursor = db.rawQuery("delete from SubItem_SideDish where ItmID='" + ItmID + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getSubItemSideDish() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from SubItem_SideDish", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// ---------------------------------------------------

	public boolean checkUserExists(String username, String pass) throws SQLException
	{

		open();

		Cursor mCursor = db.rawQuery("select * from UserMas where Username='" + username + "' and Password='" + pass + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
			if (mCursor.getCount() > 0)
			{

				mCursor.close();

				return true;
			}
		}

		mCursor.close();

		close();

		return false;
	}

	public Cursor getItemCategory() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from ItmCatMas order by CatNm asc", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getItem(String catid) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from ItemMaster where CategoryID='" + catid + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor insertOrderMaster(String itemid, String qty1, String qty2, String methoditemkey, String sauceitemkey, String sidedishitemkey, String categoryid, String ItemName,
			String tableid, String device_id, String group) throws SQLException
	{

		Cursor mCursor = db.rawQuery("INSERT INTO order_detail (itemid,qty1,qty2,methoditemkey,sauceitemkey,sidedishitemkey,categoryid,ItemName,tableno,deviceid,grp) VALUES('"
				+ itemid + "','" + qty1 + "','" + qty2 + "','" + methoditemkey + "','" + sauceitemkey + "','" + sidedishitemkey + "','" + categoryid + "','" + ItemName + "','"
				+ tableid + "','" + device_id + "','" + group + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public String getItemIDbyItemName(String item_name) throws SQLException
	{

		open();

		String id = "";

		Cursor mCursor = db.rawQuery("select * from SubItem_Method where ItmNm='" + item_name + "'", null);

		if (mCursor != null)
		{

			mCursor.moveToFirst();

			if (mCursor.getCount() > 0)
			{

				id = mCursor.getString(0);

				mCursor.close();

				close();

				return id;
			}
		}

		mCursor.close();

		close();

		return id;

	}

	public String getGroupbyTableID(String tableID) throws SQLException
	{

		open();

		String group = "";

		Cursor mCursor = db.rawQuery("select grp from order_detail where tableno='" + tableID + "'", null);

		if (mCursor != null)
		{

			mCursor.moveToFirst();

			if (mCursor.getCount() > 0)
			{

				group = mCursor.getString(0);

				mCursor.close();

				close();

				return group;
			}
		}

		mCursor.close();

		close();

		return group;

	}

	public String getSauceItemIDbyItemName(String item_name) throws SQLException
	{

		open();

		String id = "";

		Cursor mCursor = db.rawQuery("select * from SubItem_Sauces where ItmNm='" + item_name + "'", null);

		if (mCursor != null)
		{

			mCursor.moveToFirst();

			if (mCursor.getCount() > 0)
			{

				id = mCursor.getString(0);

				mCursor.close();

				close();

				return id;
			}
		}

		mCursor.close();

		close();

		return id;

	}

	//

	public String getSideDishItemIDbyItemName(String item_name) throws SQLException
	{

		open();

		String id = "";

		Cursor mCursor = db.rawQuery("select * from SubItem_SideDish where ItmNm='" + item_name + "'", null);

		if (mCursor != null)
		{

			mCursor.moveToFirst();

			if (mCursor.getCount() > 0)
			{

				id = mCursor.getString(0);

				mCursor.close();

				close();

				return id;
			}
		}

		mCursor.close();

		close();

		return id;

	}

	public Cursor getOrder() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from order_detail ", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getOrder(String tableID) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from order_detail where tableno='" + tableID + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getOrderCategory(String tableno) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select DISTINCT categoryid from order_detail where tableno='" + tableno + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getOrderData(String categoryid, String tableID) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from order_detail where categoryid = '" + categoryid + "' and tableno='" + tableID + "' order by itemid", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public String getCategoryNamebyID(String categoryid) throws SQLException
	{

		open();

		String id = "";

		Cursor mCursor = db.rawQuery("select * from ItmCatMas where CatID='" + categoryid + "'", null);

		if (mCursor != null)
		{

			mCursor.moveToFirst();

			if (mCursor.getCount() > 0)
			{

				id = mCursor.getString(2);

				mCursor.close();

				close();

				return id;
			}
			else
				return id;

		}
		else
		{
			return id;

		}

	}

	public String getItemCodebyItemId(String itemid) throws SQLException
	{

		String id = "";

		Cursor mCursor = db.rawQuery("select * from ItemMaster where ItemID='" + itemid + "'", null);

		if (mCursor != null)
		{

			mCursor.moveToFirst();

			if (mCursor.getCount() > 0)
			{

				id = mCursor.getString(1);

				mCursor.close();

				return id;

			}
			else
				return id;

		}
		else
		{
			return id;

		}

	}

	public String getMethodbyID(String itemid) throws SQLException
	{

		String id = "";

		Cursor mCursor = db.rawQuery("select * from SubItem_Method where ItmID='" + itemid + "'", null);

		if (mCursor != null)
		{

			mCursor.moveToFirst();

			if (mCursor.getCount() > 0)
			{

				id = mCursor.getString(1);

				mCursor.close();

				return id;
			}
			else
				return id;

		}
		else
		{
			return id;

		}

	}

	public String getSaucebyID(String itemid) throws SQLException
	{

		String id = "";

		Cursor mCursor = db.rawQuery("select * from SubItem_Sauces where ItmID='" + itemid + "'", null);

		if (mCursor != null)
		{

			mCursor.moveToFirst();

			if (mCursor.getCount() > 0)
			{

				id = mCursor.getString(1);

				mCursor.close();

				return id;
			}
			else
				return id;

		}
		else
		{
			return id;

		}

	}

	public String getSideDishbyID(String itemid) throws SQLException
	{

		String id = "";

		Cursor mCursor = db.rawQuery("select * from SubItem_SideDish where ItmID='" + itemid + "'", null);

		if (mCursor != null)
		{

			mCursor.moveToFirst();

			if (mCursor.getCount() > 0)
			{

				id = mCursor.getString(1);

				mCursor.close();

				return id;
			}
			else
				return id;

		}
		else
		{
			return id;

		}

	}

	public Cursor flushUserMaster()
	{
		Cursor mCursor = db.rawQuery("delete from UserMas", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor flushItemMaster()
	{
		Cursor mCursor = db.rawQuery("delete from ItemMaster", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor flushTimeStampMaster()
	{
		Cursor mCursor = db.rawQuery(
				"update timestamp set usermas=' ', tabletimestamp=' ', itemcategorytime=' ', itemdatetime=' ' , subitemmethod=' ', subitemsauces=' ' , subitemsidedish=' '", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor flushTableMaster()
	{
		Cursor mCursor = db.rawQuery("delete from TableMas", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor flushItmCatMas()
	{
		Cursor mCursor = db.rawQuery("delete from ItmCatMas", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor flushSetting()
	{
		Cursor mCursor = db.rawQuery("update settings set api_path=' ', device_id=' ', next_order_no=' '", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor flushSubItemMethod()
	{
		Cursor mCursor = db.rawQuery("delete from SubItem_Method", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor flushSubItemSauces()
	{
		Cursor mCursor = db.rawQuery("delete from SubItem_Sauces", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor flushSubItemSideDish()
	{
		Cursor mCursor = db.rawQuery("delete from SubItem_SideDish", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor flushOrderDetail()
	{
		Cursor mCursor = db.rawQuery("delete from order_detail", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;

	}

	public Cursor updateOrderStatus(String OrderNo, String ItmKy, String Status, String DeviceID)
	{

		Cursor mCursor = db.rawQuery("update order_detail set status='" + Status + "' where deviceid='" + DeviceID + "' and itemid='" + ItmKy + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor getOrderTable() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select tableno from order_detail order by tableno", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// //////////===========================================================================================

	public Cursor get_item_data(String item_type) throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from coz_cart where item_type='" + item_type + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor get_item_for_drink_food() throws SQLException
	{

		Cursor mCursor = db.rawQuery("select * from coz_cart where item_type='Food Menu' or item_type='Drinks Menu'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor delete_item_cart(String item_id) throws SQLException
	{

		Cursor mCursor = db.rawQuery("delete from coz_cart where item_id='" + item_id + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor update_quantity(String item_id, String size, String quantity) throws SQLException
	{

		Cursor mCursor = db.rawQuery("Update coz_cart set qty='" + quantity + "'  where item_id='" + item_id + "'  and size='" + size + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor delete_cart()
	{

		Cursor mCursor = db.rawQuery("delete from coz_cart", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor row_query(String query) throws SQLException
	{

		Cursor mCursor = db.rawQuery(query, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// ----------------------------insert---------------------------------------------------------------------------------------------
	public Cursor insert_coz_club_mst(String google_plus, String twitter, String facebook, String club_paypal_email, String club_id, String club_name, String club_email,
			String club_password, String club_address, String club_city_id, String club_area, String club_zip, String club_phone, String club_lat, String club_long,
			String club_is_active, String club_is_delete)
	{
		Cursor mCursor = db
				.rawQuery(
						"INSERT INTO coz_club_mst (google_plus,twitter,facebook,club_paypal_email,club_id,club_name,club_email,club_password,club_address,club_city_id,club_area,club_zip,club_phone,club_lat,club_long,club_is_active,club_is_delete) VALUES('"
								+ google_plus
								+ "','"
								+ twitter
								+ "','"
								+ facebook
								+ "','"
								+ club_paypal_email
								+ "','"
								+ club_id
								+ "','"
								+ club_name
								+ "','"
								+ club_email
								+ "','"
								+ club_password
								+ "','"
								+ club_address
								+ "','"
								+ club_city_id
								+ "','"
								+ club_area
								+ "','"
								+ club_zip
								+ "','"
								+ club_phone
								+ "','"
								+ club_lat
								+ "','" + club_long + "','" + club_is_active + "','" + club_is_delete + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;

	}

	// ------
	public Cursor insert_coz_club_time_mst(String club_time_id, String club_time_club_id, String club_time_day, String club_open_time, String club_close_time)
	{
		Cursor mCursor = db.rawQuery("INSERT INTO coz_club_time_mst (club_time_id,club_time_club_id,club_time_day,club_open_time,club_close_time) VALUES('" + club_time_id + "','"
				+ club_time_club_id + "','" + club_time_day + "','" + club_open_time + "','" + club_close_time + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;

	}

	// ------
	public Cursor insert_coz_added_privelege(String privelege_added_is_active, String privelege_added_id, String privelege_added_club_id, String privilege_added)
	{
		Cursor mCursor = db.rawQuery("INSERT INTO coz_added_privelege (privelege_added_is_active,privelege_added_id,privelege_added_club_id,privilege_added_privilege_id) VALUES('"
				+ privelege_added_is_active + "','" + privelege_added_id + "','" + privelege_added_club_id + "','" + privilege_added + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// -----
	public Cursor insert_coz_club_privilege(String club_privilege_id, String club_privilege)
	{
		Cursor mCursor = db.rawQuery("INSERT INTO coz_club_privilege (club_privilege_id,club_privilege) VALUES('" + club_privilege_id + "','" + club_privilege + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// -----delete table------
	public Cursor delete_coz_club_mst(String club_id)
	{
		Cursor mCursor = db.rawQuery("delete from coz_club_mst where club_id='" + club_id + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor delete_coz_club_time_mst(String club_time_id)
	{
		Cursor mCursor = db.rawQuery("delete from coz_club_time_mst where club_time_id='" + club_time_id + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor delete_coz_added_privelege(String privelege_added_id)
	{
		Cursor mCursor = db.rawQuery("delete from coz_added_privelege where privelege_added_id='" + privelege_added_id + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public Cursor delete_coz_club_privilege(String club_privilege_id)
	{
		Cursor mCursor = db.rawQuery("delete from coz_club_privilege where club_privilege_id='" + club_privilege_id + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// --------update table -------
	// update table section //

	private static final String DATABASE_TABLE1 = "coz_club_mst";

	private static final String DATABASE_TABLE3 = "coz_added_privelege";
	private static final String DATABASE_TABLE4 = "coz_club_privilege";

	private static final String KEY_ROWID3 = "privelege_added_id";
	private static final String KEY_ROWID4 = "club_privilege_id";

	public Boolean update_coz_club_mst(String google_plus, String twitter, String facebook, String club_paypal_email, String club_id, String club_name, String club_email,
			String club_password, String club_address, String club_city_id, String club_area, String club_zip, String club_phone, String club_lat, String club_long,
			String club_is_active, String club_is_delete)
	{
		ContentValues initialValues = new ContentValues();

		initialValues.put("google_plus", google_plus);
		initialValues.put("twitter", twitter);
		initialValues.put("facebook", facebook);

		initialValues.put("club_paypal_email", club_paypal_email);
		// initialValues.put("club_id", club_id);
		initialValues.put("club_name", club_name);
		initialValues.put("club_email", club_email);
		initialValues.put("club_password", club_password);
		initialValues.put("club_address", club_address);
		initialValues.put("club_city_id", club_city_id);
		initialValues.put("club_area", club_area);
		initialValues.put("club_zip", club_zip);
		initialValues.put("club_phone", club_phone);
		initialValues.put("club_lat", club_lat);
		initialValues.put("club_long", club_long);
		initialValues.put("club_is_active", club_is_active);
		initialValues.put("club_is_delete", club_is_delete);

		System.out.println("Updation of coz_club_mst table");

		return db.update(DATABASE_TABLE1, initialValues, KEY_ROWID1 + "='" + club_id + "'", null) > 0;

	}

	public Boolean update_coz_added_privelege(String privelege_added_is_active, String privelege_added_id, String privelege_added_club_id, String privilege_added_privilege_id)
	{
		ContentValues initialValues = new ContentValues();

		initialValues.put("privelege_added_is_active", privelege_added_is_active);
		// initialValues.put("privelege_added_id", privelege_added_id);
		initialValues.put("privelege_added_club_id", privelege_added_club_id);
		initialValues.put("privilege_added_privilege_id", privilege_added_privilege_id);

		System.out.println("Updation of coz_added_privelege table");

		return db.update(DATABASE_TABLE3, initialValues, KEY_ROWID3 + "='" + privelege_added_id + "'", null) > 0;
	}

	public Boolean update_coz_club_privilege(String club_privilege_id, String club_privilege)
	{
		ContentValues initialValues = new ContentValues();

		// initialValues.put("club_privilege_id",club_privilege_id);
		initialValues.put("club_privilege", club_privilege);

		System.out.println("Updation of coz_club_privilege table");

		return db.update(DATABASE_TABLE4, initialValues, KEY_ROWID4 + "='" + club_privilege_id + "'", null) > 0;
	}

	public Cursor updateProductDevice(final String idproduct, final String productName, final String device) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update Productdetails set idproduct='" + idproduct + "', device='" + device + "' where productName='" + productName + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor updateSalesTeamDevice(final String idsalesperson, final String Salespersonname, final String device) throws SQLException
	{

		Cursor mCursor = db.rawQuery("update tbl_salesperson set idsalesperson='" + idsalesperson + "', device='" + device + "' where Salespersonname='" + Salespersonname + "'",
				null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	/*
	 * //@SuppressWarnings({ "deprecation", "null" }) public List<VisitorsDB>
	 * getAllVisitors(String idtradeshow2,String dt)throws SQLException {
	 * List<VisitorsDB> visitorsList = new ArrayList<VisitorsDB>(); Cursor
	 * c=null; if(!idtradeshow2.equals("")){ c =
	 * db.rawQuery("select * from VisitorDetails where idtradeshow='"+
	 * idtradeshow2 + "' order by datetime desc", null); if (c.getCount() > 0) {
	 * 
	 * c.moveToFirst();
	 * 
	 * do { VisitorsDB visitors = new VisitorsDB();
	 * visitors.setId(c.getString(0));
	 * visitors.setVisitorUniqueId(c.getString(1));
	 * visitors.setIdvisitor(c.getString(2));
	 * visitors.setFirstname(c.getString(3));
	 * visitors.setLastname(c.getString(4));
	 * visitors.setOrganization(c.getString(5));
	 * visitors.setPhone1(c.getString(6)); visitors.setVip(c.getString(7));
	 * visitors.setDatetime(c.getString(8)); visitors.setDt(c.getString(9));
	 * visitors.setVisitorcardpath(c.getString(10));
	 * visitors.setIdtradeshow(c.getString(11));
	 * visitors.setIdsalesperson(c.getString(12));
	 * visitors.setScoring(c.getString(13));
	 * visitors.setContactmethod(c.getString(14));
	 * visitors.setPurchaseinfluence(c.getString(15));
	 * visitors.setPurchasetimeframe(c.getString(16));
	 * visitors.setProductinterests(c.getString(17));
	 * visitors.setFollowupaction(c.getString(18));
	 * visitors.setFollowuptimeframe(c.getString(19));
	 * visitors.setNotes(c.getString(20));
	 * visitors.setAssignedto(c.getString(21));
	 * visitors.setVisitedtime(c.getString(22));
	 * visitors.setDevice(c.getString(23));
	 * visitors.setSdcardimagepath(c.getString(24)); visitorsList.add(visitors);
	 * 
	 * } while (c.moveToNext());
	 * 
	 * } }else {
	 * 
	 * c = db.rawQuery("select * from VisitorDetails where dt='" + dt +
	 * "' order by datetime desc", null);
	 * 
	 * if (c.getCount() > 0) {
	 * 
	 * c.moveToFirst();
	 * 
	 * do { VisitorsDB visitors = new VisitorsDB();
	 * visitors.setId(c.getString(0));
	 * visitors.setVisitorUniqueId(c.getString(1));
	 * visitors.setIdvisitor(c.getString(2));
	 * visitors.setFirstname(c.getString(3));
	 * visitors.setLastname(c.getString(4));
	 * visitors.setOrganization(c.getString(5));
	 * visitors.setPhone1(c.getString(6)); visitors.setVip(c.getString(7));
	 * visitors.setDatetime(c.getString(8)); visitors.setDt(c.getString(9));
	 * visitors.setVisitorcardpath(c.getString(10));
	 * visitors.setIdtradeshow(c.getString(11));
	 * visitors.setIdsalesperson(c.getString(12));
	 * visitors.setScoring(c.getString(13));
	 * visitors.setContactmethod(c.getString(14));
	 * visitors.setPurchaseinfluence(c.getString(15));
	 * visitors.setPurchasetimeframe(c.getString(16));
	 * visitors.setProductinterests(c.getString(17));
	 * visitors.setFollowupaction(c.getString(18));
	 * visitors.setFollowuptimeframe(c.getString(19));
	 * visitors.setNotes(c.getString(20));
	 * visitors.setAssignedto(c.getString(21));
	 * visitors.setVisitedtime(c.getString(22));
	 * visitors.setDevice(c.getString(23));
	 * visitors.setSdcardimagepath(c.getString(24)); visitorsList.add(visitors);
	 * 
	 * } while (c.moveToNext());
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return visitorsList; }
	 */

	public void deleteDataFromAllTables()
	{
		// Cursor mCursor =

		db.execSQL("delete from FollowupactionDetails");
		db.execSQL("delete from Productdetails");
		db.execSQL("delete from VisitorDetails");
		db.execSQL("delete from dashboard");
		db.execSQL("delete from login");
		db.execSQL("delete from tbl_salesperson");
		db.execSQL("delete from tbl_tradeshow");
		db.execSQL("delete from timeframedetails");
		db.execSQL("delete from timestamp");

		db.execSQL("INSERT INTO timestamp (product_timestamp,visitor_timestamp,salesteam_timestamp,timeframe,followupaction) VALUES(' ',' ',' ',' ',' ')");
		/*
		 * db.delete("FollowupactionDetails", null, null);
		 * db.delete("Productdetails", null, null); db.delete("VisitorDetails",
		 * null, null); db.delete("dashboard", null, null); db.delete("login",
		 * null, null); db.delete("tbl_salesperson", null, null);
		 * db.delete("tbl_tradeshow", null, null); db.delete("timeframedetails",
		 * null, null); db.delete("timestamp", null, null);
		 */

		Log.d("bis", "deleted");

	}

	// ==========================================================================================

	// delete categories
	public Cursor delteCategories()
	{

		String query = "delete from categories";

		Cursor mCursor = db.rawQuery(query, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		// Log.d("bis", "deleted cate " + mCursor.getCount());
		return mCursor;
	}

	// insertCategories

	public Cursor insertCategories(int _cid, String _cname, String _curl)
	{

		Cursor mCursor = db.rawQuery("INSERT INTO categories (cid,cname,curl) VALUES('" + _cid + "','" + _cname + "','" + _curl + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		Cursor cc = db.rawQuery("select * from categories", null);
		// Log.d("bis", "cate :" + _cid + _cname + ", " + _curl + " size" +
		// cc.getCount());
		return mCursor;
	}

	// retriving all categories
	public Cursor retriveallcategories()
	{

		Cursor mCursor = db.rawQuery("select * from categories", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ============================================================================================================
	// delete products
	public Cursor delteproducts()
	{

		String query = "delete from products";

		Cursor mCursor = db.rawQuery(query, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		// Log.d("bis", "deleted products " + mCursor.getCount());
		return mCursor;
	}

	// insertproducts
	public Cursor insertproducts(int _pid, int _cid, String _pname, String _pinfo, String _purlTh, int _visit_status_count, String _pcost)
	{

		Cursor mCursor = db.rawQuery("INSERT INTO products (pid,cid,pname,pinfo,purlTh,visit_status_count,pcost) VALUES('" + _pid + "','" + _cid + "','" + _pname + "','" + _pinfo
				+ "','" + _purlTh + "','" + _visit_status_count + "','" + _pcost + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// retriving all products
	public Cursor retriveallproducts()
	{

		Cursor mCursor = db.rawQuery("select * from products", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor retriveallproductsByMatch(String query)
	{

		Cursor mCursor = db.rawQuery("select * from products where pid like '" + query + "%' or pname like'" + query + "%'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	// retriving all categories
	public Cursor retriveproductsbycategory(int _cid)
	{

		Cursor mCursor = db.rawQuery("select * from products where cid='" + _cid + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// retriving all categories
	public Cursor retriveproductbypid(int _pid)
	{

		Cursor mCursor = db.rawQuery("select * from products where pid='" + _pid + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ==============================================================================================================================
	// delete productimages
	public Cursor delteproductImages()
	{

		String query = "delete from productimages";

		Cursor mCursor = db.rawQuery(query, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		// Log.d("bis", "deleted productImages " + mCursor.getCount());
		return mCursor;
	}

	// insertproducts
	public Cursor insertproductImages(int _pid, String _purl)
	{

		// _purl="abc";

		// Log.d("bis", "_pid and _purl ="+_pid+"  "+_purl);
		Cursor mCursor = db.rawQuery("INSERT INTO productimages (pid,purl) VALUES('" + _pid + "','" + _purl + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;

	}

	// insertproducts
	public Cursor retriveProductImages(int _pid)
	{
		Log.d("bis", "pid== " + _pid);
		Cursor mcursor = db.rawQuery("select * from productimages where pid='" + _pid + "'", null);

		if (mcursor != null)
		{
			mcursor.moveToFirst();
		}
		return mcursor;
	}

	// ==============================================================================================================================
	// delete buyers
	public Cursor deltebuyers()
	{

		String query = "delete from buyer";

		Cursor mCursor = db.rawQuery(query, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		// Log.d("bis", "deleted productImages " + mCursor.getCount());
		return mCursor;
	}

	// insertproducts
	public Cursor insertbuyer(int _cid, int _pid, String _videostringid, String _imagestringid, int _rotaionflag, int _time, String _buyername, int _purchasestatus, String _sid,
			String _pname, int _count, String _infostatus)
	{

		// _purl="abc";

		// Log.d("bis", "_pid and _purl ="+_pid+"  "+_purl);
		Cursor mCursor = db.rawQuery("INSERT INTO buyer (cid,pid,videostringid,imagestringid,rotaionflag,time,buyername,purchasestatus,sessionid,pname,count,infostatus) VALUES('"
				+ _cid + "','" + _pid + "','" + _videostringid + "','" + _imagestringid + "','" + _rotaionflag + "','" + _time + "','" + _buyername + "','" + _purchasestatus
				+ "','" + _sid + "','" + _pname + "','" + _count + "','" + _infostatus + "')", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		Log.d("bis", "inserting ing pid= " + _pid + "  " + "sessionid= " + _sid + " count=" + _count);

		return mCursor;

	}

	public Cursor updatetbuyer(int _cid, int _pid, String _videostringid, String _imagestringid, int _rotaionflag, int _time, String _buyername, int _purchasestatus, String _sid,
			String _pname, int _count)
	{

		// _purl="abc";

		Cursor mCursor = db.rawQuery("update buyer set cid='" + _cid + "', pid='" + _pid + "',videostringid='" + _videostringid + "',imagestringid='" + _imagestringid
				+ "',rotaionflag='" + _rotaionflag + "',time='" + _time + "',buyername='" + _buyername + "',purchasestatus='" + _purchasestatus + "',sessionid = '" + _sid
				+ "' ,pname='" + _pname + "',count='" + _count + "'where pid='" + _pid + "' and sessionid='" + _sid + "' ", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		Log.d("bis", "updating existing pid= " + _pid + "  " + "sessionid= " + _sid + " count=" + _count);
		return mCursor;

	}

	public Cursor retrive()
	{
		Cursor mCursor = db.rawQuery("select * from buyer where infostatus='" + "android" + "' ", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;

	}

	public Cursor retriveOnlyOne(String _sid)
	{
		Cursor mCursor = db.rawQuery("select * from buyer where infostatus='" + "android" + "' and sessionid='" + _sid + "' ", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;

	}

	public Cursor retrivemaxseesionid()
	{
		Cursor mCursor = db.rawQuery("SELECT MAX(sessionid) FROM buyer", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;

	}

	public Cursor retriveByerInfobyseesionid(String _sessionid)
	{

		// Log.d("bis","_sessionid "+_sessionid);
		Cursor mCursor = db.rawQuery("SELECT pname,pid FROM buyer WHERE sessionid='" + _sessionid + "'", null);
		// Log.d("bis","mCursor_sessionid "+mCursor.getCount());
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;

	}

	public Cursor checkduplicateproductsforbuyer(int pid, String _sessionid)
	{
		Cursor mCursor = db.rawQuery("SELECT * FROM buyer WHERE sessionid='" + _sessionid + "' and pid='" + pid + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor updateProductPurchaseStatus(String tableusersessionid, Integer _pid, int flag)
	{
		Cursor mCursor = db.rawQuery("update buyer set purchasestatus='" + flag + "',infostatus='" + " " + "'where pid='" + _pid + "' and sessionid='" + tableusersessionid + "' ",
				null);

		//Log.d("bis", "updating details" + tableusersessionid + "," + _pid + "," + flag);
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor getProductPurchaseStatus(String tableusersessionid, Integer _pid)
	{

		Cursor mCursor = db.rawQuery("SELECT purchasestatus FROM buyer WHERE sessionid='" + tableusersessionid + "' and pid='" + _pid + "'", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();

		}

		//Log.d("bis", "record count= " + mCursor.getCount());

		return mCursor;

	}

	public Cursor updateBuyerprofile(String tableusersessionid, String bname, String bphone, String bmail, String age, String gender, String _infostatus)
	{

		Cursor mCursor = db.rawQuery("update buyer set buyername='" + bname + "',bphone='" + bphone + "',bmail='" + bmail + "',bage='" + age + "',bgender='" + gender
				+ "',infostatus='" + _infostatus + "'   where  sessionid='" + tableusersessionid + "' ", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public Cursor updateBuyerinfoStatus(String tableusersessionid, Integer _pid, String status)
	{
		Cursor mCursor = db.rawQuery("update buyer set infostatus='" + status + "'where pid='" + _pid + "' and sessionid='" + tableusersessionid + "' ", null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor delete_buyer(String sessionid, int _pid)
	{

		Cursor mCursor = db.rawQuery("delete from buyer where sessionid='" + sessionid + "' and  pid='" + _pid + "'", null);
		//Log.d("bis", "deleted");
		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}

		return mCursor;
	}

}