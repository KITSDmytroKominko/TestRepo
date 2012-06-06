package kf.csc.fixture;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import util.ListUtility;
import kf.core.logging.FitnesseLoggingErrorException;
import kf.csc.SessionController;
import kf.ws.csc.customer.Customer;
import kf.ws.csc.customer.CustomerSearchCriteria;
import kf.ws.csc.customer.CustomerWSProxy;
import com.rits.cloning.Cloner;


public class SearchForCustomerByLastNameAndPostalCode {
	
	private final CustomerWSProxy customerWs;
	private final CustomerSearchCriteria searchCriteria;
	private static final String WSDL_PATH = "CustomerWS";

	public SearchForCustomerByLastNameAndPostalCode (final String lastName, final String postCode)
	{
		searchCriteria = new CustomerSearchCriteria (lastName, postCode);
		customerWs = new CustomerWSProxy();
		customerWs.setEndpoint(SessionController.getEndpoint(WSDL_PATH));
	}
	 
	public List<Object> query()
	{
		try {
			Customer[] resultArray = customerWs.searchCustomer(searchCriteria);
			if (resultArray == null) {
	            Customer emptyCustomer = new Customer ("", "", "", "", "", "", "");
	            resultArray = new Customer[1];
	            resultArray[0]= emptyCustomer;
	        }
			List<Object> result=new ArrayList<Object>();
			String property;
			Cloner cloner=new Cloner();
			ArrayList<ArrayList<String>> customer = new ArrayList<ArrayList<String>>();
			
			for (int i = 0; i < resultArray.length; i++) {
			    
			    property = emptyIfNull(resultArray[i].getBillingPhoneNumber());
                customer.add((ArrayList<String>)ListUtility.list("Billing Phone", property));
			    
                property = emptyIfNull(resultArray[i].getBillingPostalCode());
                customer.add((ArrayList<String>)ListUtility.list("Billing Postal Code", property));
            
                property = emptyIfNull(resultArray[i].getEmail());
                customer.add((ArrayList<String>)ListUtility.list("Email", property));
            
	            property = emptyIfNull(resultArray[i].getFirstName());
	            customer.add((ArrayList<String>)ListUtility.list("First Name", property));
     
	            property = emptyIfNull(resultArray[i].getLastName());
	            customer.add((ArrayList<String>)ListUtility.list("Last Name", property));
	            
	            property = emptyIfNull(resultArray[i].getLogin());
	            customer.add((ArrayList<String>)ListUtility.list("Login", property));
	            
	            property = emptyIfNull(resultArray[i].getProfileId());
	            customer.add((ArrayList<String>)ListUtility.list("Id", property));
	          
	            //row.add(ListUtility.list("first name", emptyIfNull(searchResults[i].getFirstName())));
	            
	            result.add(cloner.deepClone(customer));
	        }
	        return result;
		}
		catch (RemoteException e) {
        throw new FitnesseLoggingErrorException("Can't connect to WS", e);
		} 
	}
		private String emptyIfNull (final String source) {
	        return source==null?"":source;
	    }
}		

