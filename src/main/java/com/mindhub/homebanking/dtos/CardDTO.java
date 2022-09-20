package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class CardDTO {

    private long id;
    private String cardHolder;

    private String number;

    private int cvv;

    private LocalDateTime fromDate;

    private LocalDateTime thruDate;

    private CardType cardType;

    private CardColor cardColor;

   private boolean cardState;

    public CardDTO(){
    }

    public CardDTO(Card card ){
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.cardType = card.getCardType();
        this.cardColor = card.getCardColor();
        this.cardState = card.isCardState();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public boolean isCardState() {
        return cardState;
    }
}
