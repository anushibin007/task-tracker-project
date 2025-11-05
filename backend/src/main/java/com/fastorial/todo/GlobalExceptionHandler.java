package com.fastorial.todo;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex,
			HttpServletRequest request) {
		return buildResponse(ex.getStatusCode().value(), ex.getReason(), request.getRequestURI());
	}

	private ResponseEntity<Map<String, Object>> buildResponse(int status, String reason, String path) {
		return ResponseEntity.status(status).body(Map.of("status", status, "message", reason, "path", path));
	}

}
