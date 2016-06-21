package org.inspur.exam.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.inspur.exam.data.Bonus;
import org.inspur.exam.data.Staff;
import org.inspur.exam.service.IBonusService;
import org.inspur.exam.util.StringUtil;
import org.inspur.exam.util.TaxCalculator;
import org.loushang.framework.mybatis.PageUtil;
import org.loushang.framework.util.JSONUtil;
import org.loushang.framework.util.NameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/bonus")
public class BonusController {

	@Autowired
	IBonusService bonusService;

	// 词条信息的查询
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	@ResponseBody
	public String getData(HttpServletRequest request) {
		Map<String, Object> bonusdata = new HashMap<String, Object>();
		Map map = new HashMap();
		String staffName = request.getParameter("staffName");
		String staffId = request.getParameter("staffId");
		try {
			if (staffName != null && !staffName.equals("")) {
				staffName = java.net.URLDecoder.decode(staffName, "UTF-8");
				map.put("staffName", staffName);
			}
			if (staffId != null && !staffId.equals("")) {
				staffId = java.net.URLDecoder.decode(staffId, "UTF-8");
				map.put("staffId", staffId);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* 后端排序的实现 */
		int orderColumn; // 获取排序的列
		String orderMethod; // 获取排序列的方法desc,asc
		String orderColumndb; // 将获得的排序列转成数据库中对应的字段
		String orderSql;// 将排序列与其方法拼接传到后台
		if (request.getParameter("order[0][column]") != null) {
			orderColumn = Integer.parseInt(request
					.getParameter("order[0][column]"));
			orderColumndb = NameUtil.javaNameToDBName(request
					.getParameter("columns[" + orderColumn + "][data]"));// 将获得的排序列转成数据库中对应的字段
			orderMethod = request.getParameter("order[0][dir]");
			orderSql = orderColumndb + " " + orderMethod;
		} else {
			orderSql = null;
		}

		int length = -1;
		int total = 0;
		List<Bonus> bonus = new ArrayList<Bonus>();
		map.put("limit", length);
		// 默认按照员工号升序排序
		if(StringUtil.isEmpty(orderSql)) {
			orderSql = "staffId";
		}
		map.put("orderSql", orderSql);
		bonus = bonusService.findByCondition(map);
		total = PageUtil.getTotalCount("total");

		bonusdata.put("draw", request.getParameter("draw"));
		bonusdata.put("recordsTotal", total);
		bonusdata.put("recordsFiltered", total);
		bonusdata.put("data", bonus);

		return JSONUtil.bean2Json(bonusdata);
	}
	
	// 增加员工年终奖
	 @RequestMapping(value = "/get", method = RequestMethod.GET)
		@ResponseBody
		public String get(HttpServletRequest request) {
	    	String staffId = request.getParameter("staffId");
	    	List<Bonus> list = bonusService.findByStaffId(staffId);
	    	if(list != null && list.size()>0) {
	    		return "1";
	    	} else {
	    		return "0";
	    	}
	    }

	// 增加员工年终奖
	@RequestMapping(value = "/save")
	public String saveBonus(Bonus bonus) {
		if (bonus != null) {
			double wages=bonus.getWages();
			double totalBonus = bonus.getTotalBonus();
			double[] result=TaxCalculator.getOptimalSolution(wages,totalBonus,true);
			bonus.setBonus1(result[0]);
			bonus.setBonus2(result[1]);
			bonus.setBonus3(result[2]);
			bonus.setEscapeTax(result[5]);
			bonus.setAfterEscapeBonus(result[3]);
			bonus.setTotalTax(Double.parseDouble(String.format("%.2f",TaxCalculator.computeBonusTax(wages,totalBonus))));
			bonus.setAfterTaxBonus(Double.parseDouble(String.format("%.2f",totalBonus-TaxCalculator.computeBonusTax(wages,totalBonus))));
			bonusService.save(bonus);
		}
		return "exam/bonus";
	}

	// 修改员工年终奖
	@RequestMapping(value = "/edit/{id}")
	public ModelAndView editBonus(@PathVariable String id) {
		Bonus bonus = bonusService.findOne(id);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("bonus", bonus);
		return new ModelAndView("exam/editBonus", model);
	}

	// 删除员工年终奖
	@RequestMapping(value = "/delete/{id}")
	public String delBonus(@PathVariable String id) {
		if (StringUtil.isNotEmpty(id)) {
			String[] ids = id.split(",");
			bonusService.delete(ids);
		}
		return "exam/bonus";
	}

}
