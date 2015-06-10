package com.jackie.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;

import com.jackie.music.constant.MusicConstant;
import com.lidroid.xutils.util.LogUtils;

/**
 * 
 * Class description： ---- 音乐播放服务
 * 
 * @author Jackie
 * 
 *         创建日期：2015 / 2015年6月10日
 * 
 */
public class PlayService extends Service {

	// 媒体播放器对象
	private MediaPlayer mediaPlayer = new MediaPlayer();

	// 音乐文件路径
	private String path;

	// 暂停状态
	private boolean isPause = false;

	/*
	 * 服务绑定事件
	 */
	@Override
	public IBinder onBind(Intent arg0) {

		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		path = intent.getStringExtra("url");
		int Msg = intent.getIntExtra("MSG", 0);

		if (Msg == MusicConstant.PLAY_MSG) {

			LogUtils.i("start");
			play(0);

		} else if (Msg == MusicConstant.PAUSE_MSG) {

			LogUtils.i("pause");
			pause();

		} else if (Msg == MusicConstant.STOP_MSG) {

			LogUtils.i("stop");
			stop();

		}
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 播放音乐
	 * 
	 * @param position
	 */
	private void play(int position) {
		try {
			mediaPlayer.reset();// 把各项参数恢复到初始状态
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepare(); // 进行缓冲
			mediaPlayer.setOnPreparedListener(new PreparedListener(position));// 注册一个监听器
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 暂停音乐
	 */
	private void pause() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {

			mediaPlayer.pause();
			isPause = true;
		} else {
			mediaPlayer.start();
		}
	}

	/**
	 * 停止音乐
	 */
	private void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();

			/*
			 * try { mediaPlayer.prepare(); //
			 * 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数 } catch (Exception e) {
			 * e.printStackTrace(); }
			 */
		}
	}

	@Override
	public void onDestroy() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}

	/**
	 * 
	 * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
	 * 
	 */
	private final class PreparedListener implements OnPreparedListener {
		private int positon;

		public PreparedListener(int positon) {
			this.positon = positon;
		}

		@Override
		public void onPrepared(MediaPlayer mp) {
			mediaPlayer.start(); // 开始播放
			if (positon > 0) { // 如果音乐不是从头播放
				mediaPlayer.seekTo(positon);
			}
		}
	}
}
