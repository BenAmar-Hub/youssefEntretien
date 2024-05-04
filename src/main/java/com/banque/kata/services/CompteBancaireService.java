package com.banque.kata.services;

import com.banque.kata.dtos.CompteCourantDTO;
import com.banque.kata.dtos.CompteDTO;
import com.banque.kata.dtos.LivretDTO;
import com.banque.kata.dtos.ReleveDeCompteDTO;
import com.banque.kata.entities.Compte;
import com.banque.kata.entities.Livret;
import com.banque.kata.exceptions.BalanceNotSufficentException;
import com.banque.kata.exceptions.BankAccountNotFoundException;
import com.banque.kata.exceptions.CeilingExceededException;

import java.util.Date;
import java.util.List;

public interface CompteBancaireService {
    LivretDTO saveLivretCompte(double plafond, double solde);
    CompteCourantDTO saveCompteCourant(boolean autorisation, double montantDecouvert, double solde);
    CompteDTO getCompte(String numCompte) throws BankAccountNotFoundException;
    void retrait(String numCompte, double montant) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void depot(String numCompte, double montant) throws BankAccountNotFoundException, CeilingExceededException;
    ReleveDeCompteDTO releve(String numCompte, Date dateEmission) throws BankAccountNotFoundException;
}
