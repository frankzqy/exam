package org.inspur.exam.service;

import java.util.List;
import java.util.Map;

import org.inspur.exam.data.Staff;

public interface IStaffTaxService {
    
    /**
     * 查询员工信息列表
     * @param param
     * @return
     */
    public List<Staff> getStaffList(Map<String, Object> param);
    
    /**
     * 增加员工
     * @param param
     * @return
     */
    public void addStaff(Staff staff);
    
    /**
     * 根据id查询员工
     * @param param
     * @return
     */
    public Staff findOneStaff(String staffId);
    
    /**
     * 根据id删除员工
     * @param param
     * @return
     */
    public void deleteStaff(String ids);
    
    /**
     * 修改
     * @param staff
     */
    public void updateStaff(Staff staff);
}
