package com.esquel.wh.dao;

import static org.neo4j.helpers.collection.MapUtil.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

import com.esquel.wh.camunda.process.service.CamundaProcessService;
import com.esquel.wh.model.WorkflowRule;
import com.esquel.wh.utils.DroolsRulesUtil;
import com.esquel.wh.utils.Neo4jUtil;

public class ReceiptDao {

	public void doReceipt(String dbPath,String cql,Map json,CamundaProcessService camundaProcessService){
		Neo4jUtil db = Neo4jUtil.getInstance(dbPath);
		Node receipt = null;
		Transaction trx = db.getTransaction();
		String docNo="";
		String transCode="";
		Long id= new Long("0");
		Result receiptResult = null; 
		try{
			receiptResult = db.execute(cql, json);
			try (ResourceIterator<Node> nodes = receiptResult.columnAs("receipt")) {
				while (nodes.hasNext()) {
					Node tmpnode = nodes.next();
					receipt = db.getNode(tmpnode.getId());
					
					Map<String, List<Map>> nd = db.executeQuery(
							"query ReceiptQuery {receipt (_id:" + tmpnode.getId() + "){ docNo,_id,transCode} }", map());
					
					Iterator<Entry<String, List<Map>>> findNode = nd.entrySet().iterator();
					while (findNode.hasNext()) {
						Map.Entry entry = (Map.Entry) findNode.next();
						String key = (String) entry.getKey();
						List<Map> valueList = (List<Map>) entry.getValue();
						if (valueList == null || valueList.size() <= 0) {
							continue;
						}
						docNo=valueList.get(0).get("docNo").toString();
						transCode = valueList.get(0).get("transCode").toString();
						id = new Long(valueList.get(0).get("_id").toString());
					}
					
					WorkflowRule wr = new WorkflowRule("GEG","Garment",transCode);
					DroolsRulesUtil<WorkflowRule> rule = new DroolsRulesUtil<WorkflowRule>();
					rule.OperationalRules();
					
					Map<String, Object> variables = new HashMap<String, Object>();
					variables.put("assignee", "Admin");
					variables.put("nodeID", id);
					variables.put("docNo", docNo);
					variables.put("MaterialType", "Garment");

					String processInstanceId = camundaProcessService.startProcess(wr.getWorkflowKey(), variables);

					System.out.println(tmpnode.getId());

				}
			}			
			trx.success();
		}catch(Exception e){
			e.printStackTrace();
			trx.failure();
			throw e;
		}	
	}
}
