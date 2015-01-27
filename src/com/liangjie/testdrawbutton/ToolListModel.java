package com.liangjie.testdrawbutton;

import java.io.Serializable;
import java.util.List;

/**
 * @author liangjie
 * @version create time:2014-5-27上午11:21:36
 * @Email liangjie@witmob.com
 * @Description 工具列表
 */
public class ToolListModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9214889804511175133L;
	private List<ToolModel> list;
	public List<ToolModel> getList() {
		return list;
	}
	public void setList(List<ToolModel> list) {
		this.list = list;
	}

}
