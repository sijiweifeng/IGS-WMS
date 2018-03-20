package com.esquel.wh.model;

import java.sql.Date;
import java.util.List;

public class Stock {
	private String id;
	private String uniqueKey;
	private String type;
	private String createdBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private String lastModifiedBy;
	private String uom;
	private int originalQty;
	private int qty;
	private Date inFtyDate;
	private Date inStoreDate;
	private Date expiryDate;
	private String status;
	private String UCC;
	private String storeCode;
	private String stockClass;
	private String stockGroup;
	private String grade;
	private String source;
	private String cartonNo;
	private String easnCustomerCd;
	private String prePackCd;
	private String prePackStyle;
	private String prePackUnit;
	private String ctNo;
	private String packingLine;
	private String firstSewingLine;
	private String sewingFactory;
	private String firstPackFactory;
	private String lastPackFactory;
	private String weight;
	private String boardCode;
	private String buyerPONO;
	private OrderModel order;
	private List<ProductModel> product;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public int getOriginalQty() {
		return originalQty;
	}
	public void setOriginalQty(int originalQty) {
		this.originalQty = originalQty;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public Date getInFtyDate() {
		return inFtyDate;
	}
	public void setInFtyDate(Date inFtyDate) {
		this.inFtyDate = inFtyDate;
	}
	public Date getInStoreDate() {
		return inStoreDate;
	}
	public void setInStoreDate(Date inStoreDate) {
		this.inStoreDate = inStoreDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUCC() {
		return UCC;
	}
	public void setUCC(String uCC) {
		UCC = uCC;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getStockClass() {
		return stockClass;
	}
	public void setStockClass(String stockClass) {
		this.stockClass = stockClass;
	}
	public String getStockGroup() {
		return stockGroup;
	}
	public void setStockGroup(String stockGroup) {
		this.stockGroup = stockGroup;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCartonNo() {
		return cartonNo;
	}
	public void setCartonNo(String cartonNo) {
		this.cartonNo = cartonNo;
	}
	public String getEasnCustomerCd() {
		return easnCustomerCd;
	}
	public void setEasnCustomerCd(String easnCustomerCd) {
		this.easnCustomerCd = easnCustomerCd;
	}
	public String getPrePackCd() {
		return prePackCd;
	}
	public void setPrePackCd(String prePackCd) {
		this.prePackCd = prePackCd;
	}
	public String getPrePackStyle() {
		return prePackStyle;
	}
	public void setPrePackStyle(String prePackStyle) {
		this.prePackStyle = prePackStyle;
	}
	public String getPrePackUnit() {
		return prePackUnit;
	}
	public void setPrePackUnit(String prePackUnit) {
		this.prePackUnit = prePackUnit;
	}
	public String getCtNo() {
		return ctNo;
	}
	public void setCtNo(String ctNo) {
		this.ctNo = ctNo;
	}
	public String getPackingLine() {
		return packingLine;
	}
	public void setPackingLine(String packingLine) {
		this.packingLine = packingLine;
	}
	public String getFirstSewingLine() {
		return firstSewingLine;
	}
	public void setFirstSewingLine(String firstSewingLine) {
		this.firstSewingLine = firstSewingLine;
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
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBoardCode() {
		return boardCode;
	}
	public void setBoardCode(String boardCode) {
		this.boardCode = boardCode;
	}
	public String getBuyerPONO() {
		return buyerPONO;
	}
	public void setBuyerPONO(String buyerPONO) {
		this.buyerPONO = buyerPONO;
	}
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
	public String getUniqueKey() {
		return uniqueKey;
	}
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

		
}
