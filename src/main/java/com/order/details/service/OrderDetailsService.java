package com.order.details.service;

import java.util.List;
import java.util.Optional;

import com.order.details.entity.OrderEntity;

public interface OrderDetailsService {

	public String saveOrder(OrderEntity orderEntity);

	public Optional<OrderEntity> getOrderDetails(String orderNumber);

	public List<OrderEntity> fetchAllOrderDetails();

	public void updateOrder(OrderEntity orderEntity, String orderNumber);

	public void deleteOrderByOrderNumber(String orderNumber);

	public void updateOrderStatus(String orderNumber, String orderStatus);

}
