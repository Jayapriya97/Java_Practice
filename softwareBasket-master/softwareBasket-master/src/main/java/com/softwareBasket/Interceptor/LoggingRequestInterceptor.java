package com.softwareBasket.Interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareBasket.demo.model.RequestHeaderLoggingModel;
import com.softwareBasket.demo.model.RequestLoggingModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

	private static final List<String> SKIP_RESPONSE_LOGS = Arrays.asList("/pcp", "/uap", "/pem", "/odmeng", "/o360pweb",
			"/ocfqco", "/ca", "/ampweb", "/chweb");

	@Value("${logMethodDuration}")
	private boolean logMethodDuration;

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		long startTime = System.currentTimeMillis();
		RequestLoggingModel requestLoggingModel = new RequestLoggingModel();
		traceRequest(request, body, requestLoggingModel);
		ClientHttpResponse response = execution.execute(request, body);
		traceResponse(request.getURI().toString(), response, startTime, requestLoggingModel);
		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body, RequestLoggingModel requestLoggingModel)
			throws IOException {

		requestLoggingModel.setRequestURI(request.getURI().toString());
		requestLoggingModel.setRequestHeaders(new RequestHeaderLoggingModel(request.getHeaders()));
		requestLoggingModel.setRequestMethod(request.getMethod().toString());
		String requestBody = request.getHeaders().getContentLength() > 7000 ? "RequestBody NOT LOGGED"
				: new String(body, StandardCharsets.UTF_8);
		requestLoggingModel.setRequestBody(requestBody);
	}

	private void traceResponse(String requestURI, ClientHttpResponse response, long startTime,
			RequestLoggingModel requestLoggingModel) throws IOException {

		String responseBody = "ResponseBody NOT LOGGED.";

		if (response.getHeaders().getContentType() != null
				&& response.getHeaders().getContentType().toString().contains("json")) {
			if (!SKIP_RESPONSE_LOGS.stream().anyMatch(requestURI::contains)) {
				responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
			}
		}

		requestLoggingModel.setResponseStatusCode(response.getStatusCode().toString());
		requestLoggingModel.setResponseBody(responseBody);
		if (logMethodDuration) {
			requestLoggingModel.setDuration((System.currentTimeMillis() - startTime));
		}

		ObjectMapper objectMapper = new ObjectMapper();
		log.debug(objectMapper.writeValueAsString(requestLoggingModel));
	}
}
