package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource // le indica a Spring que debe genera el código necesario para que se pueda administrar la data de la aplicación desde el navegador usando JSON,es decir se crea una API REST
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
