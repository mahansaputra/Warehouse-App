package com.mahansa.warehouseapp.dto;

public class UserDTO {
    private Long id;
    private String name;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName, String lastName) {
        this.name = firstName + " " + lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
