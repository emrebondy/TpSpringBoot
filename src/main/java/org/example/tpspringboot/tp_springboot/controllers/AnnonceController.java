package org.example.tpspringboot.tp_springboot.controllers;


import org.example.tpspringboot.tp_springboot.entities.Annonce;
import org.example.tpspringboot.tp_springboot.services.interfaces.AnnonceServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/annonces")
@CrossOrigin(origins = "http://localhost:4200")
public class AnnonceController {

    private final AnnonceServiceInterface annonceService;

    @Autowired
    public AnnonceController(AnnonceServiceInterface annonceService) {
        this.annonceService = annonceService;
    }

    @GetMapping
    public ResponseEntity<List<Annonce>> getAllAnnonces() {
        List<Annonce> annonces = annonceService.getAllAnnonces();
        return ResponseEntity.ok(annonces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Annonce> getAnnonceById(@PathVariable Long id) {
        return annonceService.getAnnonceById(id)
                .map(ResponseEntity::ok)

                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Annonce> createAnnonce(@RequestBody Annonce annonce) {
        Annonce savedAnnonce = annonceService.saveAnnonce(annonce);
        return ResponseEntity.ok(savedAnnonce);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Annonce> updateAnnonce(@PathVariable Long id, @RequestBody Annonce annonce) {
        Annonce updatedAnnonce = annonceService.updateAnnonce(id, annonce);
        return ResponseEntity.ok(updatedAnnonce);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Long id) {
        annonceService.deleteAnnonce(id);
        return ResponseEntity.noContent().build();
    }

}
