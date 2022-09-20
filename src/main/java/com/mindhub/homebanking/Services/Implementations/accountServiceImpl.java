package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class accountServiceImpl implements AccountService {

@Autowired
AccountRepository accountRepository;


 @Override
 public void saveAccount(Account account){
     accountRepository.save(account);
 }

@Override
public List<Account> getAllAccounts() {
    return (accountRepository.findAll());
}
@Override
public Account getAccountById(long id){
     return accountRepository.findById(id).get(); //.get() porq le pido que me "dé" el client q encontró.
 }

 @Override
    public Account getAccountByNumber(String number){
     return accountRepository.findByNumber(number);
 }


}
