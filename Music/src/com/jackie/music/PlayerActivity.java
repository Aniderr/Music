package com.jackie.music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
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
 * Class description： ---- 音乐播放界面
 * 
 * @author Jackie
 * 
 *         创建日期：2015 / 2015年6月10日
 * 
 */

@ContentView(R.layout.player)
public class PlayerActivity extends Activity {

	@ViewInject(R.id.pause)
	private Button pause;

	@ViewInject(R.id.stop)
	private Button stop;

	@ViewInject(R.id.play)
	private Button play;

	@ViewInject(R.id.gon)
	private Button gon;

	@ViewInject(R.id.seekBar)
	private SeekBar seekBar;
	
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);

		intent = getIntent();

		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);

		//根据歌曲的时长设置seekbar的长度
		seekBar.setMax(intent.getIntExtra("duration", 100));
		
		Toast.makeText(getApplicationContext(), seekBar.getMax()+"", 0).show();
		//滑动进度条改变播放进度
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				
//				Toast.makeText(getApplicationContext(), "arg1" + arg1, 0).show();
			}
		});
	}

	/**
	 * 
	 * Method description：
	 *   ----  停止播放
	 * 参数说明：
	 * @param view
	 *
	 * 返回类型：void
	 */
	@OnClick(R.id.stop)
	public void stop(View view) {

		Intent intent = new Intent();
		intent.putExtra("MSG", MusicConstant.STOP_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);

	}

	/**
	 * 
	 * Method description：
	 *   ----	暂停播放 
	 * 参数说明：
	 * @param view
	 *
	 * 返回类型：void
	 */
	@OnClick(R.id.pause)
	public void pause(View view) {

		Intent intent = new Intent();
		intent.putExtra("MSG", MusicConstant.PAUSE_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);
	}

	/**
	 * 
	 * Method description：
	 *   ----	继续播放 
	 * 参数说明：
	 * @param view
	 *
	 * 返回类型：void
	 */
	@OnClick(R.id.gon)
	public void continues(View view) {

		Intent intent = new Intent();
		intent.putExtra("MSG", MusicConstant.PAUSE_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);

	}

	/**
	 * 
	 * Method description： ---- 停止后点击播放 参数说明：
	 * 
	 * 返回类型：void
	 */
	@OnClick(R.id.play)
	public void play(View view) {

		intent.putExtra("MSG", MusicConstant.PLAY_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);

	}

}
