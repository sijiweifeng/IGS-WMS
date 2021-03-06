package com.esquel.wh.controller;

import static org.neo4j.helpers.collection.MapUtil.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.Result;
import com.esquel.wh.utils.JsonHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esquel.wh.dao.ReceiptDao;
import com.esquel.wh.model.ResultJsonModel;
import com.esquel.wh.model.scanList;
import com.esquel.wh.utils.CamundaUtil;
import com.esquel.wh.utils.Common;
import com.esquel.wh.utils.Neo4jUtil;

@RestController
@RequestMapping("/Receipt")
public class ReceiptController extends WMSAbstractController {

	@Value("${com.esquel.wh.wms.database.path}")
	private String neo4jDatabasePath;

	@RequestMapping(path = "/getScanData/{id}", method = RequestMethod.GET)
	public ResultJsonModel<List<scanList>> getScanData(@PathVariable String id) {
		Neo4jUtil db = Neo4jUtil.getInstance(neo4jDatabasePath);

		List<scanList> resultTask = new ArrayList<scanList>();
		Map<String, List<Map>> nd2 = db
				.executeQuery("query GRNQuery {GRN(_id:" + id + "){docNo,_id, transferin{ucc}} }", map());

		Iterator<Entry<String, List<Map>>> node2 = nd2.entrySet().iterator();
		LinkedHashMap aa = null;

		while (node2.hasNext()) {
			Map.Entry entry = (Map.Entry) node2.next();
			String key = (String) entry.getKey();
			List<Map> valueList = (List<Map>) entry.getValue();
			if (valueList.size() > 0) {
				aa = (LinkedHashMap) valueList.get(0).get("transferin");
				scanList scan = new scanList();
				scan.setUcc(aa.get("ucc").toString());
				scan.setLocationreadonly(true);
				scan.setLocation("A-01-01-01-01");
				scan.setStatus(false);

				resultTask.add(scan);
			}
		}

		ResultJsonModel<List<scanList>> result = new ResultJsonModel<List<scanList>>();
		result.setMessage("OK");
		result.setTotal(resultTask.size());
		result.setResult(resultTask);
		return result;
	}

