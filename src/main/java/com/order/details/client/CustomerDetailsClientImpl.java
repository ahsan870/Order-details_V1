package com.order.details.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.order.details.model.CustomerDetails;

@Component
public class CustomerDetailsClientImpl implements CustomerDetailsClient {

	@Autowired
	private RestClient restClient;

	@Value("${customer.details.endpoint:}")
	public String customerDetailsMsEndpoint;

	@Value("${customer.exist.endpoint:}")
	public String customerExistMsEndpoint;

	private UriComponentsBuilder uriComponentBuilder;

	@Override
	public CustomerDetails getCustomerDetailsByCustomerId(String customerId) throws Exception {

		MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<>();
		queryMap.add("customerId", customerId);
		uriComponentBuilder = UriComponentsBuilder.fromHttpUrl(customerDetailsMsEndpoint);
		uriComponentBuilder.queryParams(queryMap);
		return (CustomerDetails) restClient.invoke(null, CustomerDetails.class,
				uriComponentBuilder.build(false).toUriString(), HttpMethod.GET);

	}

	@Override
	public Boolean checkCustomerExistsByCustomerId(String customerId) throws Exception {

		String requestUrl = customerExistMsEndpoint.concat("/" + customerId);
		return (Boolean) restClient.invoke(null, Boolean.class,
				UriComponentsBuilder.fromUriString(requestUrl).build().expand(customerId).encode().toUri().toString(),
				HttpMethod.GET);

	}

}
