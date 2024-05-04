package com.banque.kata.dtos;

import com.banque.kata.entities.Operation;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CompteCourantDTO extends CompteDTO implements Serializable {
    private String  numeroCompte;
    private double solde;
    private boolean autorisation;
    private double montantDecouvert;
    private List<OperationDTO> operations;
}
