package com.banque.kata.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper = false)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 10, discriminatorType = DiscriminatorType.STRING)
public class Compte implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    private String  numeroCompte;
    private double solde;
    @OneToMany(mappedBy = "compte")
    private List<Operation> operations;
}
