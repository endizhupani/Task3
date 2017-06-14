package data;

import java.util.ArrayList;
import java.util.List;

public class ProductStamm {

	private ArrayList<Product> productstamm = new ArrayList<Product>();
	private static ProductStamm instance = new ProductStamm();
	
	private ProductStamm() {

	}
	
	public ArrayList<Product> getProductstamm() {
		return productstamm;
	}

	public void setProductstamm(ArrayList<Product> Productstamm) {
		this.productstamm = Productstamm;
	}
	
	public static ProductStamm getInstance(){
	      return instance;
	}
	
	public void addProductStamm(Product product) {
		this.productstamm.add(product);

	}
	
	public Product getProductByName(String name)
	{
		for (Product product : productstamm) {
			if (product.getName()== name)
			{
				return product;
			}
		}
		return null;
	}
	

}
