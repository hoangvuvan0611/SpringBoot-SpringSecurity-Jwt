package com.example.demosecurity.service;

import com.auth0.jwt.JWT;
import com.example.demosecurity.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.AlgorithmConstraints;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtSevice {        //Xây dựng một class để generate token
    private static final String Secret_key = "123";
    public String generateToken(User user, Collection<SimpleGrantedAuthority> authorities){     //Dữ liệu chuyền vào là user cùng với quyền truy nhập của nó
        //Gọi hàm thuật toán mã hóa token
        Algorithm algorithm = Algorithm.HMAC256(Secret_key.getBytes());
        return JWT.create()
                .withSubject(user.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60*60*1000))       //Thời gian tồn tại của token
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String generateRefreshToken(User user, Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm = Algorithm.HMAC256(Secret_key.getBytes());
        return JWT.create()
                .withSubject(user.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 70*60*1000))
                .sign(algorithm);
    }
}
