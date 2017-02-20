package org.openlmis.example.web;

import org.openlmis.example.domain.Book;
import org.openlmis.example.repository.PagingAndSortingBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller handling the /books resource.
 */
@RestController
public class BookController extends BaseController {

  @Autowired
  private PagingAndSortingBookRepository repository;

  /**
   * Returns all books from the system.
   * @return the list of books
   */
  @RequestMapping(path = "/books", method = RequestMethod.GET)
  @ResponseBody
  public Iterable<Book> getBooks() {
    return repository.findAll();
  }

  /**
   * Find a book by it's ID.
   * @param id the id of the book
   * @return response entity containing either the found book,
   *         or a 404 response if the book is not found
   */
  @RequestMapping(path = "/books/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getBookById(@PathVariable("id") UUID id) {
    Book book = repository.findOne(id);
    if (book == null) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(book, HttpStatus.OK);
    }
  }

  /**
   * Creates a new book.
   * @param book the book to create
   * @return the created book
   */
  @RequestMapping(path = "/books", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public Book createBook(@RequestBody Book book) {
    //The standard data validation omitted in this example would typically go here
    return repository.save(book);
  }
}
