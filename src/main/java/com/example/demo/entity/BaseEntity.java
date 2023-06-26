package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // @EnableJpaAuditing 추가시 이용가능
@EntityListeners(value = {AuditingEntityListener.class}) // {} = 값 여러게
@Getter
public class BaseEntity {
    @CreatedDate
    @Column(name = "regDate", updatable = false) // update 수정 불가능
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "modDate", updatable = true) // Re Update 허용
    private LocalDateTime modDate;
}

