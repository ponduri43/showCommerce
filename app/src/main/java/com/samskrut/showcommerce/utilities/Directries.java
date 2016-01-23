package com.samskrut.showcommerce.utilities;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class Directries
{
	
	Context _context;
	
	public static final String Anypic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Anypic/";

	public static final String picsDir = Anypic + "/Snapsof/";
	public static final String picsDir_th = picsDir + "/SnapsofTh/";
	public static final String downDir = Anypic + "/Snapson/";
	public static final String downDir_th = downDir + "SnapsonTh/";
	
	//albums
	public static final String Engagement = Anypic+ "/Engagement/";
	public static final String Engagement_th = Engagement+"EngagementTh/";
	
	public static final String Wedding = Anypic+ "/Wedding/";
	public static final String Wedding_th = Wedding+"WeddingTh/";
	
	public static final String Reception = Anypic+ "/Reception/";
	public static final String Reception_th = Reception+"ReceptionTh/";
	
	public static final String PreWedding = Anypic+ "/PreWedding/";
	public static final String PreWedding_th = PreWedding+"PreWeddingTh/";

	public static final String Mehendi = Anypic+ "/Mehendi/";
	public static final String Mehendi_th = Mehendi+"MehendiTh/";
	
	public static final String Sangeet = Anypic+ "/Sangeet/";
	public static final String Sangeet_th = Sangeet+"SangeetTh/";
	
	public static final String CocktailParty = Anypic+ "/CocktailParty/";
	public static final String CocktailParty_th = CocktailParty+"CocktailPartyTh/";
	
	public static final String CouplePhotoSession = Anypic+ "/CouplePhotoSession/";
	public static final String CouplePhotoSession_th = CouplePhotoSession+"CouplePhotoSessionTh/";
	
	public static final String AnypicOthers = Anypic+ "/AnypicOthers/";
	public static final String AnypicOthers_th = AnypicOthers+"AnypicOthersTh/";
	

	public Directries(Context context)
	{
		_context = context;

	}

	public void makeDirectries()
	{
		File anypic=new File(Anypic);
		anypic.mkdir();
		
		File offLineDBfolder = new File(picsDir);
		offLineDBfolder.mkdirs();

		File ofThumnails = new File(picsDir_th);
		ofThumnails.mkdirs();

		File dowloaddb = new File(downDir);
		dowloaddb.mkdirs();

		File dowloaddbth = new File(downDir_th);
		dowloaddbth.mkdirs();
		
		File dowloaddb_album= new File(Wedding);
		dowloaddb_album.mkdirs();
		File dowloaddb_album_th= new File(Wedding_th);
		dowloaddb_album_th.mkdirs();
		
		File Engagement_album= new File(Engagement);
		Engagement_album.mkdirs();
		File Engagement_album_th= new File(Engagement_th);
		Engagement_album_th.mkdirs();
		
		File Reception_album= new File(Reception);
		Reception_album.mkdirs();
		File Reception_album_th= new File(Reception_th);
		Reception_album_th.mkdirs();
		
		File PreWedding_album= new File(PreWedding);
		PreWedding_album.mkdirs();
		File PreWedding_album_th= new File(PreWedding_th);
		PreWedding_album_th.mkdirs();
		
		File Mehendi_album= new File(Mehendi);
		Mehendi_album.mkdirs();
		File Mehendi_album_th= new File(Mehendi_th);
		Mehendi_album_th.mkdirs();
		
		File Sangeet_album= new File(Sangeet);
		Sangeet_album.mkdirs();
		File Sangeet_album_th= new File(Sangeet_th);
		Sangeet_album_th.mkdirs();
		
		File CocktailParty_album= new File(CocktailParty);
		CocktailParty_album.mkdirs();
		File CocktailParty_album_th= new File(CocktailParty_th);
		CocktailParty_album_th.mkdirs();
		
		
		File CouplePhotoSession_album= new File(CouplePhotoSession);
		CouplePhotoSession_album.mkdirs();
		File CouplePhotoSession_album_th= new File(CouplePhotoSession_th);
		CouplePhotoSession_album_th.mkdirs();
		
		File AnypicOthers_album= new File(AnypicOthers);
		AnypicOthers_album.mkdirs();
		File AnypicOthers_album_th= new File(AnypicOthers_th);
		AnypicOthers_album_th.mkdirs();
		
	}
}
