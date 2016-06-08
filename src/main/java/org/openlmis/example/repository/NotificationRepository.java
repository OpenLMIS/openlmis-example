package org.openlmis.example.repository;

import org.openlmis.example.domain.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface NotificationRepository extends CrudRepository<Notification, UUID> {
}
