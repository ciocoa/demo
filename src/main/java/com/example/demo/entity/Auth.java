package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "auth_table")
public class Auth extends Base {

    private String name;

    private String url;

    private String remark;

    private String status;

    @JsonBackReference
    @ManyToMany(mappedBy = "auths")
    private Set<Role> roles;

}
