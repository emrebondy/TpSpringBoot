package org.example.tpspringboot.tp_springboot.repositories;


import org.example.tpspringboot.tp_springboot.entities.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

}
