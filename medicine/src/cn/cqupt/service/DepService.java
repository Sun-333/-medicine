package cn.cqupt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


import Modle.DepByDownLoad;
import Modle.FindDep;
import Modle.TableValueChangeNull;
import Util.CheckUtil;
import Util.PageBean;
import Util.ParamTool;
import Util.ResultBean;
import cn.cqupt.dao.BedDao;
import cn.cqupt.dao.DepDao;
import cn.cqupt.entity.Bed;
import cn.cqupt.entity.Dep;


public class DepService {
	//创建依赖
	private DepDao depDao;
	private BedService bedService;
	private BedDao bedDao;
	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}
	public void setBedService(BedService bedService) {
		this.bedService = bedService;
	}
	public void setBedDao(BedDao bedDao) {
		this.bedDao = bedDao;
	}
	/***
	 * 查询所有部门名
	 * @return
	 */
	public ResultBean<List<Map<String, Object>>> findAllDepName() {
		//登录校验
		//直接调用下层方法进行查询
		List<Dep> deps = depDao.findAll();
		//构建返回结果集
		List<Map<String, Object>> list = new ArrayList<>();
		for (Dep dep : deps) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("depId", dep.getDepId());
			map.put("depName", dep.getDepName());
			list.add(map);
		}
		return new ResultBean<List<Map<String,Object>>>(list);
	}
	/***
	 * 查询部门信息，depId为null或则depId不为null都适用
	 * @param findDep
	 * @return 返回两种请款list<dep>或则dep创建的resultbean
	 */
	public ResultBean<PageBean<Map<String,Object>>> findDep(FindDep findDep) {
		//1.校验参数是否为空
		CheckUtil.notNull(findDep.getCurrentPage(),"查询页数不能为空");
		CheckUtil.notNull(findDep.getLimit(),"每页查询数量不能为空");
		//2.查询初始化
		PageBean<Map<String,Object>> pageBean = new PageBean<>();
		pageBean.setLimit(findDep.getLimit());//设置每页显示数量
		pageBean.setPage(findDep.getCurrentPage());//设置查询页数
		String[] tableNames = {"科室名称","科室床位","科室描述","科室编号","科室账号"};
		pageBean.setTableNames(Arrays.asList(tableNames));
		int Cnt = depDao.findCntByName(findDep.getDepName());
		pageBean.setTotalCount(Cnt);
		//执行查询
		List<Dep> deps = depDao.findDepByCondition(findDep.getDepName(),pageBean.getBegin(),pageBean.getLimit());
		
		//封装返回结果
		List<Map<String, Object>> list = new ArrayList<>();
		for (Dep dep : deps) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("depName", ParamTool.getParam("科室名称",dep.getDepName(),false,true));
			map.put("bedMaxCnt",ParamTool.getParam("科室床位",dep.getBedMaxCnt(),false,true));
			map.put("depDetail",ParamTool.getParam("科室描述",dep.getDepDetail(),true,true));
			map.put("depId",ParamTool.getParam("科室编号",dep.getDepId(),false,false));
			map.put("countCnt",ParamTool.getParam("科室账号",dep.getSetUser().size(),true,false));
			list.add(map);
		}
		pageBean.setList(list);
		return new ResultBean<>(pageBean);
	}
	/***
	 * 公用方法将list<Dep>转化成list<map>
	 * @param deps
	 * @return
	 */
	private List<Map<String, Object>> getListMap(List<Dep> deps) {
		List<Map<String, Object>> list = new LinkedList<>();
		Map<String, Object> map = new LinkedHashMap<>();
		for (Dep dep : deps) {
			 map = getMap(dep);
			 list.add(map);
		}
		
		return list;
	}
	/***
	 * 公用方法将dep转化为map
	 * @param dep
	 * @return
	 */
	private Map<String, Object> getMap(Dep dep) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("depId", dep.getDepId());
		map.put("depName", dep.getDepName());
		map.put("bedMaxCnt", dep.getBedMaxCnt());
		map.put("depAccountCnt", dep.getSetUser().size());
		map.put("depDetail", dep.getDepDetail());
		return map;
	}
	/***
	 * 添加部门
	 * @param dep
	 * @return
	 */
	@Transactional
	public ResultBean<Boolean> save(Dep dep) {
		/*
		 * 校验
		 */
		//1.非空校验
		CheckUtil.notEmpty(dep.getDepName(),"科室名不能为空");
		CheckUtil.notNull(dep.getBedMaxCnt(),"科室最大床位不能为空");
		//2.科室名重复校验
		List<Dep> listDep = depDao.findAll();
		for (Dep dep2 : listDep) {
			CheckUtil.check(!dep2.getDepName().equals(dep.getDepName()),"科室名称重复");
		}
		//3.保存新部门
		depDao.add(dep);
		
		//4在新部门下添加新床位
		bedService.addBeds(dep);
		return new ResultBean<Boolean>(true);
	}
	/***
	 * 修改部门信息
	 * @param dep
	 * @return
	 */
	@Transactional
	public ResultBean<Boolean> modifyDep(Dep dep) {
		/**
		 * 参数校验
		 */
		//1.非空校验
		CheckUtil.notEmpty(dep.getDepName(),"科室名称不能为空");
		CheckUtil.notNull(dep.getBedMaxCnt(),"科室床位不能为空");
		CheckUtil.notNull(dep.getDepId(),"科室编号不能为空");
		//2.科室名重复校验
		List<Dep> listDep = depDao.findAll();
		for (Dep dep2 : listDep) {
			if(!dep.getDepId().equals(dep2.getDepId())) {
				CheckUtil.check(!dep2.getDepName().equals(dep.getDepName()),"科室名称重复");
			}
		}
		
		
		Dep depPas = depDao.findOne(dep.getDepId());
		List<Bed> beds = bedDao.findBedByDepId(dep.getDepId());
		List<Bed> beds_empty = bedDao.findAllEmptyBeds(dep.getDepId());
		//判断科室床位是否正确
		if(depPas.getSetBed()!=null) {
			
			if(dep.getBedMaxCnt()>=depPas.getSetBed().size()) {
				//如果当前的床位做大数量大于dep下的床位则添加
				for(int i=1;i<dep.getBedMaxCnt();i++) {
					boolean  contro=true;
					for (Bed bed : beds) {
						if(bed.getBedNumber().equals(i)) {
							contro=false;
							break;
						} 
					}
					if(contro) {
						Bed bed = new  Bed();
						bed.setBedNumber(i);
						bed.setDep(dep);
						bed.setUseState(false);
						bedService.save(bed);
					}
				}
				
				
			}else {
				 int emptyCnt = beds_empty.size();
				 CheckUtil.check(emptyCnt>=depPas.getSetBed().size()-dep.getBedMaxCnt(),"当前科室住院人数，已经大于您设置的最大床位数量");
				 for(int i=0;i<depPas.getSetBed().size()-dep.getBedMaxCnt();i++) {
					 Bed bed = beds_empty.get(i);
					 bedDao.delete(bed);
				 }
			}
			
		}else {
			bedService.addBeds(dep);
		}
		
		/**
		 * 保存修改结果
		 */
		Dep dep_new = depDao.findOne(dep.getDepId());
		dep_new.setDepName(dep.getDepName());
		dep_new.setDepDetail(dep.getDepDetail());
		dep_new.setBedMaxCnt(dep.getBedMaxCnt());
		depDao.update(dep_new);
		
		
		return new ResultBean<Boolean>(true);
	}
	/***
	 * 删除部门信息
	 * @param depId
	 * @return
	 */
	@Transactional
	public ResultBean<Boolean> delectDep(Integer depId) {
		/**
		 * 校验
		 */
		//1.depId非空校验
		CheckUtil.notNull(depId,"部门编号不能为空");
		CheckUtil.notNull(depDao.findOne(depId),"科室不存在");
		//2.判断部门下是否还存在账号
		int cnt = depDao.findCnt(depId);
		CheckUtil.check(cnt==0,"该部门下还存在账号不能删除");
		//3.删除该部门
		Dep dep_find = depDao.findOne(depId);
		depDao.delete(dep_find);
		return new ResultBean<Boolean>(true);
	}
	
	public ResultBean<List<DepByDownLoad> >findAll(){
		List<Dep> deps = depDao.findAll();
		List<DepByDownLoad> depByDownLoads = new ArrayList<>();
		for (Dep dep : deps) {
			DepByDownLoad depByDownLoad = new DepByDownLoad();
			depByDownLoad.setDepId(dep.getDepId());
			depByDownLoad.setDepName(dep.getDepName());
			depByDownLoad.setDepDetail(dep.getDepDetail());
			if(dep.getBedMaxCnt()!=null)
			depByDownLoad.setBedMaxCnt(dep.getBedMaxCnt());
			depByDownLoads.add(depByDownLoad);
		}
		return new ResultBean<List<DepByDownLoad>>(depByDownLoads);
	}
	@Transactional
	public ResultBean<Boolean> upLoad(List<Map<String,Object>> upLoadDeps){
		for (Map<String,Object> map : upLoadDeps) {
			System.out.println("执行");
			Dep dep = new Dep();
			Double double_1 = Double.parseDouble((String) map.get("最大床位"));
			dep.setBedMaxCnt(double_1.intValue());
			dep.setDepDetail((String) map.get("科室描述"));
			dep.setDepName((String) map.get("科室名称"));
			//dep.setBedMaxCnt(10);
			this.save(dep);
		}
		return new ResultBean<Boolean>(true);
	}
}
