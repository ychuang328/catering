package com.weiwork.common.base;

import org.apache.commons.lang.StringUtils;

import cn.vko.component.pageframework.pagination.PageModel;

public class CommonPageModel{

	private int startIndex;
	private int pageSize;
	private String orderByClause;
	
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderByClause() {
		return orderByClause;
	}
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}
	
	public CommonPageModel(PageModel pageModel){
		pageSize = pageModel.getRows();
		int currentPage = pageModel.getPage();
		if ( StringUtils.isNotEmpty( pageModel.getSort() ) ) {
			orderByClause = pageModel.getSort() + " " + pageModel.getOrder();
		}
		startIndex = (currentPage - 1) * pageSize;
	}
}
