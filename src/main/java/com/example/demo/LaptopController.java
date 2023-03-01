package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {
    private final LaptopRepository laptopRepository;
    private final Logger log = LoggerFactory.getLogger(LaptopController.class);

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    @GetMapping("api/laptops")
    public List<Laptop> findAll() {
        return laptopRepository.findAll();
    }

    @GetMapping("api/laptops/{id}")
    public ResponseEntity<Laptop> findById (@PathVariable Long id) {
        Optional<Laptop> laptopOpt = laptopRepository.findById(id);
        if (laptopOpt.isPresent()) {
            return ResponseEntity.ok(laptopOpt.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
        //functional style option
        //return laptopOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("api/laptops")
    public ResponseEntity<Laptop> save (@RequestBody Laptop laptop) {
        if (laptop.getId()!= null) {
            log.warn("Trying to create a laptop with an ID");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(laptopRepository.save(laptop));
    }

    @PutMapping("api/laptops")
    public ResponseEntity<Laptop> update (@RequestBody Laptop laptop) {
        if (laptop.getId() == null) { //if id is null, it means it is a new laptop
            log.warn("Trying to update a non existing laptop");
            return ResponseEntity.badRequest().build();
        }
        if (!laptopRepository.existsById(laptop.getId())) { //if the given id does not exist
            log.warn("Trying to update a non existing laptop");
            return ResponseEntity.notFound().build();
        }

        //Update the laptop
        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("api/laptops/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        if (!laptopRepository.existsById(id)) {
            log.warn("Trying to delete a non existing laptop");
            return ResponseEntity.notFound().build();
        }

        laptopRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("api/laptops")
    public ResponseEntity<Void> deleteAll() {
        log.info("REST Request to delete all laptops");
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
    }