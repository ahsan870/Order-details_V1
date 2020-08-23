package com.order.details.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
//@Service
public interface EmailUtil {
	
	void sendEmail(String toAddress, String subject, String body);

}
