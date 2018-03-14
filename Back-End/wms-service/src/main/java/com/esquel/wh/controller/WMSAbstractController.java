package com.esquel.wh.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.esquel.wh.camunda.process.service.CamundaProcessService;

public abstract class WMSAbstractController {
	
	@Autowired
	protected CamundaProcessService camundaProcessService;

}