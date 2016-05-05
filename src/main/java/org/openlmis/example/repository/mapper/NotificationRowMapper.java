package org.openlmis.example.repository.mapper;

import org.openlmis.example.domain.Notification;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class NotificationRowMapper implements RowMapper<Notification> {

  @Override
  public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
    Notification notification = new Notification();
    notification.setId(rs.getLong("id"));
    notification.setRecipient(rs.getString("recipient"));
    notification.setSubject(rs.getString("subject"));
    notification.setMessage(rs.getString("message"));
    return notification;
  }
}
