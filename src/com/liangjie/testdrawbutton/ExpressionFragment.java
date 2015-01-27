package com.liangjie.testdrawbutton;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.liangjie.testdrawbutton.ToolAdapter.ViewHolder;

/**
 * @author liangjie
 * @version create time:2014-5-29上午9:43:10
 * @Email liangjie@witmob.com
 * @Description 表情fragment
 */
public class ExpressionFragment extends BaseFragment {
	private ExpreesionListModel listModel;
	private GridView gridView;
	private ExpressionAdapter adapter;
	private List<ExpressionModel> list = new ArrayList<ExpressionModel>();
	private int type;

	public static ExpressionFragment getInstance(ExpreesionListModel model, int type) {
		ExpressionFragment fragment = new ExpressionFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("model", model);
		bundle.putInt("type", type);
		fragment.setArguments(bundle);
		return fragment;

	}

	@Override
	protected void initData() {
		super.initData();
		listModel = (ExpreesionListModel) getArguments().get("model");
		type = getArguments().getInt("type");
		list.addAll(listModel.getList());
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.expression_fragment, container, false);
	}

	@Override
	protected void initView(View view) {
		gridView = (GridView) view.findViewById(R.id.expression);
		adapter = new ExpressionAdapter(mContext);
		if (type == 0) {
			ExpressionModel model = new ExpressionModel();
			model.setStr("del_btn_nor");
			list.add(model);
		}
		adapter.refreshData(list);
		gridView.setAdapter(adapter);
	}

	@Override
	protected void initWidgetActions() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ViewHolder holder = (ViewHolder) arg1.getTag();

			}
		});
	}

}
