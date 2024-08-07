package com.lvucko.cookshare.models;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import com.lvucko.cookshare.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String realName;
    private Date creationDate;
    private String phone;
    private Long pictureId;
    private String about;
    private Boolean showRealName;
    private Boolean showPhone;
    private Boolean showEmail;

    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
