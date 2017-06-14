package data;

import java.util.ArrayList;
import java.util.List;

public class ApiInvoice extends Invoice {
	
	public ApiInvoice(Customer customer, SubscriptionsRepository invoiceItems) {
		super(customer, invoiceItems, 0);
		throw new UnsupportedOperationException();
	}
	
	public ApiInvoice(Customer customer, ArrayList<Items> invoiceItems) {
		super(customer, invoiceItems);
		// TODO Auto-generated constructor stub
	}
	
	public ApiInvoice(Customer customer, List<Product> productList, SubscriptionsRepository subscriptions) {
		super(customer, productList, subscriptions);
	}
	
	@Override
	public Invoice payInvoice() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
