package com.fastorial.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fastorial.todo.dto.TaskRequest;
import com.fastorial.todo.model.Task;
import com.fastorial.todo.repo.TaskRepo;
import com.fastorial.todo.types.Priority;
import com.fastorial.todo.types.Status;

@ExtendWith(MockitoExtension.class)
public class TestTaskService {

	@Mock
	private TaskRepo repo;

	@InjectMocks
	private TaskService svc;

	@Test
	void testSaveTask() {
		// Mock the repo save function
		Task taskToInsert = getSampleTask();
		when(repo.save(any(Task.class))).thenReturn(taskToInsert);

		// We run actual test
		Task newTask = svc.createTask(TaskRequest.buildFromTaskEntity(taskToInsert));

		assertThat(newTask.getTitle()).isEqualTo("Study Spring Boot");
	}

	@Test
	void testGetTaskById() {
		// Mock the repo's findById function
		Task taskToInsert = getSampleTask();
		when(repo.findById(any(Long.class))).thenReturn(Optional.of(taskToInsert));
		// Do the actual get and test equality
		Optional<Task> optionalTask = svc.getTaskById(1234L);
		assertThat(optionalTask.isEmpty()).isFalse();
		assertThat(optionalTask.get().getTitle()).isEqualTo("Study Spring Boot");
	}

	@Test
	void testFindAllTasks() {
		// Build the mock value
		List<Task> listOfTasks = List.of(getSampleTask(), getSampleTask());
		Page<Task> allTasks = new PageImpl<Task>(listOfTasks);

		// Mock the repo layer
		when(repo.findAll(any(Pageable.class))).thenReturn(allTasks);

		// Run the test cases
		Page<Task> pageOfTasksResponse = svc.getAllTasks(Pageable.unpaged());
		assertThat(pageOfTasksResponse.getSize()).isEqualTo(listOfTasks.size());
	}

	@Test
	void testDeleteById() {
		// Build a mock value
		Task taskToDelete = getSampleTask();
		// Mock the repo layer
		when(repo.findById(any(Long.class))).thenReturn(Optional.of(taskToDelete));
		doNothing().when(repo).delete(taskToDelete);
		// Test the svc layer
		Task deletedItem = svc.deleteById(1234L);
		assertThat(deletedItem.getTitle()).isEqualTo(taskToDelete.getTitle());

	}

	private Task getSampleTask() {
		Task sampleTask = new Task();
		sampleTask.setTitle("Study Spring Boot");
		sampleTask.setDescription("Study from Fastorial");
		sampleTask.setPriority(Priority.HIGH);
		sampleTask.setStatus(Status.TODO);
		sampleTask.setDueDate(new Timestamp(System.currentTimeMillis()));
		sampleTask.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		sampleTask.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return sampleTask;
	}
}
