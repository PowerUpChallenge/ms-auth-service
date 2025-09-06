package com.powerup.model.userauth;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserAuthTest {
    @Test
    void shouldCreateUserWithNoArgsConstructorAndSetters() {
        UserAuth user = new UserAuth();

        user.setIdNumber("12345");
        user.setIdType(1);
        user.setName("John");
        user.setLastname("Doe");
        user.setBirthDate(LocalDate.of(1990, 5, 20));
        user.setAddress("123 Street");
        user.setPhone("5551234");
        user.setEmail("john.doe@test.com");
        user.setBaseSalary(new BigDecimal("5000.00"));
        user.setPasswordHash("hashedPass");
        user.setIdRole(10L);
        user.setEnabled(true);

        assertAll(
                () -> assertEquals("12345", user.getIdNumber()),
                () -> assertEquals(1, user.getIdType()),
                () -> assertEquals("John", user.getName()),
                () -> assertEquals("Doe", user.getLastname()),
                () -> assertEquals(LocalDate.of(1990, 5, 20), user.getBirthDate()),
                () -> assertEquals("123 Street", user.getAddress()),
                () -> assertEquals("5551234", user.getPhone()),
                () -> assertEquals("john.doe@test.com", user.getEmail()),
                () -> assertEquals(new BigDecimal("5000.00"), user.getBaseSalary()),
                () -> assertEquals("hashedPass", user.getPasswordHash()),
                () -> assertEquals(10L, user.getIdRole()),
                () -> assertTrue(user.isEnabled())
        );
    }

    @Test
    void shouldCreateUserWithAllArgsConstructor() {
        LocalDate birthDate = LocalDate.of(1990, 5, 20);
        BigDecimal salary = new BigDecimal("7000.00");

        UserAuth user = new UserAuth(
                "67890",
                2,
                "Jane",
                "Smith",
                birthDate,
                "456 Avenue",
                "5556789",
                "jane.smith@test.com",
                salary,
                "secureHash",
                20L,
                false
        );

        assertAll(
                () -> assertEquals("67890", user.getIdNumber()),
                () -> assertEquals(2, user.getIdType()),
                () -> assertEquals("Jane", user.getName()),
                () -> assertEquals("Smith", user.getLastname()),
                () -> assertEquals(birthDate, user.getBirthDate()),
                () -> assertEquals("456 Avenue", user.getAddress()),
                () -> assertEquals("5556789", user.getPhone()),
                () -> assertEquals("jane.smith@test.com", user.getEmail()),
                () -> assertEquals(salary, user.getBaseSalary()),
                () -> assertEquals("secureHash", user.getPasswordHash()),
                () -> assertEquals(20L, user.getIdRole()),
                () -> assertFalse(user.isEnabled())
        );
    }
}
