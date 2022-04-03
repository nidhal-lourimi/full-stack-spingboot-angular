package com.nidhallourimi.server;

import com.nidhallourimi.server.enumeration.Status;
import com.nidhallourimi.server.model.Server;
import com.nidhallourimi.server.repository.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.nidhallourimi.server.enumeration.Status.SERVER_UP;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepo serverRepo){
        return args -> {
            serverRepo.save(new Server(null,"192.1687.1.160","Ubuntu Linux","16 GB","personal pc",
                    "http://localhost:8080/server/image/server1.png", SERVER_UP));
            serverRepo.save(new Server(null,"192.1687.1.55","Ubuntu Linux","16 GB","dell tower",
                    "http://localhost:8080/server/image/server3.png", SERVER_UP));
            serverRepo.save(new Server(null,"192.1687.1.68","MS 2000","32 GB","web server",
                    "http://localhost:8080/server/image/server2.png", SERVER_UP));
        };
    }

}
