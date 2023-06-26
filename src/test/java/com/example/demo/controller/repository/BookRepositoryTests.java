package com.example.demo.controller.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.example.demo.entity.Book;
import com.example.demo.entity.QBook;
import com.example.demo.repository.BookRepositoty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BookRepositoryTests {
    @Autowired
    private BookRepositoty BookRepositoty;



    // Querydsl을 사용하여 특정 검색어를 갖는 행들만 선택
    @Test
    public void testQuery1(){
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QBook qBook = QBook.book;
        String keyword = "7";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qBook.title.contains(keyword);
        builder.and(expression);
        Page<Book> result = BookRepositoty.findAll(builder, pageable);
        result.stream().forEach(Book -> {
            System.out.println(Book);
        });
    }

    // 다중 검색어 테스트
    @Test
    public void testQuery2(){
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("gno").ascending());
        QBook qBook = QBook.book;
        String keyword = "7";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expTitle = qBook.title.contains(keyword);
        BooleanExpression expWriter = qBook.author.contains(keyword);
        BooleanExpression expAll = expTitle.or(expWriter); //and 둘다 포함 or은 둘중 하나만 포함
        builder.and(expAll);
        builder.and(qBook.gno.gt(50L));
        Page<Book> result = BookRepositoty.findAll(builder, pageable);
        result.stream().forEach(Book -> {
            System.out.println(Book);
        });
    }



    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1, 300).forEach(i -> {

            Book book = Book.builder()
                    .title("Title....." + i)
                    .author("Author....." +i)
                    .type("type" + (i % 10))
                    .build();
            System.out.println(BookRepositoty.save(book));
        });
    }

    @Test
    public void updateTest(){
        Optional<Book> result = BookRepositoty.findById(300L);

        if(((Optional<?>) result).isPresent()){
            Book Book = result.get();
            Book.changeTitle("Changed Title...");
            Book.changeAuthor("Changed Author...");
            BookRepositoty.save(Book);
        }

    }
}
