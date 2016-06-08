package org.openlmis.example.repository;

import org.junit.runner.RunWith;
import org.openlmis.example.Application;
import org.openlmis.example.domain.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
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
