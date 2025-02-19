package com.example.sparta_3rd_newsfeed.member.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.sparta_3rd_newsfeed.common.entity.BaseEntity;
import java.util.Collection;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getUsername() {
        return email;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    @Column(name = "profile_img_id")
    private String profileImg;

    @Column(nullable=false, name = "name")
    private String username;

    @Column(nullable = false,unique = true,name = "member_email")
    private String email;

    @Column(nullable =false, name = "member_password")
    private String password;

    @Column(columnDefinition = "TEXT", name = "profile_bio")
    private String profileBio;

    @Column(nullable = false,name = "is_delete")
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Member() {}

}
