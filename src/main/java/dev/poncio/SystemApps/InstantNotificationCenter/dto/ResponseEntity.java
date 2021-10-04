package dev.poncio.SystemApps.InstantNotificationCenter.dto;

import lombok.Getter;

public class ResponseEntity<T> {

	@Getter
	private Boolean status = true;
	@Getter
	private Integer code = 200;
	@Getter
	private String message;
	@Getter
	private T attach;

	public ResponseEntity<T> status(Boolean status) {
		this.status = status;
		return this;
	}

	public ResponseEntity<T> code(Integer code) {
		this.code = code;
		return this;
	}

	public ResponseEntity<T> message(String message) {
		this.message = message;
		return this;
	}

	public ResponseEntity<T> attach(T attach) {
		this.attach = attach;
		return this;
	}

}
