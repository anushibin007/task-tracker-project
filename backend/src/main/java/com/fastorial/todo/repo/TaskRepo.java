package com.fastorial.todo.repo;

import org.springframework.data.repository.CrudRepository;

import com.fastorial.todo.model.Task;

public interface TaskRepo extends CrudRepository<Task, Long> {

}
