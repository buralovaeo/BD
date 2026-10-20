package ru.itis.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignUpForm {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
