package com.example.demosecurity.config;

import com.example.demosecurity.config.JwtAuthenticationFilter;
import com.example.demosecurity.constant.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration  //Cấu hình trước khi load
@EnableWebSecurity  //cho phép sử dụng security
//@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    //SecurityFilterChain xác định đường dẫn URL nào đưa vào sẽ được bảo mật và đường nào thì không, ở dưới đường dẫn "/" và "/home" sẽ không yêu cầu xác thực
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {     //HttpSecurity: là tham số sẽ lắng nghe toàn bộ request

//        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.cors().disable()
                .authorizeRequests()
//                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/permit/**").permitAll()
                .requestMatchers("/home").authenticated()
                .requestMatchers("/api/auth/delete").hasAnyAuthority("USER", "ADMIN", "SUPER_ADMIN")
                .requestMatchers("/api/auth/manage/**").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                .and()
//                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//                .formLogin((form) -> form
//                .loginPage("/login")      // login sẽ được thực hiện ở trang có domain "/login"
//                .permitAll())
//                .logout((logout) -> logout
//                        .permitAll()
//                );
        http.csrf().disable();
//        http.cors();
        return http.build();
    }

//    @Bean
//    //Bean này thiết lập kho lưu trữ người dùng trong bộ nhớ với một người dùng, người dùng này được cấp tên, mật khẩu, vai trò của người dùng
//    public UserDetailsService userDetailsService(){
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("Hoang")
//                .password("hoang123")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
}
