package com.cardpaymentsystem.tokenservice.exception;

import lombok.Getter;

@Getter
public class ApiCallException extends Exception {

	private final int status;

	public ApiCallException(String message, int status) {

		super(message);
		this.status = status;
	}
}
