package com.jackie.music;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.music.R;
import com.jackie.music.constant.MusicConstant;
import com.jackie.music.service.PlayService;
import com.jackie.music.utils.FormatTimes;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * Class description�� ---- ���ֲ��Ž���
 * 
 * @author Jackie
 * 
 *         �������ڣ�2015 / 2015��6��10��
 * 
 */

@ContentView(R.layout.player)
public class PlayerActivity extends Activity {

	@ViewInject(R.id.pause)
	private ImageView pause;

	@ViewInject(R.id.stop)
	private Button stop;

	@ViewInject(R.id.play)
	private Button play;

	@ViewInject(R.id.gon)
	private Button gon;

	@ViewInject(R.id.music_play_controller_process)
	private SeekBar seekBar;

	@ViewInject(R.id.current)
	private TextView current;

	@ViewInject(R.id.total)
	private TextView total;

	@ViewInject(R.id.music_title)
	private TextView music_title;

	private Intent intent;

	private MyReceiver receiver;

	private boolean isTouch = false;

	private boolean isStop = false;

	private boolean isPlay = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		ViewUtils.inject(this);

		intent = getIntent();

		isStop = false;

		init(intent);

		// ��ʾ֪ͨ��
		showNotifiaction();

		// ע��㲥������
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.ljq.activity.CountService");
		this.registerReceiver(receiver, filter);

		// �����������ı䲥�Ž���
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
	 * ��ʾ֪ͨ��
	 */
	@SuppressWarnings("deprecation")
	public void showNotifiaction() {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher,
				null, System.currentTimeMillis());// ��һ������Ϊͼ��,�ڶ�������Ϊ������ʾ����,������Ϊ֪ͨʱ��

		intent.setClass(this, SongSingleActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, 0);// �������Ϣʱ�ͻ���ϵͳ����openintent��ͼ

		notification.setLatestEventInfo(this, intent.getStringExtra("title"),
				intent.getStringExtra("author"), contentIntent);

		mNotificationManager.notify(0, notification);// ��һ������Ϊ�Զ����֪ͨΨһ��ʶ
	}

	/**
	 * ��ʼ��
	 */
	private void init(Intent intent) {

		music_title.setText(intent.getStringExtra("title"));

		// ���ݸ�����ʱ������seekbar�ĳ���
		seekBar.setMax(intent.getIntExtra("duration", 100) / 1000);

		int totals = intent.getIntExtra("duration", 100);

		// ����service���Ʋ���ʱ������ʱ���Ĺ�ϵ
		intent.putExtra("totals", totals / 1000);

		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);

		total.setText(FormatTimes.formatTotal(totals));
	}

	/**
	 * 
	 * Method description�� ---- ֹͣ���� ����˵����
	 * 
	 * @param view
	 * 
	 *            �������ͣ�void
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
	 * Method description�� ---- ��ͣ���� ����˵����
	 * 
	 * @param view
	 * 
	 *            �������ͣ�void
	 */
	@OnClick(R.id.pause)
	public void pause(View view) {

		isStop = false;

		if (isPlay) {
			isPlay = !isPlay;
			pause.setImageResource(R.drawable.play);
		} else {
			isPlay = !isPlay;
			pause.setImageResource(R.drawable.pause);
		}

		intent.putExtra("MSG", MusicConstant.PAUSE_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);
	}

	/**
	 * 
	 * Method description�� ---- �������� ����˵����
	 * 
	 * @param view
	 * 
	 *            �������ͣ�void
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
	 * Method description�� ---- ֹͣ�������� ����˵����
	 * 
	 * �������ͣ�void
	 */
	@OnClick(R.id.play)
	public void play(View view) {

		isStop = false;

		intent.putExtra("MSG", MusicConstant.PLAY_MSG);
		intent.setClass(PlayerActivity.this, PlayService.class);
		startService(intent);

	}

	/**
	 * ��ȡ�㲥����
	 * 
	 * @author
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

			// ���ڹ㲥����ʱ�����������ť֮�������ڵ��ֹͣʱ�͸���һ��״̬�����Ƶ�ǰʱ�������
			if (isStop) {

				current.setText("00:00");
			}
		}
	}

}
