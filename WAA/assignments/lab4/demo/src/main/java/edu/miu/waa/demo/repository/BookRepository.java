package edu.miu.waa.demo.repository;

import edu.miu.waa.demo.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BookRepository {

    private Map<Integer, Book> books = new HashMap<>();

    public void saveOrUpdate(Book book){
        books.put(book.getIsbn(),book);
    }
    public void delete(Integer isbn){
        books.remove(isbn);
    }
    public Map<Integer,Book> findAll(){
        return books;
    }
}
