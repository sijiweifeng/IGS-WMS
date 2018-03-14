package com.esquel.wh.model;

public class ResultJsonModel<T> {
private T result;
private int total;
private String message;
public T getResult() {
	return result;
}
public void setResult(T result) {
	this.result = result;
}

public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
}
