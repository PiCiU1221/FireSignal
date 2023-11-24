package com.piciu1221.firesignal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "username_UNIQUE", columnNames = {"username"})
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", nullable = false, length = 256)
    private String username;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "role", nullable = false, length = 255, columnDefinition = "varchar(255) default 'USER'")
    private String role = "USER";
}