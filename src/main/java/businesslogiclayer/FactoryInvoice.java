package businesslogiclayer;

public class FactoryInvoice 
{
	public static InvoiceFactory getInvoiceFactory()
	{
		if (XmlConfiguration.getInstance().isMokup())
		{
			return new MockupInvoiceFactory();
		}
		else 
		{
			return new ApiInvoiceFactory();
		}
	}
}
