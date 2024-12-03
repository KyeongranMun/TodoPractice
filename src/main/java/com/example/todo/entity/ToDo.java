package com.example.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ToDo {
    private Long id;
    private String title;
    private String contents;

}
