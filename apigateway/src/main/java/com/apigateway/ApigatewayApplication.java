package com.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

	@Bean
	RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route( p -> p
						.path("/v1/auth/**")
						.filters(f -> f.addRequestHeader("X-GATEWAY-ACCESS", "true"))
						.uri("lb://AUTH-SERVICE"))


				.route( p -> p
						.path("/v1/expense/**")
						.filters(f -> f.addRequestHeader("X-GATEWAY-ACCESS", "true"))
						.uri("lb://expense-category-service"))


				.route( p -> p
						.path("/v1/financial/**")
						.filters(f -> f.addRequestHeader("X-GATEWAY-ACCESS", "true"))
						.uri("lb://financeservice"))
				.route( p -> p
						.path("/v1/notifications/**")
						.filters(f -> f.addRequestHeader("X-GATEWAY-ACCESS", "true"))
						.uri("lb://notification-service"))
				.build();
	}

}
