package com.banque.kata.repositories;

import com.banque.kata.entities.Livret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LivretRepository extends JpaRepository<Livret, String> {
}
