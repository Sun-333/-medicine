package cn.cqupt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.cqupt.dao.PasDoctorTelDao;
import cn.cqupt.entity.PasDoctorTel;

@Transactional
public class PasDoctorTelService {
	private PasDoctorTelDao pasDoctorTelDao;

	public void setPasDoctorTelDao(PasDoctorTelDao pasDoctorTelDao) {
		this.pasDoctorTelDao = pasDoctorTelDao;
	}
	
	public void  addPasDoctorTel(PasDoctorTel pasDoctorTel){
		pasDoctorTelDao.add(pasDoctorTel);
	}
	
}
