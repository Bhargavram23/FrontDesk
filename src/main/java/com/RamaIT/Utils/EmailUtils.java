package com.RamaIT.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private TemplateEngine templateEngine;
	@Value("${spring.mail.username}")
	String fromEmail;

	public boolean sendConfirmationEmail(String toEmail, String subject, Context context)
			throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		String content = templateEngine.process("verifyPage", context);
		mimeMessageHelper.setFrom(fromEmail);
		mimeMessageHelper.setTo(toEmail);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setText(content, true);
		try {
			javaMailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			throw e;
		}

	}

}
