package com.order.details.client;

import com.order.details.model.CustomerDetails;

public interface CustomerDetailsClient {

	public CustomerDetails getCustomerDetailsByCustomerId(final String customerId) throws Exception;

	public Boolean checkCustomerExistsByCustomerId(final String customerId) throws Exception;
}
