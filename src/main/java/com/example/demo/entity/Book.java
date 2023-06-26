package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1500, nullable = false)
    private String author;

    @Column(length = 50, nullable = false)
    private String type;
    public void changeTitle(String title){
        this.title = title;
    }
    public void changeAuthor(String author){
        this.author = author;
    }

}