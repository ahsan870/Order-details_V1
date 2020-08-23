package com.order.details.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


//@Service("mailUtil")
@Component
public class EmailImpl implements EmailUtil {

	@Autowired
	private JavaMailSender javaSender;

	@Override
	public void sendEmail(String toAddress, String subject, String body) {

		MimeMessage createMimeMessage = javaSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(createMimeMessage);
		try {
			helper.setTo(toAddress);
			helper.setText(body);
			helper.setSubject(subject);
			
		} catch (MessagingException e) {

			e.printStackTrace();
		}

		javaSender.send(createMimeMessage);

	}

}
