package com.example.bookProjectTekSystem.service;


import com.example.bookProjectTekSystem.model.Book;
import com.example.bookProjectTekSystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;


    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    public void addBook(Book book) {
        bookRepository.save(book);

    }

    public void removeByBookId(long id) {
        bookRepository.deleteById((id));

    }

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    //    This method will use when user logged in and see all products by category
    public List<Book> getAllBooksByCategoryId(int id) {
        return bookRepository.findAllByCategory_Id(id);
    }


}
