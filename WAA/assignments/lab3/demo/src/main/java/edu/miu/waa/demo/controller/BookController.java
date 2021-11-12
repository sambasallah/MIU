package edu.miu.waa.demo.controller;

import edu.miu.waa.demo.model.Book;
import edu.miu.waa.demo.model.CustomErrorType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    private Map<Integer,Book> books;

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book){
        if(books.containsKey(book.getIsbn()))
            return new ResponseEntity(new CustomErrorType("Isbn already exists"),HttpStatus.FORBIDDEN);
        books.put(book.getIsbn(),book);
        return new ResponseEntity(book, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody Book book){
        if(books.containsKey(book.getIsbn())) {
            books.put(book.getIsbn(), book);
            return new ResponseEntity(book, HttpStatus.OK);
        }
        return new ResponseEntity(new CustomErrorType("Book doesn't exist"), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Book> deleteBook(@PathVariable Integer isbn){
        if(books.containsKey(isbn)) {
            books.remove(isbn);
            return new ResponseEntity(isbn+ " deleted", HttpStatus.OK);
        }
        return new ResponseEntity(new CustomErrorType("Isbn doesn't exist, then cannot be deleted!"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable Integer isbn){
        Book book = books.get(isbn);
        if(book == null) {
            return new ResponseEntity(new CustomErrorType("Book doesn't exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(book, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Book>> getAllBooks(){
        return new ResponseEntity<>(books.values(), HttpStatus.OK);
    }
    @GetMapping("/author/{author}")
    public ResponseEntity<Collection<Book>> searchBooks(@PathVariable String author){
        return new ResponseEntity(books.values().stream().filter(b -> b.getAuthor().equals(author)), HttpStatus.OK);
    }
}
