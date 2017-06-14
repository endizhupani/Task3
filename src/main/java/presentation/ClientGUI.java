package presentation;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import businesslogiclayer.HttpClientFastBill;
import businesslogiclayer.InvoiceFactory;
import businesslogiclayer.XmlConfiguration;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import data.Customer;
import data.Invoice;
import data.Items;
import data.KundenStamm;
import data.Plan;
import data.Product;
import data.ProductStamm;
import data.Subscription;
import data.SubscriptionsRepository;


public class ClientGUI extends Application {
	
    static boolean booleanMockup = false;
    private BorderPane gui = new BorderPane();
    private BorderPane topBorderPane = new BorderPane();

     /**
     * Methode um die 3 Teile der Oberflaeche werden neu augebaut. 
     */
    public void refreshOberflaeche() {
        gui.setTop(topBorderPane());
        gui.setCenter(centerOberflaeche());
        gui.setRight(rechtsOberflaeche());
    }
    
    public void popUp(String eingabe) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        String alertCustomerFehlEingabe = "Customer: Fehleingabe\n";
        String contextHeaderCustomerFehlEingabe = "Customer darf nicht..."; //TODO

        if (eingabe.contains("alertCustomerFehlEingabe")) {
            alert.setTitle(alertCustomerFehlEingabe);
            alert.setContentText(alertCustomerFehlEingabe + contextHeaderCustomerFehlEingabe);
        }

