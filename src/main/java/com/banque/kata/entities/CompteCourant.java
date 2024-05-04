package com.banque.kata.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("courant")
public class CompteCourant extends Compte implements Serializable {
private boolean autorisation;
private double montantDecouvert;
}
