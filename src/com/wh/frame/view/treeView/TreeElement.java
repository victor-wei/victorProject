package com.wh.frame.view.treeView;

import java.util.ArrayList;
import java.util.List;

import com.wh.victorwei.R;

/**
 * 树状控件--元素
 * @author weihe
 *
 */
public class TreeElement {
	private int img_tree_space_n = R.drawable.tree_space_n;
	private int img_tree_space_y = R.drawable.tree_space_y;

	private String id;
	private String caption;
	private String value;
	private int level;
	private TreeElement parent;
	private boolean isHasChild;
	private boolean isExpanded;
	private ArrayList<TreeElement> childList;
	private boolean isLastSibling;
	private ArrayList<Integer> spaceList;

	public TreeElement(String id, String caption, String value) {
		this.id = id;
		this.caption = caption;
		this.value = value;
		this.parent = null;
		this.level = 0;
		this.isHasChild = false;
		this.isExpanded = false;
		this.childList = null;
		this.isLastSibling = false;
		this.setSpaceList(new ArrayList<Integer>());
	}

	public TreeElement(String id, String caption, String value,
			Boolean isHasChild, Boolean isExpanded) {
		this.id = id;
		this.caption = caption;
		this.value = value;
		this.parent = null;
		this.level = 0;
		this.isHasChild = isHasChild;
		this.isExpanded = isExpanded;
		if (isHasChild) {
			this.childList = new ArrayList<TreeElement>();
		}
		this.isLastSibling = false;
		this.setSpaceList(new ArrayList<Integer>());
	}

	public void addChild(TreeElement treeElement) {
		treeElement.parent = this;
		if (treeElement.getParent() != null
				&& treeElement.getParent().getChildList().size() > 0) {// ��֮ǰ��ͬ���ڵ����Ϊ�����һ��ڵ�
			List<TreeElement> siblingList = treeElement.getParent()
					.getChildList();
			treeElement.getParent().getChildList().get(siblingList.size() - 1)
					.setLastSibling(false);
		}
		this.childList.add(treeElement);
		this.isHasChild = true;
		treeElement.level = this.level + 1;
		treeElement.isLastSibling = true;
		if (this.level > 0) {
			treeElement.getSpaceList().addAll(this.getSpaceList());
			if (this.isLastSibling()) {
				treeElement.getSpaceList().add(img_tree_space_n);
			} else {
				treeElement.getSpaceList().add(img_tree_space_y);
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TreeElement getParent() {
		return parent;
	}

	public void setParent(TreeElement parent) {
		this.parent = parent;
	}

	public boolean isHasChild() {
		return isHasChild;
	}

	public void setHasChild(boolean isHasChild) {
		this.isHasChild = isHasChild;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public ArrayList<TreeElement> getChildList() {
		return childList;
	}

	public void setChildList(ArrayList<TreeElement> childList) {
		this.childList = childList;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLastSibling() {
		return isLastSibling;
	}

	public void setLastSibling(boolean isLastSibling) {
		this.isLastSibling = isLastSibling;
	}

	public ArrayList<Integer> getSpaceList() {
		return spaceList;
	}

	public void setSpaceList(ArrayList<Integer> spaceList) {
		this.spaceList = spaceList;
	}

}