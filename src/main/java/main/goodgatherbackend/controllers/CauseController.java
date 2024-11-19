package main.goodgatherbackend.controllers;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.CauseDTO;
import main.goodgatherbackend.dtos.EventDTO;
import main.goodgatherbackend.services.CauseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cause")
@AllArgsConstructor
public class CauseController {
    private CauseService causeService;

    @GetMapping
    public List<CauseDTO> getAll() {
        return causeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CauseDTO> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(causeService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
