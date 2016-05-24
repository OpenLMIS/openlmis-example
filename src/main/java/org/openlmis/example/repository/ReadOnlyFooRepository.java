package org.openlmis.example.repository;

import org.openlmis.example.domain.Foo;
import org.springframework.data.repository.Repository;

public interface ReadOnlyFooRepository extends Repository<Foo, Long> {
    
    long count();
    
    boolean exists(Long id);
    
    Iterable<Foo> findAll();
    
    Iterable<Foo> findAll(Iterable<Long> ids);

    Foo findOne(Long id);
}
