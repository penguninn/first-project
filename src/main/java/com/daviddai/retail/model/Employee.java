package com.daviddai.retail.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employee {

    private Integer id;

    private String code;

    private String fullName;

    private String phone;

    private LocalDate birthDate;

    private String role = "STAFF";

    private Boolean gender;

    private String address;

    private String username;

    private String passwordHash;

    private LocalDateTime createdAt;

    private boolean isActive = true;

    public Employee() {
    }

    public Employee(Integer id, String code, String fullName, String phone, LocalDate birthDate, String role,
            Boolean gender, String address, String username, String passwordHash, LocalDateTime createdAt,
            boolean isActive) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.phone = phone;
        this.birthDate = birthDate;
        this.role = role;
        this.gender = gender;
        this.address = address;
        this.username = username;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
