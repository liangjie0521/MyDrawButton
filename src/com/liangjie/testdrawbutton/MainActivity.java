package com.liangjie.testdrawbutton;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.liangjie.testdrawbutton.MyDrawButton.MyDrawButtonListener;

public class MainActivity extends FragmentActivity {
	private MyDrawButton myDrawButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myDrawButton = (MyDrawButton) findViewById(R.id.button);
		myDrawButton.setMyDrawButtonListener(new MyDrawButtonListener() {

			@Override
			public void onScrolling(float x, float y, float offset) {
				// TODO 正在执行滑动，可限制不可操作其他
			}

			@Override
			public void onModeChange(int mode) {
				// TODO 模式变化监听

			}
		});
	}
}
