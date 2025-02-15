package com.codeartisan.blog_app.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Bloggin Application",
        version = "1.0",
        description = """
                API for managing:
                - 📌 Posts
                - 📌 Categories
                - 📌 Comments
                - 📌 Users
                - 📌 Roles
                
                🔗 **API Documentation**: [Swagger JSON Docs](http://localhost:8080/v3/api-docs)
                
                👨‍💻 **Developer**: Sachin Shinde (Specialist in Enterprise-Level Applications)
                """
))
public class SwaggerConfig {

}
