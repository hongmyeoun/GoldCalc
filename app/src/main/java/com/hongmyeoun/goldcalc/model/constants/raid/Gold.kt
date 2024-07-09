package com.hongmyeoun.goldcalc.model.constants.raid

class Gold {
    class SeeMore {
        object Normal {
            val VALTAN = listOf(300, 400)
            val BIACKISS = listOf(300, 450)
            val KOUKU_SATON = listOf(300, 500, 600)
            val ABRELSHUD = listOf(250, 300, 400, 600)
            val ILLIAKAN = listOf(450, 550, 750)
            val KAMEN = listOf(1500, 1800, 2500, 0)

            val KAYANGEL = listOf(300, 400, 500)
            val IVORY_TOWER = listOf(600, 650, 1000)

            val ECHIDNA = listOf(2200, 3400)

            val BEHEMOTH = listOf(3100, 4900)
        }

        object Hard {
            val VALTAN = listOf(450, 600)
            val BIACKISS = listOf(500, 650)
            val KOUKU_SATON = listOf(300, 500, 600)
            val ABRELSHUD = listOf(400, 400, 500, 800)
            val ILLIAKAN = listOf(600, 700, 950)
            val KAMEN = listOf(2000, 2400, 2800, 3600)

            val KAYANGEL = listOf(350, 500, 700)
            val IVORY_TOWER = listOf(1200, 1450, 2000)

            val ECHIDNA = listOf(2800, 4100)

            val BEHEMOTH = listOf(3100, 4900)
        }

        object Solo {
            val VALTAN = listOf(0, 0)
            val BIACKISS = listOf(0, 0)
            val KOUKU_SATON = listOf(0, 0, 0)
            val ABRELSHUD = listOf(0, 0, 0, 0)
            val ILLIAKAN = listOf(0, 0, 0)

            val KAYANGEL = listOf(0, 0, 0)
            val IVORY_TOWER = listOf(0, 0, 0)
        }
    }

    class Clear {
        object Normal {
            val VALTAN = listOf(500, 700)
            val BIACKISS = listOf(600, 1000)
            val KOUKU_SATON = listOf(600, 900, 1500)
            val ABRELSHUD = listOf(1000, 1000, 1000, 1600)
            val ILLIAKAN = listOf(1000, 1800, 2600)
            val KAMEN = listOf(3500, 4000, 5500, 0)

            val KAYANGEL = listOf(800, 1200, 1600)
            val IVORY_TOWER = listOf(1500, 2000, 3000)

            val ECHIDNA = listOf(5000, 9500)

            val BEHEMOTH = listOf(7000, 14500)
        }

        object Hard {
            val VALTAN = listOf(700, 1100)
            val BIACKISS = listOf(900, 1500)
            val KOUKU_SATON = listOf(600, 900, 1500)
            val ABRELSHUD = listOf(1200, 1200, 1200, 2000)
            val ILLIAKAN = listOf(1500, 2500, 3500)
            val KAMEN = listOf(5000, 6000, 9000, 21000)

            val KAYANGEL = listOf(1000, 1600, 2200)
            val IVORY_TOWER = listOf(3000, 4000, 6000)

            val ECHIDNA = listOf(6000, 12500)

            val BEHEMOTH = listOf(7000, 14500)
        }

        object Solo {
            val VALTAN = listOf(100, 150)
            val BIACKISS = listOf(150, 275)
            val KOUKU_SATON = listOf(150, 200, 450)
            val ABRELSHUD = listOf(375, 350, 300, 500)
            val ILLIAKAN = listOf(275, 575, 975)

            val KAYANGEL = listOf(250, 400, 550)
            val IVORY_TOWER = listOf(450, 675, 1000)
        }
    }
}