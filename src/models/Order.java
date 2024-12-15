package models;

public class Order {
	private int id ; 
	private String founder ;
	private String type ;
	private String conditions ;
	private String applicationdate ;
	private String paymentdate ;
	private int paid ;
	
	
	
	public Order(int id, String founder, String type, String conditions, String applicationdate, String paymentdate,
			int paid) {
		this.id = id;
		this.founder = founder;
		this.type = type;
		this.conditions = conditions;
		this.applicationdate = applicationdate;
		this.paymentdate = paymentdate;
		this.paid = paid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFounder() {
		return founder;
	}
	public void setFounder(String founder) {
		this.founder = founder;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public String getApplicationdate() {
		return applicationdate;
	}
	public void setApplicationdate(String applicationdate) {
		this.applicationdate = applicationdate;
	}
	public String getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(String paymentdate) {
		this.paymentdate = paymentdate;
	}
	public int getPaid() {
		return paid;
	}
	public void setPaid(int paid) {
		this.paid = paid;
	}
	
	
	

}
