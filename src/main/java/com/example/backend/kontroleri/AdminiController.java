package com.example.backend.kontroleri;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.AdminRepo;
import com.example.backend.modeli.Admin;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminiController {
    
    private final AdminRepo adminRepo;

    public AdminiController(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    @PostMapping("/prijava")
    public Admin prijava(@RequestBody Admin admin){
        return this.adminRepo.prijava(admin);
    }
}
