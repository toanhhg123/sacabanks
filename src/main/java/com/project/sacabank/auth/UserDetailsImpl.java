package com.project.sacabank.auth;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.sacabank.enums.EnumNameRole;
import com.project.sacabank.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private UUID id;

  private String username;

  private String email;

  @JsonIgnore
  private String password;

  private String phoneNumber;

  private Collection<? extends GrantedAuthority> authorities;

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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }

  public EnumNameRole getRole() {
    return this.authorities.stream().map(GrantedAuthority::getAuthority).map(EnumNameRole::valueOf).findFirst()
        .orElse(null);
  }

  public static UserDetailsImpl build(User user) {
    var roleName = user.getRole().getName().toString();
    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));

    return UserDetailsImpl.builder()
        .id(user.getId())
        .authorities(authorities)
        .username(user.getUsername())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .password(user.getPassword())
        .build();
  }

}
