package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "user_table")
public class User extends Base implements UserDetails {

    private String username;

    @Getter(onMethod = @__(@JsonIgnore))
    private String password;

    private String nickname;

    private Integer sex;

    private String avatar;

    private String phone;

    private String email;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    /**
     * @return Collection authorities
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> authorities = this.getRoles().stream().flatMap(role -> role.getAuths().stream().map(Auth::getUrl)).collect(Collectors.toSet());
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    /**
     * 账户是否未过期
     *
     * @return boolean
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !Objects.equals(this.getStatus(), "EXPIRED");
    }

    /**
     * 账户是否未锁定
     *
     * @return boolean
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !Objects.equals(this.getStatus(), "LOCKED");
    }

    /**
     * 凭据是否未过期
     *
     * @return boolean
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否已启用
     *
     * @return boolean
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return !Objects.equals(this.getStatus(), "DISABLE");
    }

}
