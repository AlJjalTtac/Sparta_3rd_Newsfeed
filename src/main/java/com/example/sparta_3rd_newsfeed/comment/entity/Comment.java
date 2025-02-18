package com.example.sparta_3rd_newsfeed.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contents;

    public Comment(String contents) {
        this.contents = contents;
    }

    public void updateComment(String contents) {
        this.contents = contents;
    }
}
