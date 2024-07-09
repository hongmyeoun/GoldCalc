package com.hongmyeoun.goldcalc.model.homework

import com.hongmyeoun.goldcalc.model.constants.Raid

class PhaseInfo(
    difficulty: String,
    isClearCheck: Boolean,
    moreCheck: Boolean,
    isChecked: Boolean,
    private val noHardWithSolo: Boolean = false,
    private val seeMoreGoldN: Int,
    private val seeMoreGoldH: Int,
    private val seeMoreGoldS: Int? = null,
    private val clearGoldN: Int,
    private val clearGoldH: Int,
    private val clearGoldS: Int? = null
) {
    var level = difficulty
    var seeMoreCheck = moreCheck
    var clearCheck = isClearCheck
    var showCheck = isChecked
    private var seeMoreGold = 0
    private var clearGold = 0
    var totalGold = 0


    init {
        // 객체가 생성될 때 초기값 설정
        seeMoreGold = GoldCalcFunction.smgCalculator(seeMoreCheck, level, seeMoreGoldN, seeMoreGoldH, seeMoreGoldS)
        clearGold = GoldCalcFunction.cgCalculator(clearCheck, level, clearGoldN, clearGoldH, clearGoldS)
        totalGold = GoldCalcFunction.totalCGCalculator(clearGold, seeMoreGold)
    }

    fun onLevelClicked() {
        levelClicked()
        seeMoreGoldCalculate(seeMoreGoldN, seeMoreGoldH, seeMoreGoldS)
        clearGoldCalculate(clearGoldN, clearGoldH, clearGoldS)
        totalClearGoldCalculate()
    }

    fun onClearCheckBoxClicked(phaseChecked: Boolean) {
        updateClearCheck(phaseChecked)
        clearGoldCalculate(clearGoldN, clearGoldH, clearGoldS)
        totalClearGoldCalculate()
    }

    fun onSeeMoreCheckBoxClicked(phaseChecked: Boolean) {
        updateSeeMoreCheck(phaseChecked)
        seeMoreGoldCalculate(seeMoreGoldN, seeMoreGoldH, seeMoreGoldS)
        totalClearGoldCalculate()
    }

    fun onShowChecked() {
        showCheck = !showCheck
        if (!showCheck) {
            clearCheck = false
            seeMoreCheck = false
            seeMoreGoldCalculate(seeMoreGoldN, seeMoreGoldH, seeMoreGoldS)
            clearGoldCalculate(clearGoldN, clearGoldH, clearGoldS)
            totalClearGoldCalculate()
        }
    }

    private fun levelClicked() {
        level = GoldCalcFunction.levelDetector(level, noHardWithSolo)
    }

    private fun seeMoreGoldCalculate(seeMoreGoldN: Int, seeMoreGoldH: Int, seeMoreGoldS: Int?) {
        seeMoreGold = GoldCalcFunction.smgCalculator(seeMoreCheck, level, seeMoreGoldN, seeMoreGoldH, seeMoreGoldS)
    }

    private fun clearGoldCalculate(clearGoldN: Int, clearGoldH: Int, clearGoldS: Int?) {
        clearGold = GoldCalcFunction.cgCalculator(clearCheck, level, clearGoldN, clearGoldH, clearGoldS)
    }

    private fun totalClearGoldCalculate() {
        totalGold = GoldCalcFunction.totalCGCalculator(clearGold, seeMoreGold)
    }

    private fun updateClearCheck(isChecked: Boolean) {
        clearCheck = GoldCalcFunction.phaseChecked(isChecked)
    }

    private fun updateSeeMoreCheck(isChecked: Boolean) {
        seeMoreCheck = GoldCalcFunction.phaseChecked(isChecked)
    }
}

object GoldCalcFunction {
    fun levelDetector(level: String, noHardWithSolo: Boolean): String {
        return if (noHardWithSolo) {
            if (level == Raid.Difficulty.KR_NORMAL) {
                Raid.Difficulty.KR_SOLO
            } else {
                Raid.Difficulty.KR_NORMAL
            }
        } else {
            when (level) {
                Raid.Difficulty.KR_NORMAL -> {
                    Raid.Difficulty.KR_HARD
                }
                Raid.Difficulty.KR_HARD -> {
                    Raid.Difficulty.KR_SOLO
                }
                else -> {
                    Raid.Difficulty.KR_NORMAL
                }
            }
        }
    }

    fun smgCalculator(isChecked: Boolean, level: String, seeMoreGoldN: Int, seeMoreGoldH: Int, seeMoreGoldS: Int?): Int {
        return if (isChecked && level == Raid.Difficulty.KR_NORMAL) seeMoreGoldN else if (isChecked && level == Raid.Difficulty.KR_HARD) seeMoreGoldH else if (isChecked && seeMoreGoldS != null && level == Raid.Difficulty.KR_SOLO) seeMoreGoldS else 0
    }

    fun cgCalculator(isChecked: Boolean, level: String, clearGoldN: Int, clearGoldH: Int, clearGoldS: Int?): Int {
        return if (isChecked && level == Raid.Difficulty.KR_NORMAL) clearGoldN else if (isChecked && level == Raid.Difficulty.KR_HARD) clearGoldH else if (isChecked && clearGoldS != null && level == Raid.Difficulty.KR_SOLO) clearGoldS else 0
    }

    fun totalCGCalculator(cg: Int, smg: Int): Int {
        return cg - smg
    }

    fun phaseChecked(phaseCheck: Boolean): Boolean {
        return phaseCheck
    }
}
