package cn.cqupt.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import Util.CheckUtil;
import Util.ResultBean;
import cn.cqupt.dao.IllnessDao;
import cn.cqupt.entity.Illness;

@Transactional
public class IllnessService {
	/*
	 * 配置依赖
	 */
 IllnessDao illnessDao;
public void setIllnessDao(IllnessDao illnessDao) {
	this.illnessDao = illnessDao;
}
/**
 * 添加病情
 * @param illness
 * @return
 */
public ResultBean<Boolean> add(Illness illness) {
	//1.校验病情是否为空
	CheckUtil.notNull(illness.getIllnessName(),"新加病情不能空");
	//2.添加病情
	illnessDao.add(illness);
	return new ResultBean<Boolean>(true);
}
/**
 * 查询所有病情
 * @return
 */
public ResultBean<List<Illness>> findAll() {
	List<Illness> list = illnessDao.findAll();
	return new ResultBean<List<Illness>>(list);
}
/**
 * 删除病情
 * @param illness
 * @return
 */
public ResultBean<Boolean> delect(Illness illness) {
	//1.校验illnessId是否为空
	CheckUtil.notNull(illness.getIllnessId(),"病情Id不能为空");
	//2.校验此病是否还存在
	Illness illness_find = illnessDao.findOne(illness.getIllnessId());
	CheckUtil.notNull(illness_find,"此病情已经不存在，删除失效");
	//3.执行删除
	illnessDao.delete(illness_find);
	return new ResultBean<Boolean>(true);
}
public void upLoad(List<Map<String, Object>> maps) {
	for (Map<String, Object> map : maps) {
		Illness illness = new Illness();
		illness.setIllnessName((String) map.get("诊断名称"));
		illnessDao.add(illness);
	}
	
}


 
}
