package com.fastorial.todo.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fastorial.todo.model.Task;
import com.fastorial.todo.types.Priority;
import com.fastorial.todo.types.Status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class TestTaskRepo {

	@Autowired
	private TaskRepo repo;

	@Test
	@Order(1)
	public void testTaskCreation() {

		Task savedTask = repo.save(getSampleTask());
		assertThat(savedTask).isNotNull();
		assertThat(savedTask.getId()).isEqualTo(1); // assuming that this is the first entity in the DB
		assertThat(savedTask.getTitle()).isEqualTo("Study Spring Boot");

	}

	@Test
	public void testTaskDeletion() {

		// Create a new task
		Task newTask = repo.save(getSampleTask());
		// Get its ID
		long newTaskId = newTask.getId();
		// Delete the task
		repo.delete(newTask);
		// Check if task with that ID exists
		Optional<Task> optionalTaskAfterDeletion = repo.findById(newTaskId);

		assertThat(optionalTaskAfterDeletion.isEmpty()).isTrue();
	}

	@Test
	public void testFindById() {
		// Create a new task
		Task newTask = repo.save(getSampleTask());
		// Get its ID
		long newTaskId = newTask.getId();
		// Find byId
		Optional<Task> optionalTask = repo.findById(newTaskId);
		// Make sure it is present
		assertThat(optionalTask.isPresent()).isTrue();
		assertThat(optionalTask.get().getTitle()).isEqualTo("Study Spring Boot");
	}

	@Test
	public void testFindAll() {
		int NUMBER_OF_TASKS_TO_CREATE = 3;
		// Create 3 new tasks
		for (int i = 0; i < NUMBER_OF_TASKS_TO_CREATE; i++) {
			repo.save(getSampleTask());
		}
		// Find all tasks
		List<Task> allTasks = repo.findAll();
		// Make sure that we found 3 tasks
		assertThat(allTasks.size()).isEqualTo(NUMBER_OF_TASKS_TO_CREATE);

		// Check if all tasks have the same title
		allTasks //
				.stream() //
				.forEach //
				(aTask -> {
					assertThat( //
							aTask //
									.getTitle() //
					) //
							.isEqualTo("Study Spring Boot");
				});
	}

	@Test
	public void testUpdateTask() {
		// Create a Task
		Task newTask = repo.save(getSampleTask());
		// Check if original title is correct
		assertThat(newTask.getTitle()).isEqualTo("Study Spring Boot");
		// Update its title
		newTask.setTitle("Study Spring Boot Nicely");
		Task updatedTask = repo.save(newTask);
		// Check if the title is updated
		assertThat(updatedTask.getTitle()).isEqualTo("Study Spring Boot Nicely");
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
