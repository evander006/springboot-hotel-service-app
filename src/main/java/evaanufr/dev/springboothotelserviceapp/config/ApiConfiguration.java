package evaanufr.dev.springboothotelserviceapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {
    @Bean
    public OpenAPI horelOpenApi(){
        return new OpenAPI().info(new Info()
                .title("Hotel Property View API")
                .version("1.0")
                .description("RESTful API for hotels"));
    }
}
