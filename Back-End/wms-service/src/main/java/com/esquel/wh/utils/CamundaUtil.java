package com.esquel.wh.utils;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.task.Task;

import com.esquel.wh.camunda.process.service.CamundaProcessService;

public class CamundaUtil {
	public static boolean complete(CamundaProcessService camundaProcessService,String key) {
		List<Task> assignedTasks = camundaProcessService.getTasks();

		for (Task tak : assignedTasks) {
			Map<String, Object> variables = camundaProcessService.getRuntimeService()
					.getVariables(tak.getExecutionId());
			if (key.equals(variables.get("nodeID").toString())) {
				camundaProcessService.completeTask(tak.getId());
			}
		}
		return true;
	}
}
