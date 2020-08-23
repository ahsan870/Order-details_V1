package com.order.details.transformer;

import org.springframework.stereotype.Component;

import com.order.details.entity.OrderEntity;
import com.order.details.model.OrderDetailsRequest;

@Component
public class OrderDetailsTransformer {

	public OrderEntity transformOrderDetails(OrderDetailsRequest orderDetailsRequest) {

		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setCustomerId(orderDetailsRequest.getCustomerId());
		orderEntity.setProductCode(orderDetailsRequest.getProductCode());
		orderEntity.setPhoneNumber(orderDetailsRequest.getPhoneNumber());
		orderEntity.setQuantityOrdered(orderDetailsRequest.getQuantityOrdered());
		orderEntity.setRequiredDate(orderDetailsRequest.getRequiredDate());
		return orderEntity;

	}

}
