package com.example.demosecurity.request;

import com.example.demosecurity.constant.ConstantValidate;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

    @Size(min = 1, max = 255, message = "Size name is min 1, max 255")
    private String name;
    @Size(min = 6, max = 255, message = "Size fullname is min 6, max 255")
    private String fullname;

    @Pattern(regexp = ConstantValidate.VALID_EMAIL_ADDRESS_REGEX, message = "Email is illegal")
    private String email;

    @Pattern(regexp = ConstantValidate.VALID_PASSWORD_REGEX, message = "Size password is min 8, max 50")
    private String password;
}