package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.LoanService;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class loanServiceImpl implements LoanService {

    @Autowired
   LoanRepository loanRepository;

    @Override
    public Loan getLoanById(long id){
        return loanRepository.findById(id);
    }

    @Override
    public List<Loan> getAllLoans(){
        return loanRepository.findAll();
    }
}
