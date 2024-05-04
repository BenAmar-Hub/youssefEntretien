package com.banque.kata.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LivretDTO extends CompteDTO implements Serializable {
    private String  numeroCompte;
    private double solde;
    private double plafond;
    private List<OperationDTO> operations;
}
