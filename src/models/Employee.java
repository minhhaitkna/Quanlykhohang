package models;

public class Employee {
	private String name;
	private int id;
	private double salary;
	private String account;
	private String password;
	private String phone_number;
	private String rank;
	
	
	public Employee( int id,String name, double salary, String account, String password,
			String phone_number,String rank) {
		this.name = name;
		this.id = id;
		this.salary = salary;
		this.account = account;
		this.password = password;
		
		this.phone_number = phone_number;
		this.rank = rank;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getId() {
		return this.id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getSalary() {
		return this.salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	


	public String getPhone_number() {
		return this.phone_number;
	}


	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}


	public String getRank() {
		return rank;
	}


	public void setRank(String rank) {
		this.rank = rank;
	}

	
	
}

