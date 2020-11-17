package com.soprabanking.ips.services;

import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	private JavaMailSender sender;
	
	public void sendResetLink(String to, String content, String subject) {
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setText(content);
		msg.setSubject(subject);
		sender.send(msg);
	}
	
	public String mailContent(UUID id) {
		return "Password reset link " + "http://localhost:4200/resetLink/" + id;
	}

}
