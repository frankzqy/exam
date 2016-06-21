package org.inspur.exam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.inspur.exam.util.StringUtil;
import org.inspur.exam.util.TaxCalculator;
import org.loushang.framework.util.JSONUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/calculator")
public class CalcController {
	
	@RequestMapping(value = "/wageTax",method = RequestMethod.GET)
	@ResponseBody
	public String wageTax(HttpServletRequest request) {
		if(!StringUtil.isEmpty(request.getParameter("presalary"))){
			double presalary=Double.parseDouble(request.getParameter("presalary"));
			double tax=Double.parseDouble(String.format("%.2f",TaxCalculator.getTax(presalary)));
			Map<String, Object> userdata = new HashMap<String, Object>();
			userdata.put("tax", tax);
			userdata.put("behsalary", presalary-tax);		
			return JSONUtil.bean2Json(userdata);
		}
		return null;
	}
	
	@RequestMapping(value = "/bonusTax")
	@ResponseBody
	public String bonusTax(HttpServletRequest request) {	
		if(!StringUtil.isEmpty(request.getParameter("presalary"))&&!StringUtil.isEmpty(request.getParameter("yearbonus"))){
			double presalary=Double.parseDouble(request.getParameter("presalary"));
			double yearbonus=Double.parseDouble(request.getParameter("yearbonus"));
			double tax=Double.parseDouble(String.format("%.2f",TaxCalculator.computeBonusTax(presalary, yearbonus)));
			Map<String, Object> userdata = new HashMap<String, Object>();
			userdata.put("tax", tax);
			userdata.put("behsalary", yearbonus-tax);		
			return JSONUtil.bean2Json(userdata);
		}
		return null;
	}
	
	@RequestMapping(value = "/optTax")
	@ResponseBody
	public String optTax(HttpServletRequest request) {	
		if(!StringUtil.isEmpty(request.getParameter("presalary"))&&!StringUtil.isEmpty(request.getParameter("yearbonus"))){
			double presalary=Double.parseDouble(request.getParameter("presalary"));
			double yearbonus=Double.parseDouble(request.getParameter("yearbonus"));
			double[] result=TaxCalculator.getOptimalSolution(presalary, yearbonus, true);
			//double tax=Double.parseDouble(String.format("%.2f",TaxCalculator.getTaxForBonus(yearbonus, presalary)));
			Map<String, Object> userdata = new HashMap<String, Object>();
			userdata.put("bonus1", result[0]);
			userdata.put("firstmonth", result[1]);	
			userdata.put("secondmonth", result[2]);
			userdata.put("tax", result[4]);
			userdata.put("behsalary", result[3]);
			return JSONUtil.bean2Json(userdata);
		}
		return null;
	}
}
