package org.openlmis.example.repository;

import org.openlmis.example.domain.Foo;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ReadOnlyFooRepository extends Repository<Foo, Long> {
    
    long count();
    
    boolean exists(Long id);
    
    Iterable<Foo> findAll();
    
    Iterable<Foo> findAll(Iterable<Long> ids);

    //Although this method doesn't seem to be automatically exposed via an endpoint, the alternative below it is.
    Foo findOne(Long id);
    Foo findById(@Param("id") Long id);
}
