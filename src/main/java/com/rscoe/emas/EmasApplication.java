package com.rscoe.emas;

import com.rscoe.emas.entity.User;
import com.rscoe.emas.enums.RoleType;
import com.rscoe.emas.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableScheduling
@SpringBootApplication
public class EmasApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmasApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
        return args -> {

            // check if admin already exists
            if(repo.findByEmail("admin@company.com").isEmpty()) {

                User user = new User();
                user.setName("Admin");
                user.setEmail("admin@company.com");
                user.setPassword(encoder.encode("1234"));
                user.setRole(RoleType.ADMIN);
                user.setActive(true);

                repo.save(user);

                System.out.println("Admin user created");
            }

        };
    }
}