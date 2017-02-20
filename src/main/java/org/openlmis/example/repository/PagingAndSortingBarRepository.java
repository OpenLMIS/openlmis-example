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

package org.openlmis.example.repository;

import org.openlmis.example.domain.Bar;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface PagingAndSortingBarRepository extends PagingAndSortingRepository<Bar, UUID> {

  /*
    As the name implies, extending PagingAndSortingRepository rather than CrudRepository
    will expose additional endpoints useful for paging and sorting. By default, the response
    to a request for all resources will include only twenty of them. Examples of request URIs 
    include:
    
    http://127.0.0.1:8080/api/bars?page=0&size=50
    http://127.0.0.1:8080/api/bars?page=0&size=50&sort=priority
  */
}
