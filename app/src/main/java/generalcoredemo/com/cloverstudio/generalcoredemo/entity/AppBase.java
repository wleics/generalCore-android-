package generalcoredemo.com.cloverstudio.generalcoredemo.entity;

import java.io.Serializable;

public class AppBase  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4492993227301502974L;
	protected String create_by;	// 创建者
	protected String update_by;	// 更新者
	protected String create_date;	// 创建日期
	protected String update_date;	// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核 3:未通过）
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	
	
}
