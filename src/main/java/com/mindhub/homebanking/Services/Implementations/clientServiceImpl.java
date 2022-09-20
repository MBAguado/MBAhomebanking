package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //le pongo esta notacion porq estoy generando un servicio
public class clientServiceImpl implements ClientService {  // implements porque quiero inplementar al ClientService

    @Autowired //le inyecto el repo de clientes porque aca si quiero usar el repo de clientes, se lo inyecto
    ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    @Override
    public Client getClientById(long id){
      return clientRepository.findById(id).get(); //.get() porq le pido que me "dé" el client q encontró.

    }
    @Override
    public Client getClientByEmail(String email){
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client){
       clientRepository.save(client);
    }

}
