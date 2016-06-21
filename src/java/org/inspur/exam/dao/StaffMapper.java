package org.inspur.exam.dao;

import java.util.List;
import java.util.Map;

import org.inspur.exam.data.Staff;

import tk.mybatis.mapper.common.Mapper;

public interface StaffMapper extends Mapper<Staff> {
    
    Staff findOne(String staffId);

    List<Staff> findAll(Map<String, Object> param);
    
    void addStaff(Staff staff);
    
    void deleteStaff(String staffId);
    
    void updateStaff(Staff staff);
    
    void deleteAll(String[] ids);

}
