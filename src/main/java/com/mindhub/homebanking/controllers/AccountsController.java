package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountsController {
    @Autowired
    private AccountRepository accountRepository; //los repositorios son una INTERFAZ
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

//serv let
    @GetMapping("/api/account")
    public List<AccountDTO> getAccounts(){
    //    return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
      return accountService.getAllAccounts().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    //servlet
    @GetMapping("/api/account/{id}")
    public  AccountDTO getAccount(@PathVariable long id){
     //   return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
        return new AccountDTO(accountService.getAccountById(id));
    }

    @PostMapping("/api/clients/current/accounts")

    public ResponseEntity<Object> createCurrentClientAccount(Authentication authentication){

    String randomNumber = "VIN-" + getRandomNumber(0, 99999999);

  //  Client newCurrentClient = clientRepository.findByEmail(authentication.getName());

        Client newCurrentClient = clientService.getClientByEmail(authentication.getName());

    if(newCurrentClient.getAccounts().size() >= 3) {

        return new ResponseEntity<>("You have 3 accounts. You cannot created more.", HttpStatus.FORBIDDEN);

    }
        //accountRepository.save (new Account(randomNumber,.0,LocalDateTime.now(),newCurrentClient));
    accountService.saveAccount(new Account(randomNumber,.0,LocalDateTime.now(),newCurrentClient, AccountType.CURRENT));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
public int getRandomNumber (int min, int max){
        return (int) (Math.random()*(max-min) + min);
}



    @PatchMapping ("/api/clients/current/accounts/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable long id){
        Account accountOfClient = accountService.getAccountById(id);

       if( accountOfClient.getBalance()> 0) {


       }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}











