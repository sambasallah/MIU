package edu.miu.waa.demo.controller;

import edu.miu.waa.demo.domain.Book;
import edu.miu.waa.demo.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody @Valid Book book) {
        try {
            return new ResponseEntity(bookService.addBook(book), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody @Valid Book book){
        try {
            return new ResponseEntity(bookService.updateBook(book), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Book> deleteBook(@PathVariable Integer isbn){
        try {
            return new ResponseEntity(bookService.delete(isbn), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable Integer isbn){
        try {
            return new ResponseEntity(bookService.findByIsbn(isbn), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Book>> getAllBooks(){
        return new ResponseEntity<>(bookService.getAllBooks().values(), HttpStatus.OK);
    }
    @GetMapping("/author/{author}")
    public ResponseEntity<Collection<Book>> searchBooks(@PathVariable String author){
        return new ResponseEntity(bookService.findByAuthor(author), HttpStatus.OK);
    }
}
