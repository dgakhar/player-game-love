package com.comeon.group.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
		String message,
		String friendlyMessage,
		int status, 
		String error, 
		LocalDateTime timestamp) {
}