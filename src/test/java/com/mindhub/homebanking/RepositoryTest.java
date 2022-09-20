package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE) //Las anotaciones @DataJpaTest y @AutoConfigureTestDatabase(replace = NONE) indican a Spring que debe escanear en busca de clases @Entity y configurar los repositorios JPA.

    public class RepositoryTest {

        @Autowired
        LoanRepository loanRepository;
        @Autowired
        ClientRepository clientRepository;
        @Autowired
        AccountRepository accountRepository;



        @Test
        public void existLoans(){

            List<Loan> loans = loanRepository.findAll();

            assertThat(loans,is(not(empty())));
        }

        @Test

        public void existPersonalLoan(){

            List<Loan> loans = loanRepository.findAll();

            assertThat(loans, hasItem(hasProperty("name", is("PERSONAL"))));

        }
        @Test
        public void existClients(){
            List<Client> clients = clientRepository.findAll();

            assertThat(clients,is(not(empty())));
        }

        @Test
        public void existProperty(){
            List<Client> clients = clientRepository.findAll();
            assertThat(clients, hasItem(hasProperty("firstName", is("Melba"))));
        }

        @Test
        public void existAccounts(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts, is(not(empty())));
        }
        @Test
    public void existNumberAccounts(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts, hasItem(hasProperty("number", is("VIN001"))) );
        }

    }

