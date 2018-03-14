package com.esquel.wh.controller;

import static org.neo4j.helpers.collection.MapUtil.map;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.task.Task;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esquel.wh.model.ResultJsonModel;
import com.esquel.wh.model.taskList;
import com.esquel.wh.utils.Neo4jUtil;

@RestController
@RequestMapping("/Camunda")
public class CamundaController extends WMSAbstractController {
	@RequestMapping(path = "/getAllTask")
	public ResultJsonModel<List<taskList>> getAllTask() {
		Neo4jUtil db = Neo4jUtil.getInstance();
		ResultJsonModel<List<taskList>> result = new ResultJsonModel<List<taskList>>();
		List<Task> taskList = camundaProcessService.getTasks();
		List<taskList> resultTask = new ArrayList<taskList>();
		try {
			for (Task tak : taskList) {
				Map<String, Object> variables = camundaProcessService.getRuntimeService()
						.getVariables(tak.getExecutionId());

				RepositoryService repositoryService = camundaProcessService.getProcessEngine().getRepositoryService();

				ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
						.processDefinitionId(tak.getProcessDefinitionId());
				ProcessDefinition pd = processDefinitionQuery.singleResult();

				if (variables.get("nodeID") != null && variables.get("MaterialType") != null) {
					System.out.println("Process Key:" + pd.getKey() + " Node ID:" + variables.get("nodeID"));
					Map<String, List<Map>> nd = db.executeQuery(
							"query GRNQuery {GRN(_id:" + (long) variables.get("nodeID") + "){ docNo,_id} }", map());
					if (nd == null || nd.size() <= 0) {
						continue;
					}
					Iterator<Entry<String, List<Map>>> node = nd.entrySet().iterator();
					while (node.hasNext()) {
						Map.Entry entry = (Map.Entry) node.next();
						String key = (String) entry.getKey();
						List<Map> valueList = (List<Map>) entry.getValue();
						if (valueList == null || valueList.size() <= 0) {
							continue;
						}
						Map mapType = new Hashtable();
						taskList task = new taskList();
						task.setDocNo(valueList.get(0).get("docNo").toString());
						task.setType(variables.get("MaterialType").toString());
						task.setId((long) variables.get("nodeID"));
						resultTask.add(task);
					}
				}
			}
			result.setMessage("OK");
			result.setTotal(resultTask.size());
			result.setResult(resultTask);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			resultTask = null;
		}
		return result;
	}
}
