package com.samskrut.showcommerce;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.samskrut.showcommerce.database.VideoInfo;
import com.samskrut.showcommerce.utilities.ConnectionDetector;
import com.samskrut.showcommerce.utilities.Constants;
import com.samskrut.showcommerce.utilities.InfoTracker;
import com.samskrut.showcommerce.utilities.SharedPrefencers;

public class VideoFragment extends Fragment
{

	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	Context ctx;
	GridView youtubegv;
	ProgressBar pb;

	List<ParseObject> ob;
	ProgressDialog mProgressDialog;
	CustomGrid adapter;
	private List<VideoInfo> videolist = null;

	String video_th;
	String videoId;

	ImageLoader imageLoader;

	TextView hiden_tv;

	SharedPrefencers pref;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		// Inflate the layout for this fragment
		ctx = getActivity();
		View view = inflater.inflate(R.layout.video_fragment, container, false);

		youtubegv = (GridView) view.findViewById(R.id.youtubegv);
		pb = (ProgressBar) view.findViewById(R.id.pb);
		hiden_tv = (TextView) view.findViewById(R.id.hidden_tv);

		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();
		pref = new SharedPrefencers(getActivity());

		imageLoader = ApplicationConstant.getInstance().getImageLoader();

		if (isInternetPresent)
		{
			pb.setVisibility(0);
			new RemoteDataTask().execute();

		}
		else
		{
			hiden_tv.setVisibility(0);
			hiden_tv.setText("Check Internet Connectivity");

		}

