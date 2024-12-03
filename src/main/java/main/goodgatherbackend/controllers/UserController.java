package main.goodgatherbackend.controllers;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.services.UserService;
import org.mapstruct.Mapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/{id}/contributions")
    private ResponseEntity<Double> getUserContributions(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(userService.getUserContributions(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
