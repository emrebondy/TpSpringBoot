package org.example.tpspringboot.tp_springboot.services.implement;

import org.example.tpspringboot.tp_springboot.entities.Annonce;
import org.example.tpspringboot.tp_springboot.repositories.AnnonceRepository;
import org.example.tpspringboot.tp_springboot.services.interfaces.AnnonceServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnnonceServiceImplement implements AnnonceServiceInterface {


    private final AnnonceRepository annonceRepository;

    @PostConstruct
    public void initData() {
        if (annonceRepository.count() == 0) { // Vérifie si la BD est vide
            annonceRepository.saveAll(List.of(
                    new Annonce("clio 3", "clio 3 très propre", "3 rue des clio", "clio3@gmail.com", LocalDateTime.now()),
                    new Annonce("clio 4", "clio 4 très propre", "4 rue des clio", "clio4@gmail.com", LocalDateTime.now()),
                    new Annonce("pc portable", "pc portable en bonne état", "9 rue des pc portable", "pcPortable@gmail.com", LocalDateTime.now()),
                    new Annonce("iphone 15 pro max", "iphone 15 pro max en très bonne état", "15 rue des pro max", "iphoneProMax@gmail.com", LocalDateTime.now()),
                    new Annonce("samsung s24", "samsung s24", "24 rue des samsung", "samsung@gmail.com", LocalDateTime.now())
            ));
        }
    }

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

