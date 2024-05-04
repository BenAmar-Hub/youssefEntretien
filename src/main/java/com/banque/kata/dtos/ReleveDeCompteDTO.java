package com.banque.kata.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ReleveDeCompteDTO {
    private String numeroCompte;
    private String typeCompte;
    private double solde;
    private Date dateEmission;
    private List<OperationDTO> operationDTOS;
}
