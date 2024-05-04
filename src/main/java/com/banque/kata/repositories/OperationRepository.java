package com.banque.kata.repositories;

import com.banque.kata.dtos.ReleveDeCompteDTO;
import com.banque.kata.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("select p FROM Operation p where p.operationDate<=:dateEmission and month(p.operationDate)=month (:dateEmission) " +
            "and p.compte.numeroCompte=:numCompte order by p.operationDate desc")
    List<Operation> releveBancaire(@Param("numCompte")String numCompte, @Param("dateEmission") Date dateEmission);
}
