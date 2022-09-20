package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface AccountService {
    public void saveAccount(Account account);

    public List<Account> getAllAccounts();

    public Account getAccountById(long id);

    public Account getAccountByNumber (String number);


}
