package com.example.demosecurity.service;

import com.example.demosecurity.Respon.BaseRespon;
import com.example.demosecurity.Respon.BaseResponError;
import com.example.demosecurity.constant.ErrorCodeDefine;
import com.example.demosecurity.constant.RoleEnum;
import com.example.demosecurity.entity.Role;
import com.example.demosecurity.entity.User;
import com.example.demosecurity.repositiory.RoleCustomRepo;
import com.example.demosecurity.repositiory.RoleRepository;
import com.example.demosecurity.repositiory.UserRepository;
import com.example.demosecurity.request.CreateUserRequest;
import com.example.demosecurity.request.UpdateInforRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleCustomRepo roleCustomRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RoleCustomRepo roleCustomRepo) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleCustomRepo = roleCustomRepo;
    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addToUser(String userName, String roleName) {
        User user = userRepository.findByEmail(userName).get();
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public BaseRespon registerUser(CreateUserRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            BaseResponError error = new BaseResponError();
            BaseResponError.Error err = BaseResponError.Error.builder().code(ErrorCodeDefine.VALIDATION_ERROR).message("Email already exists").errorDetailList(null).build();
            error.setError(err);
            return error;
        }

        User user = User.builder()
                .name(request.getName())
                .fullName(request.getFullname())
                .email(request.getEmail())
                .roles(new HashSet<>())
                .password(request.getPassword())
                .build();

        saveUser(user);
        addToUser(user.getEmail(), String.valueOf(RoleEnum.USER));
        return BaseRespon.builder().success(true).build();
    }

    @Override
    public BaseRespon updateInfor(String email, UpdateInforRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            BaseResponError error = new BaseResponError();
            BaseResponError.Error err = BaseResponError.Error.builder().code(ErrorCodeDefine.NOT_FOUND)
                    .message(ErrorCodeDefine.getDetailErrorMessage(ErrorCodeDefine.NOT_FOUND))
                    .errorDetailList(null).build();
            error.setError(err);
            return error;
        }
        userRepository.findByEmail(email).stream().map(user -> {
            user.setName(request.getName());
            user.setFullName(request.getFullname());
            user.setEmail(request.getEmail());
            System.out.println(user);
            return userRepository.save(user);
        }).collect(Collectors.toList());

        return BaseRespon.builder().success(true).build();
    }

    public BaseRespon deleteUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        BaseResponError error = new BaseResponError();

        if (!optionalUser.isPresent()) {
            BaseResponError.Error err = BaseResponError.Error.builder().code(ErrorCodeDefine.VALIDATION_ERROR).message(ErrorCodeDefine.getDetailErrorMessage(ErrorCodeDefine.NOT_FOUND)).errorDetailList(null).build();
            error.setError(err);
            return error;
        }

        List<Role> roleList = new ArrayList<>();
        roleList = roleCustomRepo.getRole(optionalUser.get());

        for (Role role : roleList) {
            if (role.getName().equals("ADMIN") || role.getName().equals("SUPER_ADMIN")) {
                BaseResponError.Error err = BaseResponError.Error.builder().code(ErrorCodeDefine.BAD_REQUEST).message(ErrorCodeDefine.getDetailErrorMessage(ErrorCodeDefine.BAD_REQUEST)).errorDetailList(null).build();
                error.setError(err);
                return error;
            }
        }

        userRepository.deleteByEmail(email);
        return BaseRespon.builder()
                .success(true)
                .build();
    }

}
