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

import org.openlmis.example.domain.Foo;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ReadOnlyFooRepository extends Repository<Foo, UUID> {

  // Spring Data Rest doesn't automatically create an endpoint for this - see comments 
  // in FooController.java.
  long count();

  // Spring Data Rest doesn't automatically create an endpoint for this member.
  boolean existsById(UUID id);

  // Automatically accessible via http://127.0.0.1:8080/api/foos/
  // Interestingly, without this entry, Spring Data Rest returns a 405 (Method Not Allowed) 
  // rather than a 404 (Not Found) response status.
  Iterable<Foo> findAll();

  // Automatically accessible via http://127.0.0.1:8080/api/foos/{id}
  //Foo findOne(UUID id);

  // Automatically accessible via http://127.0.0.1:8080/api/foos/search/findById?id={id}
  // Note that Spring Data Rest's hypermedia documents it as: 
  // http://127.0.0.1:8080/api/foos/search/findById{?id}"
  Foo findById(@Param("id") UUID id);
}
