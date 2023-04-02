package com.example.demosecurity.service;

import com.example.demosecurity.Respon.BaseRespon;
import com.example.demosecurity.entity.Role;
import com.example.demosecurity.entity.User;
import com.example.demosecurity.request.CreateUserRequest;
import com.example.demosecurity.request.UpdateInforRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addToUser(String userName, String roleName);

    List<User> getAll();

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    public BaseRespon registerUser(CreateUserRequest request);

    public BaseRespon updateInfor(String email, UpdateInforRequest request);

    public BaseRespon deleteUser(String email);
}
