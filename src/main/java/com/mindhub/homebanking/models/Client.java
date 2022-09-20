package com.mindhub.homebanking.models;
import javax.persistence.*;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
  private Set<Account> accounts = new HashSet<>(); //Accounts es una propiedad de clients de la linea 17

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>(); //Accounts es una propiedad de clients de la linea 17


    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();
    private String firstName; //propiedad
    private String lastName; //propiedad
    private String email; //propiedad
    private String password;

    public Client() {
    }

    public Client(String first, String last, String email, String password) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.password = password;

    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }
    public void setLastName(String lastName) {

        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() { //Retorna un LONG
        return id;
    }

    public String toString() {
        return firstName + " " + lastName; //retorna un STRING
    }

    public Set<Account> getAccounts() {
        return accounts;
    } //retorna un SET de accounts

    public void addAccount(Account account) {
        account.setClient(this); // asignar este cliente, el cual fue asignado en la prop client de la cuenta.Ese cliente es el que ejecuta el metodo addacount
        accounts.add(account); //agrego la cuenta al sET de cuentas q se llama Accounts
    }

    public List<Loan> getLoan (){
    return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList()); //nos retorna el retorne el listado de préstamos de un cliente
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() {

        return cards;
    }
}

