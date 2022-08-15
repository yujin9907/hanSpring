package com.study.board.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// 엔티티 = 테이블
@Entity
// 롬북의 데이터이용
@Data
public class Board {

    private String title;
    private String content;
    // 프라이머리키, INDENTITY = 오라클의 시퀀스 테이블의 설정을 처리해줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
