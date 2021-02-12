package com.softwareBasket.demo.model;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class RequestHeaderLoggingModel {

	private String role;
	private String sessionId;
	private String transactionId;

	public RequestHeaderLoggingModel(HttpHeaders requestHeaders) {
		this.role = String.join(",", requestHeaders.getOrEmpty("role"));
		this.sessionId = String.join(",", requestHeaders.getOrEmpty("Session-Id"));
		this.transactionId = String.join(",", requestHeaders.getOrEmpty("Transaction-id"));
	}

}
