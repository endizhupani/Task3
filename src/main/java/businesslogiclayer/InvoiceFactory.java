package businesslogiclayer;

import java.util.ArrayList;
import java.util.List;

import data.Customer;
import data.Invoice;
import data.Items;
import data.Product;
import data.SubscriptionsRepository;

/**
 * Abstract invoice factory
 * @author mersistafa
 * if test environment the Mockup factory is returned.
 */
public abstract class InvoiceFactory {
	
	/**
	 * mersistafa
	 * This method generates an invoice for a customer
	 * @param customer The customer for whom the invoice will be generated 
	 * @param invoiceItems The newly expired subscriptions to be added to the invoice
	 * @return the correct invoice type depending on the environment.
	 */
	public abstract Invoice generateInvoice(Customer customer, SubscriptionsRepository invoiceItems);
	//public abstract Invoice addInvoice(Customer customer, ArrayList<Items> invoiceItems);
	public abstract ArrayList<Invoice> getUnpaidInvoices(Customer customer);
	//public abstract Invoice addInvoice(Customer customer, List<Product> productList, SubscriptionsRepository subscriptions);
	
	/**
	 * mersistafa
	 * Gets the correct Invoice factory depending on the environment
	 * @param isMockup true if the mockup data are being used, false otherwise
	 * @return The invoice factory
	 */
	public static InvoiceFactory getInvoiceFactory(boolean isMockup)
	{
		if (isMockup) {
			return new MockupInvoiceFactory();
		}
		else {
			return new ApiInvoiceFactory();
		}
	}

}
