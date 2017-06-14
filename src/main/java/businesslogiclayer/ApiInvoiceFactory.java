package businesslogiclayer;

import java.util.ArrayList;
import java.util.List;

import data.ApiInvoice;
import data.Customer;
import data.Invoice;
import data.Items;
import data.Product;
import data.SubscriptionsRepository;

public class ApiInvoiceFactory extends InvoiceFactory {

	/**
	 * mersistafa
	 */
	@Override
	public Invoice generateInvoice(Customer customer, SubscriptionsRepository invoiceItems) {
		// TODO Auto-generated method stub
		return new ApiInvoice(customer, invoiceItems);
	}

	@Override
	public ArrayList<Invoice> getUnpaidInvoices(Customer customer) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

//	@Override
//	public Invoice addInvoice(Customer customer, ArrayList<Items> invoiceItems) {
//		// TODO Auto-generated method stub
//	return new ApiInvoice(customer, invoiceItems);
//	}
//
//	@Override
//	public Invoice addInvoice(Customer customer, List<Product> productList, SubscriptionsRepository subscriptions) {
//		return new ApiInvoice(customer, productList, subscriptions);
//	}


}
