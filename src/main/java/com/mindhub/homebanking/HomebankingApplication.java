package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;
import static java.util.Arrays.asList;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) { //main es mi método principal
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean //nos dice q dentro de la app éste sea el primer "frijol" q saque del saco dde tengo todos los frijoles (la app)
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) { //initData es el nombre qle doy al metodo ComandLineRunner q nos devuelve ComandLineRunner (las pirmeras lineas de cod. q se van a ejecutar cuando se inicie mi cod.)
		return (args) -> {

			Client client1 = new Client("Melba","Morel","melba@gmail.com",passwordEncoder.encode("melba1234")); // lo pongo en  una variable para poder relacionar luego en la cuentalinea 28
			Client client2 = new Client("Marta","Serralima","martita.admin@gmail.com", passwordEncoder.encode("marta123"));

			Account cuenta3 = new Account("VIN003", 25.0, LocalDateTime.now(), client2, AccountType.CURRENT); //CONSTRUCTOR 1
			Account cuenta1 = new Account("VIN001", 5000.0, LocalDateTime.now(), client1, AccountType.SAVING); //CONSTRUCTOR 1
			Account cuenta2 = new Account("VIN002", 7500.0,  LocalDateTime.now().plusDays(1) ); // CONSTRUCTOR 2

			Transaction transaction1 = new Transaction(cuenta1, DEBIT, -500.00, "Primera Transaccion", LocalDateTime.now());
			Transaction transaction2 = new Transaction(cuenta1, CREDIT, 876.00, "Segunda Transaccion", LocalDateTime.now());
			Transaction transaction3 = new Transaction(cuenta2, CREDIT, 2000.00, "Tercer Transaccion", LocalDateTime.now());

			Loan MORTGAGE = new Loan("MORTGAGE", 500000.00, List.of(12, 24, 36, 48, 60));
			Loan PERSONAL = new Loan("PERSONAL", 100000.00,List.of(6, 12, 24));
			Loan AUTOMOTIVE= new Loan("AUTOMOTIVE", 300000.00,List.of(6, 12, 24, 36));


			ClientLoan clientLoan1 = new ClientLoan(400000.00, 60, client1, MORTGAGE);
			ClientLoan clientLoan2 = new ClientLoan(50000.00, 12, client1, PERSONAL);
			ClientLoan clientLoan3 = new ClientLoan(100000.00, 24, client2, PERSONAL);
			ClientLoan clientLoan4 = new ClientLoan(20000.00, 36, client1, AUTOMOTIVE);
			client1.addAccount(cuenta2); // asociando usando el metodo addAccount

			Card card1 = new Card(client1,client1.getFirstName()+" "+ client1.getLastName(),"4548 2040 3000 4523", 222, LocalDateTime.now(), LocalDateTime.now(),CardType.DEBIT, CardColor.GOLD, true);
			Card card2 = new Card(client1, client1.getFirstName()+" "+ client1.getLastName(), "4548 2040 3800 4663", 444,LocalDateTime.now(), LocalDateTime.now().plusYears(5),CardType.CREDIT, CardColor.TITANIUM,true );
			Card card3 = new Card(client2, client2.getFirstName()+" "+ client2.getLastName(), "4548 2060 3050 4598", 111,LocalDateTime.now(), LocalDateTime.now().plusYears(5),CardType.CREDIT, CardColor.SILVER,  true);

			clientRepository.save(client1);
			clientRepository.save(client2);
			accountRepository.save(cuenta1);
			accountRepository.save(cuenta2);
			accountRepository.save(cuenta3);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);

			loanRepository.save(MORTGAGE);
			loanRepository.save(PERSONAL);
			loanRepository.save(AUTOMOTIVE);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
		};
	}
	@Autowired
	private PasswordEncoder passwordEncoder;

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
}

