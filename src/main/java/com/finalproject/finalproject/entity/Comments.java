package com.finalproject.finalproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_id_seq")
    @SequenceGenerator(name = "comments_id_seq", sequenceName = "comments_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Users user;

    @Lob
    @Column(name = "COMMENT")
    private String comment;
}
