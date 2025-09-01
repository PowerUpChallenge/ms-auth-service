package com.powerup.api.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for user authentication request.
 * Includes user details such as ID number, name, birth date, contact information, salary, and role ID. *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthRequestDTO {

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

}
