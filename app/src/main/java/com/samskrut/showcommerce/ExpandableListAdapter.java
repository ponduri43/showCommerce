package com.samskrut.showcommerce;

import java.util.HashMap;
import java.util.List;

import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.InfoTracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter
{

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	// ======================================================
	private List<Integer> _listDataHeadr_catids;
	private HashMap<String, List<Integer>> _listDataChildvalues2;
	DrawerLayout Drawer;

	public ExpandableListAdapter(Context context,DrawerLayout drawerLayout ,List<String> listDataHeader, List<Integer> listDataHeadr_catids, HashMap<String, List<String>> listChildData,
			HashMap<String, List<Integer>> listDataChildvalues2)
	{
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		this._listDataHeadr_catids = listDataHeadr_catids;
		this._listDataChildvalues2 = listDataChildvalues2;
		Drawer=drawerLayout;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon)
	{
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}

		TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

		txtListChild.setText(childText);

		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

				int catid = _listDataHeadr_catids.get(groupPosition);
				int pid = _listDataChildvalues2.get(_listDataHeader.get(groupPosition)).get(childPosition);
				String  pname=_listDataChild.get(_listDataHeader.get(groupPosition)).get(childPosition);
				
				Log.d("bis", "req id=" + catid + "pid=" + pid);
				//======================================================


				Constants.pid = pid;
				Constants.pname = pname;
				Constants.pcost ="4999";

				if (InfoTracker.usersessionid > 0)
				{

					InfoTracker.resetValuesAtProductLevel();

					InfoTracker.pid = pid;
					InfoTracker.pname = pname;
					InfoTracker.time = System.currentTimeMillis();

				}

				Drawer.closeDrawers();

				Intent intent = new Intent(_context, ProductOverView.class);

				_context.startActivity(intent);

			
				//=======================================================
			
			}
		});

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount()
	{
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}

		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
	//	lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}
}