package com.youbike.YouBikeSystemBackend.service;

import com.youbike.YouBikeSystemBackend.model.EasyCard;
import com.youbike.YouBikeSystemBackend.repository.EasyCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EasyCardService {
    @Autowired
    private EasyCardRepository easyCardRepository;

    public EasyCard recharge(String cardNumber, double amount) {
        EasyCard card = easyCardRepository.findById(cardNumber).orElseThrow(() -> new IllegalArgumentException("Card not found"));
        card.setBalance(card.getBalance() + amount);
        return easyCardRepository.save(card);
    }
}
