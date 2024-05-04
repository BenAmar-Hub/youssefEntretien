package com.banque.kata.services;

import com.banque.kata.dtos.CompteCourantDTO;
import com.banque.kata.dtos.CompteDTO;
import com.banque.kata.dtos.LivretDTO;
import com.banque.kata.dtos.ReleveDeCompteDTO;
import com.banque.kata.entities.Compte;
import com.banque.kata.entities.CompteCourant;
import com.banque.kata.entities.Livret;
import com.banque.kata.entities.Operation;
import com.banque.kata.enums.OperationType;
import com.banque.kata.exceptions.BalanceNotSufficentException;
import com.banque.kata.exceptions.BankAccountNotFoundException;
import com.banque.kata.exceptions.CeilingExceededException;
import com.banque.kata.mappers.CompteCourantMapper;
import com.banque.kata.mappers.LivretMapper;
import com.banque.kata.mappers.OperationMapper;
import com.banque.kata.repositories.CompteCourantRepository;
import com.banque.kata.repositories.CompteRepository;
import com.banque.kata.repositories.LivretRepository;
import com.banque.kata.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CompteBancaireServiceImpl implements CompteBancaireService{
    private final LivretRepository livretRepository;
    private final CompteRepository compteRepository;
    private final CompteCourantRepository courantRepository;
    private final LivretMapper livretMapper;
    private final CompteCourantMapper courantMapper;
    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;
    @Override
    public LivretDTO saveLivretCompte(double plafond, double solde) {
        Livret livret=new Livret();
        livret.setNumeroCompte(UUID.randomUUID().toString());
        livret.setSolde(solde);
        livret.setPlafond(plafond);
        Livret savedLivret = livretRepository.saveAndFlush(livret);
        log.info("Compte Epargne Crée !");
        return livretMapper.fromEntityToDto(savedLivret);
    }
    @Override
    public CompteCourantDTO saveCompteCourant(boolean autorisation, double montantDecouvert, double solde) {
        CompteCourant compteCourant= new CompteCourant();
        compteCourant.setNumeroCompte(UUID.randomUUID().toString());
        compteCourant.setSolde(solde);
        compteCourant.setAutorisation(autorisation);
        compteCourant.setMontantDecouvert(montantDecouvert);
        CompteCourant savedCompteCourant = courantRepository.saveAndFlush(compteCourant);
        log.info("Compte Courant Crée !");
        return courantMapper.fromEntityToDto(savedCompteCourant);
    }

    @Override
    public CompteDTO getCompte(String numCompte) throws BankAccountNotFoundException {
        Compte compte=compteRepository.findById(numCompte).orElseThrow(()->
                new BankAccountNotFoundException("Compte non trouvé !"));
        if( compte instanceof Livret){
            Livret livretAccount=(Livret) compte;
            return livretMapper.fromEntityToDto(livretAccount);
        } else {
            CompteCourant currentAccount=(CompteCourant) compte;
            return courantMapper.fromEntityToDto(currentAccount);
        }
    }

    @Override
    public void retrait(String numCompte, double montant) throws BankAccountNotFoundException, BalanceNotSufficentException {
        Compte compte=compteRepository.findById(numCompte).orElseThrow(()->
                new BankAccountNotFoundException("Compte non trouvé !"));
        if ( compte.getSolde()>=montant){
            Operation operation=new Operation();
            operation.setType(OperationType.RETRAIT);
            operation.setOperationDate(new Date());
            operation.setMontant(montant);
            operation.setCompte(compte);
            operationRepository.saveAndFlush(operation);
            compte.setSolde(compte.getSolde()-montant);
            compteRepository.saveAndFlush(compte);
        } else if (compte instanceof CompteCourant) {
            CompteCourant compteCourant= (CompteCourant) compte;
            if ( compteCourant.isAutorisation() &&
                    compteCourant.getSolde()-montant+compteCourant.getMontantDecouvert()>=0){
                Operation operation=new Operation();
                operation.setType(OperationType.RETRAIT);
                operation.setOperationDate(new Date());
                operation.setMontant(montant);
                operation.setCompte(compteCourant);
                operationRepository.saveAndFlush(operation);
                compteCourant.setSolde(compteCourant.getSolde()-montant);
                compteRepository.saveAndFlush(compteCourant);
            }else throw new BalanceNotSufficentException("Montant Découvert Dépassé !");
        }else throw new BalanceNotSufficentException("Solde insuffisant !");
    }

    @Override
    public void depot(String numCompte, double montant) throws BankAccountNotFoundException, CeilingExceededException {
        Compte compte=compteRepository.findById(numCompte).orElseThrow(()->
                new BankAccountNotFoundException("compte introuvable !"));
        if (compte instanceof CompteCourant){
            CompteCourant compteCourant= (CompteCourant) compte;
            Operation operation=new Operation();
            operation.setType(OperationType.DEPOT);
            operation.setOperationDate(new Date());
            operation.setMontant(montant);
            operation.setCompte(compteCourant);
            operationRepository.saveAndFlush(operation);
            compteCourant.setSolde(compteCourant.getSolde()+montant);
            courantRepository.saveAndFlush(compteCourant);
    } else{
            Livret livret= (Livret) compte;
            if (livret.getPlafond()>= montant){
                Operation operation=new Operation();
                operation.setType(OperationType.DEPOT);
                operation.setOperationDate(new Date());
                operation.setMontant(montant);
                operation.setCompte(livret);
                operationRepository.saveAndFlush(operation);
                livret.setSolde(livret.getSolde()+montant);
                livretRepository.saveAndFlush(livret);
            }else throw new CeilingExceededException("Plafond Dépassé !");
        }
        }

    @Override
    public ReleveDeCompteDTO releve(String numCompte, Date dateEmission) throws BankAccountNotFoundException {
        Compte compte=compteRepository.findById(numCompte).orElseThrow(()->
                new BankAccountNotFoundException("compte introuvable !"));
        ReleveDeCompteDTO releveDeCompteDTO=new ReleveDeCompteDTO();
        releveDeCompteDTO.setNumeroCompte(numCompte);
        if (compte instanceof CompteCourant){
            releveDeCompteDTO.setTypeCompte("CompteCourant");
        }else {
            releveDeCompteDTO.setTypeCompte("Livret");
        }
        releveDeCompteDTO.setSolde(compte.getSolde());
        releveDeCompteDTO.setDateEmission(dateEmission);
        List<Operation> operations = operationRepository.releveBancaire(numCompte, dateEmission);
        releveDeCompteDTO.setOperationDTOS(operationMapper.fromEntitiesToDtoList(operations));
        return releveDeCompteDTO;
    }

}
