package kf.csc.fixture;

import java.rmi.RemoteException;


import kf.core.VariableController;
import kf.csc.SessionController;
import kf.web.fixture.BasicFixture;
import kf.ws.csc.customer.Customer;
import kf.ws.csc.customer.CustomerWSProxy;

public class SetActiveProfile extends BasicFixture {
    
    private static final String WSDL_PATH = "CustomerWS"; 
    private String profileID = "";
    private String activeProfile = "";
    private String activeFirstName = "";
    private String activeLastName = "";
    private String activeLogin = "";
    
    

    public SetActiveProfile() {
        System.out.println("fixture started" + SessionController.getEndpoint(WSDL_PATH));
    }
    
    public void setProfileID(final String profileID){
        this.profileID = profileID; 
    }
    
    public void execute() throws RemoteException{
        try{
            activeProfile = "";
            activeFirstName = "";
            activeLastName = "";
            activeLogin = "";
            Customer customer = new Customer();
            customer.setProfileId(profileID);
            CustomerWSProxy proxy = new CustomerWSProxy();
            String endpoint = SessionController.getEndpoint(WSDL_PATH);
            proxy.setEndpoint(endpoint);
            customer = proxy.changeCurrentCustomer(customer);
            customer = proxy.getCurrentCustomer();
            if (customer != null){
                activeProfile = customer.getProfileId();
                activeFirstName = customer.getFirstName();
                activeLastName = customer.getLastName();
                activeLogin = customer.getLogin();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    public String activeProfile() {
        return (activeProfile == null)||("".equals(activeProfile)) ? "None" : activeProfile;
    }

    public String activeFirstName() {
        return (activeFirstName == null)||("".equals(activeFirstName)) ? "None" : activeFirstName;
    }

    public String activeLastName() {
        return (activeLastName == null)||("".equals(activeLastName)) ? "None" : activeLastName;
    }

    public String activeLogin() {
        return (activeLogin == null)||("".equals(activeLogin)) ? "None" : activeLogin;
    }

}
