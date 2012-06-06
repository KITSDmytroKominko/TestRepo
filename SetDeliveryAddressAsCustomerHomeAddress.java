package kf.csc.fixture;

import kf.csc.SessionController;
import kf.csc.service.order.Address;
import kf.csc.service.order.EditOrderDeliveryAddressWSProxy;
import kf.web.fixture.BasicFixture;
import kf.ws.csc.customer.Customer;
import kf.ws.csc.customer.CustomerWSProxy;

public class SetDeliveryAddressAsCustomerHomeAddress extends BasicFixture {
    
    private static final String WSDL_PATH = "EditOrderDeliveryAddressWS"; 
    
    public boolean activateValidProfile(final String customerId){
        Customer customer = new Customer();
        customer.setProfileId(customerId);
        CustomerWSProxy proxy = new CustomerWSProxy();
        String endpoint = SessionController.getEndpoint("CustomerWS");
        proxy.setEndpoint(endpoint);
//        System.out.println("ziga1 " + endpoint);
//        System.out.println("starting changing");
        try{
            customer = proxy.changeCurrentCustomer(customer);
            if (customer == null){
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public String activeCustomerHomeAddress(){
        String endpoint = SessionController.getEndpoint(WSDL_PATH);
        EditOrderDeliveryAddressWSProxy proxy = new EditOrderDeliveryAddressWSProxy(endpoint);
        try{
            Address address = proxy.getCurrentCustomerAddress();
            if (address != null){
                return addressToString(address);
            } else {
                return "None";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
    
    public String activeOrderDeliveryAddress(final String orderID){
        String endpoint = SessionController.getEndpoint(WSDL_PATH);
        EditOrderDeliveryAddressWSProxy proxy = new EditOrderDeliveryAddressWSProxy(endpoint);
        try{
            Address address = proxy.getOrderAddress(orderID);
            if (address != null){
                return addressToString(address);
            } else {
                return "None";
            }
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
    
    public boolean setCustomerHomeAddressToOrderDeliveryAddress(final String orderID){
        String endpoint = SessionController.getEndpoint(WSDL_PATH);
        EditOrderDeliveryAddressWSProxy proxy = new EditOrderDeliveryAddressWSProxy(endpoint);
        try{
            return proxy.editOrderDeliveryAddress(orderID);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean orderDeliveryAddressEqualsToCustomerHomeAddress(final String orderID){
        String endpoint = SessionController.getEndpoint(WSDL_PATH);
        EditOrderDeliveryAddressWSProxy proxy = new EditOrderDeliveryAddressWSProxy(endpoint);
        Address homeAddress;
        Address orderDeliveryAddress;
        try{
            homeAddress = proxy.getCurrentCustomerAddress();
            if (homeAddress == null){
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        try{
            orderDeliveryAddress = proxy.getOrderAddress(orderID);
            if (orderDeliveryAddress == null){
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return compareToAdresses(homeAddress, orderDeliveryAddress);
    }
    
    
    public static String addressToString(Address address){
        return address.getFirstName() + " " + 
        address.getAddress1() + " " +
        address.getAddress2() + " " +
        address.getAddress3() + " " +
        address.getCity() + " " +
        address.getCountry() + " " +
        address.getMiddleName() + " " +
        address.getLastName() + " " +
        address.getOwnerId() + " " +
        address.getPostalCode() + " " +
        address.getPrefix() + " " +
        address.getState() + " " +
        address.getSuffix() + " ";
    }
    
    private boolean compareToAdresses(final Address addr1, final Address other){
        return ((addr1.getAddress1()==null && other.getAddress1()==null) || 
            (addr1.getAddress1()!=null &&
                    addr1.getAddress1().equals(other.getAddress1()))) &&
           ((addr1.getAddress2()==null && other.getAddress2()==null) || 
            (addr1.getAddress2()!=null &&
                    addr1.getAddress2().equals(other.getAddress2()))) &&
           ((addr1.getAddress3()==null && other.getAddress3()==null) || 
            (addr1.getAddress3()!=null &&
                    addr1.getAddress3().equals(other.getAddress3()))) &&
           ((addr1.getCity()==null && other.getCity()==null) || 
            (addr1.getCity()!=null &&
                    addr1.getCity().equals(other.getCity()))) &&
           ((addr1.getCountry()==null && other.getCountry()==null) || 
            (addr1.getCountry()!=null &&
                    addr1.getCountry().equals(other.getCountry()))) &&
           ((addr1.getCounty()==null && other.getCounty()==null) || 
            (addr1.getCounty()!=null &&
                    addr1.getCounty().equals(other.getCounty()))) &&
           ((addr1.getFirstName()==null && other.getFirstName()==null) || 
            (addr1.getFirstName()!=null &&
                    addr1.getFirstName().equals(other.getFirstName()))) &&
           ((addr1.getLastName()==null && other.getLastName()==null) || 
            (addr1.getLastName()!=null &&
                    addr1.getLastName().equals(other.getLastName()))) &&
           ((addr1.getMiddleName()==null && other.getMiddleName()==null) || 
            (addr1.getMiddleName()!=null &&
                    addr1.getMiddleName().equals(other.getMiddleName()))) &&
           ((addr1.getPostalCode()==null && other.getPostalCode()==null) || 
            (addr1.getPostalCode()!=null &&
                    addr1.getPostalCode().equals(other.getPostalCode()))) &&
           ((addr1.getPrefix()==null && other.getPrefix()==null) || 
            (addr1.getPrefix()!=null &&
                    addr1.getPrefix().equals(other.getPrefix()))) &&
           ((addr1.getState()==null && other.getState()==null) || 
            (addr1.getState()!=null &&
                    addr1.getState().equals(other.getState()))) &&
           ((addr1.getSuffix()==null && other.getSuffix()==null) || 
            (addr1.getSuffix()!=null &&
                    addr1.getSuffix().equals(other.getSuffix())));
    }
    
    
    
}
