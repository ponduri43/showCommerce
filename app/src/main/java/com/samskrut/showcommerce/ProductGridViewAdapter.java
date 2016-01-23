package com.samskrut.showcommerce;

import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samskrut.showcommerce.CategoryGridViewAdapter.CategoryGridViewAdapterViewHolder;
import com.samskrut.showcommerce.database.productObject;
import com.samskrut.showcommerce.utilities.BitmapImageLoader;

public class ProductGridViewAdapter extends RecyclerView.Adapter<ProductGridViewAdapter.ProductGridViewAdapterViewHolder>
{
	
	Context context;
	List<productObject> productList;

	public class ProductGridViewAdapterViewHolder extends RecyclerView.ViewHolder
	{
		
		protected ImageView pimage;
		protected TextView pname;
		protected TextView pcost;

		public ProductGridViewAdapterViewHolder(View view)
		{
			super(view);

			pimage = (ImageView) view.findViewById(R.id.iv2);
			pname = (TextView) view.findViewById(R.id.nametv);
			pcost = (TextView) view.findViewById(R.id.pricetv);		}

	}
	
	
	public ProductGridViewAdapter(Context _context, List<productObject> _productList)
	{
		context = _context;
		this.productList = _productList;
	}

	@Override
	public int getItemCount()
	{
		// TODO Auto-generated method stub
		return productList.size();
	}

	@Override
	public void onBindViewHolder(ProductGridViewAdapterViewHolder holder, int position)
	{
		
		String url = productList.get(position).getPurl();
		// Log.d("bis", "url==" + url);

		//String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(url);
		//String filename = URLUtil.guessFileName(url, null, fileExtenstion);
		// Log.d("bis", "filename= " + filename);
		// String imageInSD = context.getExternalCacheDir().toString() +
		// "/commerce/productsTh/" + filename + "";
		String imageInSD = Environment.getExternalStorageDirectory().toString() + "/showcommerce/productsTh/" + url + "";
		BitmapImageLoader loadImage = new BitmapImageLoader(context, "Speaker");
		loadImage.loadBitmap(imageInSD, holder.pimage);
		holder.pname.setText(productList.get(position).getPname());
		holder.pcost.setText("Rs." + productList.get(position).pcost);

		
	}

	@Override
	public ProductGridViewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_grid_item_using_cardview, viewGroup, false);

		return new ProductGridViewAdapterViewHolder(itemView);
	}
	
	
	
	
	
	/*
	List<productObject> productList;
	Context context;

	LayoutInflater inflater;
	String url = null;

	public ProductGridViewAdapter(Context _context, List<productObject> productList)
	{
		context = _context;
		inflater = LayoutInflater.from(context);
		this.productList = productList;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return productList.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return productList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder
	{

		ImageView pimage;
		TextView pname;
		TextView pcost;

	}

	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		final ViewHolder holder;
		final int pos = position;
		if (view == null)
		{
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.product_grid_item, null);
			holder.pimage = (ImageView) view.findViewById(R.id.iv2);
			holder.pname = (TextView) view.findViewById(R.id.nametv);
			holder.pcost = (TextView) view.findViewById(R.id.pricetv);
			view.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) view.getTag();
		}

		String url = productList.get(position).getPurl();
		// Log.d("bis", "url==" + url);

		//String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(url);
		//String filename = URLUtil.guessFileName(url, null, fileExtenstion);
		// Log.d("bis", "filename= " + filename);
		// String imageInSD = context.getExternalCacheDir().toString() +
		// "/commerce/productsTh/" + filename + "";
		String imageInSD = Environment.getExternalStorageDirectory().toString() + "/showcommerce/productsTh/" + url + "";
		BitmapImageLoader loadImage = new BitmapImageLoader(context, "Speaker");
		loadImage.loadBitmap(imageInSD, holder.pimage);
		holder.pname.setText(productList.get(position).getPname());
		holder.pcost.setText("Rs." + productList.get(position).pcost);
		view.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{

				Constants.pid = productList.get(pos).getPid();
				Constants.pname = productList.get(pos).getPname();
				Constants.pcost = productList.get(pos).getPcost();

				if (InfoTracker.usersessionid > 0)
				{

					InfoTracker.resetValuesAtProductLevel();

					InfoTracker.pid = productList.get(pos).getPid();
					InfoTracker.pname = productList.get(pos).getPname();
					InfoTracker.time = System.currentTimeMillis();

				}

				Intent intent = new Intent(context, ProductOverView.class);

				context.startActivity(intent);

			}
		});

		return view;
	}
*/}
