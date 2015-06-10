package com.jackie.music;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.music.R;
import com.jackie.music.constant.MusicConstant;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

/**
 * @author jackie
 * 
 */

@ContentView(R.layout.song_single)
public class SongSingleActivity extends Activity {

	// 音乐文件路径
	private static final String PATH = "/mnt/sdcard/Music";

	String str;
	// 音乐文件名数组
	private List<MusicBean> musicBeans = new ArrayList<MusicBean>();

	// 获取listview控件
	@ViewInject(R.id.SongSingle)
	private ListView listView;

	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);

		loadMp3();

		ArrayAdapter<MusicBean> adapter = new ArrayAdapter<MusicBean>(
				getApplicationContext(), R.layout.song_single_item,
				R.id.single_item, musicBeans);

		listView.setAdapter(adapter);

	}

	/**
	 * listview每一项的单击事件，这里要说明一点，使用注解方式创建监听方法的同时也得
	 */
	@OnItemClick(R.id.SongSingle)
	public void itemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// 判断是否存在音乐信息
		if (musicBeans.size() > 0) {

			/*
			 * Intent intent = new Intent(); intent.putExtra("url",
			 * getUrl(names.get(position))); intent.putExtra("MSG",
			 * MusicConstant.PLAY_MSG); intent.setClass(SongSingleActivity.this,
			 * PlayService.class); startService(intent); //启动服务
			 */
			// Toast.makeText(getApplicationContext(),
			// musicBeans.get(position).getUrl() +"", 0).show();
			Intent intent2 = new Intent();
			intent2.putExtra("url", musicBeans.get(position).getUrl());
			intent2.putExtra("MSG", MusicConstant.PLAY_MSG);
			intent2.setClass(this, PlayerActivity.class);
			startActivity(intent2);

		}

	}

	// 读取sdcard中的音乐文件信息
	public void loadMp3() {

		// 创建一个内容提供对象
		ContentResolver mResolver = getContentResolver();

		// 设置查询连接地址
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

		// 设置查询参数
		String projetion[] = { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA };

		// 执行查询操作
		// Cursor cursor = mResolver.query(
		// uri, projetion, null, null,null);

		cursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				null, null, null, null);

		// 判断是否存在查询结果，如果存在循环显示出结果信息
		if (cursor.getCount() > 0) {

			cursor.moveToFirst();
			while (cursor.moveToNext()) {

				MusicBean musicBean = new MusicBean();

				String name = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

				// 判断文件后缀
				if (name.endsWith("mp3")) {

					// 去掉文件后缀名
					name = name.substring(0, name.lastIndexOf("."));
				}
				musicBean.setTilte(name);

				musicBean.set_id(cursor.getInt(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));

				musicBean.setUrl(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));

				musicBean.setAlbum(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));

				musicBean.setArtist(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));

				musicBean
						.setDisplay_name(cursor.getString(cursor
								.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));

				musicBean.setYear(cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR)));

				musicBean
						.setDuration(cursor.getInt(cursor
								.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));

				musicBean.setSize(cursor.getInt(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));

				musicBeans.add(musicBean);
			}
		}

		/**
		 * 这里存储根据指定路径查询文件方式，以便之后使用 File file = new File(PATH); //获取指定路径下的所有文件信息
		 * String[] fileNames = file.list(); //对数组信息进行迭代找出mp3信息 for(String name
		 * : fileNames){ //判断文件后缀 if(name.endsWith("mp3")){
		 * 
		 * //去掉文件后缀名 name =name.substring(0,name.lastIndexOf("."));
		 * names.add(name); } }
		 */
	}

	// 组装音乐路径
	public String getUrl(String name) {

		return PATH + "/" + name + ".mp3";
	}
}
