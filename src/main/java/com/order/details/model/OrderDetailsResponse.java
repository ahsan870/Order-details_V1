package com.order.details.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderDetailsResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy@HH:mm")
	private Date timestamp = new Date();
	private String message = "Success";

	public OrderDetailsResponse(String message) {
		super();
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
