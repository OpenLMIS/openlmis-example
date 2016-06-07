package org.openlmis.example.service;

import org.openlmis.example.domain.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

  private JavaMailSender mailSender;

  @Value("${mail.smtp.from}")
  private String fromAddress;

  @Autowired
  public NotificationService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void processNotifications(@Payload List<Notification> notifications) {
    for (final Notification notification : notifications) {
      SimpleMailMessage msg = new SimpleMailMessage();
      msg.setFrom(fromAddress);
      msg.setTo(notification.getRecipient());
      msg.setSubject(notification.getSubject());
      msg.setText(notification.getMessage());
      sendMsgHelper(msg);
    }
  }

  private void sendMsgHelper(SimpleMailMessage msg) {
    try {
      this.mailSender.send(msg);
    } catch (MailException ex) {
      // simply log it and go on...
      System.err.println(ex.getMessage());
    }
  }
}
