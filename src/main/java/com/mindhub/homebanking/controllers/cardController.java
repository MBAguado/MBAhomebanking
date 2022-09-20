package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.mindhub.homebanking.models.CardType.DEBIT;

@RestController
public class cardController {

  @Autowired
  private CardRepository cardRepository;
  @Autowired
  private ClientRepository clientRepository;

@Autowired
private ClientService clientService;
@Autowired
private CardService cardService;

  @PostMapping( "/api/clients/current/cards")
  public ResponseEntity<Object> newCards(@RequestParam CardColor cardColor, @RequestParam CardType cardType, Authentication authentication){

   // Client newCurrentCard = clientRepository.findByEmail(authentication.getName());
    Client newCurrentCard = clientService.getClientByEmail(authentication.getName());

    String cardNumber =  getRandomNumber(1,9999)+ "-" + getRandomNumber(1,9999)+ "-" + getRandomNumber(1,9999)+ "-" + getRandomNumber(1,9999);
    Integer cvvNumber = getRandomNumber(100,999);
                //______________________//
               //Puede tener como máximo 3  tarjetas de CREDIT
              // Puede tener como máximo 3  tarjetas de  DEBIR
              //Puede tener como máximo HASTA 6  tarjetas en total
    if( cardType == null || cardColor == null  ) {

      return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

    } if ( newCurrentCard.getCards().stream().filter(card -> card.getCardType().equals(cardType)).count() >= 3 ) {

      return new ResponseEntity<>("You have enough cards of that type", HttpStatus.FORBIDDEN);
    }
              //______________________//

    //cardRepository.save(new Card(newCurrentCard, newCurrentCard.toString(), cardNumber, cvvNumber, LocalDateTime.now(), LocalDateTime.now().plusYears(5),cardType,cardColor));
    cardService.saveCard(new Card(newCurrentCard, newCurrentCard.toString(), cardNumber, cvvNumber, LocalDateTime.now(), LocalDateTime.now().plusYears(5),cardType,cardColor, true));
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
  public int getRandomNumber (int min, int max){
    return (int) ((Math.random()* (max - min))+min);
  }


@PatchMapping ("/api/clients/current/cards/cardState")
    public ResponseEntity<Object> deleteCards(@RequestParam String number, Authentication authentication){

    Client newCurrentClient = clientService.getClientByEmail(authentication.getName());
    Card cardOfClient = cardService.getCardByNumber(number);

    if( number.isEmpty()) {

      return new ResponseEntity<>("Select a card to delete", HttpStatus.FORBIDDEN);
    }

    cardOfClient.setCardState(false);
    cardService.saveCard((cardOfClient));

  return new ResponseEntity<>(HttpStatus.OK);

}

}
