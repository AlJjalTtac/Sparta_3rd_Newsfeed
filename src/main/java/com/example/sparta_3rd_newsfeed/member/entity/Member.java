package com.example.sparta_3rd_newsfeed.member.entity;

import com.example.sparta_3rd_newsfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
