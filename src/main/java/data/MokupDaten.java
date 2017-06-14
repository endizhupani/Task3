package data;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class MokupDaten {
	/**
	 * mersistafa
	 * added an expired subscription to c1
	 */
    public void MokupLaden() {
        
    	/*
    	HttpClientFastBill clientFS = new HttpClientFastBill();
        
        Customer a1 = new Customer();
        a1.addCustomer("consumer", "TESTORG", "Moeller"); 
        
        clientFS.sendObjectsToFastBill(a1);
        */
   
    	
    	Customer c1 = new Customer("consumer", "TESTORG1", "User1");
    	Customer c2 = new Customer("consumer", "TESTORG2", "User2");
    	Customer c3 = new Customer("consumer", "TESTORG3", "User3");
    	
    	
    	Product p1 = new Product("Office365");
    	Product p2 = new Product("Dynamics");
    	Product p3 = new Product("CRM");
    	
    	List<Product> p1List = new ArrayList<>(); 
    	p1List.add(p1);
    	
    	List<Product> p2List = new ArrayList<>(); 
    	p1List.add(p2);
    	List<Product> p3List = new ArrayList<>(); 
    	p1List.add(p3);
    	
    	
    	Plan plan1 = new Plan(10, p1, "Standard");
    	Plan plan2 = new Plan(15, p1, "Business");
    	p1.addOfferedPlan(plan1);
    	p1.addOfferedPlan(plan2);
    	
    	Plan plan3 = new Plan(15, p2,"Standard");
    	Plan plan4 = new Plan(25, p2,"Business");
    	p2.addOfferedPlan(plan3);
    	p2.addOfferedPlan(plan4);
    	
    	Plan plan5 = new Plan(20, p3,"Business");
    	Plan plan6 = new Plan(15, p3,"Standard");
    	p3.addOfferedPlan(plan5);
    	p3.addOfferedPlan(plan6);
    	
        c1.addSubscription(plan1);
        c1.addSubscription(plan5);
        c2.addSubscription(plan2);
        c2.addSubscription(plan3);
        c3.addSubscription(plan4);
        
        Subscription expiredSubscription = new Subscription(c1, plan6);
        expiredSubscription.setValidUntil(ZonedDateTime.now(ZoneOffset.UTC));
        
        c1.addSubscription(expiredSubscription);
        
//        c1.addInvoice(p1List);
//    	c2.addInvoice(p2List);
//    	c3.addInvoice(p3List);
  
        ProductStamm.getInstance().addProductStamm(p1);
    	ProductStamm.getInstance().addProductStamm(p2);
    	ProductStamm.getInstance().addProductStamm(p3);

        KundenStamm.getInstance().addKundenStamm(c1);
        KundenStamm.getInstance().addKundenStamm(c2);
        KundenStamm.getInstance().addKundenStamm(c3);
   
        
        
    }
}
