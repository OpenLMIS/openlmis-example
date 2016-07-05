package org.openlmis.example.repository;

import org.openlmis.example.domain.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface PagingAndSortingBookRepository extends PagingAndSortingRepository<Book, UUID> {
}
