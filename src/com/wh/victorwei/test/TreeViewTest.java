package com.wh.victorwei.test;

import java.util.ArrayList;

import com.wh.frame.view.treeView.TreeElement;
import com.wh.frame.view.treeView.TreeViewAdapter;
import com.wh.victorwei.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TreeViewTest extends Activity {
	private ListView lv_tree;
	private TreeViewAdapter treeViewAdapter;
	private ArrayList<TreeElement> mRootList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tree_view_activity);
		lv_tree = (ListView) findViewById(R.id.lv_tree);
		mRootList = new ArrayList<TreeElement>();
		treeViewAdapter = new TreeViewAdapter(this, R.layout.atom_tree,
				mRootList);
		lv_tree.setAdapter(treeViewAdapter);
		lv_tree.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("position:" + position);
				if (!mRootList.get(position).isHasChild()) {
					Toast.makeText(TreeViewTest.this,
							mRootList.get(position).getCaption(),
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (mRootList.get(position).isExpanded()) {
					mRootList.get(position).setExpanded(false);
					TreeElement element = mRootList.get(position);
					ArrayList<TreeElement> temp = new ArrayList<TreeElement>();

					for (int i = position + 1; i < mRootList.size(); i++) {
						if (element.getLevel() >= mRootList.get(i).getLevel()) {
							break;
						}
						temp.add(mRootList.get(i));
					}
					mRootList.removeAll(temp);
					treeViewAdapter.notifyDataSetChanged();
				} else {
					TreeElement obj = mRootList.get(position);
					obj.setExpanded(true);
					int level = obj.getLevel();
					int nextLevel = level + 1;
					String currentId = obj.getId();

					ArrayList<TreeElement> tempList = obj.getChildList();

					for (int i = tempList.size() - 1; i > -1; i--) {
						TreeElement element = tempList.get(i);
						element.setLevel(nextLevel);
						element.setExpanded(false);
						mRootList.add(position + 1, element);
					}
					treeViewAdapter.notifyDataSetChanged();
				}
			}
		});

		TreeElement rootElement = new TreeElement("33", "root", "1", true,
				false);

		TreeElement treeElement1 = new TreeElement("node1", "child1", "child1",
				true, false);
		// treeElement1.getParent().getId();

		TreeElement treeElement2 = new TreeElement("node2", "child2", "child2",
				false, false);
		TreeElement treeElement3 = new TreeElement("node3", "child3", "child3",
				false, true);
		TreeElement treeElement4 = new TreeElement("node3", "child4", "child4",
				true, true);

		mRootList.add(rootElement);
		rootElement.addChild(treeElement1);
		rootElement.addChild(treeElement2);
		rootElement.addChild(treeElement3);
		rootElement.addChild(treeElement4);

		treeViewAdapter.notifyDataSetChanged();
	}

}
