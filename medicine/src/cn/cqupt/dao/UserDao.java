package cn.cqupt.dao;

import java.util.List;

import Modle.findUserCondicion;
import Util.BaseDao;
import cn.cqupt.entity.User;

public interface UserDao extends BaseDao<User>{

	User UserLogin(String username, String userPwd);

	int findCnt(int roleId);

	int findMoreCondicionCnt(findUserCondicion findUserCondicion);

	List<User> findMoreCondicionPage(findUserCondicion findUserCondicion, int begin, int limit);

	List<User> findByDepId(Integer depId);

}
