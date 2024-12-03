package com.example.todo.controller;

import com.example.todo.dto.ToDoRequestDto;
import com.example.todo.dto.ToDoResponseDto;
import com.example.todo.entity.ToDo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 일정 관리 애플리케이션의 ToDoController 클래스
 * 일정 관리와 관련된 HTTP 요청을 처리
 *
 * /todos 엔드포인트를 통해 클라이언트와 통신
 * Map 자료구조를 In-memory 데이터베이스로 사용하여 일정 데이터 저장 ( 임시 활용 )
 */

@RestController
@RequestMapping("/todos") // 공통으로 들어가는 /todos url
public class ToDoController {
    // 자료구조 Map을 임시 데이터베이스로 사용
    private final Map<Long, ToDo> todoList = new HashMap<>();

    @PostMapping
    public ToDoResponseDto createTodo(@RequestBody ToDoRequestDto requestDto) { //createTodo() : 클라이언트로부터 전달받은 데이터를 기반으로 새 일정을 생성하고 생성된 데이터를 클라이언트에 반환
        // 중복이 되지 않도록 식별자가 1씩 증가하도록 만든다.
        Long todoId = todoList.isEmpty() ? 1 : Collections.max(todoList.keySet()) + 1;

        // 요청받은 데이터로 Todo 객체 생성
        ToDo todo = new ToDo(todoId, requestDto.getTitle(), requestDto.getContents());

        // In-memory DB에 Todo 일정 저장 : 실제 데이터베이스를 연결하지 않고 Map 자료구조를 사용함 -> 프로젝트가 실행되었다가 종료될 때 모든 메모리가 삭제됨
        todoList.put(todoId, todo);

        return new ToDoResponseDto(todo); // 매개변수로 받은 todo 객체를 ResponseDto 형태로 반환
    }
}
