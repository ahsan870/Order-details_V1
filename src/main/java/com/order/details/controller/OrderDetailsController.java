package com.order.details.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.details.client.CustomerDetailsClient;
import com.order.details.client.ProductDetailsClient;
import com.order.details.entity.OrderEntity;
import com.order.details.model.OrderDetailsRequest;
import com.order.details.model.OrderDetailsResponse;
import com.order.details.service.OrderDetailsService;
import com.order.details.transformer.OrderDetailsTransformer;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/order")
@Api
public class OrderDetailsController {

	private static final String MESSAGE = "Order requested With ";

	private static final Logger logger = LoggerFactory.getLogger(OrderDetailsController.class);

	@Autowired
	private OrderDetailsService orderDetailsService;

	@Autowired
	private OrderDetailsTransformer orderDetailsTransformer;

	@Autowired
	private ProductDetailsClient productDetailsClient;

	@Autowired
	private CustomerDetailsClient customerDetailsClient;

	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDetailsResponse> createOrder(@Valid @RequestBody OrderDetailsRequest orderDetailsRequest)
			throws Exception {

		customerValidation(orderDetailsRequest);
		productValidation(orderDetailsRequest);
		productQuantityValidation(orderDetailsRequest);
		productDetailsClient.updateQuantity(orderDetailsRequest.getProductCode(),
				orderDetailsRequest.getQuantityOrdered());
		orderDetailsService.saveOrder(orderDetailsTransformer.transformOrderDetails(orderDetailsRequest));
		OrderDetailsResponse response = new OrderDetailsResponse(
				MESSAGE + orderDetailsRequest.getOrderNumber() + " created Successfully");
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@GetMapping(path = "/get/{orderNumber}")
	public ResponseEntity<OrderEntity> getOrderDetails(@PathVariable(name = "orderNumber") final String orderNumber) {

		logger.info("Order number is received as::{}", orderNumber);
		Optional<OrderEntity> orderDetails = orderDetailsService.getOrderDetails(orderNumber);
		if (orderDetails.isPresent()) {
			return new ResponseEntity<>(orderDetails.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/all")
	public ResponseEntity<List<OrderEntity>> getAllOrderDetails() {

		return new ResponseEntity<>(orderDetailsService.fetchAllOrderDetails(), HttpStatus.OK);
	}

	@DeleteMapping(path = "/delete/{orderNumber}")
	public ResponseEntity<OrderDetailsResponse> deleteOrder(
			@PathVariable(name = "orderNumber") final String orderNumber) {

		logger.info("Order number is received for delete as::{}", orderNumber);
		orderDetailsService.deleteOrderByOrderNumber(orderNumber);
		OrderDetailsResponse response = new OrderDetailsResponse(MESSAGE + orderNumber + " Delete Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PutMapping(path = "/update")
	public ResponseEntity<OrderDetailsResponse> updateOrder(@Valid @RequestBody OrderDetailsRequest orderDetailsRequest,
			@PathVariable(name = "orderNumber") final String orderNumber) {

		logger.info("Order number is received for update as::{}", orderNumber);
		orderDetailsService.updateOrder(orderDetailsTransformer.transformOrderDetails(orderDetailsRequest),
				orderNumber);
		OrderDetailsResponse response = new OrderDetailsResponse(MESSAGE + orderNumber + " updated Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PutMapping(path = "/update/{orderNumber}/{orderStatus}")
	public ResponseEntity<OrderDetailsResponse> updateOrderStatus(
			@PathVariable(name = "orderNumber") final String orderNumber,
			@PathVariable(name = "orderStatus") final String orderStatus) {

		logger.info("Order number is received for update as::{}", orderNumber);
		orderDetailsService.updateOrderStatus(orderNumber, orderStatus);
		OrderDetailsResponse response = new OrderDetailsResponse(MESSAGE + orderNumber + " updated Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	private ResponseEntity<OrderDetailsResponse> customerValidation(OrderDetailsRequest orderDetailsRequest)
			throws Exception {

		String customerId = orderDetailsRequest.getCustomerId();
		Boolean checkCustomerExistsByCustomerId = customerDetailsClient.checkCustomerExistsByCustomerId(customerId);
		if (!checkCustomerExistsByCustomerId) {
			OrderDetailsResponse response = new OrderDetailsResponse("Customer id: " + customerId + " not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return null;
	}

	private ResponseEntity<OrderDetailsResponse> productValidation(OrderDetailsRequest orderDetailsRequest)
			throws Exception {

		Boolean productDetailsByProductCode = productDetailsClient
				.checkProductExistsByProductCode(orderDetailsRequest.getProductCode());
		if (!productDetailsByProductCode) {
			OrderDetailsResponse response = new OrderDetailsResponse(
					"Product with code " + orderDetailsRequest.getProductCode() + " not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return null;

	}

	private ResponseEntity<OrderDetailsResponse> productQuantityValidation(OrderDetailsRequest orderDetailsRequest)
			throws Exception {

		Boolean isQuantityAvailable = productDetailsClient.checkQuantityAvailableByProductCode(
				orderDetailsRequest.getProductCode(), orderDetailsRequest.getQuantityOrdered());
		if (!isQuantityAvailable) {
			OrderDetailsResponse response = new OrderDetailsResponse("Quantity Ordered for Product with code "
					+ orderDetailsRequest.getProductCode() + " not avialable");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return null;

	}
}
