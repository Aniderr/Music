package com.jackie.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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
	private String path1 = "";
	private String path2 = "";

	private int Msg;

	// 暂停状态
	private boolean isPause = false;

	private boolean isDisabled = true;

	private int position = 0;

	private int totals = 0;

	/*
	 * 服务绑定事件
	 */
	@Override
	public IBinder onBind(Intent arg0) {

		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		new Thread(new Runnable() {

			@Override
			public void run() {

				while (isDisabled) {
					if (!isPause) {

						try {
							Thread.sleep(1000);
							++position;

							// 防止当前时长超过总时长
							if (totals == 0 || position < totals
									|| position == totals) {

								// 发送广播
								Intent intent = new Intent();
								intent.putExtra("count", position);
								intent.setAction("com.ljq.activity.CountService");
								sendBroadcast(intent);
							}

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mPlayer) {

				isPause = true;
			}
		});
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		path2 = intent.getStringExtra("url");
		Msg = intent.getIntExtra("MSG", 0);
		totals = intent.getIntExtra("totals", 0);

		// 如果点击需要播放的歌曲是当前正在播放的歌曲时不进行从头播放，即对现在的播放不造成任何影响
		if (!path2.equals(path1)) {
			path1 = path2;

			if (Msg == MusicConstant.PLAY_MSG) {

				position = intent.getIntExtra("position", 0);

				play(position);

				isPause = false;

			}
		}

		if (Msg == MusicConstant.PAUSE_MSG) {

			LogUtils.i("pause");
			pause();

		} else if (Msg == MusicConstant.STOP_MSG) {

			LogUtils.i("stop");
			stop();

			isPause = true;

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
			mediaPlayer.setDataSource(path1);
			mediaPlayer.prepare(); // 进行缓冲

			// 根据进度条的拖拽情况播放不同进度的音乐信息
			if (position > 0) {
				mediaPlayer.seekTo(position * 1000);
			}

			mediaPlayer.setOnPreparedListener(new PreparedListener(
					position * 1000));// 注册一个监听器

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
			isPause = false;
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
			isPause = true;
			isDisabled = false;
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
