package com.liangjie.testdrawbutton;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author liangjie
 * @version create time:2014-5-22下午8:32:55
 * @Email liangjie@witmob.com
 * @Description 添加工具 adapter
 */
public class ToolAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ToolModel> list = new ArrayList<ToolModel>();
	private ResourceUtil resourceUtil;
	private Context context;

	public ToolAdapter(Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		resourceUtil = ResourceUtil.getInstance(context);
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
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_tool, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.updateView(list.get(position));
		return convertView;
	}

	public void refreshData(List<ToolModel> arg0) {
		this.list.clear();
		list.addAll(arg0);
		notifyDataSetChanged();
	}

	public class ViewHolder {
		private ImageView imageView;
		public ToolModel model;
		private TextView name;

		public ViewHolder(View convertView) {
			imageView = (ImageView) convertView.findViewById(R.id.image);
			name = (TextView) convertView.findViewById(R.id.name);
		}

		public void updateView(ToolModel model) {
			this.model = model;
			int resId = resourceUtil.getDrawableId(model.getResId());
			if (!model.getId().equals("burn")) {
				imageView.setImageResource(resId);
			} else {
					imageView.setImageResource(R.drawable.burn_unclick);
			}
				name.setText(model.getName());
		

		}
	}

}
