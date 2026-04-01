package com.hongmyeoun.goldcalc.model.common

import com.hongmyeoun.goldcalc.R

object ImageReturn {
    fun goldImage(gold: Int): Int {
        return when {
            gold < 0 -> R.drawable.gold_bound
            gold < 10000 -> R.drawable.gold_coins
            gold < 20000 -> R.drawable.gold_bar
            gold < 50000 -> R.drawable.gold_box
            else -> R.drawable.gold_bar_many
        }
    }
}