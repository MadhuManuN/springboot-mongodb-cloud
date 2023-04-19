package com.zkteco.mongodb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zkteco.mongodb.exception.TodoCollectionException;
import com.zkteco.mongodb.model.TodoDTO;

import jakarta.validation.ConstraintViolationException;

@Service
public interface TodoService {

	public void createTodo(TodoDTO todoDTO) throws ConstraintViolationException, TodoCollectionException;
	
	public List<TodoDTO> getAllTodos();
	
	public TodoDTO getSingleTodo(String id) throws TodoCollectionException;
	
	public void updateTodo(String id,TodoDTO todoDTO) throws TodoCollectionException;
	
	public void deleteById(String id) throws TodoCollectionException;
}
