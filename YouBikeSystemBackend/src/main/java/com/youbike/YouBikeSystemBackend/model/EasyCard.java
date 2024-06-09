package com.youbike.YouBikeSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyCard {
    @Id
    @Column(name = "card_number")
    private String cardNumber;

    @Column(name="balance")
    private double balance;
}
