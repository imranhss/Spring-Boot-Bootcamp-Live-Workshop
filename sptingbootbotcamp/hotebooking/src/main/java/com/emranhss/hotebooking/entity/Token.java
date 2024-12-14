package com.emranhss.hotebooking.entity;

import jakarta.persistence.*;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    @Column(name = "is_log_out")
    private boolean logout;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User user;

}
