package com.rpg.asyncload;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedItemAdapter extends ArrayAdapter<FeedMessage> {

	private Activity myContext;
	private FeedMessage[] feeds;
	
	
	public FeedItemAdapter(Context context, int textViewResourceId, FeedMessage[] objects) {
		super(context, textViewResourceId, objects);
		myContext = (Activity) context;
		feeds = objects;
	}
	
	static class ViewHolder {
		TextView postTitleView;
		TextView postDateView;
		ImageView postThumbView;
	}	
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			
			LayoutInflater inflater = myContext.getLayoutInflater();
			convertView = inflater.inflate(R.layout.feeditem, null);

			viewHolder = new ViewHolder();
			viewHolder.postThumbView = (ImageView) convertView.findViewById(R.id.postThumb);
			viewHolder.postTitleView = (TextView) convertView.findViewById(R.id.postTitleLabel);
			viewHolder.postDateView = (TextView) convertView.findViewById(R.id.postDateLabel);
			convertView.setTag(viewHolder);
		
		} 
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (feeds[position].getLink() == null) {
			viewHolder.postThumbView.setImageResource(R.drawable.postthumb_loading);
		}

		viewHolder.postTitleView.setText(feeds[position].getTitle());
		viewHolder.postDateView.setText(feeds[position].getDescription());

		return convertView;
	}
	
	
}
