package com.powerup.r2dbc.userauth;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "user_auth")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthEntity {

    @Id
    private Long userId;
    private String idNumber;
    private Integer idType;
    private String name;
    private String lastname;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private BigDecimal baseSalary;
    private Integer idRole;
    private String passwordHash;
    private boolean enabled;

}
