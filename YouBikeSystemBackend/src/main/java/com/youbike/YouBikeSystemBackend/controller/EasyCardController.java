package com.youbike.YouBikeSystemBackend.controller;

import com.youbike.YouBikeSystemBackend.dto.EasyCardDTO;
import com.youbike.YouBikeSystemBackend.model.EasyCard;
import com.youbike.YouBikeSystemBackend.repository.EasyCardRepository;
import com.youbike.YouBikeSystemBackend.service.EasyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/easycard")
@CrossOrigin("*")
public class EasyCardController {
    @Autowired
    private EasyCardService easyCardService;
    @Autowired
    private EasyCardRepository easyCardRepository;
    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestParam String cardNumber) {
        EasyCard easyCard = easyCardRepository.findById(cardNumber).orElseThrow(() -> new RuntimeException("EasyCard not found"));
        return ResponseEntity.ok(easyCard.getBalance());
    }
    @PostMapping("/recharge")
    public ResponseEntity<Map<String, Object>> recharge(@RequestBody EasyCardDTO easyCardDTO) {
        EasyCard updatedCard;
        try {
            updatedCard = easyCardService.recharge(easyCardDTO.getCardNumber(), easyCardDTO.getAmount());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("cardNumber", updatedCard.getCardNumber());
        response.put("balance", updatedCard.getBalance());
        return ResponseEntity.ok(response);
    }
}
