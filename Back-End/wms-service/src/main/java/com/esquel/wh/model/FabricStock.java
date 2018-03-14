package com.esquel.wh.model;

import java.math.BigDecimal;
import java.util.List;

public class FabricStock {
private String StockKey;
private String UCC;
private BigDecimal qty	;
private BigDecimal weight	;
private BigDecimal foc_qty	;
private BigDecimal allowance_qty	;
private String stockUom	;
private BigDecimal invoicePrice	;
private String invoiceNo	;
private BigDecimal grossPrice	;
private BigDecimal ppoPrice	;
private String currency	;
private OrderModel order;
public OrderModel getOrder() {
	return order;
}
public void setOrder(OrderModel order) {
	this.order = order;
}
public List<FabricProductModel> getProduct() {
	return product;
}
public void setProduct(List<FabricProductModel> product) {
	this.product = product;
}
private List<FabricProductModel> product;

public String getStockKey() {
	return StockKey;
}
public void setStockKey(String stockKey) {
	StockKey = stockKey;
}
public String getUCC() {
	return UCC;
}
public void setUCC(String uCC) {
	UCC = uCC;
}
public BigDecimal getQty() {
	return qty;
}
public void setQty(BigDecimal qty) {
	this.qty = qty;
}
public BigDecimal getWeight() {
	return weight;
}
public void setWeight(BigDecimal weight) {
	this.weight = weight;
}
public BigDecimal getFoc_qty() {
	return foc_qty;
}
public void setFoc_qty(BigDecimal foc_qty) {
	this.foc_qty = foc_qty;
}
public BigDecimal getAllowance_qty() {
	return allowance_qty;
}
public void setAllowance_qty(BigDecimal allowance_qty) {
	this.allowance_qty = allowance_qty;
}
public String getStockUom() {
	return stockUom;
}
public void setStockUom(String stockUom) {
	this.stockUom = stockUom;
}
public BigDecimal getInvoicePrice() {
	return invoicePrice;
}
public void setInvoicePrice(BigDecimal invoicePrice) {
	this.invoicePrice = invoicePrice;
}
public String getInvoiceNo() {
	return invoiceNo;
}
public void setInvoiceNo(String invoiceNo) {
	this.invoiceNo = invoiceNo;
}
public BigDecimal getGrossPrice() {
	return grossPrice;
}
public void setGrossPrice(BigDecimal grossPrice) {
	this.grossPrice = grossPrice;
}
public BigDecimal getPpoPrice() {
	return ppoPrice;
}
public void setPpoPrice(BigDecimal ppoPrice) {
	this.ppoPrice = ppoPrice;
}
public String getCurrency() {
	return currency;
}
public void setCurrency(String currency) {
	this.currency = currency;
}

}
