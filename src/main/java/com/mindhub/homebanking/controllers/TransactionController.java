package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@RestController
public class TransactionController {
     @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @Transactional // si hay algun error vuelve al estado incial.  Spring se encargará de revertir las operaciones realizadas en caso de ocurrir un error.
    @PostMapping( "/api/transactions")
    public ResponseEntity<Object> newTransaction(@RequestParam Double amount, @RequestParam String description, @RequestParam String originAccount,
                                                 @RequestParam String destinyAccount, Authentication authentication) {

        Client newClientAuthentication = clientService.getClientByEmail(authentication.getName());
        Account newOriginAccount = accountService.getAccountByNumber(originAccount);
        Account newDestinyAccount =accountService.getAccountByNumber(destinyAccount);

        if (amount <= 0 || amount == null) {

            return new ResponseEntity<>("Invalid amount", HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty() ) {

            return new ResponseEntity<>("Please, complete description transfers", HttpStatus.FORBIDDEN);
        }
        if (originAccount.isEmpty() ) {

            return new ResponseEntity<>("Please, complete the Origin Account", HttpStatus.FORBIDDEN);
        }
        if (destinyAccount.isEmpty()) {

            return new ResponseEntity<>("Please, complete the Destiny Account", HttpStatus.FORBIDDEN);
        }

        if (originAccount.equals(destinyAccount)) {
            return new ResponseEntity<>("Origin and Destiny are the Same account´s", HttpStatus.FORBIDDEN);
        }
        if (accountService.getAccountByNumber(originAccount) == null) {
            return new ResponseEntity<>("Missing account origin", HttpStatus.FORBIDDEN);
        }

        if (accountService.getAccountByNumber(destinyAccount) == null) {
            return new ResponseEntity<>("Missing account destiny", HttpStatus.FORBIDDEN);
        }

        if (accountService.getAccountByNumber(originAccount).getBalance() < amount) {
            return new ResponseEntity<>("Not enough money", HttpStatus.FORBIDDEN);
        }


        Transaction debitTransaction = new Transaction(newOriginAccount, DEBIT, - amount, description + " go to " + destinyAccount, LocalDateTime.now());
        Transaction creditTransaction = new Transaction(newDestinyAccount, CREDIT, amount, description + " from " + originAccount, LocalDateTime.now());


        transactionService.saveTransaction(debitTransaction);
        transactionService.saveTransaction(creditTransaction);


        newOriginAccount.setBalance(newOriginAccount.getBalance()- amount);
        newDestinyAccount.setBalance(newDestinyAccount.getBalance()+ amount);

        accountService.saveAccount(newOriginAccount);
        accountService.saveAccount(newDestinyAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}