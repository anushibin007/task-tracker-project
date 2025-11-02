package com.fastorial.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastorial.todo.model.Task;

public interface TaskRepo extends JpaRepository<Task, Long> {

}
