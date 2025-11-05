package com.fastorial.todo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fastorial.todo.dto.TaskDto;
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

	@PostMapping("/api/tasks")
	public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskRequest taskRequest) throws URISyntaxException {

		Task createdTask = svc.createTask(taskRequest);

		return ResponseEntity.status(201).location(new URI("http://localhost/task/" + createdTask.getId()))
				.body(TaskDto.buildFromTaskEntity(createdTask));
	}

	// Client (Browser/Bruno)* -> Controller* -> Service* -> Repository* -> DB*

	@GetMapping("/api/tasks/{id}")
	public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
		Optional<Task> optionalTask = svc.getTaskById(id);

		if (optionalTask.isPresent()) {
			return ResponseEntity.ok(TaskDto.buildFromTaskEntity(optionalTask.get()));
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " is not found");
		}

	}

	@PutMapping("/api/tasks/{id}")
	public ResponseEntity<TaskDto> fullUpdateTaskByID(@PathVariable Long id, @RequestBody TaskRequest req) {
		try {
			Task updatedTask = svc.fullUpdateTaskByID(id, req);
			return ResponseEntity.ok(TaskDto.buildFromTaskEntity(updatedTask));
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " is not found");
		}
	}

	@PatchMapping("/api/tasks/{id}/status")
	public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long id, @RequestBody TaskRequest req) {
		try {
			Task updatedTask = svc.updateTaskStatusById(id, req.getStatus());
			return ResponseEntity.ok(TaskDto.buildFromTaskEntity(updatedTask));
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " is not found");
		}
	}

	@DeleteMapping("/api/tasks/{id}")
	public ResponseEntity<TaskDto> deleteTaskById(@PathVariable Long id) {
		Task deletedTaskData = svc.deleteById(id);
		return ResponseEntity.status(204).body(TaskDto.buildFromTaskEntity(deletedTaskData));
	}

	@GetMapping("/api/tasks")
	public Page<TaskDto> getAllTasks(Pageable pageable) {
		Page<Task> allTasks = svc.getAllTasks(pageable);
		Page<TaskDto> allTasksDto = allTasks.map(taskEntity -> TaskDto.buildFromTaskEntity(taskEntity));

		return allTasksDto;
	}
}
