package com.zkteco.mongodb.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkteco.mongodb.exception.TodoCollectionException;
import com.zkteco.mongodb.model.TodoDTO;
import com.zkteco.mongodb.repository.TodoRepository;
import com.zkteco.mongodb.service.TodoService;

import jakarta.validation.ConstraintViolationException;

@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	private TodoRepository todoRepository;

	@Override
	public void createTodo(TodoDTO todoDTO) throws ConstraintViolationException,TodoCollectionException {
		Optional<TodoDTO> todoDb=todoRepository.findByTodo(todoDTO.getTodo());
		if(todoDb.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
		}
		else {
			todoDTO.setCreateAt(new Date(System.currentTimeMillis()));
			todoRepository.save(todoDTO);
		}
	}

	@Override
	public List<TodoDTO> getAllTodos() {
		List<TodoDTO> todos=todoRepository.findAll();
		if(!todos.isEmpty()) {
			return todos;
		}
		else {
			return new ArrayList<TodoDTO>();
		}
	}

	@Override
	public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
		Optional<TodoDTO> todoDb =todoRepository.findById(id);
		if(!todoDb.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}else {
			return todoDb.get();
		}
	}

	@Override
	public void updateTodo(String id, TodoDTO todoDTO) throws TodoCollectionException {
		Optional<TodoDTO> todoDb =todoRepository.findById(id);
		Optional<TodoDTO> todoDbByName=todoRepository.findByTodo(todoDTO.getTodo());
		if(todoDb.isPresent()) {
			if(todoDbByName.isPresent() && todoDbByName.get().getId().equals(id)) {
				throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
			}
			TodoDTO todoUpdate=todoDb.get();
			todoUpdate.setCompleted(todoDTO.getCompleted());
			todoUpdate.setTodo(todoDTO.getTodo());
			todoUpdate.setDescription(todoDTO.getDescription());
			todoUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
			todoRepository.save(todoUpdate);
		}else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
	}

	@Override
	public void deleteById(String id) throws TodoCollectionException {
		Optional<TodoDTO> todoDb =todoRepository.findById(id);
		if(!todoDb.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
		else {
			todoRepository.deleteById(id);
		}
	}

}
