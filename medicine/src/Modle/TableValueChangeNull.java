package Modle;

public class TableValueChangeNull {
	private String tableName;//对应表头
	private Object value;//值
	private Boolean canNull;//是否能为空
	private Boolean canChange;//是否能改变
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
	public Boolean getCanNull() {
		return canNull;
	}
	public void setCanNull(Boolean canNull) {
		this.canNull = canNull;
	}
	public Boolean getCanChange() {
		return canChange;
	}
	public void setCanChange(Boolean canChange) {
		this.canChange = canChange;
	}
	public TableValueChangeNull() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public TableValueChangeNull(String tableName, Object value, Boolean canNull, Boolean canChange) {
		super();
		this.tableName = tableName;
		this.value = value;
		this.canNull = canNull;
		this.canChange = canChange;
	}
	
}
