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

        return tier4(tooltip)
    }

    private fun tier4(tooltip: String): String {
        if (tooltip.contains("경직 및 피격 이상에 면역")) {
            if (tooltip.contains("90")) {
                return "하"
            } else if (tooltip.contains("80")) {
                return "중"
            } else if (tooltip.contains("70")) {
                return "상"
            }
        }

        if (tooltip.contains("시드 등급 이하 몬스터에게 받는")) {
            if (tooltip.contains("4")) {
                return "하"
            } else if (tooltip.contains("6")) {
                return "중"
            } else if (tooltip.contains("8")) {
                return "상"
            }
        }

        if (tooltip.contains("시드 등급 이하 몬스터에게 주는")) {
            if (tooltip.contains("3")) {
                return "하"
            } else if (tooltip.contains("4")) {
                return "중"
            } else if (tooltip.contains("5")) {
                return "상"
            }
        }

        if (tooltip.contains("공격 적중 시 매 초마다 10초 동안")) {
            if (tooltip.contains("1000")) {
                return "하"
            } else if (tooltip.contains("1160")) {
                return "중"
            } else if (tooltip.contains("1320")) {
                return "상"
            }
        }

        if (tooltip.contains("공격 및 이동 속도가")) {
            if (tooltip.contains("3")) {
                return "하"
            } else if (tooltip.contains("4")) {
                return "중"
            } else if (tooltip.contains("5")) {
                return "상"
            }
        }

        if (tooltip.contains("이동기 및 기상기")) {
            if (tooltip.contains("6")) {
                return "하"
            } else if (tooltip.contains("8")) {
                return "중"
            } else if (tooltip.contains("10")) {
                return "상"
            }
        }

        if (tooltip.contains("방향성 공격이 아닌 스킬이")) {
            if (tooltip.contains("2")) {
                return "하"
            } else if (tooltip.contains("2.5")) {
                return "중"
            } else if (tooltip.contains("3")) {
                return "상"
            }
        }

        if (tooltip.contains("백어택")) {
            if (tooltip.contains("2")) {
                return "하"
            } else if (tooltip.contains("2.5")) {
                return "중"
            } else if (tooltip.contains("3")) {
                return "상"
            }
        }

        if (tooltip.contains("헤드어택")) {
            if (tooltip.contains("2")) {
                return "하"
            } else if (tooltip.contains("2.5")) {
                return "중"
            } else if (tooltip.contains("3")) {
                return "상"
            }
        }

        if (tooltip.contains("무력화 상태의")) {
            if (tooltip.contains("1.5")) {
                return "하"
            } else if (tooltip.contains("2")) {
                return "중"
            } else if (tooltip.contains("2.5")) {
                return "상"
            }
        }

        if (tooltip.contains("공격 적중 시 30초 마다 120초 동안")) {
            if (tooltip.contains("120")) {
                return "하"
            } else if (tooltip.contains("130")) {
                return "중"
            } else if (tooltip.contains("140")) {
                return "상"
            }
        }

        if (tooltip.contains("자신의 생명력이")) {
            if (tooltip.contains("1800")) {
                return "하"
            } else if (tooltip.contains("2000")) {
                return "중"
            } else if (tooltip.contains("2200")) {
                return "상"
            }
        }

        if (tooltip.contains("대기 시간이 2%")) {
            if (tooltip.contains("4")) {
                return "하"
            } else if (tooltip.contains("4.5")) {
                return "중"
            } else if (tooltip.contains("5")) {
                return "상"
            }
        }

        if (tooltip.contains("방어력을")) {
            if (tooltip.contains("1.5")) {
                return "하"
            } else if (tooltip.contains("2")) {
                return "중"
            } else if (tooltip.contains("2.5")) {
                return "상"
            }
        }

        if (tooltip.contains("치명타 저항을")) {
            if (tooltip.contains("1.5")) {
                return "하"
            } else if (tooltip.contains("2")) {
                return "중"
            } else if (tooltip.contains("2.5")) {
                return "상"
            }
        }

        if (tooltip.contains("치명타 피해 저항을")) {
            if (tooltip.contains("1.5")) {
                return "하"
            } else if (tooltip.contains("2")) {
                return "중"
            } else if (tooltip.contains("2.5")) {
                return "상"
            }
        }

        if (tooltip.contains("대악마 계열 피해량이")) {
            if (tooltip.contains("2")) {
                return "하"
            } else if (tooltip.contains("2.5")) {
                return "중"
            } else if (tooltip.contains("3")) {
                return "상"
            }
        }

        if (tooltip.contains("치명타 적중률이")) {
            if (tooltip.contains("2.6")) {
                return "하"
            } else if (tooltip.contains("3.4")) {
                return "중"
            } else if (tooltip.contains("4.2")) {
                return "상"
            }
        }

        if (tooltip.contains("치명타 피해가")) {
            if (tooltip.contains("5.2")) {
                return "하"
            } else if (tooltip.contains("6.8")) {
                return "중"
            } else if (tooltip.contains("8.4")) {
                return "상"
            }
        }

        if (tooltip.contains("파티 효과로 보호 효과")) {
            if (tooltip.contains("1.5")) {
                return "하"
            } else if (tooltip.contains("2")) {
                return "중"
            } else if (tooltip.contains("2.5")) {
                return "상"
            }
        }

        if (tooltip.contains("파티원 보호 및 회복 효과")) {
            if (tooltip.contains("2")) {
                return "하"
            } else if (tooltip.contains("2.5")) {
                return "중"
            } else if (tooltip.contains("3")) {
                return "상"
            }
        }

        if (tooltip.contains("적에게 주는 피해가")) {
            if (tooltip.contains("1.5")) {
                return "하"
            } else if (tooltip.contains("2")) {
                return "중"
            } else if (tooltip.contains("2.5")) {
                return "상"
            }
        }

        return  ""
    }

    fun effectShortName(tooltip: String): String {
        if (tooltip.contains("치명타 적중률이")) {
            return "치적+"
        } else if (tooltip.contains("치명타 피해가")) {
            return "치피+"
        } else if (tooltip.contains("증가하며, 무력화 상태의")) {
            return "적주피+"
        } else if (tooltip.contains("추가 피해가")) {
            return "추피+"
        } else if (tooltip.contains("스킬의 재사용 대기 시간이")) {
            return "쿨적주피"
        } else if (tooltip.contains("대상의 방어력을")) {
            return "비수"
        } else if (tooltip.contains("대상의 치명타 저항을")) {
            return "약노"
        } else if (tooltip.contains("파티 효과로 보호 효과가 적용된 대상이")) {
            return "응원"
        } else if (tooltip.contains("대상의 치명타 피해 저항을")) {
            return "치피감"
        } else if (tooltip.contains("공격 적중 시 매 초 마다 10초 동안 무기 공격력이")) {
            return "무공이속"
        } else if (tooltip.contains("자신의 생명력이 50% 이상일 경우 적에게 공격 적중 시")) {
            return "무공+"
        } else if (tooltip.contains("추가 피해 ")) {
            return "추피"
        } else if (tooltip.contains("백어택 스킬이 적에게 ")) {
            return "기습"
        } else if (tooltip.contains("헤드어택 스킬이 적에게")) {
            return "결대"
        } else if (tooltip.contains("방향성 공격이 아닌 스킬이 적에게 ")) {
            return "타대"
        } else if (tooltip.contains("파티원 보호 및 회복 효과가 ")) {
            return "전문의"
        } else if (tooltip.contains("아군 공격력 강화 효과 ")) {
            return "아공강"
        } else if (tooltip.contains("아군 피해량 강화 효과 ")) {
            return "아피강"
        } else if (tooltip.contains("치명타 적중률 ")) {
            return "치적"
        } else if (tooltip.contains("치명타 피해 ")) {
            return "치피"
        } else if (tooltip.contains("무기 공격력 ")) {
            return "무공"
        } else if (tooltip.contains("적에게 주는 피해가 ")) {
            return "적주피"
        }

        return ""
    }
}