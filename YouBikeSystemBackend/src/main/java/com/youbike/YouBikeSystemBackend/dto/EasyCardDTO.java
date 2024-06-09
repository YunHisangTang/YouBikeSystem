package com.youbike.YouBikeSystemBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EasyCardDTO {
    private String cardNumber;
    private double amount;
}
