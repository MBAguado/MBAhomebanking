package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    private String number;
    private Double balance;
    private LocalDateTime creationDate;

    private AccountType AccountType;


    public Account() {

    }

    public Account(String number, Double balance, LocalDateTime creationDate, Client client, AccountType accountType) { // CONSTRUCTOR 1, polimorfismo
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.client = client;
        this.AccountType = accountType;
    }

    public Account(String number, Double balance, LocalDateTime creationDate) { // CONSTRUCTOR 2, polimorfismo
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
    }

    public com.mindhub.homebanking.models.AccountType getAccountType() {
        return AccountType;
    }

    public long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Client getAccounts() {
        return client;
    }

    public void setAccount(Client client) {
        this.client = client;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    public Set<Transaction> getTransactions() {
        return transactions;
    }
    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}


