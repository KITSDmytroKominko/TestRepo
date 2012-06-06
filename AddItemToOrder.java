package kf.csc.fixture;

import kf.core.logging.FitnesseLoggingErrorException;
import kf.csc.checkout.OrderWSProxy;
import kf.web.fixture.BasicFixture;

public class AddItemToOrder extends BasicFixture {
	private String skuId;
	private Long addedQuantity;

	public void setSkuID(String skuId) {
		this.skuId = skuId;
	}

	public void setAddedQuantity(String addedQuantity) {
		try {
			this.addedQuantity = Long.valueOf(addedQuantity);
		} catch (NumberFormatException e) {
			throw new FitnesseLoggingErrorException(
					"***Quantity should be integer.", e);
		}
	}
	
	public void execute() {
		OrderWSProxy clientToWS = new OrderWSProxy(); 
	}

}