        alert.showAndWait();
        refreshOberflaeche();
    }
    
    public BorderPane erstelleOberflaeche() {
        gui.setTop(topBorderPane());
        gui.setRight(rechtsOberflaeche());
        gui.setCenter(centerOberflaeche());
        return gui;
    }
    
    public GridPane topOberflaecheLinks() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setStyle("-fx-background-color: #b0e5b8;");
        
        Button buttonRefresh = new Button("Refresh");
        buttonRefresh.setPrefSize(100, 20);
        grid.add(buttonRefresh, 0, 0);

        buttonRefresh.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                refreshOberflaeche();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } 
        });
        
        return grid;
    }
     
    public GridPane topOberflaecheCenter() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 12, 15, 12));
        grid.setStyle("-fx-background-color: #b0e5b8;");
        return grid;
    }
    
    public GridPane topOberflaecheRechts() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 12, 15, 12));
        grid.setStyle("-fx-background-color: #adadad;");
        
        /*
         * Mockup oder API Boolean
         */
    	Text mockupTxt = new Text();
        if (booleanMockup) {
        	mockupTxt = new Text("Connection: Mockup");
        	mockupTxt.setFill(Color.RED);
        } else {
        	mockupTxt = new Text("Connection: API");
        	mockupTxt.setFill(Color.GREEN);
        }
        mockupTxt.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        grid.add(mockupTxt, 0, 0);
           
        
        return grid;
    }
    
    public BorderPane topBorderPane() {
        topBorderPane.setLeft(topOberflaecheLinks());
        topBorderPane.setRight(topOberflaecheRechts());
        topBorderPane.setCenter(topOberflaecheCenter());

        return topBorderPane;
    }
    
    /*
     * mersistafa
     * Added invoice generation on the Subscriptions Anzeigen button. See the setOnAction method for this button.
     * 
     */
    public GridPane rechtsOberflaeche() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setStyle("-fx-background-color: #adadad;");
        
        /**
         * mersistafa
         * quelle: https://stackoverflow.com/questions/36248348/javafx-sqlite-get-id-from-selected-listview-element
         */
        ListView<Invoice> invoicesList = new ListView<>();
        
        //Set the displayFormat of the list items
        invoicesList.setCellFactory(lv -> new ListCell<Invoice>(){
        	@Override
            public void updateItem(Invoice invoice, boolean empty) {
                super.updateItem(invoice, empty) ;
                setText(empty ? null : invoice.toString());
            }
        });
        invoicesList.setVisible(false);
        
        //Set the displayFormat of the list items
        ListView<Subscription> subscriptionsList = new ListView<>();
        subscriptionsList.setCellFactory(lv -> new ListCell<Subscription>(){
        	@Override
            public void updateItem(Subscription subscription, boolean empty) {
                super.updateItem(subscription, empty) ;
                setText(empty ? null : subscription.toString());
            }
        });
        subscriptionsList.setPrefWidth(400);

        Text textAllCustomer = new Text("Kundenstamm:");
        textAllCustomer.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        grid.add(textAllCustomer, 0, 0);

        ListView<String> customerList = new ListView<>();
        customerList.setPrefWidth(400);
        customerList.setPrefHeight(600);
       
        List customerListe = (List) KundenStamm.getInstance().getCustomerAsList();
        ObservableList<String> customerItems = FXCollections.observableArrayList(customerListe);
        
        customerList.setItems(customerItems);
        grid.add(customerList, 0, 1);
        

        
        Text textCustomerData = new Text("Subscriptions / Invoices vom Kunden:");
        textCustomerData.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        grid.add(textCustomerData, 1, 0);

        
        Button subscriptionAnzeigenBtn = new Button("Subscriptions");
        subscriptionAnzeigenBtn.setPrefSize(150, 20);
        grid.add(subscriptionAnzeigenBtn, 0, 2);
        
        
        grid.add(subscriptionsList, 1, 1);
        subscriptionAnzeigenBtn.setOnAction(e -> {
        String ausgewaehlterKunde = customerList.getFocusModel().getFocusedItem();
        String customerName = ausgewaehlterKunde.substring(ausgewaehlterKunde.indexOf("Name:") + 6);
        ObservableList<Subscription> subscriptionItems;
        
        
	        if(customerName=="")
	        {
	        	// If no customer has been chosen, display an error message on the subscriptions list.
	        	ArrayList<Subscription> subscriptionsEmptyArray = new ArrayList<>();
	        	subscriptionItems = FXCollections.observableArrayList(subscriptionsEmptyArray);
	        }
	        else{
		        
		        
//		        subscriptionsList.setPrefHeight(200);
		        Customer activeCustomer = KundenStamm.getInstance().getCustomerByName(customerName);
		        SubscriptionsRepository subscriptions = activeCustomer.getSubscriptions();
		        
		        SubscriptionsRepository newlyExpiredSubscriptions = new SubscriptionsRepository();		        
		        
		        //Check if any of the active subscriptions has expired.
		        for(Subscription s: subscriptions){
		        	
		        	String subscriptionState = s.getSubscriptionState().getState().toLowerCase();
		        	
		        	// Set payment pending and add to the list of newly expired subscriptions
		        	// A subscription is considered newly expired if it has the state = active and the valid until is smaller than the current moment
		        	if (s.getValidUntil().isBefore(ZonedDateTime.now(ZoneOffset.UTC)) && 
		        		!subscriptionState.equals("suspended") &&
		        		!subscriptionState.equals("inactive"))
		        	{
		        		s.getSubscriptionState().paymentPending();
		        		newlyExpiredSubscriptions.addSubscription(s);
		        	}
		        } 
		        
		        //Generate invoice for the newly expired subscriptions
		        if (newlyExpiredSubscriptions.iterator().hasNext())
		        {
		        	InvoiceFactory invoiceFactory = InvoiceFactory.getInvoiceFactory(booleanMockup);
		        	Invoice newinvoice = invoiceFactory.generateInvoice(activeCustomer, newlyExpiredSubscriptions);
		        	activeCustomer.addInvoice(newinvoice);
		        }
		        
		        subscriptionItems = FXCollections.observableArrayList(subscriptions.toArrayList());
		        
	        }
	        subscriptionsList.setItems(null);
	        subscriptionsList.setItems(subscriptionItems);
	        subscriptionsList.setVisible(true);
	        invoicesList.setVisible(false);
	        textCustomerData.setText("Subscriptions vom Kunden:");
        });