	@RequestMapping(path = "/Create", method = RequestMethod.POST, consumes = "application/json")
	public ResultJsonModel<String> Create(@RequestBody String inputJson) {
		String objectStr = inputJson;
		System.out.println(objectStr);
		Map<String, List<String>> nodeProperty = new HashMap<String, List<String>>();
		Map<String, String> nodeKey = new HashMap<String, String>();
		
		String cql = "", hdSql = "", cSql = "", kSql = "", eSql = "";
		String ghdSql = "", gcSql = "", gkSql = "", geSql = "";
		String shdSql = "", scSql = "", skSql = "", seSql = "";
		String ohdSql = "", ocSql = "", okSql = "", oeSql = "";
		String query ="";
		
		Map json = null;
		try {
			json = JsonHelper.jsonToMap(inputJson);
			Common.getJsonValue("", json, nodeProperty, nodeKey);
			Iterator node = nodeProperty.entrySet().iterator();
			while (node.hasNext()) {
				Map.Entry entry = (Map.Entry) node.next();
				String key = (String) entry.getKey();
				List<String> valueList = (List<String>) entry.getValue();
				String nodeKeyStr = nodeKey.get(key + "UniqueKey");
				String[] nodeKeyStrList = nodeKeyStr.split(";");
				List<String> nodeKeyList = Arrays.asList(nodeKeyStrList);

				if (key.equalsIgnoreCase("Order")) {
					oeSql = valueList.size() > 1 ? "}) ON CREATE SET " : "})";
				} else if (key.equalsIgnoreCase("Stock")) {
					seSql = valueList.size() > 1 ? "}) ON CREATE SET " : "})";
				} else if (key.equalsIgnoreCase("Product")) {
					geSql = valueList.size() > 1 ? "}) ON CREATE SET " : "})";
				} else {
					eSql = valueList.size() > 1 ? "}) ON CREATE SET " : "})";
				}
				for (String str : valueList) {
					if (nodeKeyList.contains(str.toLowerCase())) {
						if (key.equalsIgnoreCase("Order")) {
							if (ohdSql.equals("")) {
								ohdSql = "FOREACH (o IN s.order | MERGE(" + key.toLowerCase() + ":" + key + "{";
							}
							okSql = (okSql != "" ? okSql + "," : okSql) + str + ":o." + str;
						} else if (key.equalsIgnoreCase("Product")) {
							if (ghdSql.equals("")) {
								ghdSql = "FOREACH (g IN s.product | MERGE(" + key.toLowerCase() + ":" + key + "{";
							}
							gkSql = (gkSql != "" ? gkSql + "," : gkSql) + str + ":g." + str;

						} else if (key.equalsIgnoreCase("Stock")) {
							if (shdSql.equals("")) {
								shdSql = "FOREACH (s IN q.stock | MERGE(" + key.toLowerCase() + ":" + key + "{";
							}
							skSql = (skSql != "" ? skSql + "," : skSql) + str + ":s." + str;
						} else {
							if (hdSql.equals("")) {
								hdSql = "MERGE(" + key.toLowerCase() + ":" + key + "{";
							}
							kSql = (kSql != "" ? kSql + "," : kSql) + str + ":q." + str;
						}
					} else {
						if (key.equalsIgnoreCase("Order")) {
							ocSql = (ocSql != "" ? ocSql + "," : ocSql) + key.toLowerCase() + "." + str + "=" + "o." + str;
						} else if (key.equalsIgnoreCase("Product")) {
							gcSql = (gcSql != "" ? gcSql + "," : gcSql) + key.toLowerCase() + "." + str + "=" + "g." + str;
						} else if (key.equalsIgnoreCase("Stock")) {
							scSql = (scSql != "" ? scSql + "," : scSql) + key.toLowerCase() + "." + str + "=" + "s." + str;
						} else {
							cSql = (cSql != "" ? cSql + "," : cSql) + key.toLowerCase() + "." + str + "=" + "q." + str;
						}
					}
				}
			}

			cql = hdSql + kSql + eSql + cSql + "\n" + shdSql + skSql + seSql + scSql
					+ "\nMERGE (receipt)-[r:TransferIn]->(stock) \n" + ghdSql + gkSql + geSql + gcSql
					+ " MERGE (stock)-[r:InstanceOf]->(product) )\n" + ohdSql + okSql + oeSql + ocSql
					+ " MERGE (order)-[r:Produces]->(stock) MERGE (receipt)-[r2:Contanis]->(order) )\n"
					+ ") RETURN receipt \n";

			query = "WITH {json} as data\nUNWIND data.receipt as q \n" + cql + " ";
			System.out.println(query);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ResultJsonModel<String> result = new ResultJsonModel<String>();
		ReceiptDao recDao = new ReceiptDao();
		try {
			recDao.doReceipt(neo4jDatabasePath, query, json, camundaProcessService);
			result.setMessage("OK");
			result.setTotal(1);
			result.setResult("OK");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
		}

		return result;
	}

	@RequestMapping(path = "/Test/{id}", method = RequestMethod.POST, consumes = "application/json")
	public ResultJsonModel<String> Test(@PathVariable String id, @RequestBody String inputJson) {
		System.out.println(id);
		CamundaUtil.complete(camundaProcessService, id);
		ResultJsonModel<String> result = new ResultJsonModel<String>();
		result.setMessage("OK");
		result.setTotal(1);
		result.setResult("OK");

		return result;
	}

	@RequestMapping(path = "/Scan/{id}", method = RequestMethod.POST, consumes = "application/json")
	public ResultJsonModel<String> GRNScan(@PathVariable String id, @RequestBody String inputJson) {
		System.out.println(id);
		CamundaUtil.complete(camundaProcessService, id);
		ResultJsonModel<String> result = new ResultJsonModel<String>();
		result.setMessage("OK");
		result.setTotal(1);
		result.setResult("OK");

		return result;
	}

	@RequestMapping(path = "/UpdateLocation", method = RequestMethod.POST, consumes = "application/json")
	public Result UpdateLocation(@RequestBody String inputJson) {
		String cql = "";
		Map json = null;
		String query = "WITH {json} as data\nUNWIND data.Stock as q \n" + cql + " ";
		System.out.println(query);
		Neo4jUtil db = Neo4jUtil.getInstance();
		return db.execute(query, java.util.Collections.singletonMap("json", json));
	}
}
