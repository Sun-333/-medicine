package test;

import java.util.HashMap;
import java.util.Map;

import Util.JavaWebToken;
import Util.UserUtil;
import cn.cqupt.entity.UserToken;

public class Test {
	public void fun1(){
		UserToken userToken = new UserToken();
		userToken.setAccountId(1);
		userToken.setAccountName("123");
		String string = UserUtil.setToken(userToken);
		System.out.println(string);
	
	}

}
