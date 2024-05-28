package com.youbike.YouBikeSystemBackend.controller;

import com.youbike.YouBikeSystemBackend.dto.UserDTO;
import com.youbike.YouBikeSystemBackend.service.UserService;
import com.youbike.YouBikeSystemBackend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Claims claims = jwtUtil.extractClaims(token);
        String phoneNumber = claims.getSubject();

        UserDTO userDTO = userService.getUserByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/user/details")
    public ResponseEntity<UserDTO> getUserDetails(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Claims claims = jwtUtil.extractClaims(token);
        String phoneNumber = claims.getSubject();

        UserDTO userDTO = userService.getUserDetailsByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(userDTO);
    }
}