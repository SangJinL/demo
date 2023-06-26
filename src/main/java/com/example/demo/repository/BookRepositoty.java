package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BookRepositoty extends JpaRepository<Book, Long>,
        QuerydslPredicateExecutor<Book> {

}
