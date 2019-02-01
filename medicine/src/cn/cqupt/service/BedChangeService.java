package cn.cqupt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.cqupt.dao.BedChangeDao;

@Transactional
public class BedChangeService {
	BedChangeDao bedChangeDao;
	public void setBedChangeDao(BedChangeDao bedChangeDao) {
		this.bedChangeDao = bedChangeDao;
	}
	
}
