package com.order.details.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.details.entity.OrderEntity;
import com.order.details.repository.OrderDetailsRepository;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Override
	public String saveOrder(OrderEntity orderEntity) {
		Random ran = new Random();
		String orderId=String.valueOf(ran.nextInt(Integer.MAX_VALUE));
		orderEntity.setOrderNumber(orderId);
		orderDetailsRepository.save(orderEntity);
		return orderId;

	}

	@Override
	public Optional<OrderEntity> getOrderDetails(String orderNumber) {
		return orderDetailsRepository.findByOrderNumber(orderNumber);
	}

	@Override
	public List<OrderEntity> fetchAllOrderDetails() {
		return orderDetailsRepository.findAll();
	}

	@Override
	public void updateOrder(OrderEntity orderEntity, String orderNumber) {
		orderDetailsRepository.save(orderEntity);

	}

	@Override
	public void deleteOrderByOrderNumber(String orderNumber) {
		orderDetailsRepository.deleteByOrderNumber(orderNumber);

	}

	@Override
	public void updateOrderStatus(String orderNumber, String orderStatus) {
		orderDetailsRepository.updateOrderStatus(orderNumber, orderStatus);

	}

}
