package edu.miu.waa.demo.service;

import edu.miu.waa.demo.domain.Book;
import edu.miu.waa.demo.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    public Book addBook(Book book) throws Exception {
        if(getAllBooks().containsKey(book.getIsbn()))
            throw new Exception("Isbn "+book.getIsbn()+" already exists");
        bookRepository.saveOrUpdate(book);
        return book;
    }

    public Book updateBook(Book book) throws Exception {
        if(getAllBooks().containsKey(book.getIsbn())) {
            bookRepository.saveOrUpdate(book);
            return book;
        }
        throw new Exception("Book doesn't exist");
    }

    public String delete(Integer isbn) throws Exception {
        if(getAllBooks().containsKey(isbn)) {
            bookRepository.delete(isbn);
            return "Book with isbn "+isbn+ " deleted";
        }
        throw new Exception("Isbn "+isbn+" doesn't exist, then cannot be deleted!");
    }

    public Map<Integer,Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book findByIsbn(Integer isbn) throws Exception {
        Book book = getAllBooks().get(isbn);
        if(book == null)
            throw new Exception("Book doesn't exist");
        return book;
    }

    public Collection<Book> findByAuthor(String author){
        return getAllBooks().values().stream().filter(b -> b.getAuthor().equals(author)).collect(Collectors.toList());
    }
}
