package com.example.demo.controller.repository.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResultDTO;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceTests {
    @Autowired
    private BookService service;

    @Test
    public void testRegister(){
        BookDTO guestBookDTO = BookDTO.builder()
                .title("Sample Title...")
                .author("Sample author...")
                .type("userSample")
                .build();

        System.out.println(service.register(guestBookDTO));
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResultDTO<BookDTO, Book> resultDTO = service.getList(pageRequestDTO);
        System.out.println("prev: " + resultDTO.isPrev());
        System.out.println("next: " + resultDTO.isNext());
        System.out.println("total: " + resultDTO.getTotalPage());
        System.out.println("===================================");
        for (BookDTO BookDTO : resultDTO.getDtoList()){
            System.out.println(BookDTO);
        }
        resultDTO.getDtoList().forEach(i -> System.out.println(i));
    }
}
