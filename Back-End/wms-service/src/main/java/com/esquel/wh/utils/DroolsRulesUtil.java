package com.esquel.wh.utils;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.builder.DecisionTableConfiguration;
import org.drools.builder.DecisionTableInputType;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

//@SuppressWarnings("restriction")
public class DroolsRulesUtil<T> {
	static Logger logger = Logger.getLogger(DroolsRulesUtil.class);
	private T ruleObject;
	private String ruleExclePath ;
	
	public void OperationalRules(){
		logger.info("开始");
		DecisionTableConfiguration dtc=KnowledgeBuilderFactory.newDecisionTableConfiguration();
		dtc.setInputType(DecisionTableInputType.XLS);
		KnowledgeBuilder builder=KnowledgeBuilderFactory.newKnowledgeBuilder();
		builder.add(ResourceFactory.newClassPathResource(this.getRuleExclePath() , DroolsRulesUtil.class),ResourceType.DTABLE,dtc);
		KnowledgeBase base=builder.newKnowledgeBase();
		base.addKnowledgePackages(base.getKnowledgePackages());
		StatefulKnowledgeSession session=base.newStatefulKnowledgeSession();
		session.insert(ruleObject);
		int count = session.fireAllRules();
		System.out.println("总共执行" + count + "条规则------------------------------");
		System.out.println(ruleObject.toString());
		session.dispose();//释放资源
		logger.info("结束");
	}

	public T getRuleObject() {
		return ruleObject;
	}

	public void setRuleObject(T ruleObject) {
		this.ruleObject = ruleObject;
	}

	public String getRuleExclePath() {
		return ruleExclePath;
	}

	public void setRuleExclePath(String ruleExclePath) {
		this.ruleExclePath = ruleExclePath;
	}
}
