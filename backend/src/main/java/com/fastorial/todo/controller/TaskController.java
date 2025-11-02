package com.fastorial.todo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastorial.todo.dto.TaskRequest;
import com.fastorial.todo.model.Task;
import com.fastorial.todo.service.TaskService;

import jakarta.validation.Valid;

@RestController
public class TaskController {

	TaskService svc;

	public TaskController(TaskService svc) {
		this.svc = svc;
	}

	// TODO: Change all Task entities that are being returned to the client into DTO
	// objects

	@PostMapping("/api/tasks")
	public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest taskRequest) throws URISyntaxException {

		Task createdTask = svc.createTask(taskRequest);

		return ResponseEntity.status(201).location(new URI("http://localhost/task/" + createdTask.getId()))
				.body(createdTask);
	}

	// Client (Browser/Bruno)* -> Controller* -> Service* -> Repository* -> DB*

	@GetMapping("/api/tasks/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
		Optional<Task> optionalTask = svc.getTaskById(id);

		if (optionalTask.isPresent()) {
			return ResponseEntity.ok(optionalTask.get());
		} else {
			return ResponseEntity.status(404).body(null);
		}

	}

	@PutMapping("/api/tasks/{id}")
	public ResponseEntity<Task> fullUpdateTaskByID(@PathVariable Long id, @RequestBody TaskRequest req) {
		try {
			Task updatedTask = svc.fullUpdateTaskByID(id, req);
			return ResponseEntity.ok(updatedTask);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(404).body(null);
		}
	}

	@PatchMapping("/api/tasks/{id}/status")
	public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestBody TaskRequest req) {
		try {
			Task updatedTask = svc.updateTaskStatusById(id, req.getStatus());
			return ResponseEntity.ok(updatedTask);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(404).body(null);
		}
	}

	@DeleteMapping("/api/tasks/{id}")
	public ResponseEntity<Task> deleteTaskById(@PathVariable Long id) {
		Task deletedTaskData = svc.deleteById(id);
		return ResponseEntity.status(204).body(deletedTaskData);
	}

	@GetMapping("/api/tasks")
	public Page<Task> getAllTasks(Pageable pageable) {
		return svc.getAllTasks(pageable);
	}
}
