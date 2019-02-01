package cn.cqupt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.cqupt.dao.PasPatientDao;

@Transactional
public class PasPatientService {
	PasPatientDao pasPatientDao;
	public void setPasPatientDao(PasPatientDao pasPatientDao) {
		this.pasPatientDao = pasPatientDao;
	}
}
