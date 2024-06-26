package com.youbike.YouBikeSystemBackend.service;

import com.youbike.YouBikeSystemBackend.dto.UserDTO;
import com.youbike.YouBikeSystemBackend.model.EasyCard;
import com.youbike.YouBikeSystemBackend.model.User;
import com.youbike.YouBikeSystemBackend.repository.EasyCardRepository;
import com.youbike.YouBikeSystemBackend.repository.UserRepository;
import com.youbike.YouBikeSystemBackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EasyCardRepository easyCardRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<Map<String, String>> register(UserDTO userDTO) {
        Map<String, String> response = new HashMap<>();
        if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            response.put("message", "Phone number already in use");
            return ResponseEntity.badRequest().body(response);
        }

        User user = new User();
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setIdCardNumber(userDTO.getIdCardNumber());
        user.setEmail(userDTO.getEmail());
        user.setEasyCardNumber(userDTO.getEasyCardNumber());
        userRepository.save(user);

        EasyCard easyCard = new EasyCard();
        easyCard.setCardNumber(userDTO.getEasyCardNumber());
        easyCard.setBalance(0.0);
        easyCardRepository.save(easyCard);

        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, String>> login(UserDTO userDTO) {
        Map<String, String> response = new HashMap<>();
        Optional<User> userOptional = userRepository.findByPhoneNumber(userDTO.getPhoneNumber());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getPhoneNumber(), user.getIdCardNumber(), user.getEmail(), user.getEasyCardNumber());
                response.put("message", "Login successful");
                response.put("token", token);
                return ResponseEntity.ok(response);
            }
        }
        response.put("message", "Invalid phone number or password");
        return ResponseEntity.status(401).body(response);
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + phoneNumber));
        return new org.springframework.security.core.userdetails.User(user.getPhoneNumber(), user.getPassword(), new ArrayList<>());
    }

    public UserDTO getUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getId());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setIdCardNumber(user.getIdCardNumber());
        userDTO.setEmail(user.getEmail());
        userDTO.setEasyCardNumber(user.getEasyCardNumber());
        return userDTO;
    }

    public UserDTO getUserDetailsByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setIdCardNumber(user.getIdCardNumber());
        userDTO.setEmail(user.getEmail());
        userDTO.setEasyCardNumber(user.getEasyCardNumber());
        return userDTO;
    }
}
