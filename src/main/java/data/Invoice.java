package data;

import java.util.ArrayList;
import java.util.List;

public abstract class Invoice {
	Customer customer;
	int invoiceId;
	public int getInvoiceId() {
		return invoiceId;
	}

	SubscriptionsRepository subscriptions = new SubscriptionsRepository();
	List<Items> itemListe = new ArrayList<>();
	List<Product> productList = new ArrayList<>();
	int total;
	boolean isPaid;
	
	//public abstract Invoice generateInvoice(Customer customer, SubscriptionsRepository items);
	
	/**
	 * TODO: implement observer pattern. The subject will be the invoice itself. Observer will be all subscriptions inside invoice.
	 * when an invoice is paid, all subscriptions will be notified.
	 * @param customer
	 * @return
	 */
	public abstract Invoice payInvoice();
	
	
	
	/**
	 * This block of code will always be executed when a new object is created
	 */
	{

		isPaid = false;
	}
	
	public Invoice(Customer customer, SubscriptionsRepository invoiceItems, int invoiceId)
	{
		this.invoiceId = invoiceId;
		this.customer = customer;
		this.subscriptions = invoiceItems;
		subscriptions.forEach(s -> {
			total += s.getSubscriptionPlan().getMonthlyrate();
		});
	}
	
	public Invoice(Customer customer, ArrayList<Items> invoiceItems){
		
		this.customer = customer;
		this.itemListe = invoiceItems;
	}
	public Invoice(Customer customer, List<Product> productList, SubscriptionsRepository subscriptions){
		
		subscriptions.forEach(s -> {
			total += s.getSubscriptionPlan().getMonthlyrate();
		});
		isPaid = false;
		this.customer = customer;
		this.productList = productList;
	}
	
	/*
	 * mersistafa
	 * Return the state of the invoice.
	 */
	public boolean isPaid() {
		return isPaid;
	}


	public int getTotal() {
		return total;
	}

	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public List<Items> getItemListe() {
		return itemListe;
	}


	public void setItemListe(ArrayList<Items> itemListe) {
		this.itemListe = itemListe;
	}


	public SubscriptionsRepository getSubscriptions() {
		return subscriptions;
	}


	public void setSubscriptions(SubscriptionsRepository subscriptions) {
		this.subscriptions = subscriptions;
	}
	
	@Override
	public String toString(){
		return String.format("No: %s; Invoice total: %s; Customer: %s; Is Paid: %s", this.invoiceId, this.total, this.customer.getLastName(), this.isPaid);
	}
	
}
