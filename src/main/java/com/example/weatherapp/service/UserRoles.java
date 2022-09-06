package com.example.weatherapp.service;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.weatherapp.service.UserPermissions.*;

public enum UserRoles {

    ADMIN(Sets.newHashSet(USER_DELETE)),
    USER(Sets.newHashSet(CITY_CREATE,CITY_DELETE));

    private final Set<UserPermissions> permissions;

    UserRoles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> GetGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream().map(userPermissions ->
                new SimpleGrantedAuthority(userPermissions.getPermission())).collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE"+ this.name()));
        return permissions; 
    }
}
