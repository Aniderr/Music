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
 * Class description： ---- 程序第一界面
 * 
 * @author Jackie
 * 
 *         创建日期：2015 / 2015年6月10日
 * 
 */

@ContentView(R.layout.activity_main)
public class MainActivity extends Activity {

	@ViewInject(R.id.open)
	private TextView open;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 注入view和相关事件
		ViewUtils.inject(this);

	}

	@OnClick(R.id.open)
	public void openSource(View view) {

		Intent intent = new Intent();
		intent.setClass(this, SongSingleActivity.class);
		startActivity(intent);
	}

	/**
	 * 菜单按钮
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
