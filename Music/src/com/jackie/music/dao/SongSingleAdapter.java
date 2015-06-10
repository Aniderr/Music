/**
 * 
 */
package com.jackie.music.dao;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.music.R;
import com.jackie.music.entity.MusicBean;

/**
 * ∏Ë«˙¡–±Ì  ≈‰∆˜
 * 
 * @author Jackie
 * 
 */
public class SongSingleAdapter extends BaseAdapter {

	private List<MusicBean> list;
	private Context context;
	private LayoutInflater mInflater;

	public SongSingleAdapter(Context context, List<MusicBean> list) {
		this.context = context;
		this.list = list;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		view = mInflater.inflate(R.layout.song_single_item, null);

		TextView textView = (TextView) view.findViewById(R.id.single_item);

		textView.setText(list.get(position).getTilte());

		return view;
	}

}
