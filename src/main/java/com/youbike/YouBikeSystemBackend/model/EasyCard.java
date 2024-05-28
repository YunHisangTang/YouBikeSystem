package com.youbike.YouBikeSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyCard {
    @Id
    private String cardNumber;

    private double balance;
}
