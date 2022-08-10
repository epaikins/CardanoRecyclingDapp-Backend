package com.cardanorecyclingdapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Cardano Recycling Dapp API", version = "1.0.0"),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Server"),
                @Server(url = "http://mysql-database-server.csyk75ifilol.us-east-1.rds.amazonaws.com:3306", description = "Development Server"),
        }
)
public class CardanoRecyclingDappBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardanoRecyclingDappBackendApplication.class, args);
    }

}
