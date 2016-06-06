package org.openlmis.example.repository;

import org.openlmis.example.domain.Bar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;


public interface PagingAndSortingBarRepository extends PagingAndSortingRepository<Bar, Long>
{
    /*
        As the name implies, extending PagingAndSortingRepository rather than CrudRepository
        will expose additional endpoints useful for paging and sorting. By default, the response
        to a request for all resources will include only twenty of them. Examples of request URIs include:
        
        http://127.0.0.1:8080/api/bars?page=0&size=50
        http://127.0.0.1:8080/api/bars?page=0&size=50&sort=priority
    */
}
