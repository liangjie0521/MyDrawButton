package com.liangjie.testdrawbutton;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author liangjie
 * @version create time:2014-5-16上午9:54:47
 * @Email liangjie@witmob.com
 * @Description fragment基类
 */
public abstract class BaseFragment extends Fragment {
	protected String TAG;
	protected Context mContext;
	protected ResourceUtil resourceUtil;
	protected String clientId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		initData();
		View view = initView(inflater, container, savedInstanceState);
		initView(view);
		initWidgetActions();
		return view;
	}

	protected void initData() {
		mContext = getActivity();
		TAG = toString();
		resourceUtil = ResourceUtil.getInstance(mContext);
	}

	protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	protected abstract void initView(View view);

	protected abstract void initWidgetActions();

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
