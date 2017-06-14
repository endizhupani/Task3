package data;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Subscription {
	


	private Customer customer;
	private Plan subscriptionPlan;
	private ZonedDateTime validUntil;
	private SubscriptionState subscriptionState;
	
	public Subscription() {
		subscriptionState = new ActiveSubscriptionState(this);
		
	}

	public Subscription(Customer customer, Plan subscriptionPlan) {
		this();
		this.customer = customer;
		this.subscriptionPlan = subscriptionPlan;
	}

	
	@Override
	public String toString() {
		return String.format("Subscription for product %s with plan %s with monthly rate %s, by customer %s, valid until %s. State: %s", 
				subscriptionPlan.getSubscriptionpProduct().getName(), 
				subscriptionPlan.getPlanName(),
				subscriptionPlan.getMonthlyrate(),
				customer.getLastName(),
				DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(validUntil),
				subscriptionState.getState());
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Plan getSubscriptionPlan() {
		return subscriptionPlan;
	}
	public void setSubscriptionPlan(Plan subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}
	
	public ZonedDateTime getValidUntil() {
		return validUntil;
	}
	public void setValidUntil(ZonedDateTime validUntil) {
		this.validUntil = validUntil;
	}

	public SubscriptionState getSubscriptionState() {
		return subscriptionState;
	}

	public void setSubscriptionState(SubscriptionState subscriptionState) {
		this.subscriptionState = subscriptionState;
	}
	

}
