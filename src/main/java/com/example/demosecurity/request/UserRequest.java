package com.example.demosecurity.request;

import com.example.demosecurity.constant.ConstantValidate;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @Pattern(regexp = ConstantValidate.VALID_EMAIL_ADDRESS_REGEX, message = "Email is illegal")
    private String email;
}
