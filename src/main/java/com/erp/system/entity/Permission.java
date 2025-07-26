package com.erp.system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Size(max = 200)
    @Column(name = "description")
    private String description;

    @Size(max = 100)
    @Column(name = "module")
    private String module;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    // Constructors
    public Permission() {}

    public Permission(String name, String description, String module) {
        this.name = name;
        this.description = description;
        this.module = module;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}