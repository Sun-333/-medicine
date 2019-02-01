package cn.cqupt.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


import org.springframework.transaction.annotation.Transactional;

import cn.cqupt.dao.AlarmDao;
import cn.cqupt.dao.PasAlarmDao;
import cn.cqupt.dao.PatientDao;
import cn.cqupt.dao.PlanDao;
import cn.cqupt.entity.Alarm;
import cn.cqupt.entity.Plan;



@Transactional
public class AutoMachineService {
	//配置依赖
	
	public PatientDao patientDao;
	public PlanDao planDao;
	public PasAlarmDao pasAlarmDao;
	public AlarmDao alarmDao;
	public void setPatientDao(PatientDao patientDao) {
		this.patientDao = patientDao;
	}
	public void setPlanDao(PlanDao planDao) {
		this.planDao = planDao;
	}
	public void setPasAlarmDao(PasAlarmDao pasAlarmDao) {
		this.pasAlarmDao = pasAlarmDao;
	}
	public void setAlarmDao(AlarmDao alarmDao) {
		this.alarmDao = alarmDao;
	}
	
	//执行切换状态机
	public void autoMachine(){
		System.out.println("执行状态机service");
		//planDao.findByPatientId(1);
		//1.首先查询所有plan
		List<Plan> plans = planDao.findAll();
		System.out.println("执行查询");
		Date nowTime = new Date();
		for (Plan plan : plans) {
			if(plan.getPlanBeginTime().before(nowTime)&&
					plan.getPlanState().intValue()==1){
				/**
				 * 发药超时
				 */
				//切换此服药计划为5
				plan.setPlanState(5);
				//产生告警
				Alarm alarm = new Alarm();
				alarm.setAlarmTime(nowTime);
				alarm.setAlarmType(5);
				alarm.setBed(plan.getBed());
				alarm.setDep(plan.getDep());
				alarm.setPatient(plan.getPatient());
				alarm.setPlan(plan);
				alarm.setAlarmId(UUID.randomUUID().toString());
				//3.保存警告
				alarmDao.add(alarm);
				//更新计划状态
				planDao.update(plan);
			}else if(plan.getPlanBeginTime().before(nowTime)&&plan.getPlanState()==2) {
			/**
			 * 服药超时
			 */
			//1.更新并且保存计划，为服药超时
				plan.setPlanState(4);
			//2.创建告警
				Alarm alarm = new Alarm();
				alarm.setAlarmTime(nowTime);
				alarm.setAlarmType(4);
				alarm.setBed(plan.getBed());
				alarm.setDep(plan.getDep());
				alarm.setPatient(plan.getPatient());
				alarm.setPlan(plan);
				alarm.setAlarmId(UUID.randomUUID().toString());
			//3.保存警告
				alarmDao.add(alarm);
			//更新计划状态
				planDao.update(plan);
			}else if(plan.getPlanState()==3){
				//将计划删除，新加历史计划
				planDao.delete(plan);
				/**
				 * 加入历史计划中
				 */
			}
		}
		return;
		
	}
	
}
