package com.hongmyeoun.goldcalc.model.common

import com.hongmyeoun.goldcalc.R

object ImageReturn {
    fun goldImage(gold: Int): Int {
        return when (gold) {
            in 0 until 10000 -> R.drawable.gold_coins
            in 10000 until 20000 -> R.drawable.gold_bar
            in 20000 until 50000 -> R.drawable.gold_box
            else -> R.drawable.gold_bar_many
        }
    }
}