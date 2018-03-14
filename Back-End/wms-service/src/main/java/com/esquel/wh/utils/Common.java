package com.esquel.wh.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Common {
	public static Map JosnToMap(String jsonValue) {
		ObjectMapper mapper = new ObjectMapper();
		Map json = null;
		try {
			json = mapper.readValue(jsonValue, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public static String ReadFile(String path) {
		String laststr = "";
		File file = new File(path);// 打开文件
		BufferedReader reader = null;
		try {
			FileInputStream in = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));// 读取文件
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr = laststr + tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException el) {
				}
			}
		}
		return laststr;
	}

	public static void getJsonValue(String node, Map json, Map<String, List<String>> nodeproperty,
			Map<String, String> nodeKey) {
		boolean addFlag = true;
		Iterator entries = json.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String key = (String) entry.getKey();
			addFlag = true;
			if (entry.getValue() instanceof ArrayList) {
				for (int i = 0; i < ((ArrayList) entry.getValue()).size(); i++) {
					getJsonValue(key, (Map) (((ArrayList) entry.getValue()).get(i)), nodeproperty, nodeKey);
				}
			} else if (entry.getValue() instanceof Map) {
				getJsonValue(key, (Map) (entry.getValue()), nodeproperty, nodeKey);
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
				if (key.equalsIgnoreCase(node + "Key")) {
					String tmpKeyNode = "";
					if (nodeKey != null) {
						tmpKeyNode = nodeKey.get(node + "Key");
					}
					if (tmpKeyNode == null || tmpKeyNode.equals("")) {
						nodeKey.put(node + "Key", ((String) entry.getValue()).toLowerCase());
					}
				}
			}
		}
	}
}
