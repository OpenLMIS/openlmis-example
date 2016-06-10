package org.openlmis.example.repository.mapper;

import org.openlmis.example.domain.Notification;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class NotificationRowMapper implements RowMapper<Notification> {

  @Override
  public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
    Notification notification = new Notification();
    notification.setId(UUID.fromString(rs.getString("id")));
    notification.setRecipient(rs.getString("recipient"));
    notification.setSubject(rs.getString("subject"));
    notification.setMessage(rs.getString("message"));
    return notification;
  }
}
