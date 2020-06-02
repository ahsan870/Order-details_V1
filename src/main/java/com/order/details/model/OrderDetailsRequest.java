package com.order.details.model;

import java.util.Date;

public class OrderDetailsRequest {

	private String orderNumber;
	private String orderStatus;
	private String productCode;
	private String customerId;
	private int quantityOrdered;
	private String phoneNumber;
	private Date requiredDate;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

	@Override
	public String toString() {
		return "OrderDetailsRequest [orderNumber=" + orderNumber + ", orderStatus=" + orderStatus + ", productCode="
				+ productCode + ", customerId=" + customerId + ", quantityOrdered=" + quantityOrdered + ", phoneNumber="
				+ phoneNumber + ", requiredDate=" + requiredDate + "]";
	}

}
