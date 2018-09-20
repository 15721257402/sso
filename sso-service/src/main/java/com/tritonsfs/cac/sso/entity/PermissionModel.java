/**
 * 
 */
package com.tritonsfs.cac.sso.entity;

import java.util.List;

/**
 * @author chenshunyu
 *
 */
public class PermissionModel {

	
	String title;
	
	List<PermissionModel> children;
	
	boolean folder;
	
	Long key;
	
	boolean checkbox;
	
	boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PermissionModel> getChildren() {
		return children;
	}

	public void setChildren(List<PermissionModel> children) {
		this.children = children;
	}

	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public String getKey() {
		return String.valueOf(key);
	}

	public void setKey(Long key) {
		this.key = key;
	}


	
	
}
