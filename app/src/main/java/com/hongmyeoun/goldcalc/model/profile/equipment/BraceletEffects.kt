package com.hongmyeoun.goldcalc.model.profile.equipment

object BraceletEffects {
    fun effectQual(effectName: String, tooltip: String): String {
        if (effectName == "마나회수") {
            if (tooltip.contains("150")) {
                return "하"
            } else if (tooltip.contains("175")) {
                return "중"
            } else if (tooltip.contains("200")) {
                return "상"
            }
        }

        if (effectName == "비수") {
            if (tooltip.contains("1.8%")) {
                return "하"
            } else if (tooltip.contains("2.1%")) {
                return "중"
            } else if (tooltip.contains("2.5%")) {
                return "상"
            }
        }

        if (effectName == "약점노출") {
            if (tooltip.contains("1.8%")) {
                return "하"
            } else if (tooltip.contains("2.1%")) {
                return "중"
            } else if (tooltip.contains("2.5%")) {
                return "상"
            }
        }

        if (effectName == "응원") {
            if (tooltip.contains("0.9%")) {
                return "하"
            } else if (tooltip.contains("1.1%")) {
                return "중"
            } else if (tooltip.contains("1.3%")) {
                return "상"
            }
        }

        if (effectName == "정밀") {
            if (tooltip.contains("3%")) {
                return "하"
            } else if (tooltip.contains("4%")) {
                return "중"
            } else if (tooltip.contains("5%")) {
                return "상"
            }
        }

        if (effectName == "습격") {
            if (tooltip.contains("6%")) {
                return "하"
            } else if (tooltip.contains("8%")) {
                return "중"
            } else if (tooltip.contains("10%")) {
                return "상"
            }
        }

        if (effectName == "우월") {
            if (tooltip.contains("2.5%")) {
                return "중"
            } else if (tooltip.contains("2%")) {
                return "하"
            } else if (tooltip.contains("3%")) {
                return "상"
            }
        }

        if (effectName == "결투") {
            if (tooltip.contains("3.5%")) {
                return "중"
            } else if (tooltip.contains("3%")) {
                return "하"
            } else if (tooltip.contains("4%")) {
                return "상"
            }
        }

        if (effectName == "기습") {
            if (tooltip.contains("3.5%")) {
                return "중"
            } else if (tooltip.contains("3%")) {
                return "하"
            } else if (tooltip.contains("4%")) {
                return "상"
            }
        }

        if (effectName == "냉정") {
            if (tooltip.contains("3.5%")) {
                return "중"
            } else if (tooltip.contains("3%")) {
                return "하"
            } else if (tooltip.contains("4%")) {
                return "상"
            }
        }

        if (effectName == "열정") {
            if (tooltip.contains("3.5%")) {
                return "중"
            } else if (tooltip.contains("3%")) {
                return "하"
            } else if (tooltip.contains("4%")) {
                return "상"
            }
        }

        if (effectName == "망치") {
            // 증가 수치에 HTML 값이 남아있어서 < 를 남겨 수치 확인
            if (tooltip.contains("8%<")) {
                return "하"
            } else if (tooltip.contains("10%<")) {
                return "중"
            } else if (tooltip.contains("12%<")) {
                return "상"
            }
        }

        if (effectName == "쐐기") {
            if (tooltip.contains("0.35%")) {
                return "하"
            } else if (tooltip.contains("0.45%")) {
                return "중"
            } else if (tooltip.contains("0.5%")) {
                return "상"
            }
        }

        if (effectName == "순환") {
            if (tooltip.contains("8%")) {
                return "하"
            } else if (tooltip.contains("10%")) {
                return "중"
            } else if (tooltip.contains("12%")) {
                return "상"
            }
        }

        return ""
    }
}