package com.esquel.wh.model;

public class WorkflowRule {
	private String factoryCode;
	private String type	;
	private String transCD	;
	private String workflowKey;
	
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTransCD() {
		return transCD;
	}
	public void setTransCD(String transCD) {
		this.transCD = transCD;
	}
	public String getWorkflowKey() {
		return workflowKey;
	}
	public void setWorkflowKey(String workflowKey) {
		this.workflowKey = workflowKey;
	}
public WorkflowRule(){
		
	}

	public WorkflowRule(String factorycode,String type,String transcd){
		super();
		this.factoryCode = factorycode;
		this.type = type;
		this.transCD = transcd;
	}
	
	@Override
	public String toString() {
		return "Facotry Code="+factoryCode+" Type="+type+" Trans Code="+transCD+
		" Workflow Key="+workflowKey;
	}
}
