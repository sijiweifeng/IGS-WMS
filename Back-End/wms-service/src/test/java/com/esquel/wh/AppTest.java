package com.esquel.wh;

import org.junit.Test;
import com.esquel.wh.model.WorkflowRule;
import com.esquel.wh.utils.DroolsRulesUtil;

public class AppTest{
	@Test
    public void OperationalRules() throws Exception {
		WorkflowRule rule = new WorkflowRule("GEG","Garment","KBR");
		DroolsRulesUtil<WorkflowRule> rules = new DroolsRulesUtil<WorkflowRule>();
		rules.setRuleExclePath("com/esquel/wh/workflowRule.xls");
		rules.setRuleObject(rule);
		rules.OperationalRules();
		System.out.println(rule.getWorkflowKey());
    }
    
}
