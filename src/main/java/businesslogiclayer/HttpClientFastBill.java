package businesslogiclayer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;

import data.Customer;
import data.Invoice;
import data.KundenStamm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Quellen:
 * https://github.com/docker-java/docker-java/blob/master/src/main/java/com/github/dockerjava/jaxrs/JerseyDockerCmdExecFactory.java
 *
 */
public class HttpClientFastBill {

	private WebResource apiTarget;

	/**
	 * Quellen http://www.vogella.com/tutorials/REST/article.html
	 * http://www.vogella.com/tutorials/REST/article.html#firstclient
	 * https://stackoverflow.com/questions/6125837/send-raw-xml-using-jersey-client
	 * https://stackoverflow.com/questions/4343683/how-to-convert-org-jdom-document-to-string
	 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
	 */
	public void sendObjectsToFastBill(Object input) {
		String username = XmlConfiguration.getInstance().getApiEmail();
		String apikey = XmlConfiguration.getInstance().getApiKey();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.addFilter(new HTTPBasicAuthFilter(username, apikey));
		//client.addFilter(new LoggingFilter(System.out));
		apiTarget = client.resource("https://my.fastbill.com/api/1.0/api.php");
		System.out.println("clientFastbillConnection gestartet");

		apiTarget.type("application/xml");
		String response = apiTarget.type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_XML).post(String.class,
				objectToXmlFormat(input));
		//System.out.println("reponse: " + response);
		responseToXmlObject(input, response);
	}

	public void responseToXmlObject(Object object, String input) {

		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = null;
		try {
			document = saxBuilder.build(new StringReader(input));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		if (object instanceof Customer) {
			Element classElement = document.getRootElement();
			List<Element> customerList = classElement.getChildren();
			for (int i = 0; i < customerList.size(); i++) {
				Element customer = customerList.get(i);
				if (customer.getName().equals("RESPONSE")) {
					((Customer) object).setCustomerId(customer.getChild("CUSTOMER_ID").getText());
				}
			}
		}
		
		/*
		 if(object instanceof String) {
			String tmp = (String) input;
			if (tmp.contains("customer.get")) {
				Customer customerTmp = new Customer();
				
				
				Element classElement = document.getRootElement();
				List<Element> customerList = classElement.getChildren();

				for (int j = 0; j < customerList.size(); j++) {
					Element customer = customerList.get(j);
					if (customer.getName().equals("RESPONSE")) {
						System.out.println("CUSTOMER_TYPE: " + customer.getChild("CUSTOMER_TYPE").getText());
						System.out.println("ORGANIZATION: " + customer.getChild("ORGANIZATION").getText());
						System.out.println("LAST_NAME: " + customer.getChild("LAST_NAME").getText());
						customerTmp.addCustomer(customer.getChild("CUSTOMER_TYPE").getText(), customer.getChild("ORGANIZATION").getText(), customer.getChild("LAST_NAME").getText());
					}
				}

				for(int i=0; i < KundenStamm.getInstance().getKundenstamm().size(); i++) {
					System.out.println("in der schleife");
					if(!customerTmp.getCustomerId().equals(KundenStamm.getInstance().getKundenstamm().get(i).getCustomerId())) {
						System.out.println("Kunde hinzugefuegt: " + customerTmp.getLastName());
						KundenStamm.getInstance().addKundenStamm(customerTmp);
					}
				}
			}
		}
		*/
	}

	public String objectToXmlFormat(Object input) {
		Element fbapi = new Element("FBAPI");
		Document doc = new Document(fbapi);

		if (input instanceof Customer) {
			Element customerType = new Element("SERVICE").setText("customer.create");
			Element data = new Element("DATA");
			data.addContent(new Element("CUSTOMER_TYPE").setText(((Customer) input).getCustomerType()));
			data.addContent(new Element("ORGANIZATION").setText(((Customer) input).getOrganization()));
			data.addContent(new Element("LAST_NAME").setText(((Customer) input).getLastName()));
			fbapi.addContent(customerType);
			fbapi.addContent(data);
		}
		if (input instanceof Invoice) {
			Element customerType = new Element("SERVICE").setText("invoice.create");
			Element data = new Element("DATA");
			data.addContent(new Element("CUSTOMER_ID").setText(((Invoice) input).getCustomer().getCustomerId()));
			data.addContent(new Element("ITEMS").setText(((Invoice) input).getItemListe().toString()));
			// data.addContent(new Element("ORGANIZATION").setText(((Invoice)
			// input).getItemListe()));
			fbapi.addContent(customerType);
			fbapi.addContent(data);
		}

		/*
		 * https://stackoverflow.com/questions/1903252/extract-integer-part-in-
		 * string
		 */
		if (input instanceof String) {
			String tmp = (String) input;
			if (tmp.contains("customerGet")) {
				String intValue = tmp.replaceAll("[^0-9]", "");

				Element customerType = new Element("SERVICE").setText("customer.get");
				Element data = new Element("FILTER");
				data.addContent(new Element("CUSTOMER_ID").setText(intValue));
				fbapi.addContent(customerType);
				fbapi.addContent(data);
				System.out.println("input instanceof String): " + intValue);
			}

		}
		return new XMLOutputter().outputString(doc);

	}
}
