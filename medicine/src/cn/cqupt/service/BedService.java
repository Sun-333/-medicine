package cn.cqupt.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import Util.CheckUtil;
import Util.ResultBean;
import cn.cqupt.dao.BedDao;
import cn.cqupt.entity.Bed;
import cn.cqupt.entity.Dep;


public class BedService {
	//创建依赖
	BedDao bedDao;
	public void setBedDao(BedDao bedDao) {
		this.bedDao = bedDao;
	}
	/**
	 * 获取所有床位Number
	 * @param depId 
	 * @return
	 */
	public ResultBean<List<Map<String, Object>>> findAllName(Integer depId) {
		//直接调用下层方法
		List<Bed> lists= bedDao.findBedByDepId(depId);
		//封装结果集
		List<Map<String, Object>> result = new LinkedList<>();
		for (Bed bed : lists) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("bedId", bed.getBedId());
			map.put("bedNumber", bed.getBedNumber());
			map.put("state", bed.getUseState());
			result.add(map);
		}
		//返回结果
		return new ResultBean<List<Map<String,Object>>>(result);
	}
	/**
	 * 添加新床位
	 * @param bed
	 */
	@Transactional
	public void save(Bed bed) {
		bedDao.add(bed);
	}
	
	/**
	 * 批量添加新床位
	 */
	public ResultBean<Boolean> addBeds(Dep dep){
		int cnt=dep.getBedMaxCnt().intValue();
		for(int i=1;i<=cnt;i++) {
			Bed bed = new  Bed();
			bed.setBedNumber(i);
			bed.setDep(dep);
			bed.setUseState(false);
			bedDao.add(bed);
		}
		return new ResultBean<Boolean>(true);
		
	}
	/**
	 * 查询特定科室下的空床位
	 * @param depId
	 * @return
	 */
	public ResultBean<List<Map<String, Object>>> findEmptyBeds(Integer depId) {
		/*
		 * 校验
		 */
		CheckUtil.notNull(depId,"科室ID不能为空");
		//调用下层方法执行查询
		List<Bed> beds = bedDao.findAllEmptyBeds(depId);
		/**
		 * 封装结果集
		 */
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (Bed bed : beds) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("bedId",bed.getBedId());
			map.put("bedNumber",bed.getBedNumber());
			list.add(map);
		}
		return new ResultBean<List<Map<String,Object>>>(list);
	}
	
	
	public List<Bed> findEmptyBedsList(Integer depId) {
		/*
		 * 校验
		 */
		CheckUtil.notNull(depId,"科室ID不能为空");
		//调用下层方法执行查询
		List<Bed> beds = bedDao.findAllEmptyBeds(depId);
		
		return beds;
	}
	
}
