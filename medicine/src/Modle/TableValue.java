package Modle;

public class TableValue {
	String tableName;
	Object value;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public TableValue() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public TableValue(String tableName, Object value) {
		super();
		this.tableName = tableName;
		this.value = value;
	}
}
