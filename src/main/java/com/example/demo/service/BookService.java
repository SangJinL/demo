package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResultDTO;
import com.example.demo.entity.Book;
import com.querydsl.core.BooleanBuilder;


public interface BookService {
    Long register(BookDTO dto);
    PageResultDTO<BookDTO, Book> getList(PageRequestDTO requestDTO);
    BookDTO read(Long gno);

    void modify(BookDTO dto);
    void remove(Long gno);
    BooleanBuilder getSearch(PageRequestDTO requestDTO);

    default Book dtoToEntity(BookDTO dto){
        Book entity = Book.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .type(dto.getType())
                .build();
        return entity;
    }

    default BookDTO entityToDto(Book entity){

        BookDTO dto = BookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .type(entity.getType())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }
}