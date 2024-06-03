package com.hongmyeoun.goldcalc.model.searchedInfo.card

class CardDetail(private val cardsWithEffects: CardsWithEffects) {
    fun getCardsDetails(): List<Cards> {
        return cardsWithEffects.cards
    }

    fun getCardEffects(): List<CardEffects> {
        return cardsWithEffects.effects
    }
}