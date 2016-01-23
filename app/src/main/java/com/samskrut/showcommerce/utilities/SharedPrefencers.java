package com.samskrut.showcommerce.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SharedPrefencers
{
	Context context;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;

	public SharedPrefencers(Context _context)
	{
		context = _context;
		prefs = context.getSharedPreferences("Logindetails", context.MODE_WORLD_WRITEABLE);
		editor = prefs.edit();
	}

	public ArrayList<String> getuserCredentails()
	{


		String username = prefs.getString("username", "");
		String password = prefs.getString("password", "");


		ArrayList<String> al=new ArrayList<>();
		al.add(username);
		al.add(password);
		return  al;

	}

	public String getPrefe()
	{


		String username = prefs.getString("username", "");
		String password = prefs.getString("password", "");

		if (username == "" && password == "")
		{
			return "firstime";
		}

		return "alreadysignedin";

	}


	public void savePrefs(String _username, String _password, int id)
	{


		editor.putString("username", _username);
		editor.putString("password", _password);
		editor.putBoolean("loggedin",true);
		editor.putInt("CustomerId", id);
		editor.commit();
	}

	public int getCustomerId()
	{
		int customerId = prefs.getInt("CustomerId", 0);
		return customerId;

	}
	public boolean getloggedinstatus()
	{
		return prefs.getBoolean("loggedin",false);

	}
	public void setloggedinstatus(Boolean b)
	{
		editor.putBoolean("loggedin",b);
		editor.commit();

	}
}
