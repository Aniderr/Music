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
 * Class description�� ---- ���ֲ��ŷ���
 * 
 * @author Jackie
 * 
 *         �������ڣ�2015 / 2015��6��10��
 * 
 */
public class PlayService extends Service {

	// ý�岥��������
	private MediaPlayer mediaPlayer = new MediaPlayer();

	// �����ļ�·��
	private String path;

	// ��ͣ״̬
	private boolean isPause = false;

	private boolean isDisabled = true;

	private int position = 0;

	/*
	 * ������¼�
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

							// ���͹㲥
							Intent intent = new Intent();
							intent.putExtra("count", position);
							intent.setAction("com.ljq.activity.CountService");
							sendBroadcast(intent);

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		path = intent.getStringExtra("url");
		int Msg = intent.getIntExtra("MSG", 0);

		if (Msg == MusicConstant.PLAY_MSG) {

			position = intent.getIntExtra("position", 0);

			play(position);

			isPause = false;

		} else if (Msg == MusicConstant.PAUSE_MSG) {

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
	 * ��������
	 * 
	 * @param position
	 */
	private void play(int position) {
		try {
			mediaPlayer.reset();// �Ѹ�������ָ�����ʼ״̬
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepare(); // ���л���

			// ���ݽ���������ק������Ų�ͬ���ȵ�������Ϣ
			if (position > 0) {
				mediaPlayer.seekTo(position);
			}

			mediaPlayer.setOnPreparedListener(new PreparedListener(position));// ע��һ��������

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ͣ����
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
	 * ֹͣ����
	 */
	private void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();

			/*
			 * try { mediaPlayer.prepare(); //
			 * �ڵ���stop�������Ҫ�ٴ�ͨ��start���в���,��Ҫ֮ǰ����prepare���� } catch (Exception e) {
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
	 * ʵ��һ��OnPrepareLister�ӿ�,������׼���õ�ʱ��ʼ����
	 * 
	 */
	private final class PreparedListener implements OnPreparedListener {
		private int positon;

		public PreparedListener(int positon) {
			this.positon = positon;
		}

		@Override
		public void onPrepared(MediaPlayer mp) {
			mediaPlayer.start(); // ��ʼ����
			if (positon > 0) { // ������ֲ��Ǵ�ͷ����
				mediaPlayer.seekTo(positon);
			}
		}
	}

}
