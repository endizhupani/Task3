package businesslogiclayer;

import java.util.ArrayList;
import java.util.List;

import data.ApiInvoice;
import data.Customer;
import data.Invoice;
import data.Items;
import data.MockupInvoice;
import data.Product;
import data.SubscriptionsRepository;

public class MockupInvoiceFactory extends InvoiceFactory {

	/**
	 * mersistafa
	 */
	@Override
	public Invoice generateInvoice(Customer customer, SubscriptionsRepository invoiceItems) {
		int previousInvoiceId = this.getUnpaidInvoices(customer).size();
		return new MockupInvoice(customer, invoiceItems, previousInvoiceId + 1);
	}

	@Override
	public ArrayList<Invoice> getUnpaidInvoices(Customer customer) {
		ArrayList<Invoice> result = new ArrayList<Invoice>();
		for(Invoice i: customer.getInvoices())
		{
			if (!i.isPaid())
			{
				result.add(i);
			}
		}
		return result;
	}


//	@Override
//	public Invoice addInvoice(Customer customer, ArrayList<Items> invoiceItems) {
//		// TODO Auto-generated method stub
//		return new MockupInvoice(customer, invoiceItems);
//	}
//
//	@Override
//	public Invoice addInvoice(Customer customer, List<Product> productList, SubscriptionsRepository subscriptions) {
//		return new ApiInvoice(customer, productList, subscriptions);
//	}
	
}
