package com.liangjie.testdrawbutton;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @author liangjie
 * @version create time:2014-5-29上午10:02:45
 * @Email liangjie@witmob.com
 * @Description 表情adapter
 */
public class ExpressionAdapter extends BaseAdapter {
	private ResourceUtil resourceUtil;
	private List<ExpressionModel> list = new ArrayList<ExpressionModel>();
	private LayoutInflater inflater;

	public ExpressionAdapter(Context context) {
		super();
		resourceUtil = ResourceUtil.getInstance(context);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_expression, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.updateView(list.get(position));
		return convertView;
	}

	public void refreshData(List<ExpressionModel> arg0) {
		this.list.clear();
		list.addAll(arg0);
		notifyDataSetChanged();
	}

	public class ViewHolder {
		public ExpressionModel model;
		private ImageView imageView;

		public ViewHolder(View convertView) {
			imageView = (ImageView) convertView.findViewById(R.id.image);
		}

		public void updateView(ExpressionModel model) {
			this.model = model;
			imageView.setImageResource(resourceUtil.getDrawableId(model.getStr()));
		}
	}

}
