package com.liangjie.testdrawbutton;

import java.util.List;

/**
 * @author liangjie
 * @version create time:2014-5-29上午9:32:48
 * @Email liangjie@witmob.com
 * @Description 表情列表
 */
public class ExpreesionListModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4873727300391056356L;
	
	private List<ExpressionModel> list;

	public List<ExpressionModel> getList() {
		return list;
	}

	public void setList(List<ExpressionModel> list) {
		this.list = list;
	}

}
