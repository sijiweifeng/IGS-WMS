package com.esquel.wh.model;

public class scanList {
	public String getUcc() {
		return ucc;
	}
	public void setUcc(String ucc) {
		this.ucc = ucc;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRecommendedlocation() {
		return recommendedlocation;
	}
	public void setRecommendedlocation(String recommendedlocation) {
		this.recommendedlocation = recommendedlocation;
	}
	public boolean isLocationreadonly() {
		return locationreadonly;
	}
	public void setLocationreadonly(boolean locationreadonly) {
		this.locationreadonly = locationreadonly;
	}
	private String ucc;
	private boolean status;
	private String location;
	private String recommendedlocation;
	private boolean locationreadonly;
	
}
