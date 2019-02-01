package cn.cqupt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.cqupt.dao.ConstantDao;
import cn.cqupt.entity.PlanConstant;

@Transactional
public class PlanConstantService {
	//配置依赖
	ConstantDao constantDao;
	public void setConstantDao(ConstantDao constantDao) {
		this.constantDao = constantDao;
	}
	/**
	 * 得到计划相关参数
	 * @return
	 */
	public PlanConstant getConstant() {
		System.out.println("执行");
		PlanConstant planConstant = constantDao.findOne(1);
		System.out.println(planConstant.toString());
		return planConstant;
	}
}
