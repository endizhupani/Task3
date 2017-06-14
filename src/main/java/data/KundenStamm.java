package data;

import java.util.ArrayList;
import java.util.List;

import businesslogiclayer.XmlConfiguration;

/**
 * Quelle: 
 * https://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
 * Singleton Pattern 
 *
 */
public class KundenStamm {
	private ArrayList<Customer> kundenstamm = new ArrayList<Customer>();
	private static KundenStamm instance = new KundenStamm();
	private KundenStamm(){}
	private int fortlaufendekundennummer = 1; 

	public static KundenStamm getInstance(){
	      return instance;
	}
	

	
	public void addKundenStamm(Customer customer){
		this.kundenstamm.add(customer);
		customer.setInterneId(fortlaufendekundennummer);
		this.fortlaufendekundennummer ++;
	}

	public ArrayList<Customer> getKundenstamm() {
		return kundenstamm;
	}

	public void setKundenstamm(ArrayList<Customer> kundenstamm) {
		this.kundenstamm = kundenstamm;
	}

	public List<String> getCustomerAsList(){
		List<String> customerList = new ArrayList<String>();
		for (Customer c : getKundenstamm()){
			customerList.add("KdNr.: " + c.getInterneId() + " Kd.Typ: " + c.getCustomerType() + ", Org.: " + c.getOrganization() + ", Name: " + c.getLastName());
		}
		
		return customerList;
	}
	
	public Customer getCustomerByName(String name)
	{
		for (Customer customer : kundenstamm) {
			if (customer.getLastName().equalsIgnoreCase(name))
			{
				return customer;
			}
		}
		return null;
	}
}
