package com.fastorial.todo.dto;

import java.sql.Timestamp;

import com.fastorial.todo.model.Task;
import com.fastorial.todo.types.Priority;
import com.fastorial.todo.types.Status;

import jakarta.validation.constraints.NotBlank;

public class TaskRequest {
	@NotBlank(message = "Title is mandatory for Task")
	String title;
	String description;
	// TODO: This needs a custom validator. Will be done sometime later when doing
	// global validation
	Status status;
	// TODO: This needs a custom validator. Will be done sometime later when doing
	// global validation
	Priority priority;
	// TODO: This needs a custom validator. Will be done sometime later when doing
	// global validation
	Timestamp dueDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	public static Task buildFromTaskCreationRequest(TaskRequest req) {
		Task task = new Task();

		task.setTitle(req.getTitle());
		task.setDescription(req.getDescription());
		task.setPriority(req.getPriority());
		task.setStatus(req.getStatus());
		task.setDueDate(req.getDueDate());

		return task;
	}

	public static TaskRequest buildFromTaskEntity(Task task) {
		TaskRequest req = new TaskRequest();

		req.setTitle(task.getTitle());
		req.setDescription(task.getDescription());
		req.setPriority(task.getPriority());
		req.setStatus(task.getStatus());
		req.setDueDate(task.getDueDate());

		return req;
	}

}
