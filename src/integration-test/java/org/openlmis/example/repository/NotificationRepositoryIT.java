package org.openlmis.example.repository;

import org.openlmis.example.domain.Notification;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationRepositoryIT extends BaseCrudRepositoryIT<Notification> {

  @Autowired
  NotificationRepository repository;

  NotificationRepository getRepository() {
    return this.repository;
  }

  Notification generateInstance() {
    int instanceNumber = this.getNextInstanceNumber();
    Notification notification = new Notification();
    notification.setRecipient("recipient #" + instanceNumber);
    notification.setMessage("Message #" + instanceNumber);
    return notification;
  }
}
