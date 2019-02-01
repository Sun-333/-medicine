package Util;

public class Strings {

	public static boolean isNullOrEmpty(String headerCellValue) {
		if(headerCellValue==null||headerCellValue.trim().isEmpty()) {
			return true;
		}
		return false;
	}

}
