package com.powerup.model.userauth;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * UserAuth class represents a user model
 * It includes fields such as idNumber, idType, name, lastname, birthDate, address, phone, email, baseSalary and idRole.
 * Uses Lombok annotations to generate boilerplate code like getters, setters, constructors, and builder pattern.
 * @version 1.0
 * @since 2025-08-23
 */

public class UserAuth {

    private String idNumber;
    private Integer idType;
    private String name;
    private String lastname;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private BigDecimal baseSalary;
    private String passwordHash;
    private Long idRole;
    private boolean enabled;

    public UserAuth() {
    }

    public UserAuth(String idNumber, Integer idType, String name, String lastname, LocalDate birthDate, String address, String phone, String email, BigDecimal baseSalary, String passwordHash, Long idRole, boolean enabled) {
        this.idNumber = idNumber;
        this.idType = idType;
        this.name = name;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.baseSalary = baseSalary;
        this.passwordHash = passwordHash;
        this.idRole = idRole;
        this.enabled = enabled;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
