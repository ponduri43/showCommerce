package com.samskrut.showcommerce;

/*import java.util.ArrayList;

 import android.annotation.SuppressLint;
 import android.content.Context;
 import android.content.Intent;
 import android.os.Environment;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.View.OnClickListener;
 import android.view.ViewGroup;
 import android.webkit.MimeTypeMap;
 import android.webkit.URLUtil;
 import android.widget.BaseAdapter;
 import android.widget.ImageView;
 import android.widget.TextView;


 import com.samskrut.showcommerce.database.cateObject;
 import com.samskrut.showcommerce.utilities.BitmapImageLoader;
 import com.samskrut.showcommerce.utilities.InfoTracker;

 @SuppressLint({ "CutPasteId", "InflateParams" })
 public class CategoryGridViewAdapter extends BaseAdapter
 {

 // Declare Variables
 Context context;
 LayoutInflater inflater;

 private ArrayList<cateObject> clist;

 public CategoryGridViewAdapter(Context context, ArrayList<cateObject> _clist)
 {
 this.context = context;
 this.clist = _clist;
 inflater = LayoutInflater.from(context);

 }

 public class ViewHolder
 {

 ImageView cimage;
 TextView cname;

 }

 @Override
 public int getCount()
 {
 return clist.size();
 }

 @Override
 public Object getItem(int position)
 {
 return clist.get(position);
 }

 @Override
 public long getItemId(int position)
 {
 return position;
 }

 public View getView(final int position, View view, ViewGroup parent)
 {
 final ViewHolder holder;

 if (view == null)
 {
 holder = new ViewHolder();
 view = inflater.inflate(R.layout.category_grid_item, null);
 holder.cimage = (ImageView) view.findViewById(R.id.iv2);
 holder.cname = (TextView) view.findViewById(R.id.nametv);
 view.setTag(holder);
 }
 else
 {
 holder = (ViewHolder) view.getTag();
 }

 String url = clist.get(position).getCurl();

 //String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(url);
 //String filename = URLUtil.guessFileName(url, null, fileExtenstion);

 // Log.d("filename", "filename==" + filename);
 try
 {
 String imageInSD = Environment.getExternalStorageDirectory().toString() + "/showcommerce/categories/" + url + "";

 // String imageInSD = context.getExternalCacheDir().toString() +
 // "/commerce/categories/" + filename + "";
 BitmapImageLoader loadImage = new BitmapImageLoader(context, "Speaker");
 loadImage.loadBitmap(imageInSD, holder.cimage);
 }
 catch (Exception e)
 {

 }
 holder.cname.setText(clist.get(position).getCname());
 view.setOnClickListener(new OnClickListener()
 {

 @Override
 public void onClick(View arg0)
 {

 if (CategoryGridView.startSessionll.getVisibility() == 8)
 {

 if (InfoTracker.usersessionid > 0)
 {

 InfoTracker.resetValuesAtCategoryLevel();

 InfoTracker.cid = clist.get(position).getCid();

 }

 Intent intent = new Intent(context, ProductList.class);
 intent.putExtra("cid", clist.get(position).getCid());
 intent.putExtra("cname", clist.get(position).getCname());
 context.startActivity(intent);

 }
 }
 });

 return view;
 }
 }
 */

import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samskrut.showcommerce.database.cateObject;
import com.samskrut.showcommerce.utilities.BitmapImageLoader;

public class CategoryGridViewAdapter extends RecyclerView.Adapter<CategoryGridViewAdapter.CategoryGridViewAdapterViewHolder>
{

	private List<cateObject> cateObjectList;
	Context context;

	public CategoryGridViewAdapter(List<cateObject> contactList, Context context)
	{
		this.cateObjectList = contactList;
		this.context = context;
	}

	@Override
	public int getItemCount()
	{
		return cateObjectList.size();
	}

	@Override
	public void onBindViewHolder(CategoryGridViewAdapterViewHolder cgvaVH, int position)
	{
		cateObject ci = cateObjectList.get(position);
		cgvaVH.cname.setText(cateObjectList.get(position).getCname());

		String url = cateObjectList.get(position).getCurl();

		// String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(url);
		// String filename = URLUtil.guessFileName(url, null, fileExtenstion);

		// Log.d("filename", "filename==" + filename);
		try
		{
			String imageInSD = Environment.getExternalStorageDirectory().toString() + "/showcommerce/categories/" + url + "";

			// String imageInSD = context.getExternalCacheDir().toString() +
			// "/commerce/categories/" + filename + "";
			BitmapImageLoader loadImage = new BitmapImageLoader(context, "Speaker");
			loadImage.loadBitmap(imageInSD, cgvaVH.cimage);
		}
		catch (Exception e)
		{

		}

	}

	@Override
	public CategoryGridViewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_gridview_item_using_cardview, viewGroup, false);

		return new CategoryGridViewAdapterViewHolder(itemView);
	}

	public static class CategoryGridViewAdapterViewHolder extends RecyclerView.ViewHolder
	{

		protected ImageView cimage;
		protected TextView cname;

		public CategoryGridViewAdapterViewHolder(View view)
		{
			super(view);

			cimage = (ImageView) view.findViewById(R.id.iv2);
			cname = (TextView) view.findViewById(R.id.nametv);

		}

	}
}
