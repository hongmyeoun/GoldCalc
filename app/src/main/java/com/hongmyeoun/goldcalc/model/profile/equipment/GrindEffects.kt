package com.hongmyeoun.goldcalc.model.profile.equipment

import com.hongmyeoun.goldcalc.model.constants.viewConst.EquipmentConsts

object GrindEffects {
    fun effectsQual(input: String): String {
        // 공용옵션
        if (input.startsWith("최대 생명력")) {
            if (input.contains("1300")) {
                return "하"
            } else if (input.contains("3250")) {
                return "중"
            } else if (input.contains("6500")) {
                return "상"
            }
        } else if (input.startsWith("공격력") && input.last() != '%') {
            if (input.contains("80")) {
                return "하"
            } else if (input.contains("195")) {
                return "중"
            } else if (input.contains("390")) {
                return "상"
            }
        } else if (input.startsWith("무기 공격력") && input.last() != '%') {
            if (input.contains("195")) {
                return "하"
            } else if (input.contains("480")) {
                return "중"
            } else if (input.contains("960")) {
                return "상"
            }
        } else if (input.startsWith("최대 마나")) {
            if (input.contains("6")) {
                return "하"
            } else if (input.contains("15")) {
                return "중"
            } else if (input.contains("30")) {
                return "상"
            }
        } else if (input.startsWith("상태이상 공격 지속시간")) {
            if (input.contains("0.2")) {
                return "하"
            } else if (input.contains("0.5")) {
                return "중"
            } else if (input.contains("1")) {
                return "상"
            }
        } else if (input.startsWith("전투 중 생명력 회복량")) {
            if (input.contains("10")) {
                return "하"
            } else if (input.contains("25")) {
                return "중"
            } else if (input.contains("50")) {
                return "상"
            }
        }

        // 목걸이 옵션
        if (input.startsWith("추가 피해")) {
            if (input.contains("0.7")) {
                return "하"
            } else if (input.contains("1.6")) {
                return "중"
            } else if (input.contains("2.6")) {
                return "상"
            }
        } else if (input.startsWith("적에게 주는 피해")) {
            if (input.contains("0.55")) {
                return "하"
            } else if (input.contains("1.2")) {
                return "중"
            } else if (input.contains("2.0")) {
                return "상"
            }
        } else if (input.startsWith("세레나데, 신앙, 조화 게이지 획득량")) {
            if (input.contains("1.6")) {
                return "하"
            } else if (input.contains("3.6")) {
                return "중"
            } else if (input.contains("6.0")) {
                return "상"
            }
        } else if (input.startsWith("낙인력")) {
            if (input.contains("2.15")) {
                return "하"
            } else if (input.contains("4.8")) {
                return "중"
            } else if (input.contains("8")) {
                return "상"
            }
        }

        // 귀걸이 옵션
        if (input.startsWith("공격력") && input.last() == '%') {
            if (input.contains("0.4")) {
                return "하"
            } else if (input.contains("0.9")) {
                return "중"
            } else if (input.contains("1.55")) {
                return "상"
            }
        } else if (input.startsWith("무기 공격력") && input.last() == '%') {
            if (input.contains("0.8")) {
                return "하"
            } else if (input.contains("1.8")) {
                return "중"
            } else if (input.contains("3")) {
                return "상"
            }
        } else if (input.startsWith("파티원 회복 효과")) {
            if (input.contains("0.95")) {
                return "하"
            } else if (input.contains("2.1")) {
                return "중"
            } else if (input.contains("3.5")) {
                return "상"
            }
        } else if (input.startsWith("파티원 보호막 효과")) {
            if (input.contains("0.95")) {
                return "하"
            } else if (input.contains("2.1")) {
                return "중"
            } else if (input.contains("3.5")) {
                return "상"
            }
        }

        // 반지 옵션
        if (input.startsWith("치명타 적중률")) {
            if (input.contains("0.4")) {
                return "하"
            } else if (input.contains("0.9")) {
                return "중"
            } else if (input.contains("1.55")) {
                return "상"
            }
        } else if (input.startsWith("치명타 피해")) {
            if (input.contains("1.1")) {
                return "하"
            } else if (input.contains("2.4")) {
                return "중"
            } else if (input.contains("4")) {
                return "상"
            }
        } else if (input.startsWith("아군 공격력 강화 효과")) {
            if (input.contains("1.35")) {
                return "하"
            } else if (input.contains("3")) {
                return "중"
            } else if (input.contains("5")) {
                return "상"
            }
        } else if (input.startsWith("아군 피해량 강화 효과")) {
            if (input.contains("2")) {
                return "하"
            } else if (input.contains("4.5")) {
                return "중"
            } else if (input.contains("7.5")) {
                return "상"
            }
        }

        return EquipmentConsts.GRIND_ERROR
    }
}