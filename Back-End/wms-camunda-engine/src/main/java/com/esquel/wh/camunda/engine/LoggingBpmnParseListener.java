package com.esquel.wh.camunda.engine;

import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;

import com.esquel.wh.camunda.engine.LoggingListeners.LoggingExecutionListener;
import com.esquel.wh.camunda.engine.LoggingListeners.LoggingStartEndEventExecutionListener;
import com.esquel.wh.camunda.engine.LoggingListeners.LoggingTransitionListener;
import com.esquel.wh.camunda.engine.LoggingListeners.LoggingUserTaskExecutionListener;

public abstract class LoggingBpmnParseListener extends AbstractBpmnParseListener {

	protected final LoggingExecutionListener loggingExecutionListener;
	protected final LoggingTransitionListener loggingTransitionListener;
	protected final LoggingUserTaskExecutionListener loggingUserTaskExecutionListener;
	protected final LoggingStartEndEventExecutionListener loggingStartEndEventExecutionListener;

	public LoggingBpmnParseListener() {
		LoggingListeners loggingListeners = new LoggingListeners();
		loggingExecutionListener = loggingListeners.executionListener;
		loggingTransitionListener = loggingListeners.transitionListener;
		loggingUserTaskExecutionListener = loggingListeners.userTaskExecutionListener;
		loggingStartEndEventExecutionListener = loggingListeners.startEndEventExecutionListener;
	}

}