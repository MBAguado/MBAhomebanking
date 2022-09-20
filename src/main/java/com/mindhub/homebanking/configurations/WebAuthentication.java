package com.mindhub.homebanking.configurations;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired

    ClientRepository clientRepository;


    @Override //sobreescribe al metodo "init" q recibe como parámetro "AuthenticationManagerBuilder" y nos arroje una excepcion "Exception"
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName-> {
            Client client = clientRepository.findByEmail(inputName); //almacemanos en CLient algo de tipo client,(guardams un "cliente" que tenga ese Email)

            if (client != null) {
               if (client.getEmail().contains(".admin")){
                    return new User(client.getEmail(), client.getPassword(), //instanciamos (creamos) un nuevo objeto "User"
                            AuthorityUtils.createAuthorityList("ADMIN")); //aca creamos el session_id
                } else {
                   return new User(client.getEmail(), client.getPassword(), //instanciamos (creamos) un nuevo objeto "User"
                           AuthorityUtils.createAuthorityList("CLIENT")); //aca creamos el session_id
               }
            } else {

                throw new UsernameNotFoundException("Unknown user: " + inputName);

            }

        });

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }
}
