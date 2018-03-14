package com.esquel.wh;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.builder.DecisionTableConfiguration;
import org.drools.builder.DecisionTableInputType;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import com.esquel.wh.model.LocationRule;
import com.esquel.wh.model.WorkflowRule;

@SuppressWarnings("restriction")
public class LocationXLS {
	static Logger logger = Logger.getLogger(LocationXLS.class);
	public static void main(String[] args) {
		logger.info("开始");
		DecisionTableConfiguration dtc=KnowledgeBuilderFactory.newDecisionTableConfiguration();
		dtc.setInputType(DecisionTableInputType.XLS);
		KnowledgeBuilder builder=KnowledgeBuilderFactory.newKnowledgeBuilder();
		//builder.add(ResourceFactory.newClassPathResource("com/esquel/wh/locationRule.xls", LocationXLS.class),ResourceType.DTABLE,dtc);
		builder.add(ResourceFactory.newClassPathResource("com/esquel/wh/workflowRule.xls", LocationXLS.class),ResourceType.DTABLE,dtc);
		KnowledgeBase base=builder.newKnowledgeBase();
		base.addKnowledgePackages(base.getKnowledgePackages());
		StatefulKnowledgeSession session=base.newStatefulKnowledgeSession();
		//LocationRule cal=new LocationRule("A","K","13025","N");
		WorkflowRule cal = new WorkflowRule("GEG","Garment","KBR");
		session.insert(cal);
		int count = session.fireAllRules();
		System.out.println("总共执行" + count + "条规则------------------------------");
		System.out.println(cal.toString());
		session.dispose();//释放资源
		logger.info("结束");
		logger.debug("This is debug message.");  
		 logger.error("This is error message.");  
	}

}
