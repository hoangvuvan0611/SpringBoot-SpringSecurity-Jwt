package com.example.demosecurity.Respon;

import com.example.demosecurity.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRespon {
    private Long id;
    private String fullName;
    private String email;
    private List<String> roleList;
    private String accessToken;
    private String refreshToken;
    private Long expirationTime;
}
