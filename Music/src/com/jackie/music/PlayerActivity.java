package com.jackie.music;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.example.music.R;
import com.jackie.music.constant.MusicConstant;
import com.jackie.music.service.PlayService;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * @author jackie
 *
 */

@ContentView(R.layout.player)
public class PlayerActivity extends Activity{

	@ViewInject(R.id.pause)
	private Button pause;
	
	@ViewInject(R.id.stop)
	private Button stop;
	
	@ViewInject(R.id.play)
	private Button play;
	
	@ViewInject(R.id.gon)
	private Button gon;
	
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		
		intent = getIntent();
		intent.setClass(PlayerActivity.this, PlayService.class);  
        startService(intent);
    
	}
	
	
	/**
	 * Í£Ö¹²¥·Å
	 * @param view
	 */
	@OnClick(R.id.stop)
	public void stop(View view){
		
		Intent intent = new Intent();
		intent.putExtra("MSG", MusicConstant.STOP_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);  
		startService(intent);
		
	}
	
	/**
	 * ÔÝÍ£²¥·Å
	 * @param view
	 */
	@OnClick(R.id.pause)
	public void pause(View view){
		
		Intent intent = new Intent();
		intent.putExtra("MSG", MusicConstant.PAUSE_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);  
		startService(intent);
	}
	
	
	/**
	 * ¼ÌÐø²¥·Å
	 * @param view
	 */
	@OnClick(R.id.gon)
	public void continues(View view){
		
		Intent intent = new Intent();
		intent.putExtra("MSG", MusicConstant.PAUSE_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);  
		startService(intent);
		
	}
}
