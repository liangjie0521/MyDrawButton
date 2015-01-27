package com.liangjie.testdrawbutton;

import java.io.Serializable;

/**
 * @author liangjie
 * @version create time:2014-5-27上午11:04:56
 * @Email liangjie@witmob.com
 * @Description 聊天底部model
 */
public class ToolModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 192722144729451060L;
	private String resId;
	private String id;
	private String name;
	private String name_en;

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

}
