package com.liangjie.testdrawbutton;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author liangjie
 * @version create time:2014-5-29上午9:36:56
 * @Email liangjie@witmob.com
 * @Description 表情viewpage
 */
public class ExpreesionViewPageAdapter extends FragmentStatePagerAdapter {
	private List<ExpressionModel> list = new ArrayList<ExpressionModel>();

	public ExpreesionViewPageAdapter(FragmentManager fm, List<ExpressionModel> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		ExpreesionListModel listModel = new ExpreesionListModel();
		List<ExpressionModel> models = new ArrayList<ExpressionModel>();
		if ((arg0 + 1) * 20 < list.size()) {
			for (int i = 0; i < 20; i++) {
				models.add(list.get(arg0 * 20 + i));
			}
		} else {
			for (int i = 0; i < list.size() % 20; i++) {
				models.add(list.get(arg0 * 20 + i));
			}
		}
		listModel.setList(models);
		ExpressionFragment fragment = ExpressionFragment.getInstance(listModel, 0);
		return fragment;
	}

	@Override
	public int getCount() {
		if (list.size() / 20 != 0) {
			return list.size() / 20 + 1;
		}
		return list.size() / 20;
	}

}