//        Text textCustomerInvoice = new Text("Invoices vom Kunden:");
//        textCustomerInvoice.setFont(Font.font("Arial", FontWeight.BOLD, 12));
//        grid.add(textCustomerInvoice, 1, 2);

        
        Button invoiceAnzeigenBtn = new Button("Invoices");
        invoiceAnzeigenBtn .setPrefSize(150, 20);
        grid.add(invoiceAnzeigenBtn, 1, 2);
       
        Button payInvoiceBtn = new Button("Pay invoice");
        payInvoiceBtn.setPrefSize(150, 20);
        payInvoiceBtn.setVisible(false);
        grid.add(payInvoiceBtn, 2, 0);
        
        /**
         * mersistafa
         * quelle: https://stackoverflow.com/questions/36248348/javafx-sqlite-get-id-from-selected-listview-element
         */
        payInvoiceBtn.setOnAction(e -> {
           Invoice invoice =  invoicesList.getFocusModel().getFocusedItem();
           invoice.payInvoice();
            	//Refresh the invoices list
           InvoiceFactory invoiceFactory = InvoiceFactory.getInvoiceFactory(booleanMockup);
	        ArrayList<Invoice> unpaidInvoices = invoiceFactory.getUnpaidInvoices(invoice.getCustomer()); 
	        ObservableList<Invoice> invoices = FXCollections.observableArrayList(unpaidInvoices);
	        if(unpaidInvoices.isEmpty())
	        {
	        	payInvoiceBtn.setVisible(false);
	        }
	        else 
	        {
	        	payInvoiceBtn.setVisible(true);
	        	
	        }
		    invoicesList.setItems(invoices);
		        
	        
       });

        
        grid.add(invoicesList, 1, 1);
        
        invoiceAnzeigenBtn.setOnAction(e -> {
        String ausgewaehlterKunde = customerList.getFocusModel().getFocusedItem();
        String customerName = ausgewaehlterKunde.substring(ausgewaehlterKunde.indexOf("Name:") + 6);
        ObservableList<Invoice> invoices;
        
        invoicesList.setPrefWidth(400);
	        if(customerName==""){
	        	ArrayList<Invoice> invoicesEmptyArray = new ArrayList<>();
	        	invoices = FXCollections.observableArrayList(invoicesEmptyArray);
	        }
	        else {
		        
	        	//Get the selected customer
		        Customer customer = KundenStamm.getInstance().getCustomerByName(customerName);
		        
		        //Create the invoice factory
		        InvoiceFactory invoiceFactory = InvoiceFactory.getInvoiceFactory(booleanMockup);
		        ArrayList<Invoice> unpaidInvoices = invoiceFactory.getUnpaidInvoices(customer); 
		        
		        invoices = FXCollections.observableArrayList(unpaidInvoices);
		        if(unpaidInvoices.isEmpty())
		        {
		        	payInvoiceBtn.setVisible(false);
		        }
		        else 
		        {
		        	payInvoiceBtn.setVisible(true);
		        	
		        }
	        }
	        invoicesList.setItems(invoices);
	        invoicesList.setVisible(true);
	        subscriptionsList.setVisible(false);
	        textCustomerData.setText("Invoices vom Kunden:");
        });
       
        return grid;
    }
    
    /**
     * mersistafa
     * An invoice was added to a new customer. 
     * This doesn't make sense because when a customer is added he cannot have invoices. 
     * If he subscribes to a plan he or she gets a free month
     * @return
     */
    public GridPane centerOberflaeche() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        Text addCustomerTypeText = new Text("Kundentyp:");
        addCustomerTypeText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        grid.add(addCustomerTypeText, 0, 0);
        ObservableList<String> addCustomerType
                = FXCollections.observableArrayList(
                        "business",
                        "consumer"
                );
        
        final ComboBox comboBoxAddCustomer = new ComboBox(addCustomerType);
        grid.add(comboBoxAddCustomer, 1, 0);
        
        Text customerLastNameText = new Text("Nachname:");
        customerLastNameText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        grid.add(customerLastNameText, 0, 1);
        final TextField customerLastName = new TextField();
        customerLastName.setPrefColumnCount(15);
        customerLastName.setPromptText("Nachname");
        GridPane.setConstraints(customerLastName, 1, 1);
        grid.getChildren().add(customerLastName);
        
        Text customerOrganizationText = new Text("Unternehmensname:");
        customerOrganizationText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        grid.add(customerOrganizationText, 0, 2);
        final TextField textCustomerOrganization = new TextField();
        textCustomerOrganization.setPrefColumnCount(15);
        textCustomerOrganization.setPromptText("Unternehmensname");
        GridPane.setConstraints(textCustomerOrganization, 1, 2);
        grid.getChildren().add(textCustomerOrganization);
                
        Text textChooseProduct = new Text("Produkt waehlen:");
        textChooseProduct .setFont(Font.font("Arial", FontWeight.BOLD, 12));
        grid.add(textChooseProduct , 0, 3);
        ObservableList<String> chooseProduct= FXCollections.observableArrayList(
                        "Office365",
                        "Dynamics",
                        "CRM"
                );
        
        final ComboBox comboChooseProduct = new ComboBox(chooseProduct);
        grid.add(comboChooseProduct, 1, 3);
        
        
        Text textChoosePlan= new Text("Plan waehlen:");
        textChoosePlan .setFont(Font.font("Arial", FontWeight.BOLD, 12));
        grid.add(textChoosePlan , 0, 4);
        ObservableList<String> choosePlan
                = FXCollections.observableArrayList(
                        "Standard",
                        "Business"
                );
        
        final ComboBox comboChoosePlan = new ComboBox(choosePlan);
        grid.add(comboChoosePlan, 1, 4);
 
        
        Button addCustomerBTN = new Button("Customer Hinzufügen");
        addCustomerBTN.setPrefSize(150, 20);
        grid.add(addCustomerBTN, 0, 5);
        
        addCustomerBTN.setOnAction(e ->  {
        	
            String customerType = comboBoxAddCustomer.getValue().toString();
            String customerOrganization = textCustomerOrganization.getText();
            String lastName = customerLastName.getText();
            String product =  comboChooseProduct.getValue().toString();
            String plan =  comboChoosePlan.getValue().toString();
            Customer tmp_customer = new Customer(customerType, customerOrganization, lastName);
            Plan tmp_plan = ProductStamm.getInstance().getProductByName(product).getPlanByName(plan);
            tmp_customer.addSubscription(tmp_plan);
            List<Product> products = new ArrayList<Product>();
            products.add(ProductStamm.getInstance().getProductByName(product));
            //tmp_customer.addInvoice(products);
            KundenStamm.getInstance().addKundenStamm(tmp_customer);
            
            if(!booleanMockup) {
            	HttpClientFastBill fastbill = new HttpClientFastBill();
            	System.out.println("getCustomerType: " + tmp_customer.getCustomerType());
            	System.out.println("getOrganization: " + tmp_customer.getOrganization());
            	System.out.println("getLastName: " + tmp_customer.getLastName());
            	fastbill.sendObjectsToFastBill(tmp_customer);
            }

//        	Items ioffice = new Items("Office", "10", "20");
//        	Items idynamics = new Items("Dynamics", "50", "20");
//        	Items icrm = new Items("CRM", "20", "20");
//            if(true){ //TODO
//            	tmp.addSubscription(ioffice);
//            }
            refreshOberflaeche();
        });
        
        
