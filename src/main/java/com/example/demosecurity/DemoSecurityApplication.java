package com.example.demosecurity;

import com.example.demosecurity.entity.Role;
import com.example.demosecurity.entity.User;
import com.example.demosecurity.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
//@EnableWebSecurity
@EnableJpaRepositories
public class DemoSecurityApplication {

    public static void main(String[] args) throws Throwable{
        SpringApplication.run(DemoSecurityApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner runner(UserService userService){
//        return args -> {
//            userService.saveRole(new Role(null,"ROLE_USER", null));
//            userService.saveRole(new Role(null,"ROLE_MANAGER", null));
//            userService.saveRole(new Role(null,"ROLE_ADMIN", null));
//            userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN", null));
//
//            userService.saveUser(new User(null, "Hoang", "VuVanHoang", "hoangvuvan@gmail.com", "hoang123", new HashSet<>()));
//            userService.saveUser(new User(null, "Trung", "NguyenDucTrung", "trungco@gmail.com", "trung123", new HashSet<>()));
//
//            userService.addToUser("Hoang", "ROLE_USER");
//            userService.addToUser("Hoang", "ROLE_ADMIN");
//
//            userService.addToUser("Trung", "ROLE_USER");
//            userService.addToUser("Trung", "ROLE_ADMIN");
//        };
//    }

//12345667
}
