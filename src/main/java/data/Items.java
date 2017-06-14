package data;


/**
 * Quellen: 
 * https://www.tutorialspoint.com/design_pattern/iterator_pattern.htm 
 *
 */
public class Items {
	String description; //Produktname
	String unitPrice;
	String vatPercent;
	

	
	public Items(String description, String unitPrice, String vatPercent){
		this.description = description;
		this.unitPrice = unitPrice;
		this.vatPercent = vatPercent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getVatPercent() {
		return vatPercent;
	}

	public void setVatPercent(String vatPercent) {
		this.vatPercent = vatPercent;
	}
	
	
	@Override
	public String toString() {
		return String.format("Description: %s %s %s", description, unitPrice, vatPercent);
	}
	
}
	

