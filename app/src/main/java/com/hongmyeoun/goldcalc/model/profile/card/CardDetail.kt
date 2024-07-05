package com.hongmyeoun.goldcalc.model.profile.card

class CardDetail(private val cardsWithEffects: CardsWithEffects) {
    fun getCardsDetails(): List<Cards> {
        return cardsWithEffects.cards
    }

    fun getCardEffects(): List<CardEffects> {
        return cardsWithEffects.effects
    }
}