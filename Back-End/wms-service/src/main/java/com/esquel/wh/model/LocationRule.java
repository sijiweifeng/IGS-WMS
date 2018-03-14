package com.esquel.wh.model;

public class LocationRule {
	private String stockGroup;
	private String garmentType;
	private String customerCode;
	private String outSource;
	private String location;
	
	public String getStockGroup() {
		return stockGroup;
	}
	public void setStockGroup(String stockGroup) {
		this.stockGroup = stockGroup;
	}
	public String getGarmentType() {
		return garmentType;
	}
	public void setGarmentType(String garmentType) {
		this.garmentType = garmentType;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getOutSource() {
		return outSource;
	}
	public void setOutSource(String outSource) {
		this.outSource = outSource;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public LocationRule(){
		
	}

	public LocationRule(String stockgroup,String garmenttype,String customercode,String outsource){
		super();
		this.stockGroup = stockgroup;
		this.garmentType = garmenttype;
		this.customerCode = customercode;
		this.outSource = outsource;
	}
	
	@Override
	public String toString() {
		return "Stock Group="+stockGroup+" Garment Type="+garmentType+" Customer Code="+customerCode+
		" OutSource="+outSource+" Location="+location;
	}
}
