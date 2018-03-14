package com.esquel.wh.model;

import java.util.List;

public class Stock {
	
	private String StockKey;
	private String buyerPONO;
	private String cartonNo;
	private String UCC;
	private String UCCNo;
	private String SKU;
	private int qty;
	private String cutNo;
	private String firstSewingLine;
	private String grade;
	private String packingLine;
	private String sewingFactory;
	private String firstPackFactory;
	private String lastPackFactory;
	public OrderModel getOrder() {
		return order;
	}
	public void setOrder(OrderModel order) {
		this.order = order;
	}
	public List<ProductModel> getProduct() {
		return product;
	}
	public void setProduct(List<ProductModel> product) {
		this.product = product;
	}
	private OrderModel order;
	private List<ProductModel> product;
	
	public String getCartonNo() {
		return cartonNo;
	}
	public void setCartonNo(String cartonNo) {
		this.cartonNo = cartonNo;
	}
	public String getUCC() {
		return UCC;
	}
	public void setUCC(String uCC) {
		UCC = uCC;
	}
	public String getUCCNo() {
		return UCCNo;
	}
	public void setUCCNo(String uCCNo) {
		UCCNo = uCCNo;
	}
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getCutNo() {
		return cutNo;
	}
	public void setCutNo(String cutNo) {
		this.cutNo = cutNo;
	}
	public String getFirstSewingLine() {
		return firstSewingLine;
	}
	public void setFirstSewingLine(String firstSewingLine) {
		this.firstSewingLine = firstSewingLine;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getPackingLine() {
		return packingLine;
	}
	public void setPackingLine(String packingLine) {
		this.packingLine = packingLine;
	}
	public String getSewingFactory() {
		return sewingFactory;
	}
	public void setSewingFactory(String sewingFactory) {
		this.sewingFactory = sewingFactory;
	}
	public String getFirstPackFactory() {
		return firstPackFactory;
	}
	public void setFirstPackFactory(String firstPackFactory) {
		this.firstPackFactory = firstPackFactory;
	}
	public String getLastPackFactory() {
		return lastPackFactory;
	}
	public void setLastPackFactory(String lastPackFactory) {
		this.lastPackFactory = lastPackFactory;
	}
	public String getBuyerPONO() {
		return buyerPONO;
	}
	public void setBuyerPONO(String buyerPONO) {
		this.buyerPONO = buyerPONO;
	}
	public String getStockKey() {
		return StockKey;
	}
	public void setStockKey(String stockKey) {
		StockKey = stockKey;
	}

}
