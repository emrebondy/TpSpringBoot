package org.example.tpspringboot.tp_springboot.services.implement;

import org.example.tpspringboot.tp_springboot.entities.Annonce;
import org.example.tpspringboot.tp_springboot.repositories.AnnonceRepository;
import org.example.tpspringboot.tp_springboot.services.interfaces.AnnonceServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnnonceServiceImplement implements AnnonceServiceInterface {


    private final AnnonceRepository annonceRepository;

    @Autowired
    public AnnonceServiceImplement(AnnonceRepository annonceRepository) {
        this.annonceRepository = annonceRepository;
    }

    @Override
    public Annonce saveAnnonce(Annonce annonce) {
        System.out.println("dans creer");
        annonce.setDate(LocalDateTime.now());
        return annonceRepository.save(annonce);
    }

    @Override
    public List<Annonce> getAllAnnonces() {
        System.out.println("getAllAnnonces je suis là..........................");
        return annonceRepository.findAll();
    }

    @Override
    public Optional<Annonce> getAnnonceById(Long id) {
        return annonceRepository.findById(id);
    }

    @Override
    public Annonce updateAnnonce(Long id, Annonce annonce) {
        return annonceRepository.findById(id)
                .map(existingAnnonce -> {
                    existingAnnonce.setTitle(annonce.getTitle());
                    existingAnnonce.setDescription(annonce.getDescription());
                    existingAnnonce.setAdress(annonce.getAdress());
                    existingAnnonce.setMail(annonce.getMail());
                    existingAnnonce.setDate(LocalDateTime.now());
                    return annonceRepository.save(existingAnnonce);
                }).orElseThrow(() -> new RuntimeException("Annonce not found with id " + id));
    }

    @Override
    public void deleteAnnonce(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AccessDeniedException("Vous devez être admin pour pouvoir supprimer une annonce");
        }


        annonceRepository.deleteById(id);
    }
}

