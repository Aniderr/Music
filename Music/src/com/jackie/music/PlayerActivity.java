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

		// ���ݸ�����ʱ������seekbar�ĳ���
		seekBar.setMax(intent.getIntExtra("duration", 100) / 1000);
		
		double totals = intent.getIntExtra("duration", 100); 
		
		total.setText(FormatTimes.formatTotal(intent.getIntExtra("duration", 100)));

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
			
			//���ڹ㲥����ʱ�����������ť֮�������ڵ��ֹͣʱ�͸���һ��״̬�����Ƶ�ǰʱ�������
			if(isStop){
				
				current.setText("00:00");
			}
			
			LogUtils.i(seekBar.getProgress()+"*");
		}
	}

}
