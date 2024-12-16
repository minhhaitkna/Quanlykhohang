package models ;

public class Customer {
	private int id ;
	private String name ;
	private String phonenumber ;
	private String address ;
	private String date ;
	private String rank ;
	
	
	public Customer(int id, String name, String phonenumber, String address, String date, String rank) {
		this.id = id;
		this.name = name;
		this.phonenumber = phonenumber;
		this.address = address;
		this.date = date;
		this.rank = rank;
	}
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
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	
	
	
}