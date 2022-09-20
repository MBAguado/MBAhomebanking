package com.mindhub.homebanking.Services;


import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    public Loan getLoanById(long id);

    public List<Loan> getAllLoans();
}
