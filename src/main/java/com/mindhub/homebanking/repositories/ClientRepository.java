package com.mindhub.homebanking.repositories;


import com.mindhub.homebanking.models.Client;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@RepositoryRestResource // le indica a Spring que debe genera el código necesario para que se pueda administrar la data de la aplicación desde el navegador usando JSON, es decir se crea una API REST
public interface ClientRepository extends JpaRepository<Client, Long> { // va a trabajar con datos Client, y su clave primaria será de tipo Long-ClientRepos extiende de JpaRep, por lo tanto ClientRepos es un JpaRep. Hereda de su padre JpaRep

Client findByEmail(String email);


}