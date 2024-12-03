package com.example.todo.dto;

import lombok.Getter;

/**
 * 일정 관리 애플리케이션의 ToDoRequestDto 클래스
 * <p>
 *  클라이언트로부터 전달받은 데이터를 담는 DTO
 *  새로운 일정 생성 요청이나 기존 일정 수정 시 사용
 *  -
 *  - title : 일정의 제목으로 클라이언트가 요청한 일정의 이름
 *  - contents : 클라이언트가 요청한 일정에 대한 구체적인 내용
 * </p>
 *
 * Lombok 애너테이션
 * - @Getter : 모든 필드에 대한 Getter 메서드를 자동 생성
 */

@Getter
public class ToDoRequestDto {
    private Long id;
    private String title;
    private String contents;
}
