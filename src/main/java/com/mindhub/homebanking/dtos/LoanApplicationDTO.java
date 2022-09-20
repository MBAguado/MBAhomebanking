package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public class LoanApplicationDTO {
    private long id;
    private Double amount;
    private Integer payments;
    private String destinyAccount;

    public LoanApplicationDTO(){

    }
    public LoanApplicationDTO(long id, double amount, Integer payments, String destinyAccount){ // lo usamos p/hacer la peticion del prestam. Lo q nos mandan desde el front.
        this.id = id;
        this.amount = amount;
       this.payments = payments;
        this.destinyAccount = destinyAccount;
    }

    public long getId() {return id;}

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setDestinyAccount(String destinyAccount) {
        this.destinyAccount = destinyAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {return payments;}

    public String getDestinyAccount() {
        return destinyAccount;
    }

    public LoanApplicationDTO(Loan loan){



    }
}
