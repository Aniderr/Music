package com.jackie.music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.music.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * Class description�� ---- �����һ����
 * 
 * @author Jackie
 * 
 *         �������ڣ�2015 / 2015��6��10��
 * 
 */

@ContentView(R.layout.activity_main)
public class MainActivity extends Activity {

	@ViewInject(R.id.open)
	private TextView open;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ע��view������¼�
		ViewUtils.inject(this);

	}

	@OnClick(R.id.open)
	public void openSource(View view) {

		Intent intent = new Intent();
		intent.setClass(this, SongSingleActivity.class);
		startActivity(intent);
	}

	/**
	 * �˵���ť
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
