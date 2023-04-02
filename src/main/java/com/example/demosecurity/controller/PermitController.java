package com.example.demosecurity.controller;

import com.example.demosecurity.Respon.AuthenticationRespon;
import com.example.demosecurity.Respon.BaseItemRespon;
import com.example.demosecurity.request.AuthenticationRequest;
import com.example.demosecurity.entity.User;
import com.example.demosecurity.request.CreateUserRequest;
import com.example.demosecurity.request.UpdateInforRequest;
import com.example.demosecurity.request.UserRequest;
import com.example.demosecurity.service.AuthenticationService;
import com.example.demosecurity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/permit")
@RequiredArgsConstructor
@Slf4j
//@CrossOrigin(origins = "*")
public class PermitController {
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")  //Phân quyền


//

    @PostMapping("/login")
    ResponseEntity<BaseItemRespon> login(@RequestBody AuthenticationRequest request){
        BaseItemRespon<AuthenticationRespon> respon = new BaseItemRespon<>();
        respon.setSuccess(true);
        respon.setData(authenticationService.authenticate(request));
        return ResponseEntity.ok(respon);
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    ResponseEntity<?> register(@Valid @RequestBody CreateUserRequest request){
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/editAccount/{email}")
    ResponseEntity<?> updateAccount(@Valid @PathVariable String email, @RequestBody UpdateInforRequest request){
        return ResponseEntity.ok(userService.updateInfor(email, request));
    }
}
