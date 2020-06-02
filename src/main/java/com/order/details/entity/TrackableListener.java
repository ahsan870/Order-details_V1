package com.order.details.entity;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class TrackableListener {

	@PrePersist
	public void setCreatedDate(Trackable trackable) {

		trackable.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		this.setModifiedDate(trackable);
	}

	@PreUpdate
	public void setModifiedDate(Trackable trackable) {
		trackable.setModifiedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
	}

}