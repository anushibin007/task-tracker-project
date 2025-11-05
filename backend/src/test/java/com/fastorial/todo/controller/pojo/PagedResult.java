package com.fastorial.todo.controller.pojo;

import java.util.List;

import com.fastorial.todo.dto.TaskDto;

public class PagedResult {
	int totalElements;
	List<TaskDto> content;

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public List<TaskDto> getContent() {
		return content;
	}

	public void setContent(List<TaskDto> content) {
		this.content = content;
	}

}
