package com.esquel.wh.controller;

import static org.neo4j.helpers.collection.MapUtil.map;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.server.rest.domain.JsonHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esquel.wh.dao.EASNDao;
import com.esquel.wh.dao.GEKFabricDao;
import com.esquel.wh.dao.WMSMasterDao;
import com.esquel.wh.model.FabricStock;
import com.esquel.wh.model.ResultJsonModel;
import com.esquel.wh.model.Stock;
import com.esquel.wh.utils.Neo4jUtil;

@RestController
@RequestMapping("/Inquiry")
public class InquiryController {
	
	@Value("${com.esquel.wh.wms.database.mysqlip}")
	private String wmsMasterDB ;
	
	@Value("${com.esquel.wh.wms.database.path}")
	private String path;
	
	@RequestMapping(path = "/Search", method = RequestMethod.POST, consumes = "application/json")
	public Map<String,List<Map>> Search(@RequestBody String query) {
		
		Neo4jUtil db = Neo4jUtil.getInstance(path);
		Map<String, List<Map>> result = db.executeQuery(query,map());

		return result;
	}
	
	@RequestMapping(path = "/getEASNSacnData", method = RequestMethod.GET)
	public ResultJsonModel<List<Stock>> getEASNScanData() {
		ResultJsonModel<List<Stock>> result = new ResultJsonModel<List<Stock>>();
		List<Stock> stockList = new ArrayList<Stock>();
		try {
			stockList = new EASNDao().getEASNScanData();
			result.setMessage("OK");
			result.setResult(stockList);
			result.setTotal(stockList.size());
		} catch (Throwable e) {
			e.printStackTrace();
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(path = "/getGEKFabric", method = RequestMethod.GET)
	public ResultJsonModel<List<FabricStock>> getGEKFabric() {
		ResultJsonModel<List<FabricStock>> result = new ResultJsonModel<List<FabricStock>>();
		List<FabricStock> stockList = new ArrayList<FabricStock>();
		try {
			stockList = new GEKFabricDao().getGEKFabricData();
			result.setMessage("OK");
			result.setResult(stockList);
			result.setTotal(stockList.size());
		} catch (SQLException e) {
			e.printStackTrace();
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(path = "/checkTransDateFrozen", method = RequestMethod.POST, consumes = "application/json")
	public ResultJsonModel<String> checkTransDate(@RequestBody String inputJson) {
		ResultJsonModel<String> result = new ResultJsonModel<String>();

		String type = "";
		String factoryCode = "";
		Date transDate = null;
		try {
			Map json = JsonHelper.jsonToMap(inputJson);
			Iterator entries = json.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String key = (String) entry.getKey();
				if (key.equalsIgnoreCase("type")) {
					type = (String) entry.getValue();
				} else if (key.equalsIgnoreCase("factoryCode")) {
					factoryCode = (String) entry.getValue();
				} else if (key.equalsIgnoreCase("transDate")) {
					DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = new java.util.Date();
					try {
						date = fmt.parse((String) entry.getValue());
					} catch (ParseException e) {
						e.printStackTrace();
						throw e;
					}
					if (date != null) {
						transDate = new java.sql.Date(date.getTime());
					}
				}
			}
			if (type.equals("")) {
				throw new Exception("Please input type.");
			}
			if (factoryCode.equals("")) {
				throw new Exception("Please input factory code.");
			}
			if (transDate == null) {
				throw new Exception("Please input trans date.");
			}

			new WMSMasterDao(wmsMasterDB).checkTransDateFrozen(type, factoryCode, transDate);
			
			result.setMessage("OK");
			result.setTotal(1);
			result.setResult("OK");
		} catch (Throwable e) {
			result.setMessage(e.getMessage());
		}

		return result;
	}
}
