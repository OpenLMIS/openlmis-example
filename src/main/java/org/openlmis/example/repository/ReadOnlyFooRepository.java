package org.openlmis.example.repository;

import org.openlmis.example.domain.Foo;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ReadOnlyFooRepository extends Repository<Foo, Long> {
  // Spring Data Rest doesn't automatically create an endpoint for this - see comments 
  // in NotificationController.java.
  long count();

  // Spring Data Rest doesn't automatically create an endpoint for this member.
  boolean exists(Long id);

  // Automatically accessible via http://127.0.0.1:8080/api/foos/
  // Interestingly, without this entry, Spring Data Rest returns a 405 (Method Not Allowed) 
  // rather than a 404 (Not Found) response status.
  Iterable<Foo> findAll();

  // Automatically accessible via http://127.0.0.1:8080/api/foos/{id}
  Foo findOne(Long id);

  // Automatically accessible via http://127.0.0.1:8080/api/foos/search/findById?id={id}
  // Note that Spring Data Rest's hypermedia documents it as: 
  // http://127.0.0.1:8080/api/foos/search/findById{?id}"
  Foo findById(@Param("id") Long id);
}
