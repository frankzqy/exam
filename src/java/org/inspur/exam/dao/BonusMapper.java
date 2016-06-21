package org.inspur.exam.dao;

import java.util.List;
import java.util.Map;

import org.inspur.exam.data.Bonus;

/**
 * CRUD
 * 
 * @author
 *
 */
public interface BonusMapper {

	Bonus findOne(String id);

	List<Bonus> findAll(Map map);

	void addBonus(Bonus bonus);

	void deleteBonus(String[] ids);

	void deleteBonusById(String id);

	void updateBonus(Bonus Bonus);
	
	List<Bonus> findByStaffId(String staffId);
	
	void deleteBonusByStaffId(String staffId);

}
