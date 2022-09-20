package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.LoanService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class LoanController {
//    @Autowired
//    private LoanRepository loanRepository;  //desacoplamos los repo para liberar al controller de los repo, a través de los servicios!
//    @Autowired
//    private ClientRepository clientRepository;
//    @Autowired
//    private AccountRepository accountRepository;
//    @Autowired
//    private TransactionRepository transactionRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired  //inyeccion de dependencia, le decims a nuestra app que vamos a trabajar con el servicio de ese repo
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private TransactionService transactionService;


    @Transactional //volver al punto de inicio por si tenemos algun error, pro q es de tipo trnasaccion, por seguridad esta @
    @PostMapping("/api/loans") //antes era requestMapping. Es una anotacion, que Asocia una ruta con un metodo (addLoan)
   public ResponseEntity<Object> addLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {  //este metodo nos devuelve una respuesta "http status"

        //Client currentClientAuthentication = clientRepository.findByEmail(authentication.getName());
        Client currentClientAuthentication = clientService.getClientByEmail(authentication.getName());

        //Loan loanId =loanRepository.findById(loanApplicationDTO.getId());
        Loan loanId = loanService.getLoanById(loanApplicationDTO.getId());

       // Account accountLoan = accountRepository.findByNumber(loanApplicationDTO.getDestinyAccount());
       Account accountLoan = accountService.getAccountByNumber(loanApplicationDTO.getDestinyAccount());


//        if(!loanService.getAllLoans().stream().map(loan -> loan.getId()).collect(Collectors.toList()).contains(requestedLoan.getId())){
//            return new ResponseEntity<>("This is not a valid type of loan.", HttpStatus.FORBIDDEN);
//        }

        if (loanApplicationDTO.getAmount() <= 0 ) { //Verificar que el campo de Amount no esté vacio o sea negativo el monto ingresado

            return new ResponseEntity<>("Please, enter required loan amount", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getPayments() <= 0 ) { //Verificar que se haya seleccionado un Payment(cuota)
            return new ResponseEntity<>("Please, choose a payment", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getDestinyAccount()==null) { //Verificar que la cuenta de destino donde se depositará el prestamo, exista.
            return new ResponseEntity<>("Please, choose a destiny account", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getId()==0) {
            return new ResponseEntity<>("Please, choose a loan", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getId()>3) {
            return new ResponseEntity<>("Please, choose a loan", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getAmount() > loanId.getMaxAmount()){ //verifica que el monto solicitado no exceda el monto maximo del préstamo
            return new ResponseEntity<>("Amount limit exceded", HttpStatus.FORBIDDEN);
        }

        if (loanService.getLoanById(loanApplicationDTO.getId()) == null) { //Verificar que el préstamo exista

            return new ResponseEntity<>("Loan dosen´t exit", HttpStatus.FORBIDDEN);
        }
        if (accountService.getAccountByNumber(loanApplicationDTO.getDestinyAccount()) == null) { //Verificar que la cuenta de destino exista

            return new ResponseEntity<>("Destiny account dosen´t exit", HttpStatus.FORBIDDEN);
        }

        if ( !loanId.getPayments().contains(loanApplicationDTO.getPayments())) { //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo

            return new ResponseEntity<>("Payments dosen´t allowed", HttpStatus.FORBIDDEN);
        }

        Transaction transactionLoan = new Transaction(accountLoan, TransactionType.CREDIT, loanApplicationDTO.getAmount(), loanId.getName() + " loan approved", LocalDateTime.now());
        double interestPlus = ((loanApplicationDTO.getAmount())*0.2 ) + loanApplicationDTO.getAmount();
       transactionService.saveTransaction(transactionLoan);
        accountLoan.setBalance(accountLoan.getBalance()+ loanApplicationDTO.getAmount());

        ClientLoan loanPetition = new ClientLoan(interestPlus,loanApplicationDTO.getPayments(),currentClientAuthentication, loanId);
        clientLoanRepository.save(loanPetition);

        return new ResponseEntity<>(HttpStatus.CREATED);
        }

        @GetMapping("/api/loans")
    public  List<LoanDTO> getAll(){ //este metodo lo usamos para en vue hacer generateLoan para obtener los datos de los intereses y generar el loan
        return loanService.getAllLoans().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    }
