package Modle;

import java.util.Date;

public class Student {
	private int id;
	private String name;
	private int age;
	private boolean sex;
	private Date date;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean getSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Student(int id, String name, int age, boolean sex, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.date = date;
	}

	public Student() {
		super();
		// TODO 自动生成的构造函数存根
	}

}
