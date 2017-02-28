/**

 * Copyright &copy; 2012-2014 <a href="https://zh-soft.com">ABS</a> All rights reserved.
 */
package generalcoredemo.com.cloverstudio.generalcoredemo.entity;


/**
 * 分页类
 * @author jincool.cao
 * @version 2013-7-2
 */
public class AppPage extends AppBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4062095602495446637L;

	private int pageNo = 1; // 当前页码
	
	private int pageSize = 20;	//每页展示
	
	private String orderBy = ""; // 标准查询有效， 实例： updatedate desc, name asc

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	
	
}
