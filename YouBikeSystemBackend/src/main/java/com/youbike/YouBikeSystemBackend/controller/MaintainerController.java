package com.youbike.YouBikeSystemBackend.controller;

import com.youbike.YouBikeSystemBackend.dto.MaintainerDTO;
import com.youbike.YouBikeSystemBackend.model.Maintainer;
import com.youbike.YouBikeSystemBackend.service.LdapAuthService;
import com.youbike.YouBikeSystemBackend.service.MaintainerService;
import com.youbike.YouBikeSystemBackend.util.MaintainerJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/maintainers")
@CrossOrigin("*")
public class MaintainerController {

    @Autowired
    private LdapAuthService ldapAuthService;

    @Autowired
    private MaintainerService maintainerService;

    @Autowired
    private MaintainerJwtUtil maintainerJwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MaintainerDTO maintainerDTO) {
        boolean isAuthenticated = ldapAuthService.authenticateWithLdap(maintainerDTO.getEmployeeId(), maintainerDTO.getPassword());

        if (isAuthenticated) {
            Maintainer maintainer = maintainerService.findByEmployeeId(maintainerDTO.getEmployeeId());
            if (maintainer == null) {
                maintainer = maintainerService.saveMaintainer(new Maintainer(null, maintainerDTO.getEmployeeId()));
            }
            String token = maintainerJwtUtil.generateToken(maintainer.getEmployeeId());

            // Prepare response data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("maintainer", maintainer);

            return ResponseEntity.ok(responseData);
        } else {
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Implement logout logic if needed
        return ResponseEntity.ok("Logged out successfully");
    }


}
