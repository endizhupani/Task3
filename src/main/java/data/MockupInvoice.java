package data;

import java.util.ArrayList;
import java.util.List;

public class MockupInvoice extends Invoice {
	
	
	public MockupInvoice(Customer customer, SubscriptionsRepository invoiceItems, int invoiceId) {
		super(customer, invoiceItems, invoiceId);
	}
	
	
	public MockupInvoice(Customer customer, ArrayList<Items> invoiceItems) {
		super(customer, invoiceItems);
	}

	/**
	 * mersistafa
	 * When an invoice is paid call the paymentReceived for each subscription to set the state to active
	 */
	@Override
	public Invoice payInvoice() {
		this.isPaid = true;
		for(Subscription s: this.subscriptions)
		{
			s.getSubscriptionState().paymentReceived();
		}
		return this;
	}
	
	public MockupInvoice(Customer customer, List<Product> productList, SubscriptionsRepository subscriptions) {
		super(customer, productList, subscriptions);
	}
	
	
}
