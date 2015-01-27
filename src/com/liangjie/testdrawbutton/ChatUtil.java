package com.liangjie.testdrawbutton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/**
 * @author liangjie
 * @version create time:2014-6-18上午10:34:32
 * @Email liangjie@witmob.com
 * @Description 聊天界面util
 */
public class ChatUtil {

	private View chat_bottom_layout, record_audio_layout, send_location_layout, expression_layout;

	public FrameLayout creatLayout(Context context, int height) {
		FrameLayout layout = new FrameLayout(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, height);
		layout.setLayoutParams(params);
		chat_bottom_layout = inflater.inflate(R.layout.chat_bottom_layout, null);
		record_audio_layout = inflater.inflate(R.layout.record_audio_layout, null);
		send_location_layout = inflater.inflate(R.layout.send_location_layout, null);
		expression_layout = inflater.inflate(R.layout.expression_layout, null);
		layout.addView(chat_bottom_layout);
		layout.addView(record_audio_layout);
		layout.addView(send_location_layout);
		layout.addView(expression_layout);
		layout.setVisibility(View.GONE);
		return layout;
	}
}
