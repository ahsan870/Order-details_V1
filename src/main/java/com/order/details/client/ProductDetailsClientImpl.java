package com.order.details.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.order.details.model.ProductDetails;

@Component
public class ProductDetailsClientImpl implements ProductDetailsClient {

	@Autowired
	private RestClient restClient;

	@Value("${product.details.endpoint:}")
	public String productDetailsMsEndpoint;

	@Value("${product.exist.endpoint:}")
	public String productExistMsEndpoint;

	@Value("${quantity.available.endpoint:}")
	public String quantityAvailableMsEndpoint;

	@Value("${quantity.update.endpoint:}")
	public String quantityUpdateMsEndpoint;

	private UriComponentsBuilder uriComponentBuilder;

	@Override
	public ProductDetails getProductDetailsByProductCode(String productCode) throws Exception {

		MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<>();
		queryMap.add("productCode", productCode);
		uriComponentBuilder = UriComponentsBuilder.fromHttpUrl(productDetailsMsEndpoint);
		uriComponentBuilder.queryParams(queryMap);
		return (ProductDetails) restClient.invoke(null, ProductDetails.class,
				uriComponentBuilder.build(false).toUriString(), HttpMethod.GET);
	}

	@Override
	public Boolean checkProductExistsByProductCode(String productCode) throws Exception {

		String requestUrl = productExistMsEndpoint.concat("/" + productCode);
		return (Boolean) restClient.invoke(null, Boolean.class,
				UriComponentsBuilder.fromUriString(requestUrl).build().expand(productCode).encode().toUri().toString(),
				HttpMethod.GET);
	}

	@Override
	public Boolean checkQuantityAvailableByProductCode(String productCode, int quantityOrdered) throws Exception {

		String requestUrl = quantityAvailableMsEndpoint.concat("/" + productCode).concat("/" + quantityOrdered);
		return (Boolean) restClient.invoke(null, Boolean.class, UriComponentsBuilder.fromUriString(requestUrl).build()
				.expand(productCode, quantityOrdered).encode().toUri().toString(), HttpMethod.GET);
	}

	@Override
	public void updateQuantity(String productCode, int quantityOrdered) throws Exception {
		String requestUrl = quantityUpdateMsEndpoint.concat("/" + productCode).concat("/" + quantityOrdered);
		restClient.invoke(null, Object.class, UriComponentsBuilder.fromUriString(requestUrl).build()
				.expand(productCode, quantityOrdered).encode().toUri().toString(), HttpMethod.PUT);

	}
}
