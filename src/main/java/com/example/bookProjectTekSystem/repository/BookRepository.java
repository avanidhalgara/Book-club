package com.example.bookProjectTekSystem.repository;

import com.example.bookProjectTekSystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    //    declare a custom method to find all products by category id
    List<Book> findAllByCategory_Id(int id);
}
