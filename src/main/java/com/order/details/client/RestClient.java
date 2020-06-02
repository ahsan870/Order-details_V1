package com.order.details.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@SuppressWarnings("unchecked")
	@Retryable(maxAttemptsExpression = "#{${retry.rest.attempts:3}}", backoff = @Backoff(delayExpression = "#{${retry.rest.delay:90000}}"))
	public <T> Object invoke(T requestObject, Class<?> clazz, String url, HttpMethod requestMethod) throws Exception {

		HttpHeaders headers = httpHeadersFactory.createRestHttpHeader();

		HttpEntity<T> entity = new HttpEntity<>(requestObject, headers);
		ResponseEntity<Object> responseEntity = null;
		if (requestObject != null && requestMethod == HttpMethod.POST) {
			responseEntity = (ResponseEntity<Object>) restTemplate.postForEntity(url, entity, clazz);
		} else if (requestMethod == HttpMethod.DELETE) {
			responseEntity = (ResponseEntity<Object>) restTemplate.exchange(url, HttpMethod.DELETE, entity, clazz,
					headers);
		} else if (requestMethod == HttpMethod.PUT) {
			responseEntity = (ResponseEntity<Object>) restTemplate.exchange(url, HttpMethod.PUT, entity, clazz,
					headers);
		} else {
			responseEntity = (ResponseEntity<Object>) restTemplate.exchange(url, HttpMethod.GET, entity, clazz);
		}
		if (responseEntity != null) {
			return responseEntity.getBody();
		} else {
			return null;
		}

	}

}
