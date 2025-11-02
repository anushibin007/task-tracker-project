package com.fastorial.todo.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fastorial.todo.dto.TaskRequest;
import com.fastorial.todo.model.Task;
import com.fastorial.todo.repo.TaskRepo;
import com.fastorial.todo.types.Status;

@Service
public class TaskService {

	TaskRepo repo;

	public TaskService(TaskRepo repo) {
		this.repo = repo;
	}

	public Task createTask(TaskRequest req) {

		Task incomingTask = TaskRequest.buildFromTaskCreationRequest(req);

		incomingTask.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		incomingTask.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

		Task savedTask = repo.save(incomingTask);

		return savedTask;
	}

	public Optional<Task> getTaskById(Long id) {
		Optional<Task> optionalTask = repo.findById(id);
		return optionalTask;
	}

	public Task fullUpdateTaskByID(Long id, TaskRequest req) {

		// First check if a Task exists for the given ID
		Optional<Task> optionalTask = getTaskById(id);

		if (optionalTask.isEmpty()) {
			throw new IllegalArgumentException("No Task found with ID " + id);
		}

		Task oldTask = optionalTask.get();

		// If it exists, update it
		Task newTask = TaskRequest.buildFromTaskCreationRequest(req);

		// migrate values from old entity to new
		newTask.setId(oldTask.getId());
		newTask.setCreatedAt(oldTask.getCreatedAt());

		// update time metadata
		newTask.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

		Task savedTask = repo.save(newTask);

		return savedTask;
	}

	public Task updateTaskStatusById(Long id, Status status) {
		// First check if a Task exists for the given ID
		Optional<Task> optionalTask = getTaskById(id);

		if (optionalTask.isEmpty()) {
			throw new IllegalArgumentException("No Task found with ID " + id);
		}

		Task task = optionalTask.get();

		task.setStatus(status);
		task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

		Task updatedTask = repo.save(task);

		return updatedTask;
	}

	public Task deleteById(Long id) {
		// First check if a Task exists for the given ID
		Optional<Task> optionalTask = getTaskById(id);

		if (optionalTask.isEmpty()) {
			return null;
		}

		Task task = optionalTask.get();

		repo.delete(task);

		return task;
	}

}
