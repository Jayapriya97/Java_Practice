package com.softwareBasket.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import com.softwareBasket.Interceptor.LoggingRequestInterceptor;
import com.softwareBasket.Interceptor.RestAuthInterceptor;

@Configuration
public class RestTemplateConfig {

  @Value("${useLoggingInterceptor}")
  protected boolean useLoggingInterceptor;

  @Value("${ri.setAPIToken.enable}")
  protected boolean restApiTokenEnabled;

  @Value("${request.timeout}")
  protected int requestTimeout;

  @Autowired
  private Environment environment;

  @Bean
  public RestTemplate bufferedRestTemplate(RestTemplateBuilder builder) {
    RestTemplate restTemplate = new RestTemplate(bufferedRequestFactory());
    addInterceptors(restTemplate);
    return restTemplate;
  }

  public BufferingClientHttpRequestFactory bufferedRequestFactory() {

    PoolingHttpClientConnectionManager poolingConnectionManager =
        new PoolingHttpClientConnectionManager();
    poolingConnectionManager.setMaxTotal(200);
    poolingConnectionManager.setDefaultMaxPerRoute(20);

    RequestConfig config = RequestConfig.custom().setConnectTimeout(requestTimeout)
        .setConnectionRequestTimeout(requestTimeout).setSocketTimeout(requestTimeout).build();

    CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config)
        .setConnectionManager(poolingConnectionManager).build();

    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory(httpClient);

    BufferingClientHttpRequestFactory bufferingRequestFactory =
        new BufferingClientHttpRequestFactory(requestFactory);

    return bufferingRequestFactory;
  }

  void addInterceptors(RestTemplate restTemplate) {
    if (useLoggingInterceptor) {
      restTemplate.getInterceptors().add(new LoggingRequestInterceptor());
    }

    if (restApiTokenEnabled) {
      String serviceId = environment.getProperty("SERVICE_ID").trim();
      String apiKey = environment.getProperty("API_KEY");
      restTemplate.getInterceptors().add(new RestAuthInterceptor(serviceId, apiKey));
    }
  }

}
