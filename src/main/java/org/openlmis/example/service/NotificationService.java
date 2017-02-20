/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.example.service;

import org.openlmis.example.domain.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for sending notifications to users.
 */
@Service
public class NotificationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

  private JavaMailSender mailSender;

  @Value("${mail.smtp.from}")
  private String fromAddress;

  @Autowired
  public NotificationService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  /**
   * Sends email notifications to users.
   * @param notifications the list of notifications to process
   */
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
      LOGGER.error("Unable to send mail", ex);
    }
  }
}
