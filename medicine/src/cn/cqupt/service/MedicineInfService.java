package cn.cqupt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;



import Modle.FindMedicineInf;
import Modle.TableValue;
import Util.CheckUtil;
import Util.PageBean;
import Util.ResultBean;
import cn.cqupt.dao.MedicineInfDao;
import cn.cqupt.entity.MedicineInf;

@Transactional
public class MedicineInfService {
	 MedicineInfDao medicineInfDao;
	public void setMedicineInfDao(MedicineInfDao medicineInfDao) {
		this.medicineInfDao = medicineInfDao;
	}
	public ResultBean<PageBean<Map<String,Object>>> findByPageBean(FindMedicineInf findMedicineInf) {
		/**
		 * 校验
		 */
		CheckUtil.notNull(findMedicineInf.getCurrentPage(),"当前页数不能为空");
		CheckUtil.notNull(findMedicineInf.getLimit(),"每页显示数量不能为空");
		
		PageBean<Map<String,Object>> pageBean = new PageBean<>();
		pageBean.setLimit(findMedicineInf.getLimit());
		pageBean.setPage(findMedicineInf.getCurrentPage());
		
		//查询数量
		Integer count = medicineInfDao.findCntByMoreCondicine(findMedicineInf);
		pageBean.setTotalCount(count);
		//查询记录
		List<MedicineInf> medicineInfs = medicineInfDao.findByMoreCondition(pageBean.getBegin(),findMedicineInf);
		List<Map<String,Object>> list = new ArrayList<>();
		for (MedicineInf medicineInf : medicineInfs) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("药瓶名称",new TableValue("药品名",medicineInf.getMedicineName()));
			map.put("商品名",new TableValue("商品名", medicineInf.getGoodsName()));
			map.put("类型", new TableValue("类型", medicineInf.getMedicineType()));
			map.put("适应症",new TableValue("适应症",medicineInf.getIndications()));
			map.put("用法用量",new TableValue("用法用量",medicineInf.getUseWay()));
			map.put("不良反应",new TableValue("不良反应",medicineInf.getAdverse()));
			map.put("禁忌症",new TableValue("禁忌症",medicineInf.getContraindication()));
			map.put("注意事项",new TableValue("注意事项",medicineInf.getOtherInf()));
			list.add(map);
			
		}
		pageBean.setList(list);
		String[] tableNames = {"药瓶名称","商品名","适应症","用法用量","不良反应","禁忌症","注意事项"};
		pageBean.setTableNames(Arrays.asList(tableNames));
		return new ResultBean<PageBean<Map<String,Object>>>(pageBean);
	}
	public ResultBean<Boolean> upLoad(List<Map<String, Object>> maps, String medicineType) {
		for (Map<String, Object> map : maps) {
			MedicineInf medicineInf = new MedicineInf();
			if(map.get("药品名")!=null) {
				medicineInf.setMedicineName((String) map.get("药品名"));
			}
			if(map.get("商品名")!=null) {
				medicineInf.setGoodsName((String) map.get("商品名"));
			}
			if(map.get("规格")!=null) {
				medicineInf.setStandard((String) map.get("规格"));
			}
			if(map.get("适应症")!=null) {
				medicineInf.setIndications((String) map.get("适应症"));
			}
			if(map.get("用法用量")!=null) {
				medicineInf.setUseWay((String) map.get("用法用量"));
			}
			if(map.get("不良反应")!=null) {
				medicineInf.setAdverse((String) map.get("不良反应"));
			}
			
			if(map.get("禁忌症")!=null) {
				medicineInf.setContraindication((String) map.get("禁忌症"));
			}
			
			if(map.get("注意事项")!=null) {
				medicineInf.setOtherInf((String) map.get("注意事项"));
			}
			medicineInf.setMedicineType(medicineType);
			addMedicine(medicineInf);
		}
		return new ResultBean<Boolean>(true);
		

	}
	public void addMedicine(MedicineInf medicineInf){
		medicineInfDao.add(medicineInf);
	}
}
