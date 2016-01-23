package com.samskrut.showcommerce.utilities;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapImageLoader {
Context context;
String CALLINGPAGEFLAG;
	public BitmapImageLoader(Context _context,String _pageflag){
		this.context=_context;
	    this.CALLINGPAGEFLAG=_pageflag;
	}
	public void loadBitmap(String resId, ImageView imageView) {
	    BitmapWorkerTask task = new BitmapWorkerTask(imageView);
	    task.execute(resId);
	    
	}
	/*----------------------------------------------*/
	/*       Read Bitmap Dimensions		 			*/
	/*----------------------------------------------*/
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
	/*----------------------------------------------*/
	/*       Load a Scaled Down Version into Memory	*/
	/*----------------------------------------------*/
	public static Bitmap decodeSampledBitmapFromResource(Resources res, String resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true; BitmapFactory.decodeFile(resId);
	    //BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    //return BitmapFactory.decodeResource(res, resId, options);
	    return BitmapFactory.decodeFile(resId);
	}
	class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
	    private final WeakReference<ImageView> imageViewReference;
	    //private int data = 0;
	    private String data = null;
	
	    public BitmapWorkerTask(ImageView imageView) {
	        // Use a WeakReference to ensure the ImageView can be garbage collected
	        imageViewReference = new WeakReference<ImageView>(imageView);
	       
	    }

	    // Decode image in background.
	    @Override
	    protected Bitmap doInBackground(String... params) {
	        data = params[0];
	        return decodeSampledBitmapFromResource(context.getResources(), data, 100, 100);
	    }

	    // Once complete, see if ImageView is still around and set bitmap.
	    @Override
	    protected void onPostExecute(Bitmap bitmap) {
	        if (imageViewReference != null && bitmap != null) {
	            final ImageView imageView = imageViewReference.get();
	            if (imageView != null) {
	                imageView.setImageBitmap(bitmap);
	            }
	        }
	        else{
	        	final ImageView imageView = imageViewReference.get();
	            if (imageView != null) {
	            	if(CALLINGPAGEFLAG.equalsIgnoreCase("Exhibitor")){
	            	    // imageView.setImageResource(R.drawable.exhibitorlistimage);
	            	}
	            	if(CALLINGPAGEFLAG.equalsIgnoreCase("Speaker")){
	            	     //imageView.setImageResource(R.drawable.speakerlistimage);
	            	}
	            	if(CALLINGPAGEFLAG.equalsIgnoreCase("Splashscreen")){
	            	     //imageView.setImageResource(R.drawable.artwork);
	            	}
	            	if(CALLINGPAGEFLAG.equalsIgnoreCase("Venuemap")){
	            	     //imageView.setImageResource(R.drawable.artwork);
	            	}
	            }
	        }
	    	
	    }
	}
}
