package kf.csc.fixture;

import java.rmi.RemoteException;

import kf.core.logging.FitnesseLoggingErrorException;
import kf.csc.SessionController;
import kf.web.fixture.BasicFixture;
import kf.ws.csc.order.returns.ReturnForOrderWSProxy;
import kf.ws.csc.order.returns.SelectedItemInfo;

public class StartReturnAndSpecifyReturnItem extends BasicFixture {

    private static final String WSDL_NAME = "ReturnForOrderWS";

    private static final String SUCCESS_MESSAGE = "yes";
    private static final String FAILURE_MESSAGE = "no";

    private String orderId;
    private String quantityToReturn;
    private String reason;
    private SelectedItemInfo returnItemInfo;
    private boolean returnInitialised;
    private boolean returnItemSpecified;

    private final ReturnForOrderWSProxy returnWS;

    public StartReturnAndSpecifyReturnItem() {
        super();
        returnWS = new ReturnForOrderWSProxy(SessionController.getEndpoint(WSDL_NAME));
        returnItemSpecified = false;
    }

    public String returnInitialisedSuccessfully() {
        return returnInitialised ? SUCCESS_MESSAGE : FAILURE_MESSAGE;
    }

    public void execute() {
        try {
            returnInitialised = returnWS.initiateReturnForOrder(orderId);
            returnItemInfo = returnWS.selectItemToReturnRequest(quantityToReturn, reason);
            returnItemSpecified = true;
        } catch(RemoteException exc) {
            if(exc.getMessage().contains("401")) {
                returnInitialised = false;
            } else if(exc.getMessage().contains("NullPointer")) {
                returnItemSpecified = false;
            } else {
                throw new FitnesseLoggingErrorException("An error occured while communicating to web service", exc);
            }
        }
    }

    public void reset() {
        orderId = "";
        quantityToReturn = "0";
        reason = "";
        returnItemInfo = null;
        returnInitialised = false;
        returnItemSpecified = false;
    }

    public String returnItemSpecfiedSuccessfully() {
        return returnItemSpecified ? SUCCESS_MESSAGE : FAILURE_MESSAGE;
    }

    public String paymentTypeUsedForRefund() {
        String paymentType = "";
        if(returnItemInfo != null) {
            paymentType = returnItemInfo.getPaymentGroup();
        }
        return paymentType;
    }

    public double refundAmount() {
        double refundAmount = 0;
        if(returnItemInfo != null) {
            refundAmount = returnItemInfo.getAmount();
        }
        return refundAmount;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }

    public void setQuantityToReturn(final String quantityToReturn) {
        this.quantityToReturn = quantityToReturn;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }
}
