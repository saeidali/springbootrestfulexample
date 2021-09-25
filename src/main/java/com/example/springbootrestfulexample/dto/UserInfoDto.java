package com.example.springbootrestfulexample.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto implements Serializable {
    private Long id;
    @NotNull(message = "")
    @NotEmpty(message = "")
    @Size(min = 2, max = 10, message = "{input.size.invalid}")
    @Pattern(regexp = "^[A-Za-z]*$", message = "{invalid.name}")
    private String name;
    @NotNull(message = "{email.nul}")
    @NotEmpty(message = "{email.empty}")
    @Email(message = "{invalid.email}")
    private String email;

}
