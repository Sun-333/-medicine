package Util;

import java.util.List;

public class PageBean<T> {
	    private int page;//当前页数
	    private int totalCount;//总记录数
	    private int totalPage;//总页数
	    private int limit;//每页显示的记录数
	    private List<T> list;//每页显示数据的集合
	    private List<String> tableNames;//表头
	    
	    public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
		public List<String> getTableNames() {
			return tableNames;
		}
		public void setTableNames(List<String> tableNames) {
			this.tableNames = tableNames;
		}
		public int getBegin() {
			return limit*(page-1);
		}
		public int getPage() {
	        return page;
	    }
	    public void setPage(int page) {
	        this.page = page;
	    }
	    public int getTotalCount() {
	        return totalCount;
	    }
	    public void setTotalCount(int totalCount) {
	        this.totalCount = totalCount;
	    }
	    public int getTotalPage() {
	    	if(totalCount%limit==0) {
	    		this.totalPage=totalCount/limit;
	    		return this.totalPage;
	    	}else {
	    		this.totalPage=totalCount/limit+1;
	    		return this.totalPage;
	    	}
	        
	    }
	    public int getLimit() {
	        return limit;
	    }
	    public void setLimit(int limit) {
	        this.limit = limit;
	    }
		public List<T> getList() {
	        return list;
	    }
	    public void setList(List<T> list) {
	        this.list = list;
	    }
}
