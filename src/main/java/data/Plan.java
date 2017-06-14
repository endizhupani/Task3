package data;

public class Plan {
	private int monthlyrate;
	private Product subscriptionProduct;
	//Kay
	private String planName;
	
	
	public Plan() {
	}

	public Plan(int monthlyrate, Product subscriptionpProduct, String planName) {
		this.monthlyrate = monthlyrate;
		this.planName= planName;
		this.subscriptionProduct = subscriptionpProduct;
	
	}

	public int getMonthlyrate() {
		return monthlyrate;
	}
	
	public void setMonthlyrate(int monthlyrate) {
		this.monthlyrate = monthlyrate;
	}
	
	public Product getSubscriptionpProduct() {
		return subscriptionProduct;
	}
	
	public void setsubscriptionpProduct(Product product) {
		this.subscriptionProduct = product;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	
}

