package cn.cqupt.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.cqupt.dao.UserTokenDao;
import cn.cqupt.entity.UserToken;

@Transactional
public class UserTokenService {
	//创建依赖
	UserTokenDao userTokenDao;
	public void setUserTokenDao(UserTokenDao userTokenDao) {
		this.userTokenDao = userTokenDao;
	}
	
	/**
	 * 向数据库添加userToken
	 * @param userToken
	 */
	public void add(UserToken userToken){
		//判断userToken是否为空
		if(userToken==null){
			throw new RuntimeException("userToken不能为空");
		}else {
			userTokenDao.add(userToken);
		}
	}
	/**
	 * 从数据库删除判断userToken是否还存在
	 * @param userToken
	 */
	public void delect(UserToken userToken){
		//判断userToken是否为空
		if(userToken==null){
			throw new RuntimeException("userToken不能为空");
		}else{
			userTokenDao.delete(userToken);
		}
	}
	public Boolean ifHasToken(int accountId){
		UserToken userToken = userTokenDao.findOne(accountId);
		if(userToken==null){
			return false;
		}else{
			return true;
		}
	}
	public UserToken findOne(int accountId){
		return userTokenDao.findOne(accountId);
	}

	public List<UserToken> findAll() {
		return userTokenDao.findAll();
		
	}
}
