package com.example.demosecurity.service;

import com.example.demosecurity.request.AuthenticationRequest;
import com.example.demosecurity.Respon.AuthenticationRespon;
import com.example.demosecurity.entity.Role;
import com.example.demosecurity.entity.User;
import com.example.demosecurity.repositiory.RoleCustomRepo;
import com.example.demosecurity.repositiory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService{

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleCustomRepo roleCustomRepo;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtSevice jwtSevice;

    public AuthenticationRespon  authenticate(AuthenticationRequest authenticationRequest){
       log.info("AuthenticationRespon  authenticate");
        //tìm trong database xem co tồn tại user có email như controller truyền vào hay không
        User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new RuntimeException("Email incorrect"));

        //Khi nhận name&password từ controller thì cần generate một cái token, sau đó xác thực xem mã token này có tồn tại không
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        //tạo list chứa role do một người có thể có nhiều role
        List<Role> roles = null;

        //Nếu User tồn tại trong database
        if(user != null){

            //Lấy ra danh sách role của user trong database vừa tìm thấy
            roles = roleCustomRepo.getRole(user);
        }


        Set<Role> set = new HashSet<>();
        //Truyền các đối tượng role lấy từ trong user vào trong set, set ở đây để chỉ các quyền chỉ có 1
        roles.stream().forEach(role -> set.add(new Role(role.getName())));

        //Truyền set chứa các role vào trong user của controller
        user.setRoles(set);

        //Tạo một list SimpleGrantedAuthority chứa các đối tượng role để cấp quyền
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        //Lấy từng đối tượng role của user đưa vào SimpleGrantedAuthority để chứa các quyền
        set.stream().forEach(s -> authorities.add(new SimpleGrantedAuthority(s.getName())));

        //tạo chuỗi chứa mã token, phải tạo một class service để chuyển đổi user và authen thành chuỗi token, chuỗi token này có tgian sử dụng
        //Token được tạo từ Class JwtService
        List<String> roleList = new ArrayList<>();
        roles.stream().forEach(role -> {
            roleList.add(role.getName());
        });

        var jwtToken = jwtSevice.generateToken(user, authorities);
        var jwtRefreshToken = jwtSevice.generateRefreshToken(user, authorities);
        return AuthenticationRespon.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roleList(roleList)
                .accessToken(jwtToken)
                .refreshToken(jwtRefreshToken)
                .expirationTime((long) (60*60*1000))
                .build();
    }
}
