package org.openlmis.example.web;

import org.openlmis.example.domain.Book;
import org.openlmis.example.repository.PagingAndSortingBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
public class BookController extends BaseController {

  @Autowired
  private PagingAndSortingBookRepository repository;

  @RequestMapping(path = "/books", method = RequestMethod.GET)
  public ResponseEntity<?> getBooks()
  {
    Iterable<Book> products = repository.findAll();
    if(products == null || !products.iterator().hasNext()) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    else {
      return new ResponseEntity(products, HttpStatus.OK);
    }
  }

  @RequestMapping(path = "/books/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getBookById(@PathVariable("id") UUID id)
  {
    Book book = repository.findOne(id);
    if(book == null) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    else {
      return new ResponseEntity(book, HttpStatus.OK);
    }
  }

  @RequestMapping(path = "/books", method = RequestMethod.POST)
  public ResponseEntity<?> createBook(@RequestBody Book book)
  {
    //The standard data validation omitted in this example would typically go here

    Book savedBook = repository.save(book);
    return new ResponseEntity(savedBook, HttpStatus.CREATED);
  }

  private static void setLastModifiedHeader()
  {
    HttpHeaders headers = new HttpHeaders();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EE, dd MMM yyyy HH:mm:ss z");
    headers.add("Last-Modified" , dateFormat.format(ZonedDateTime.now()));
  }

}
