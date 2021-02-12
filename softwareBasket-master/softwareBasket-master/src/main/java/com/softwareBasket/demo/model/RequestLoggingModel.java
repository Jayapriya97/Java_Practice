package com.softwareBasket.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class RequestLoggingModel {

	private String requestURI;
	private String requestDetails;
	private RequestHeaderLoggingModel requestHeaders;
	private String requestParameters;
	private String requestMethod;
	private String requestBody;
	private String responseStatusCode;
	private String responseBody;
	private Long duration;

}
