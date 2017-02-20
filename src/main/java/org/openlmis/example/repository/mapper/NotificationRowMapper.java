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
