package com.esquel.wh.configuration;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.esquel.wh.camunda.engine.LoggingExecutionParseListener;
import com.esquel.wh.camunda.engine.LoggingStartEndEventExecutionParseListener;
import com.esquel.wh.camunda.engine.LoggingTransitionParseListener;
import com.esquel.wh.camunda.engine.LoggingUserTaskExecutionParseListener;


@Component
@Order(Ordering.DEFAULT_ORDER + 1)
public class ProcessEngineConfiguration implements ProcessEnginePlugin {

	@Override
	public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
		List<BpmnParseListener> bpmnParseListener = new ArrayList<>();
		bpmnParseListener.add(new LoggingExecutionParseListener());
		bpmnParseListener.add(new LoggingStartEndEventExecutionParseListener());
		bpmnParseListener.add(new LoggingTransitionParseListener());
		bpmnParseListener.add(new LoggingUserTaskExecutionParseListener());
		processEngineConfiguration.setCustomPostBPMNParseListeners(bpmnParseListener);
	}

	@Override
	public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
	}

	@Override
	public void postProcessEngineBuild(ProcessEngine processEngine) {
	}

}
