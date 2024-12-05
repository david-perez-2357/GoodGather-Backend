package main.goodgatherbackend.controllers;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.CauseDTO;
import main.goodgatherbackend.services.CauseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cause")
@AllArgsConstructor
public class CauseController {
    private CauseService causeService;

    @GetMapping
    public ResponseEntity<List<CauseDTO>> getAll() {
        try {
            return ResponseEntity.ok(causeService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CauseDTO> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(causeService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/funds")
    public ResponseEntity<Double> getCauseFunds(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(causeService.getCauseFunds(id));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CauseDTO>> getCausesInUsersRange(@PathVariable Integer userId) {
        try {
            return ResponseEntity.ok(causeService.getCausesInUsersRange(userId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<CauseDTO> createCause(@RequestBody CauseDTO causeDTO) {
        try {
            return ResponseEntity.ok(causeService.createCause(causeDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
