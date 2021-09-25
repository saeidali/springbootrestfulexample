package com.example.springbootrestfulexample.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -4439114469417994311L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @NotNull(message = "")
    @NotEmpty(message = "")
    @Size(min = 2, max = 10, message = "{input.size.invalid}")
    @Pattern(regexp = "^[A-Za-z]*$", message = "{invalid.name}")
    private String name;
    @Column
    @NotNull(message = "{email.nul}")
    @NotEmpty(message = "{email.empty}")
    @Email(message = "{invalid.email}")
    private String email;

}