		return view;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		if (videolist != null && videolist.size() > 0)
		{
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			{
				youtubegv.setNumColumns(3);
				if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
				{
					youtubegv.setNumColumns(3);
					Log.d("bis", "ori 1");

				}
				else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
				{
					youtubegv.setNumColumns(4); // Landscape mode
					Log.d("bis", "ori 2=land");
				}
				Log.d("bis", "ori 1");

			}
			else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			{
				youtubegv.setNumColumns(4); // Landscape mode
				Log.d("bis", "ori 2=land");
			}
		}
	}

	private class RemoteDataTask extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			// Create a progressdialog
			/*
			 * mProgressDialog = new ProgressDialog(testclass.this); // Set
			 * progressdialog title mProgressDialog.setTitle("Please wait"); //
			 * Set progressdialog message
			 * mProgressDialog.setMessage("Loading...");
			 * mProgressDialog.setIndeterminate(false); // Show progressdialog
			 * mProgressDialog.show();
			 */

		}

		@Override
		protected Void doInBackground(Void... params)
		{
			// Create the array

			videolist = new ArrayList<VideoInfo>();
			try
			{
				// Locate the class table named "TestLimit" in Parse.com
				// ParseQuery<ParseObject> query = new
				// ParseQuery<ParseObject>("youtube");
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("productvideos");
				query.whereEqualTo("product_id", Constants.pid);
				query.whereEqualTo("customer_id", pref.getCustomerId());
				// query.orderByDescending("createdAt");
				// Set the limit of objects to show
				// query.setLimit(limit);
				ob = query.find();

				for (ParseObject num : ob)
				{
					VideoInfo vinfo = new VideoInfo();

					vinfo.setVideo_th((String) num.get("product_video_url"));
					// vinfo.v((String) num.get("person_name"));

					videolist.add(vinfo);
				}
			}
			catch (ParseException e)
			{
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{

			if (videolist != null && videolist.size() > 0 && getActivity() != null && !getActivity().isFinishing())
			{

				pb.setVisibility(8);
				youtubegv.setVisibility(0);

				adapter = new CustomGrid(getActivity(), videolist);
				// Binds the Adapter to the ListView
				youtubegv.setAdapter(adapter);

				if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
				{
					youtubegv.setNumColumns(3);// portarait

				}
				else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
				{
					youtubegv.setNumColumns(4); // Landscape mode

				}

			}
			else
			{

				if (getActivity() != null && !getActivity().isFinishing())
				{
					pb.setVisibility(8);
					hiden_tv.setVisibility(0);
				}

			}

		}
	}

	// ==============
	private class CustomGrid extends BaseAdapter
	{
		private Context mContext;
		private final List<VideoInfo> vifolist;

		public CustomGrid(Context c, List<VideoInfo> vifolist)
		{
			mContext = c;

			this.vifolist = vifolist;

			// imageLoader = ApplicationConstant.getInstance().getImageLoader();

			// imageLoader = ApplicationConstant.getInstance();
			// imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return vifolist.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return vifolist.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub
			final int pos = position;
			View grid;
			NetworkImageView imageView;

			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null)
			{
				grid = new View(mContext);
				grid = inflater.inflate(R.layout.volley_niv, null);
				// TextView textView = (TextView)
				// grid.findViewById(R.id.grid_text);
				imageView = (NetworkImageView) grid.findViewById(R.id.NetworkIV);
				// textView.setText(web[position]);
				try
				{
					videoId = extractYoutubeId(vifolist.get(position).getVideo_th());
				}
				catch (MalformedURLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				video_th = "http://img.youtube.com/vi/" + videoId + "/0.jpg";

				// Log.d("bis", "video_th =" + video_th);
				imageView.setImageUrl(video_th, imageLoader);
				// imageView.setBackgroundColor(Color.DKGRAY);
			}
			else
			{
				grid = (View) convertView;
			}

			grid.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{

					try
					{
						videoId = extractYoutubeId(vifolist.get(pos).getVideo_th());
						int tmm = extractTimeFromYoutubeId(vifolist.get(pos).getVideo_th());
						// FragmentTransaction fragmentTransaction =
						// getFragmentManager().beginTransaction();
						// Fragment profileFragment = new EditorFragment2();
						// profileFragment.setArguments(bundle);
						// fragmentTransaction.replace(R.id.myfrag,
						// profileFragment);
						// fragmentTransaction.addToBackStack(null);
						// fragmentTransaction.commit();

						PlayerYouTubeFrag myFragment = PlayerYouTubeFrag.newInstance(videoId, tmm);
						getFragmentManager().beginTransaction().replace(R.id.myfrag, myFragment).commit();
						myFragment.init();
					}
					catch (MalformedURLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Toast.makeText(getActivity(), "videoId=" + videoId,
					// 1000).show();

				}
			});
			return grid;
		}

		public String extractYoutubeId(String url) throws MalformedURLException
		{

			String query = new URL(url).getQuery();

			String[] param = query.split("&");
			String id = null;
			for (String row : param)
			{
				String[] param1 = row.split("=");
				if (param1[0].equals("v"))
				{
					id = param1[1];
				}
			}
			return id;
		}

		public int extractTimeFromYoutubeId(String url) throws MalformedURLException
		{
			int tm;
			String[] time = url.split("#t=");
			if (time.length == 2)
			{

				String t = time[1];

				tm = Integer.parseInt(t);
			}
			else
			{

				tm = 0;

			}

			return tm;
		}
	}

	// =============

	public static class PlayerYouTubeFrag extends YouTubePlayerSupportFragment
	{

		private String currentVideoID = "video_id";
		private YouTubePlayer activePlayer;

		public static PlayerYouTubeFrag newInstance(String url, int tm2)
		{

			PlayerYouTubeFrag playerYouTubeFrag = new PlayerYouTubeFrag();

			Bundle bundle = new Bundle();
			bundle.putString("url", url);
			bundle.putInt("time", tm2);

			playerYouTubeFrag.setArguments(bundle);

			return playerYouTubeFrag;
		}

		private void init()
		{

			initialize("AIzaSyAHAXqbOC8IiAuKQwGhM4k3pSorZOdYbwE", new OnInitializedListener()
			{

				@Override
				public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1)
				{

				}

				// ===================
				@Override
				public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored)
				{
					activePlayer = player;
					activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
					if (!wasRestored)
					{
						//Log.d("bis", "url= " + getArguments().getString("url"));
						activePlayer.loadVideo(getArguments().getString("url"), getArguments().getInt("time") * 1000);

						if (InfoTracker.usersessionid > 0)
						{

							InfoTracker.videoid = getArguments().getString("url");
						}

						// ================
						activePlayer.setPlaybackEventListener(new PlaybackEventListener()
						{

							@Override
							public void onStopped()
							{
								Log.d("ystat", "stoped");

							}

							@Override
							public void onSeekTo(int arg0)
							{
								Log.d("ystat", "onSeekTo");

							}

							@Override
							public void onPlaying()
							{
								Log.d("ystat", "onPlaying");

							}

							@Override
							public void onPaused()
							{

								Log.d("ystat", "onPaused");

							}

							@Override
							public void onBuffering(boolean arg0)
							{
								Log.d("ystat", "onBuffering");

							}

						});

						// =================

					}

				}

				// ===================

			});
		}

	}
}
