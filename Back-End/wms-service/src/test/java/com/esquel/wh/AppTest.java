package com.esquel.wh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import com.esquel.wh.model.WorkflowRule;
import com.esquel.wh.utils.Common;
import com.esquel.wh.utils.DroolsRulesUtil;

import org.neo4j.graphql.IDLParser;
import org.neo4j.server.rest.domain.JsonHelper;

public class AppTest {
	// @Test
	public void OperationalRules() throws Exception {
		WorkflowRule rule = new WorkflowRule("GEG", "Garment", "KBR");
		DroolsRulesUtil<WorkflowRule> rules = new DroolsRulesUtil<WorkflowRule>();
		rules.setRuleExclePath("com/esquel/wh/workflowRule.xls");
		rules.setRuleObject(rule);
		rules.OperationalRules();
		System.out.println(rule.getWorkflowKey());
	}

	@Test
	public void testJson() {
		try {
			String inputString = Common.ReadFile("C:\\zhanghonl\\WMS\\receipt.json");
			Map<String, Object> aa = JsonHelper.jsonToMap(inputString);
			Map<String, List<String>> nodeProperty = new HashMap<String, List<String>>();
			Map<String, String> nodeKey = new HashMap<String, String>();
			getJsonValue("", aa, nodeProperty, nodeKey);
			System.out.println(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getJsonValue(String node, Map<String, Object> json, Map<String, List<String>> nodeproperty,
			Map<String, String> nodeKey) {
		boolean addFlag = true;

		Iterator<Entry<String, Object>> entries = json.entrySet().iterator(); // json.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, Object> entry = entries.next();
			String key = (String) entry.getKey();
			addFlag = true;
			if (entry.getValue() instanceof ArrayList) {
				for (int i = 0; i < ((ArrayList<Object>) entry.getValue()).size(); i++) {
					getJsonValue(key, (Map) (((ArrayList) entry.getValue()).get(i)), nodeproperty, nodeKey);
				}
			} else if (entry.getValue() instanceof Map) {
				getJsonValue(key, (Map<String, Object>) (entry.getValue()), nodeproperty, nodeKey);
			} else {
				List<String> tmpNode = null;

				if (nodeproperty != null && nodeproperty.size() > 0) {
					tmpNode = nodeproperty.get(node);
				}
				if (tmpNode == null) {
					tmpNode = new ArrayList<String>();
				}
				for (String str : tmpNode) {
					if (str.equals(key)) {
						addFlag = false;
						break;
					}
				}
				if (addFlag) {
					tmpNode.add(key);
					nodeproperty.put(node, tmpNode);
				}
				if (key.equalsIgnoreCase("UniqueKey")) {
					String tmpKeyNode = "";
					if (nodeKey != null) {
						tmpKeyNode = nodeKey.get(node + "UniqueKey");
					}
					if (tmpKeyNode == null || tmpKeyNode.equals("")) {
						nodeKey.put(node + "UniqueKey", ((String) entry.getValue()).toLowerCase());
					}
				}
			}
		}
	}
}
