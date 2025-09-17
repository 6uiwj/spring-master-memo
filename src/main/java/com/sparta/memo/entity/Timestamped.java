package com.sparta.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

    @CreatedDate //최초 생성시간 자동저장
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate //변경시간 자동저장
    @Column
    @Temporal(TemporalType.TIMESTAMP) //Date, Calendar패키지 날짜 데이터를 매핑할 때 사용 (DB의 DATE, TIME, TIMESTAMP)
    private LocalDateTime modifiedAt;
}