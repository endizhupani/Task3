package businesslogiclayer;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import data.MokupDaten;

/**
 * Quelle: https://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
 * Singleton Pattern
 *
 */
public class XmlConfiguration {
	boolean mokup;
	String apiKey;
	String apiEmail;

	private static XmlConfiguration instance = new XmlConfiguration();

	private XmlConfiguration() {
	}

	public static XmlConfiguration getInstance() {
		return instance;
	}

	public void configurationLesen() {
		try {

			File inputFile = new File("config/settings.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("settings");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					setApiKey(eElement.getElementsByTagName("api-key").item(0).getTextContent());
					setApiEmail(eElement.getElementsByTagName("api-email").item(0).getTextContent());
					
					if(eElement.getElementsByTagName("environment").item(0).getTextContent().equals("test")) {
						setMokup(true);
					} else {
						if(eElement.getElementsByTagName("environment").item(0).getTextContent().equals("productive")) {
							setMokup(false);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * spaeter ins Proxy ueberfuehren
		 */
		if(isMokup()) {
			MokupDaten mokDaten = new MokupDaten();
			mokDaten.MokupLaden();
		}

	}

	public boolean isMokup() {
		return mokup;
	}

	public void setMokup(boolean mokup) {
		this.mokup = mokup;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiEmail() {
		return apiEmail;
	}

	public void setApiEmail(String apiEmail) {
		this.apiEmail = apiEmail;
	}

}
