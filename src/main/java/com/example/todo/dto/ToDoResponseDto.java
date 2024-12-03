package com.example.todo.dto;

import com.example.todo.entity.ToDo;
import lombok.Getter;

/**
 * 클라이언트에게 데이터를 반환할 때 사용하는 ToDoResponseDto 클래스
 * 일정 조회, 생성 완료 응답, 수정 완료 응답 시 사용
 * <p>
 * - id : 일정의 고유 식별자를 반환
 * - title : 일정의 제목을 반환
 * - contents : 일정의 내용을 반환
 * </p>
 *
 * Lombok 애너테이션
 *- @Getter : 모든 필드에 대한 Getter 메서드를 자동 생성
 */
@Getter
public class ToDoResponseDto {
    private Long id;
    private String title;
    private String contents;

    public ToDoResponseDto(ToDo todo) { // 매개변수로 Todo 객체를 그대로 받음
        id = todo.getId();
        title = todo.getTitle();
        contents = todo.getContents();
    }
}
