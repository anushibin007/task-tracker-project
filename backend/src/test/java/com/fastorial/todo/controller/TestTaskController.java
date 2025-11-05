package com.fastorial.todo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastorial.todo.controller.pojo.PagedResult;
import com.fastorial.todo.dto.TaskDto;
import com.fastorial.todo.dto.TaskRequest;
import com.fastorial.todo.types.Priority;
import com.fastorial.todo.types.Status;
import com.fastorial.todo.util.HelperFunctions;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTaskController {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	TaskDto testCreateTodo() throws Exception {
		long dueDateTimestamp = System.currentTimeMillis();
		TaskRequest reqObj = getSampleTaskRequest(dueDateTimestamp);

		String jsonStringResponse = mockMvc.perform(post("/api/tasks") //
				.contentType(MediaType.APPLICATION_JSON) //
				.content(objectMapper.writeValueAsString(reqObj))) //
				.andExpect(status().isCreated()) // 201
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)) //
				.andExpect(jsonPath("$.title").value("Study Spring Boot")) //
				.andExpect(jsonPath("$.description").value("Study from Fastorial"))
				.andExpect(jsonPath("$.priority").value("HIGH"))
				//
				.andExpect(jsonPath("$.status").value("TODO")) //
				.andExpect(jsonPath("$.dueDate").value(HelperFunctions.timeStampAsString(dueDateTimestamp)))//
				.andReturn().getResponse().getContentAsString();

		TaskDto responseTaskObject = objectMapper.readValue(jsonStringResponse, TaskDto.class);

		return responseTaskObject;
	}

	@Test
	void testDeleteTodo() throws Exception {
		// Create a Task
		TaskDto createdTask = testCreateTodo();
		System.out.println("Going to delete Task with ID " + createdTask.getId());
		// Delete the task object
		mockMvc.perform(delete("/api/tasks/" + createdTask.getId()).contentType(MediaType.APPLICATION_JSON))//
				// Expect a 204 status code
				.andExpect(status().isNoContent()) // 204
				.andExpect(jsonPath("$.id").value(createdTask.getId()));
	}

	@Test
	void testFindByIdSuccess() throws Exception {
		// Create a Task
		TaskDto createdTask = testCreateTodo();
		// Get its ID
		Long id = createdTask.getId();
		// Find by ID
		mockMvc.perform(get("/api/tasks/" + id).contentType(MediaType.APPLICATION_JSON)) //
				.andExpect(status().isOk()) // 200
				// Match its ID
				.andExpect(jsonPath("$.id").value(id))
				// Match its title
				.andExpect(jsonPath("$.title").value(createdTask.getTitle()));
	}

	@Test
	void testFindByIdFail() throws Exception {
		mockMvc.perform(get("/api/tasks/63636") // arbitrary non-existent ID
				.contentType(MediaType.APPLICATION_JSON)) //
				.andExpect(status().isNotFound()) // 404
				.andExpect(jsonPath("$.status").value(404)) //
				.andExpect(jsonPath("$.message").value("Task with id 63636 is not found")) //
				.andExpect(jsonPath("$.path").value("/api/tasks/63636"));
	}

	@Test
	void testUpdateTaskStatus() throws Exception {
		// Create a Task (which will have TODO as its status)
		TaskDto createdTask = testCreateTodo();
		Long id = createdTask.getId();
		// Building a dummy request with status as DONE
		TaskRequest requestForDoneStatus = new TaskRequest();
		requestForDoneStatus.setStatus(Status.DONE);
		// Call the update API and set the status to DONE
		mockMvc.perform(patch("/api/tasks/" + id + "/status") //
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestForDoneStatus))) //
				.andExpect(status().isOk()) // 200
				// match the response ID with request ID
				.andExpect(jsonPath("$.id").value(id))
				// Check if the status in the response is DONE
				.andExpect(jsonPath("$.status").value("DONE"));
	}

	@Test
	void testUpdateFullTask() throws Exception {
		// Create a Task
		TaskDto createdTask = testCreateTodo();
		// Get the ID of the new task
		Long id = createdTask.getId();
		// Build a new TaskRequest to represent the values to update
		TaskRequest req = new TaskRequest();
		req.setTitle("Updated title");
		req.setDescription("Updated description");
		req.setPriority(Priority.LOW);
		req.setStatus(Status.DONE);
		req.setDueDate(createdTask.getDueDate());
		// Perform update
		mockMvc.perform(put("/api/tasks/" + id) //
				.contentType(MediaType.APPLICATION_JSON) //
				.content(objectMapper.writeValueAsString(req)) //
		) //
				.andExpect(status().isOk()) //
				// Test ID matches
				.andExpect(jsonPath("$.id").value(id)) //
				// Compare all other fields
				.andExpect(jsonPath("$.title").value("Updated title")) //
				.andExpect(jsonPath("$.description").value("Updated description")) //
				.andExpect(jsonPath("$.priority").value("LOW"))
				//
				.andExpect(jsonPath("$.status").value("DONE")) //
				// These 2 date fields should be unaltered
				.andExpect(jsonPath("$.dueDate").value(HelperFunctions.timeStampAsString(createdTask.getDueDate()))) //
				.andExpect(jsonPath("$.createdAt").value(HelperFunctions.timeStampAsString(createdTask.getCreatedAt()))) //
				// The updated time should not match the original value
				.andExpect(jsonPath("$.updatedAt",
						not(is(HelperFunctions.timeStampAsString(createdTask.getUpdatedAt())))));
	}

	@Test
	@Order(1)
	void testGetAllTasks() throws Exception {
		// Create 2 Tasks
		testCreateTodo();
		testCreateTodo();

		// Perform GET all API and get the MvcResult
		MvcResult andReturn = mockMvc.perform(get("/api/tasks").content(MediaType.APPLICATION_JSON_VALUE)).andReturn(); //

		// Build the PagedResult object
		String jsonResponse = andReturn.getResponse().getContentAsString();
		System.out.println(jsonResponse);
		PagedResult resultObject = objectMapper.readValue(jsonResponse, PagedResult.class);

		// Run tests on the PagedResult object
		assertThat(resultObject.getTotalElements()).isEqualTo(2);
		assertThat(resultObject.getContent().size()).isEqualTo(2);

		// Just make sure that all tasks have the expected title
		resultObject.getContent().stream() //
				.forEach(taskDto -> assertThat(taskDto.getTitle()).isEqualTo("Study Spring Boot"));
	}

	private TaskRequest getSampleTaskRequest(long dueDateTimeStamp) {
		TaskRequest sampleTaskRequest = new TaskRequest();
		sampleTaskRequest.setTitle("Study Spring Boot");
		sampleTaskRequest.setDescription("Study from Fastorial");
		sampleTaskRequest.setPriority(Priority.HIGH);
		sampleTaskRequest.setStatus(Status.TODO);
		sampleTaskRequest.setDueDate(new Timestamp(dueDateTimeStamp));
		return sampleTaskRequest;
	}

}
