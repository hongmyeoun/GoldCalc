package com.hongmyeoun.goldcalc.model.goldCheck

class PhaseInfo(
    difficulty: String,
    isClearCheck: Boolean,
    moreCheck: Boolean,
    private val seeMoreGoldN: Int,
    private val seeMoreGoldH: Int,
    private val clearGoldN: Int,
    private val clearGoldH: Int
) {
    var level = difficulty
    var seeMoreCheck = moreCheck
    var clearCheck = isClearCheck
    private var seeMoreGold = 0
    private var clearGold = 0
    var totalGold = 0


    init {
        // 객체가 생성될 때 초기값 설정
        seeMoreGold = GoldCalcFunction.smgCalculator(seeMoreCheck, level, seeMoreGoldN, seeMoreGoldH)
        clearGold = GoldCalcFunction.cgCalculator(clearCheck, level, clearGoldN, clearGoldH)
        totalGold = GoldCalcFunction.totalCGCalculator(clearGold, seeMoreGold)
    }

    fun onLevelClicked() {
        levelClicked()
        seeMoreGoldCalculate(seeMoreGoldN, seeMoreGoldH)
        clearGoldCalculate(clearGoldN, clearGoldH)
        totalClearGoldCalculate()
    }

    fun onClearCheckBoxClicked(isChecked: Boolean) {
        updateClearCheck(isChecked)
        clearGoldCalculate(clearGoldN, clearGoldH)
        totalClearGoldCalculate()
    }

    fun onSeeMoreCheckBoxClicked(isChecked: Boolean) {
        updateSeeMoreCheck(isChecked)
        seeMoreGoldCalculate(seeMoreGoldN, seeMoreGoldH)
        totalClearGoldCalculate()
    }

    private fun levelClicked() {
        level = GoldCalcFunction.levelDetector(level)
    }

    private fun seeMoreGoldCalculate(seeMoreGoldN: Int, seeMoreGoldH: Int) {
        seeMoreGold = GoldCalcFunction.smgCalculator(seeMoreCheck, level, seeMoreGoldN, seeMoreGoldH)
    }

    private fun clearGoldCalculate(clearGoldN: Int, clearGoldH: Int) {
        clearGold = GoldCalcFunction.cgCalculator(clearCheck, level, clearGoldN, clearGoldH)
    }

    private fun totalClearGoldCalculate() {
        totalGold = GoldCalcFunction.totalCGCalculator(clearGold, seeMoreGold)
    }

    private fun updateClearCheck(isChecked: Boolean) {
        clearCheck = GoldCalcFunction.checked(isChecked)
    }

    private fun updateSeeMoreCheck(isChecked: Boolean) {
        seeMoreCheck = GoldCalcFunction.checked(isChecked)
    }
}

object GoldCalcFunction {
    fun levelDetector(level: String): String {
        return if (level == "노말") "하드" else "노말"
    }

    fun smgCalculator(isChecked: Boolean, level: String, seeMoreGoldN: Int, seeMoreGoldH: Int): Int {
        return if (isChecked && level == "노말") seeMoreGoldN else if (isChecked && level == "하드") seeMoreGoldH else 0
    }

    fun cgCalculator(isChecked: Boolean, level: String, clearGoldN: Int, clearGoldH: Int): Int {
        return if (isChecked && level == "노말") clearGoldN else if (isChecked) clearGoldH else 0
    }

    fun totalCGCalculator(cg: Int, smg: Int): Int {
        return cg - smg
    }

    fun checked(isChecked: Boolean): Boolean {
        return isChecked
    }

}
