package com.example.demosecurity.controller;

import com.example.demosecurity.Respon.BaseListItemRespon;
import com.example.demosecurity.entity.User;
import com.example.demosecurity.request.UserRequest;
import com.example.demosecurity.service.AuthenticationService;
import com.example.demosecurity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final UserService userService;

    @GetMapping("/manage/getAll")
    @PreAuthorize("hasAnyAuthority(ADMIN)")
    ResponseEntity<?> getAll(){
        BaseListItemRespon<List<User>> baseListItemRespon = new BaseListItemRespon<>();
        baseListItemRespon.setSuccess(true);
        BaseListItemRespon.DataList dataList = new BaseListItemRespon.DataList<>();
        dataList.setTotal(userService.getAll().size());
        dataList.setItems(userService.getAll());
        baseListItemRespon.setDataList(dataList);
        return ResponseEntity.ok(baseListItemRespon);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Optional<User>> getByName(@PathVariable String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @DeleteMapping("/delete")
    ResponseEntity<?> deleteUser(@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(userService.deleteUser(request.getEmail()));
    }
}
