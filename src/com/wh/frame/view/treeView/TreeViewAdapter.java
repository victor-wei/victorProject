package com.wh.frame.view.treeView;

import java.util.ArrayList;
import java.util.List;

import com.wh.victorwei.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TreeViewAdapter extends ArrayAdapter<TreeElement> {
	private int img_leaf = R.drawable.icon_user;// û���ӽڵ�Ľڵ�ͼ��
	private int img_expand = R.drawable.tree_view_icon_open;// չ����ͼ��
	private int img_collapse = R.drawable.tree_view_icon_close;// �����ͼ��
	private int img_tree_space_1 = R.drawable.tree_space_1;// l����
	private int img_tree_space_2 = R.drawable.tree_space_2;

	private Context context;
	private LayoutInflater mInflater;
	private List<TreeElement> mfilelist;
	private int viewResourceId;

	public TreeViewAdapter(Context context, int viewResourceId,
			List<TreeElement> objects) {
		super(context, viewResourceId, objects);
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.mfilelist = objects;
		this.viewResourceId = viewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		convertView = mInflater.inflate(viewResourceId, null);
		holder = new ViewHolder();
		holder.caption = (TextView) convertView.findViewById(R.id.caption);
		holder.icon = (ImageView) convertView.findViewById(R.id.icon);
		holder.space = (LinearLayout) convertView.findViewById(R.id.space);
		convertView.setTag(holder);
		TreeElement treeElement = mfilelist.get(position);

		int level = treeElement.getLevel();
		if (level == 0) {

		} else {
			ArrayList<Integer> spaceList = treeElement.getSpaceList();

			for (int i = 0; i < spaceList.size(); i++) {
				ImageView img = new ImageView(context);
				img.setImageResource(spaceList.get(i));
				holder.space.addView(img);
			}
			ImageView img = new ImageView(context);
			if (treeElement.isLastSibling()) {
				img.setImageResource(img_tree_space_2);
				
			} else {
				img.setImageResource(img_tree_space_1);
			}

			holder.space.addView(img);
		}
		if (treeElement.isHasChild()) {
			if (treeElement.isExpanded()) {
				holder.icon.setImageResource(img_expand);
			} else {
				holder.icon.setImageResource(img_collapse);
			}
		} else {
			holder.icon.setImageResource(img_leaf);
			holder.icon.setVisibility(View.GONE);
		}
		holder.caption.setText(treeElement.getCaption());// ���ñ���
		return convertView;
	}

	class ViewHolder {
		LinearLayout space;
		TextView caption;
		ImageView icon;
	}
}
