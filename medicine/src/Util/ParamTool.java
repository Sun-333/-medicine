package Util;

import Modle.TableValue;
import Modle.TableValueChangeNull;

public class ParamTool {
	public static TableValue getParam(String tableName,Object paramValue){
		TableValue tableValue = new TableValue();
		tableValue.setTableName(tableName);
		tableValue.setValue(paramValue);
		return tableValue;
	}
	public static TableValueChangeNull getParam(String tableName,Object paramValue,Boolean canNull,Boolean canChange){
		TableValueChangeNull tableValueChangeNull = new TableValueChangeNull(tableName, paramValue, canNull, canChange);
		return tableValueChangeNull;
	}
}
