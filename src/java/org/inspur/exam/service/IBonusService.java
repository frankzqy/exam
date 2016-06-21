package org.inspur.exam.service;

import java.util.List;
import java.util.Map;

import org.inspur.exam.data.Bonus;

public interface IBonusService {

	/**
	 * 根据ID获取奖金信息
	 * 
	 * @param id
	 * @return
	 */
	public Bonus findOne(String id);

	/**
	 * 根据用户ID删除奖金信息
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 根据ID批量删除
	 * 
	 * @param id
	 */
	public void delete(String[] ids);

	/**
	 * 保存信息
	 * 
	 * @param bonus
	 */
	public Bonus save(Bonus bonus);

	/**
	 * 根据查询条件查询符合条件的列表
	 */
	public List<Bonus> findByCondition(Map param);
	
	List<Bonus> findByStaffId(String staffId);
	
	void deleteBonusByStaffId(String staffId);
}
