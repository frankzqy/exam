package org.inspur.exam.service.Impl;

import java.util.List;
import java.util.Map;

import org.inspur.exam.dao.StaffMapper;
import org.inspur.exam.data.Staff;
import org.inspur.exam.service.IStaffTaxService;
import org.inspur.exam.util.StringUtil;
import org.inspur.exam.util.TaxCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("staffTaxService")
public class StaffTaxServiceImpl implements IStaffTaxService {

    @Autowired
    private StaffMapper staffMapper;

    /**
     * 
     * @return
     */
    public List<Staff> getStaffList(Map<String, Object> param) {

	return staffMapper.findAll(param);
    }

    public void addStaff(Staff staff) {
	staffMapper.addStaff(staff);
    }

    public Staff findOneStaff(String staffId) {
	return staffMapper.findOne(staffId);
    }
    
    public void deleteStaff(String ids) {
	if (-1 == ids.indexOf(",")) {
	    staffMapper.deleteStaff(ids);
	} else {
	    String[] idArr = ids.split(",");
	    staffMapper.deleteAll(idArr);
	}
    }
    
    public void updateStaff(Staff staff) {
	double tax = TaxCalculator.getTax(staff.getWages());
	double afterTax = staff.getWages() - tax;
	staff.setAfterTax(Double.parseDouble(String.format("%.2f",afterTax)));
	staffMapper.updateStaff(staff);
    }
}
