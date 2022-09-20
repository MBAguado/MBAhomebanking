package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Loan;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.springframework.data.jpa.repository.JpaRepository;

@RepositoryRestResource // le indica a Spring que debe genera el código necesario para que se pueda administrar la data de la aplicación desde el navegador usando JSON,es decir se crea una API REST
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findById(long id);
}