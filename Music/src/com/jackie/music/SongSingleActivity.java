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
import android.widget.ListView;

import com.example.music.R;
import com.jackie.music.constant.MusicConstant;
import com.jackie.music.dao.SongSingleAdapter;
import com.jackie.music.entity.MusicBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

/**
 * 
 * Class description�� ---- �����б�ҳ
 * 
 * @author Jackie
 * 
 *         �������ڣ�2015 / 2015��6��10��
 * 
 */

@ContentView(R.layout.song_single)
public class SongSingleActivity extends Activity {

	// �����ļ�·��
	private static final String PATH = "/mnt/sdcard/Music";

	String str;
	// �����ļ�������
	private List<MusicBean> musicBeans = new ArrayList<MusicBean>();

	// ��ȡlistview�ؼ�
	@ViewInject(R.id.SongSingle)
	private ListView listView;

	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);

		loadMp3();

		// �Զ�����������ʾ�����б�
		SongSingleAdapter adapter = new SongSingleAdapter(
				getApplicationContext(), musicBeans);

		listView.setAdapter(adapter);

	}

	/**
	 * listviewÿһ��ĵ����¼�������Ҫ˵��һ�㣬ʹ��ע�ⷽʽ��������������ͬʱҲ��
	 */
	@OnItemClick(R.id.SongSingle)
	public void itemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// �ж��Ƿ����������Ϣ
		if (musicBeans.size() > 0) {

			/*
			 * Intent intent = new Intent(); intent.putExtra("url",
			 * getUrl(names.get(position))); intent.putExtra("MSG",
			 * MusicConstant.PLAY_MSG); intent.setClass(SongSingleActivity.this,
			 * PlayService.class); startService(intent); //��������
			 */
			// Toast.makeText(getApplicationContext(),
			// musicBeans.get(position).getUrl() +"", 0).show();
			Intent intent2 = new Intent();
			intent2.putExtra("url", musicBeans.get(position).getUrl());
			intent2.putExtra("MSG", MusicConstant.PLAY_MSG);
			intent2.putExtra("duration", musicBeans.get(position).getDuration());
			intent2.setClass(this, PlayerActivity.class);
			startActivity(intent2);

		}

	}

	/**
	 * 
	 * Method description�� ---- ɨ������豸�е������ļ���Ϣ ����˵����
	 * 
	 * �������ͣ�void
	 */
	public void loadMp3() {

		// ����һ�������ṩ����
		ContentResolver mResolver = getContentResolver();

		// ���ò�ѯ���ӵ�ַ
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

		// ���ò�ѯ����
		String projetion[] = { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA };

		// ִ�в�ѯ����
		cursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				null, null, null, null);

		// �ж��Ƿ���ڲ�ѯ������������ѭ����ʾ�������Ϣ
		if (cursor.getCount() > 0) {

			cursor.moveToFirst();
			while (cursor.moveToNext()) {

				MusicBean musicBean = new MusicBean();

				int size = cursor.getInt(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

				if (size > 500) {

					String name = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

					// �ж��ļ���׺

					String display_name = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));

					// �ж��Ƿ��Ѿ�����
					if (isExits(display_name)) {

						if (display_name.endsWith("mp3")) {

							// ȥ���ļ���׺��
							// name = name.substring(0, name.lastIndexOf("."));

							musicBean.setTilte(name);

							musicBean
									.set_id(cursor.getInt(cursor
											.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));

							musicBean
									.setUrl(cursor.getString(cursor
											.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));

							musicBean
									.setAlbum(cursor.getString(cursor
											.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));

							musicBean
									.setArtist(cursor.getString(cursor
											.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));

							musicBean
									.setDisplay_name(cursor.getString(cursor
											.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));

							musicBean
									.setYear(cursor.getString(cursor
											.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR)));

							musicBean
									.setDuration(cursor.getInt(cursor
											.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));

							musicBean
									.setSize(cursor.getInt(cursor
											.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));

							musicBeans.add(musicBean);
						}
					}

				}

			}
		}

		/**
		 * ����洢����ָ��·����ѯ�ļ���ʽ���Ա�֮��ʹ�� File file = new File(PATH); //��ȡָ��·���µ������ļ���Ϣ
		 * String[] fileNames = file.list(); //��������Ϣ���е����ҳ�mp3��Ϣ for(String name
		 * : fileNames){ //�ж��ļ���׺ if(name.endsWith("mp3")){
		 * 
		 * //ȥ���ļ���׺�� name =name.substring(0,name.lastIndexOf("."));
		 * names.add(name); } }
		 */
	}

	// ��װ����·��
	public String getUrl(String name) {

		return PATH + "/" + name + ".mp3";
	}

	/**
	 * 
	 * Method description�� ---- �жϴ������ļ��Ƿ��Ѿ����� ����˵����
	 * 
	 * @param displayName
	 *            �ļ�ȫ��
	 * @return
	 * 
	 *         �������ͣ�boolean
	 */
	public boolean isExits(String displayName) {

		boolean b = true;

		if (musicBeans.size() > 0) {

			for (MusicBean bean : musicBeans) {

				if (displayName.endsWith(bean.getDisplay_name())) {
					b = false;
				}
			}
		}
		return b;
	}
}
