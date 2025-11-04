package com.fastorial.todo.util;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class HelperFunctions {
	/**
	 * Converts long timestamp (created using functions like
	 * {@link System#currentTimeMillis()} into the format
	 * 2025-11-04T17:23:35.475+00:00
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeStampAsString(long timestamp) {
		// Note: This should be mandatory 5x's. Otherwise, you will get 'Z' appended
		// instead of 00.00
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSxxxxx");
		OffsetDateTime odt = OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+00:00"));
		String formatted = odt.format(formatter);
		System.out.println("timeStampAsString = " + formatted);
		return formatted;
	}
}
