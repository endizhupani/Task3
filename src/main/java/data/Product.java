package data;

import java.util.ArrayList;

public class Product {
	private String name;
	private ArrayList<Plan> offeredPlans;
	
	

	public Product() {
		offeredPlans = new ArrayList<Plan>();
	}
	
	public Product(String name) {
		this();
		this.name = name;
	}

	public Product(String name, ArrayList<Plan> offeredPlans) {
		this(name);
		this.offeredPlans = offeredPlans;
	}
	
	
	public String getName() {
		return name;
	}
	public Product setName(String name) {
		this.name = name;
		return this;
	}
	
	public ArrayList<Plan> getOfferedPlans() {
		return offeredPlans;
	}
	public void setOfferedPlans(ArrayList<Plan> offeredPlans) {
		this.offeredPlans = offeredPlans;
	}
	
	public Plan getPlanByName(String name)
	{
		for (Plan plan : offeredPlans) {
			if (plan.getPlanName() == name)
			{
				return plan;
			}
		}
		return null;
	}
	
	
	/**
	 * Adds a plan into the list of the offered plans for the product
	 * @param planToAdd
	 * @return The added plan
	 */
	public Plan addOfferedPlan(Plan planToAdd){
		this.offeredPlans.add(planToAdd);
		return planToAdd;
	}
	
	/**
	 * Removes a plan from the list of the offered plans for this product
	 * @param planToRemove
	 * @return The removed plan
	 */
	public Plan removeOfferedPlan(Plan planToRemove)
	{
		this.offeredPlans.remove(planToRemove);
		return planToRemove;
	}

	
	
}
