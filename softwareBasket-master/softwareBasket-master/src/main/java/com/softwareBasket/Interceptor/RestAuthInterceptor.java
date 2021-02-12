package com.softwareBasket.Interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestAuthInterceptor implements ClientHttpRequestInterceptor {

  private String serviceId;
  private String apiKey;

  public RestAuthInterceptor(String serviceId, String apiKey) {
    this.serviceId = serviceId;
    this.apiKey = apiKey;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    String time = Long.toString(new Date().getTime());
    String apiToken = this.generateAPIToken(time, serviceId, apiKey);
    request.getHeaders().add("API_TOKEN", apiToken);
    request.getHeaders().add("SERVICE_ID", serviceId);
    request.getHeaders().add("TIME_STAMP", time);
    ClientHttpResponse response = execution.execute(request, body);

    return response;
  }

  private String generateAPIToken(String time, String serviceID, String apiKey) {
    String apiToken = "";
    String apiCode = apiKey + serviceID + time;
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] apiKeyBytes = digest.digest(apiCode.getBytes(StandardCharsets.UTF_8));
      apiToken = this.bytesToHex(apiKeyBytes);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

    return apiToken;
  }

  private String bytesToHex(byte[] hash) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if (hex.length() == 1)
        hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }

}
