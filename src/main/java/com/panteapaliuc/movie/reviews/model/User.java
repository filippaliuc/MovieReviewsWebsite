package com.panteapaliuc.movie.reviews.model;

import com.panteapaliuc.movie.reviews.utility.enUserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotEmpty(message = "Email is required")
    private String email;

    @OneToOne
    @JoinColumn(name = "watchlistId")
    private Watchlist watchlist;

    @Enumerated(EnumType.STRING)
    private enUserRole userRole;


    public User(@NotBlank(message = "Username is required") String username,
                @NotBlank(message = "Password is required") String password,
                @Email @NotEmpty(message = "Email is required") String email,
                Watchlist watchlist,
                enUserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.watchlist = watchlist;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
