package com.fastorial.todo.dto;

import java.sql.Timestamp;

import com.fastorial.todo.model.Task;
import com.fastorial.todo.types.Priority;
import com.fastorial.todo.types.Status;

/**
 * Full representation of the Task
 */
public class TaskDto {

	Long id;
	String title;
	String description;
	Status status;
	Priority priority;
	Timestamp dueDate;
	Timestamp createdAt;
	Timestamp updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public static TaskDto buildFromTaskEntity(Task taskEntity) {
		TaskDto dto = new TaskDto();
		dto.setId(taskEntity.getId());
		dto.setTitle(taskEntity.getTitle());
		dto.setDescription(taskEntity.getDescription());
		dto.setStatus(taskEntity.getStatus());
		dto.setPriority(taskEntity.getPriority());
		dto.setDueDate(taskEntity.getDueDate());
		dto.setCreatedAt(taskEntity.getCreatedAt());
		dto.setUpdatedAt(taskEntity.getUpdatedAt());
		return dto;
	}

}
