package kf.csc.fixture;

import java.rmi.RemoteException;

import kf.core.logging.FitnesseLoggingErrorException;
import kf.csc.SessionController;
import kf.ws.csc.authentication.SimpleGreetingsWSProxy;

public class SimpleGreetingsWS {
	
	private static final String WSDL_PATH = "SimpleGreetingsWS";
	private final SimpleGreetingsWSProxy greetings;
	
	public SimpleGreetingsWS() {
		greetings = new SimpleGreetingsWSProxy();
		greetings.setEndpoint(SessionController.getEndpoint(WSDL_PATH));
		
		
	}

    public String greeting() {
        try {
            return greetings.getGreeting();
        } catch(RemoteException e) {
            throw new FitnesseLoggingErrorException("An error occured when getting greeting", e);
        }
    }

}
