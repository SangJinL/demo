package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResultDTO;
import com.example.demo.entity.Book;
import com.example.demo.entity.QBook;
import com.example.demo.repository.BookRepositoty;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepositoty repositoty;
    @Override
    public Long register(BookDTO dto) {
        Book entity = dtoToEntity(dto);
        log.info(entity);
        repositoty.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<BookDTO, Book> getList(PageRequestDTO requestDTO) {
        PageRequest pageable = requestDTO.getPageable(Sort.by("gno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<Book> result = repositoty.findAll(booleanBuilder, pageable);
        Function<Book, BookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BookDTO read(Long gno) {
        Optional<Book> result = repositoty.findById(gno);
        return result.isPresent()? entityToDto(result.get()): null;
    }

    @Override
    public void modify(BookDTO dto) {
        Optional<Book> result = repositoty.findById(dto.getGno());
        if(result.isPresent()){
            Book entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeAuthor(dto.getAuthor());
            repositoty.save(entity);
        }
    }

    @Override
    public void remove(Long gno) {
        repositoty.deleteById(gno);
    }

    // 검색 조회 기능은 QueryDSL을 사용한다.
    @Override
    public BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBook qBook = QBook.book;
        BooleanExpression expression = qBook.gno.gt(0L);

        booleanBuilder.and(expression);

        // 검색형식이 선택되어 있지 않은 경우
        if (type == null || type.trim().length()==0){
            return booleanBuilder;
        }

        // 검색조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")){
            conditionBuilder.or(qBook.title.contains(keyword));
        }
        if (type.contains("c")){
            conditionBuilder.or(qBook.author.contains(keyword));
        }
        if (type.contains("w")){
            conditionBuilder.or(qBook.type.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}