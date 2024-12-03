package com.example.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 일정 관리 애플리케이션의 ToDo Entity 클래스
 * 이 클래스는 데이터베이스의 ToDo 테이블과 매핑되며 일정 데이터를 표현한다.
 *
 * <p>
 *     - id : 일정의 고유 식별자
 *     - title : 일정의 제목
 *     - contentes : 일정의 내용
 * </p>
 *
 * Lombok 애너테이션
 * - @Getter : 모든 필드에 대한 Getter 메서드를 자동 생성
 * - @AllArgsConstructor : 모든 필드를 초기화하는 생성자를 자동 생성
 */

@Getter
@AllArgsConstructor
public class ToDo {
    private Long id;
    private String title;
    private String contents;

}
