package com.hongmyeoun.goldcalc.model.constants.raid

class Gold {
    class SeeMore {
        object Normal {
            val VALTAN = listOf(75, 100)
            val BIACKISS = listOf(100, 150)
            val KOUKU_SATON = listOf(100, 150, 200)
            val ABRELSHUD = listOf(100, 150, 200, 375)
            val ILLIAKAN = listOf(190, 230, 330)
            val KAMEN = listOf(360, 440, 640, 0)

            val KAYANGEL = listOf(180, 200, 270)
            val IVORY_TOWER = listOf(180, 220, 300)

            val ECHIDNA = listOf(380, 840)
            val EGIR = listOf(1030, 2400)
            val ABRELSHUD_2 = listOf(3240, 4830)
            val MORDUM = listOf(2400, 3200, 4200)

            val BEHEMOTH = listOf(920, 1960)

            val EVENT_RAID = listOf(0)
        }

        object Hard {
            val VALTAN = listOf(175, 275)
            val BIACKISS = listOf(225, 375)
            val KOUKU_SATON = listOf(100, 150, 200)
            val ABRELSHUD = listOf(300, 300, 300, 500)
            val ILLIAKAN = listOf(300, 500, 700)
            val KAMEN = listOf(500, 600, 900, 1250)

            val KAYANGEL = listOf(225, 350, 500)
            val IVORY_TOWER = listOf(350, 500, 950)

            val ECHIDNA = listOf(920, 1960)
            val EGIR = listOf(3640, 5880)
            val ABRELSHUD_2 = listOf(4500, 7200)
            val MORDUM = listOf(2700, 4100, 5800)

            val BEHEMOTH = listOf(920, 1960)

            val EVENT_RAID = listOf(0)
        }

        object Solo {
            val EGIR = listOf(1030, 2400)
        }
    }

    class Clear {
        object Normal {
            val VALTAN = listOf(250, 350)
            val BIACKISS = listOf(300, 500)
            val KOUKU_SATON = listOf(300, 450, 750)
            val ABRELSHUD = listOf(500, 500, 500, 800)
            val ILLIAKAN = listOf(425, 775, 1150)
            val KAMEN = listOf(800, 1000, 1400, 0)

            val KAYANGEL = listOf(325, 550, 725)
            val IVORY_TOWER = listOf(600, 800, 1200)

            val ECHIDNA = listOf(1150, 2500)
            val EGIR = listOf(4750, 10750)
            val ABRELSHUD_2 = listOf(7250, 14250)
            val MORDUM = listOf(6000, 9500, 12500)

            val BEHEMOTH = listOf(2800, 6000)

            val EVENT_RAID = listOf(15000)
        }

        object Hard {
            val VALTAN = listOf(350, 550)
            val BIACKISS = listOf(450, 750)
            val KOUKU_SATON = listOf(300, 450, 750)
            val ABRELSHUD = listOf(600, 600, 600, 1000)
            val ILLIAKAN = listOf(600, 1000, 1400)
            val KAMEN = listOf(1000, 1200, 1800, 2500)

            val KAYANGEL = listOf(450, 700, 1000)
            val IVORY_TOWER = listOf(700, 1000, 1900)

            val ECHIDNA = listOf(2800, 6000)
            val EGIR = listOf(8000, 16500)
            val ABRELSHUD_2 = listOf(10000, 20500)
            val MORDUM = listOf(7000, 11000, 20000)

            val BEHEMOTH = listOf(2800, 6000)

            val EVENT_RAID = listOf(30000)
        }

        object Solo {
            val EGIR = listOf(2375, 5375)
        }
    }
}