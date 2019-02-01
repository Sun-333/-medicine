package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Test;

import Util.ExcelUtil;

public class Test3 {
	@Test
	public void fun1() {
		Map<String,Object> map = new HashMap<>();
		map.put("男","男");
		System.out.println(map.get("男"));
	}
/*	@Test
	public void fun2(){
		ExcelUtil excelUtil = new ExcelUtil();
		List<Map<String,Object>> map = excelUtil.readExcelFileToDB("D://a.xls");
		System.out.println(map.toString());
	}*/
	
	@Test
	public void fun2(){
		String data="0a:00:00";
		System.out.println(check(data));
	}
	private boolean check(String str) {
		String[] data = str.split(":");
		if (data.length != 3) {
			return false;
		}
		for (String string : data) {

			if (string.length() != 2) {
				return false;
			} else {
				if (!isInteger(string)) {
					return false;
				}
			}
			
		}
		return true;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
}
