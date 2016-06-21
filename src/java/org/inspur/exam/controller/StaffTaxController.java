package org.inspur.exam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.inspur.exam.data.Staff;
import org.inspur.exam.service.IStaffTaxService;
import org.inspur.exam.util.StringUtil;
import org.inspur.exam.util.TaxCalculator;
import org.loushang.framework.mybatis.PageUtil;
import org.loushang.framework.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/stafftax")
public class StaffTaxController {

    @Autowired
    private IStaffTaxService staffTaxService;
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String getData(HttpServletRequest request) {
	String staffId = request.getParameter("staffId");
	String staffName = request.getParameter("staffName");
	/* 后端分页的实现 */
	int start = 0;
	int length = 0;
	// datatable使用前台分页
	if (request.getParameter("start") == null || request.getParameter("start").equals("")
		|| request.getParameter("length") == null || request.getParameter("length").equals("")) {
	    length = -1;
	} else {
	    start = Integer.parseInt(request.getParameter("start"));
	    length = Integer.parseInt(request.getParameter("length"));
	}

	int total = 0;
	List<Staff> staffList = null;
	Map<String, Object> param = new HashMap<String, Object>();
	if (StringUtil.isNotEmpty(staffId)) {
	    param.put("staffId", staffId);
	}
	if (StringUtil.isNotEmpty(staffName)) {
	    param.put("name", staffName);
	}
	if (length != -1) {
	    param.put("start", start);
	    param.put("limit", length);
	    staffList = staffTaxService.getStaffList(param);
	    total = PageUtil.getTotalCount("total");
	} else {
	    staffList = staffTaxService.getStaffList(param);
	    total = PageUtil.getTotalCount("total");
	}
	Map<String, Object> userdata = new HashMap<String, Object>();
	userdata.put("draw", request.getParameter("draw"));
	userdata.put("recordsTotal", total);
	userdata.put("recordsFiltered", total);
	userdata.put("data", staffList);
	return JSONUtil.bean2Json(userdata);
    }
    
    @RequestMapping(value = "get", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request) {
    	String staffId = request.getParameter("staffId");
    	Staff staff = staffTaxService.findOneStaff(staffId);
    	if(staff != null) {
    		return "1";
    	} else {
    		return "0";
    	}
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String getData(HttpServletRequest request, Staff staff) {
	// 根据月工资计算本月该交的税
	double tax = TaxCalculator.getTax(staff.getWages());
	double afterTax = staff.getWages() - tax;
	staff.setAfterTax(Double.parseDouble(String.format("%.2f",afterTax)));
	staffTaxService.addStaff(staff);
	return "exam/staffTax";
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView addStaff(HttpServletRequest request) {
	ModelAndView mav = new ModelAndView("exam/updateStaff");
	String staffId = request.getParameter("staffId");
	Staff staff = staffTaxService.findOneStaff(staffId);
	mav.addObject("staff", staff);
	return mav;
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public void deleteStaff(HttpServletRequest request) {
	String ids = request.getParameter("ids");
	if (StringUtil.isNotEmpty(ids)) {
	    if (",".equals(ids.substring(ids.length() - 1))) {
		ids = ids.substring(0, ids.length() - 1);
	    }
	    staffTaxService.deleteStaff(ids);
	}
    }
    
    @RequestMapping(value = "/updateSave", method = RequestMethod.POST)
    public String updateStaff(HttpServletRequest request, Staff staff) {
	staffTaxService.updateStaff(staff);
	return "exam/staffTax";
    }
}
