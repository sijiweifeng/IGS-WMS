package com.esquel.wh.model;

import java.sql.Date;

public class ProductModel {
	private String id;
	private String uniqueKey;
	private String createdBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String lastModifiedBy;	
	private int revision;
	private String custStyleNo;
	private String styleNo;
	private int styleRevNo;
	private String custColorCode;
	private String custSizeCode;
	private String custSize1Code;
	private String custSize2Code;
	private String esqColorCode;
	private String esqSizeCode1;
	private String esqSizeCode2;
	private String sku;
	private String upc;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public String getStyleNo() {
		return styleNo;
	}
	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}
	public int getStyleRevNo() {
		return styleRevNo;
	}
	public void setStyleRevNo(int styleRevNo) {
		this.styleRevNo = styleRevNo;
	}
	public String getCustColorCode() {
		return custColorCode;
	}
	public void setCustColorCode(String custColorCode) {
		this.custColorCode = custColorCode;
	}
	public String getCustSizeCode() {
		return custSizeCode;
	}
	public void setCustSizeCode(String custSizeCode) {
		this.custSizeCode = custSizeCode;
	}
	public String getCustSize1Code() {
		return custSize1Code;
	}
	public void setCustSize1Code(String custSize1Code) {
		this.custSize1Code = custSize1Code;
	}
	public String getCustSize2Code() {
		return custSize2Code;
	}
	public void setCustSize2Code(String custSize2Code) {
		this.custSize2Code = custSize2Code;
	}
	public String getEsqColorCode() {
		return esqColorCode;
	}
	public void setEsqColorCode(String esqColorCode) {
		this.esqColorCode = esqColorCode;
	}
	public String getEsqSizeCode1() {
		return esqSizeCode1;
	}
	public void setEsqSizeCode1(String esqSizeCode1) {
		this.esqSizeCode1 = esqSizeCode1;
	}
	public String getEsqSizeCode2() {
		return esqSizeCode2;
	}
	public void setEsqSizeCode2(String esqSizeCode2) {
		this.esqSizeCode2 = esqSizeCode2;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getCustStyleNo() {
		return custStyleNo;
	}
	public void setCustStyleNo(String custStyleNo) {
		this.custStyleNo = custStyleNo;
	}
	public String getUniqueKey() {
		return uniqueKey;
	}
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
}
