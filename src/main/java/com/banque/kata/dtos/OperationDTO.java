package com.banque.kata.dtos;

import com.banque.kata.enums.OperationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OperationDTO implements Serializable {
    private Long id;
    private Date operationDate;
    private double montant;
    private OperationType type;
}
