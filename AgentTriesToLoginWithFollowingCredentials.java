package kf.csc.fixture;

import java.rmi.RemoteException;
//comment
import kf.core.logging.FitnesseLoggingErrorException;
import kf.core.logging.Logger;
import kf.csc.SessionController;
import kf.web.fixture.BasicFixture;
import kf.ws.csc.authentication.AuthenticationResult;
import kf.ws.csc.authentication.AuthenticationWSProxy;
import kf.ws.csc.authentication.Exception;

public class AgentTriesToLoginWithFollowingCredentials extends BasicFixture {

    private static final String SUCCESSFULL_MESSAGE = "user successfully login";
    private static final String ERROR_MESSAGE = "acccess denied";
    private static final String WSDL_PATH = "AuthenticationWS";

    private AuthenticationResult result;

    private String login;
    private String password;
    private final AuthenticationWSProxy loginWS;

    public AgentTriesToLoginWithFollowingCredentials() {
        super();
        loginWS = new AuthenticationWSProxy();
        loginWS.setEndpoint(SessionController.getEndpoint(WSDL_PATH));
        Logger.out.debug(loginWS.getEndpoint());
    }

    public String loginIsSuccessfulAndSessionIdIsReturned() {
        String success;
        if(result.isSuccessful()) {
            success = SUCCESSFULL_MESSAGE;
        } else {
            success = ERROR_MESSAGE;
        }
        return success;
    }

    public void execute() {
        try {
            result = loginWS.login(login, password);
            SessionController.setCscSessionId(result.getSessionID());
        } catch(Exception e) {
            throw new FitnesseLoggingErrorException("An error occured when accessing web service", e);
        } catch(RemoteException e) {
            throw new FitnesseLoggingErrorException("An error occured when accessing web service", e);
        }
    }

    public String nameOfLoggedInUserIsDisplayed() {
        return result.getUser();
    }

    public void setLogin(final String login) {
        this.login = login;

    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String isLoginSuccessful() {
        return Boolean.toString(result.isSuccessful());
    }
}
