package com.powerup.api.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * UserAuthResponseDTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthResponseDTO {

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
