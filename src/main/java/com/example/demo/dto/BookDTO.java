package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
    private Long gno;
    private String title;
    private String author;
    private String type;
    private LocalDateTime regDate, modDate;

}
