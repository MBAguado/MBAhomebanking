package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Card;

import java.util.List;


public interface CardService {

    public void saveCard(Card card);

    public Card getCardById(long id);

    public Card getCardByNumber (String number);
}
