package org.inspur.exam.service.Impl;

import java.util.List;
import java.util.Map;

import org.inspur.exam.dao.BonusMapper;
import org.inspur.exam.data.Bonus;
import org.inspur.exam.service.IBonusService;
import org.inspur.exam.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("bonusService")
@Transactional("mybatisTransactionManager")
public class BonusService implements IBonusService {

	@Autowired
	private BonusMapper bonusMapper;

	@Override
	public Bonus findOne(String id) {
		return bonusMapper.findOne(id);
	}

	@Override
	public void delete(String id) {
		bonusMapper.deleteBonusById(id);
	}

	@Override
	public void delete(String[] ids) {
		bonusMapper.deleteBonus(ids);
	}

	@Override
	public Bonus save(Bonus bonus) {
		if (bonus.getId() != null)
			bonusMapper.updateBonus(bonus);
		else {
			bonus.setId(UuidUtils.uuid());
			bonusMapper.addBonus(bonus);
		}
		return bonus;
	}

	@Override
	public List<Bonus> findByCondition(Map param) {
		return bonusMapper.findAll(param);
	}

	@Override
	public List<Bonus> findByStaffId(String staffId) {
		// TODO Auto-generated method stub
		return bonusMapper.findByStaffId(staffId);
	}

	@Override
	public void deleteBonusByStaffId(String staffId) {
		// TODO Auto-generated method stub
		bonusMapper.deleteBonusByStaffId(staffId);
	}

}
