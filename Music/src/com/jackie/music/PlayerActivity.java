package com.jackie.music;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.music.R;
import com.jackie.music.constant.MusicConstant;
import com.jackie.music.service.PlayService;
import com.jackie.music.utils.FormatTimes;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
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
	
	@ViewInject(R.id.current)
	private TextView current;
	
	@ViewInject(R.id.total)
	private TextView total;

	private Intent intent;

	private MyReceiver receiver;

	private boolean isTouch = false;
	
	private boolean isStop = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);

		intent = getIntent();

		isStop = false;
		
		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);

		// 根据歌曲的时长设置seekbar的长度
		seekBar.setMax(intent.getIntExtra("duration", 100) / 1000);
		
		double totals = intent.getIntExtra("duration", 100); 
		
		total.setText(FormatTimes.formatTotal(intent.getIntExtra("duration", 100)));

		// 注册广播接收器
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.ljq.activity.CountService");
		this.registerReceiver(receiver, filter);

		// 滑动进度条改变播放进度
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekbar) {

				isTouch = false;

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {

				isTouch = true;

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int position,
					boolean arg2) {

				if (isTouch) {
					
					isStop = false;
					intent.putExtra("MSG", MusicConstant.PLAY_MSG);
					intent.putExtra("position", position);

					intent.setClass(PlayerActivity.this, PlayService.class);
					startService(intent);
				}
			}
		});
	}

	/**
	 * 
	 * Method description： ---- 停止播放 参数说明：
	 * 
	 * @param view
	 * 
	 *            返回类型：void
	 */
	@OnClick(R.id.stop)
	public void stop(View view) {

		seekBar.setProgress(0);
		current.setText("00:00");

		isStop = true;
		
		intent.putExtra("MSG", MusicConstant.STOP_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);

	}

	/**
	 * 
	 * Method description： ---- 暂停播放 参数说明：
	 * 
	 * @param view
	 * 
	 *            返回类型：void
	 */
	@OnClick(R.id.pause)
	public void pause(View view) {

		isStop = false;
		
		intent.putExtra("MSG", MusicConstant.PAUSE_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);
	}

	/**
	 * 
	 * Method description： ---- 继续播放 参数说明：
	 * 
	 * @param view
	 * 
	 *            返回类型：void
	 */
	@OnClick(R.id.gon)
	public void continues(View view) {

		isStop = false;
		
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

		isStop = false;
		
		intent.putExtra("MSG", MusicConstant.PLAY_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);

	}

	/**
	 * 获取广播数据
	 * 
	 * @author jiqinlin
	 * 
	 */
	public class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			int count = bundle.getInt("count");
			isTouch = false;
			seekBar.setProgress(count);
			
			current.setText(FormatTimes.formatCurrent(count));
			
			//由于广播接收时间在与操作按钮之后，所以在点击停止时就给出一个状态来控制当前时间归于零
			if(isStop){
				
				current.setText("00:00");
			}
			
			LogUtils.i(seekBar.getProgress()+"*");
		}
	}

}
