package com.youbike.YouBikeSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "id_card_number")
    private String idCardNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "easy_card_number")
    private String easyCardNumber;

}
