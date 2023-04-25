package com.sunmnet.mediaroom.brand.bean.event;

public class SwipeCardEvent {
    private String cardId;

    public SwipeCardEvent() {
    }

    public SwipeCardEvent(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
