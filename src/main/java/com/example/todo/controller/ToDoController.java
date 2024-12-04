package com.example.todo.controller;

import com.example.todo.dto.ToDoRequestDto;
import com.example.todo.dto.ToDoResponseDto;
import com.example.todo.entity.ToDo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 일정 관리 애플리케이션의 ToDoController 클래스
 * 일정 관리와 관련된 HTTP 요청을 처리
 *
 * /todos 엔드포인트를 통해 클라이언트와 통신
 * Map 자료구조를 In-memory 데이터베이스로 사용하여 일정 데이터 저장 ( 임시 활용 )
 */

@RestController // 데이터의 형태를 JSON으로 일일이 바꿔주지 않아도 RestController 에서 자동으로 JSON 형태로 반환해주기 때문에
@RequestMapping("/todos") // 공통으로 들어가는 /todos url
public class ToDoController {
    // 자료구조 Map을 임시 데이터베이스로 사용
    private final Map<Long, ToDo> todoList = new HashMap<>(); // Map은 인터페이스이기 때문에 구현체인 HashMap<>()을 사용해서 초기화

    // 일정 생성 기능 - 상태코드 추가로 성공 시 201 OK 상태코드 출력
    @PostMapping
    public ResponseEntity<ToDoResponseDto> createTodo(@RequestBody ToDoRequestDto requestDto) { //createTodo() : 클라이언트로부터 전달받은 데이터를 기반으로 새 일정을 생성하고 생성된 데이터를 클라이언트에 반환
        // 중복이 되지 않도록 식별자가 1씩 증가하도록 만든다.
        Long todoId = todoList.isEmpty() ? 1 : Collections.max(todoList.keySet()) + 1;

        // 요청받은 데이터로 Todo 객체 생성
        ToDo todo = new ToDo(todoId, requestDto.getTitle(), requestDto.getContents());

        // In-memory DB에 Todo 일정 저장 : 실제 데이터베이스를 연결하지 않고 Map 자료구조를 사용함 -> 프로젝트가 실행되었다가 종료될 때 모든 메모리가 삭제됨
        todoList.put(todoId, todo);

        return new ResponseEntity<>(new ToDoResponseDto(todo), HttpStatus.CREATED); // 매개변수로 받은 todo 객체를 ResponseDto 형태로 반환, ResponseEntity를 이용해 생성에 성공하면 HTTPSTATUS Enum값의 201 상태코드 함께 출력
    }

    // 일정 조회 기능 - 상태코드 추가로 성공 시 200 OK 출력, 조회하려는 식별자의 일정이 없을 경우 404 NOT FOUND 출력
    @GetMapping("/{id}") // prefix 로 todos가 만들어져 있기 때문에 식별자를 입력해준다. 식별자를 파라미터로 바인딩 할 때 Pathvariable을 이용한다.
    public ResponseEntity<ToDoResponseDto> findTodoById(@PathVariable Long id) {
       ToDo toDo = todoList.get(id);

       // 사용자가 찾으려는 식별자의 일정이 조회되지 않을 경우 예외처리
        if (toDo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ToDoResponseDto(toDo), HttpStatus.OK);
    }

    // 일정 목록 조회 기능
    @GetMapping
    public ResponseEntity<List<ToDoResponseDto>> findAllTodos() {
        // 리스트 형태로 데이터를 응답받기 위해 init List (리스트 초기화)
        List<ToDoResponseDto> responseList = new ArrayList<>(); // List는 인터페이스이기 때문에 구현체를 사용해 초기화 해줘야 한다. (인터페이스는 new 키워드로 인스턴스화 할 수 없다.)

        //Map to List 스트림으로 코드 복잡성 낮추기
        //responseList =  todoList.values().stream().map(ToDoResponseDto::new).toList(); -> 아직 stream에 익숙치 않으니 나중에 쓰도록 한다.
        // HashMap<ToDo> -> List<ToDoResponseDto>
        for (ToDo todo : todoList.values()) {
            ToDoResponseDto responseDto = new ToDoResponseDto(todo);
            responseList.add(responseDto);
        }

        return ResponseEntity.ok(responseList); // ResponseEntity 형태로 반환해야 하므로 responseList를 그대로 ResponseEntity로 감싼다.
    }

    //일정 수정 기능
    @PutMapping("/{id}")
    public ResponseEntity<ToDoResponseDto> updateTodoById(
            @PathVariable Long id,
            @RequestBody ToDoRequestDto requestDto)
    {
        ToDo todo = todoList.get(id);

        // NullPointerException 방지
        if (todo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // 필수값 검증 (제목, 내용)
        if (requestDto.getTitle() == null || requestDto.getContents() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //일정 수정
        todo.update(requestDto);

        // 응답
        return new ResponseEntity<>(new ToDoResponseDto(todo),HttpStatus.OK);
    }

    //일정 삭제 기능
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoList.remove(id);
    }
}
