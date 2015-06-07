package com.jackie.music;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.music.R;
import com.jackie.music.constant.MusicConstant;
import com.jackie.music.service.PlayService;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

/**
 * @author jackie
 *
 */

@ContentView(R.layout.song_single)
public class SongSingleActivity extends Activity{

	//�����ļ�·��
	private static final String PATH = "/mnt/sdcard/Music";
	
	//�����ļ�������
	private List<String> names = new ArrayList<String>();
	
	//��ȡlistview�ؼ�
	@ViewInject(R.id.SongSingle)
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		
		loadMp3();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.song_single_item, 
				R.id.single_item, names);
		
		listView.setAdapter(adapter);
		
		/*listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});*/
	}
	
	
	/**
	 * listviewÿһ��ĵ����¼�������Ҫ˵��һ�㣬ʹ��ע�ⷽʽ��������������ͬʱҲ��
	 */
	@OnItemClick(R.id.SongSingle)
	public void itemClick(AdapterView<?> parent, View view, int position,long id){
		
		//�ж��Ƿ����������Ϣ
		if(names != null){
			
			Intent intent = new Intent();
			intent.putExtra("url",  getUrl(names.get(position)));         
            intent.putExtra("MSG", MusicConstant.PLAY_MSG);  
            intent.setClass(SongSingleActivity.this, PlayService.class);  
            startService(intent);       //��������  
            
			Toast.makeText(getApplicationContext(), getUrl(names.get(position)), 0).show();
		}
		
	}
	
	
	//��ȡsdcard�е������ļ���Ϣ
	public void loadMp3(){
		
		File file = new File(PATH);
		//��ȡָ��·���µ������ļ���Ϣ
		String[] fileNames = file.list();
		//��������Ϣ���е����ҳ�mp3��Ϣ
		for(String name : fileNames){
			//�ж��ļ���׺
			if(name.endsWith("mp3")){
				
				//ȥ���ļ���׺��
				name =name.substring(0,name.lastIndexOf("."));
				names.add(name);
			}
		}
	}
	
	//��װ����·��
	public String getUrl(String name){
		
		return PATH + "/" + name + ".mp3";
	}
}