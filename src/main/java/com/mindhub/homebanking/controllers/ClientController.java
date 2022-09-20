package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController //para que me retorne un Json en vez de una web
@RequestMapping("/api") //request: una peticion para asociar. Le digo q la ruta de inicioo del controller sera /api Para diferenciar la ruta de los servicios REST creados automáticamente
public class ClientController { //Este es el controlador que administras las peticiones q se realicen sobre la entidad Client.
    @Autowired
    //generamos una instancia (crear un nuevo objeto) del ClientRepositorio el concepto es: inyeccion de dependencia)
    private ClientRepository clientRepository; //el nombre se lo pongo al repositorio, porq generamos la instancia del repositorio en la linea de arriba
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientService clientService; //inyecto la dependencia del servicio para poder usar el metodo del servicio
    @Autowired
private AccountService accountService;

    @RequestMapping("/clients") // es una peticion(request) asociada(mapping) a una ruta.
    public List<ClientDTO> getClients() {   //este metodo nos retorna una lista de client "list<client>", yendo al repositorio del client y retornando una list
        return clientService.getAllClients().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList()); //convierte la lista en stream(es una coleccion) para poder mapearlo
    } //este metodo esta desacloplado, porq le pusimoso el clientService.GetAllClients()

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) { //@PathVariable dice que el parametro q esta anotando, en este caso long id, va a estar teniendo el valor de variable de ruta q esté en esa ruta entre llaves {}

        return new ClientDTO(clientService.getClientById(id));
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/clients")

    public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {


        if (firstName.isEmpty() ) {

            return new ResponseEntity<>("Missing first name", HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty()) {

            return new ResponseEntity<>("Missing last name", HttpStatus.FORBIDDEN);
        }
        if ( email.isEmpty()) {

            return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()) {

            return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);
        }


        if (clientService.getClientByEmail(email) != null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client registerClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        String randomNumber = "VIN-" + getRandomNumber(0, 99999999);

        clientService.saveClient(registerClient);

     //   accountRepository.save (new Account(randomNumber,.0, LocalDateTime.now(),registerClient));
        accountService.saveAccount(new Account(randomNumber,.0, LocalDateTime.now(),registerClient, AccountType.CURRENT));

        return new ResponseEntity<>(registerClient,HttpStatus.CREATED);
    }


    @GetMapping("/clients/current")

    public ClientDTO getCurrentClient(Authentication authentication) {

       // return new ClientDTO(clientRepository.findByEmail(authentication.getName()));

        return new ClientDTO(clientService.getClientByEmail(authentication.getName()));
    }


    public int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

}