//        Button checkSubscriptionsBtn = new Button("Check Subscriptions");
//        checkSubscriptionsBtn.setPrefSize(150, 20);
//        grid.add(checkSubscriptionsBtn, 1, 4);
//
//        checkSubscriptionsBtn.setOnAction(e -> {
//        
//        
//        String ausgewaehlterKunde = customerList.getFocusModel().getFocusedItem();
//        String customerName = ausgewaehlterKunde.substring(ausgewaehlterKunde.indexOf("Name:") + 6);
//        
//        if(ausgewaehlterKunde!=""){ //TODO
//	        ListView<String> subscriptionsList = new ListView<>();
//	        subscriptionsList.setPrefWidth(400);
//	        subscriptionsList.setPrefHeight(200);
//	       
//	        List subscriptionsListe = (List) KundenStamm.getInstance().getCustomerByName(customerName).getSubscriptions();
//	        ObservableList<String> subscriptionItems = FXCollections.observableArrayList(subscriptionsListe);
//	        
//	        subscriptionsList.setItems(subscriptionItems);
//	        grid.add(subscriptionsList, 0, 3);
//        }
//        //refreshOberflaeche();
//        });
//        
        
        return grid;
    }
        
        
        
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Client: Debitoren Management");
        primaryStage.centerOnScreen();
        Scene scene = new Scene(erstelleOberflaeche());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	XmlConfiguration.getInstance().configurationLesen();
    	booleanMockup = XmlConfiguration.getInstance().isMokup();
    	
    	if(!booleanMockup) {
    		HttpClientFastBill fastbill = new HttpClientFastBill();
    		fastbill.sendObjectsToFastBill(new String("customerGet:3776242"));
    	}
    	
        launch(args);
        
    }

}
