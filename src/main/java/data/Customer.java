package data;

import java.util.ArrayList;
import java.util.List;

import businesslogiclayer.FactoryInvoice;
import businesslogiclayer.InvoiceFactory;

public class Customer {
	private int interneId;
	private String customerId;
	private String customerType;
	private String organization;
	private String lastName;
	private SubscriptionsRepository subscriptions;
	private ArrayList<Invoice> invoices;
	
	public Customer(String customerType, String organization, String lastName) {		
		this.customerType = customerType;
		this.organization = organization;
		this.lastName = lastName;
		subscriptions = new SubscriptionsRepository();
		setInvoices(new ArrayList<Invoice>());
	}
	
	public Customer(int interneId, String customerId, String customerType, String organization, String lastName) {
		this(customerType, organization, lastName);
		this.interneId = interneId;
		this.customerId = customerId;
		
	}

	public Customer(int interneId, String customerId, String customerType, String organization, String lastName, SubscriptionsRepository subscriptions) {
		this(interneId, customerId, customerType, organization, lastName);
		this.subscriptions = subscriptions;
	}

	public void addCustomer(String customerType, String organization, String lastName){
		this.customerType = customerType;
		this.organization = organization;
		this.lastName = lastName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public SubscriptionsRepository getSubscriptions() {
		return subscriptions;
		
	}

	public void setSubscriptions(SubscriptionsRepository subscriptions) {
		this.subscriptions = subscriptions;
	}
	
	public void addSubscription(Subscription item){
		this.subscriptions.addSubscription(item);
	}

	public void addSubscription(Plan plan)
	{
		Subscription newSubscription = new Subscription(this, plan); 
		this.addSubscription(newSubscription);
	}
		
	
//	public void addInvoice(List<Product> productList)
//	{
//		InvoiceFactory invoiceFactory = FactoryInvoice.getInvoiceFactory();
//		Invoice invoice  = invoiceFactory.addInvoice(this, productList, subscriptions);
//		addInvoice(invoice);
//	}
	
	public int getInterneId() {
		return interneId;
	}

	public void setInterneId(int interneId) {
		this.interneId = interneId;
	}

	public ArrayList<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(ArrayList<Invoice> invoices) {
		this.invoices = invoices;
	}
	public Invoice addInvoice(Invoice invoice){
		invoices.add(invoice);
		return invoice;
	}
	
	@Override
	public String toString() 
	{
		String invoiceStr = "";
		for(Invoice invoice : invoices)
		{
			invoiceStr = String.format("Invoice total: %s Customer: %s, Is Paid: %s", invoice.total, this.getLastName(), invoice.isPaid);
		}
		return invoiceStr;
	}
}
