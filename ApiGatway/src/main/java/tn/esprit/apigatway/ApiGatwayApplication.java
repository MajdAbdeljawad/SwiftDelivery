	package tn.esprit.apigatway;

	import org.springframework.cloud.gateway.route.RouteLocator;
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.context.annotation.Bean;
	import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

	@SpringBootApplication
	public class ApiGatwayApplication {

		public static void main(String[] args) {
			SpringApplication.run(ApiGatwayApplication.class, args);
		}



		@Bean
		public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
			return builder.routes()

					.route("user-service-users", r -> r
							.path("/api/users/**")
							.and()
							.method("GET","POST","PUT","DELETE","OPTIONS")
							.uri("lb://user"))

					.route("user-service-auth", r -> r
							.path("/api/auth/**")
							.and()
							.method("GET","POST","PUT","DELETE","OPTIONS")
							.uri("lb://user"))

					.build();
		}
	}
