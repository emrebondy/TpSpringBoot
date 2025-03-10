package org.example.tpspringboot.tp_springboot.services.interfaces;



import org.example.tpspringboot.tp_springboot.entities.Annonce;

import java.util.List;
import java.util.Optional;

public interface AnnonceServiceInterface {
    Annonce saveAnnonce(Annonce annonce);
    List<Annonce> getAllAnnonces();
    Optional<Annonce> getAnnonceById(Long id);
    Annonce updateAnnonce(Long id, Annonce annonce);
    void deleteAnnonce(Long id);
}

