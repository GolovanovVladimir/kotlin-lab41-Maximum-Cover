package ru.netology

val minAmount = 0


fun main() {
    var flag = -1
    var typeCard = "VK Pay"

    println("Программа расчета при переводе")
    while (flag == -1) {
        println("Выберите сумму операции текущей операции в рублях: ")
        println("0-Выход из программы")

        var input = readLine()!!
        when (input) {
            "0" -> {
                break
            }
        }
        var amountCurrent: Double = input.toDouble()

        if (amountCurrent > 0) {
            println("Выберите тип карты: ")
            println("1-MASTER / MAESTRO")
            println("2-VISA / МИР")
            println("3-VK Pay (по умолчанию)")
            println("0-Выход из программы")

            input = readLine()!!
            when (input) {
                "0" -> {
                    break
                }

                "1" -> typeCard = "MASTER / MAESTRO"
                "2" -> typeCard = "VISA / МИР"
                else -> typeCard = "VK Pay"
            }

            calculateCommission(typeCard, 37_000.0, amountCurrent)
        } else
            println("Отрицательных перечислений не может быть!")

    }
    println("Программа расчета процентов закончила свою работу.")
}

fun calculateCommission(cardType :String = "VK Pay", amountsPrevious : Double = 0.0 ,amountCurrent: Double ) :Double {
    val minMasterLimit = 300
    val maxMasterLimit = 75_000
    val percentMasterCommission = 0.6
    val amountMasterCommission = 20.0
    var amountCommission = 0.0
    val minVisaLimit = 35.0
    val percentVisaCommission = 0.75
    val maxVKPayOperation = 15_000
    val maxVKPayMonthOperation = 40_000
    val limitOperation = 150_000.0
    val limitMonth = 600_000.0
    var flag = 0

    when (cardType) {
        "MASTER / MAESTRO" -> {
            if ((amountCurrent > minMasterLimit) && (amountCurrent < maxMasterLimit)) {
                amountCommission = 0.0
            } else {
                if (limitOperation < amountCurrent)
                        if (limitMonth < (amountCurrent + amountsPrevious)) {
                        amountCommission = 0.0
                    }
                else {
                    amountCommission = amountCurrent / 100 * percentMasterCommission + amountMasterCommission
                }
            }
        }
        "VISA / МИР" -> {
            if (amountCurrent > minVisaLimit) {
                amountCommission = amountCurrent / 100 * percentVisaCommission
            } else {
                println("Операция невозможна : операция меньше $minVisaLimit по карте $cardType!")
                flag = 2
            }
        }

        else -> amountCommission =0.0
    }

    if (cardType == "MASTER / MAESTRO"){
        if (limitOperation < amountCurrent) {
            if (limitMonth < (amountCurrent + amountsPrevious)) {
                println("В планируете перечислить с карты $cardType сумму $amountCurrent  руб., ранее перечисляли $amountsPrevious руб.")
                println("Комиссия по операции $amountCommission руб.")
                println("")
            } else {
                println("Операция невозможна : превышен лимит по месяцу по карте $cardType!")
                amountCommission = 0.0
            }
        } else {
            amountCommission = 0.0
            println("Операция невозможна : превышен лимит на операцию по карте $cardType!")
        }
    }

    if ((cardType == "VISA / МИР")&& (flag!=2)){
        if (limitOperation > amountCurrent) {
            if (limitMonth > (amountCurrent + amountsPrevious)) {
                println("В планируете перечислить с карты $cardType сумму $amountCurrent  руб., ранее перечисляли $amountsPrevious руб.")
                println("Комиссия по операции $amountCommission руб.")
                println("")
            } else {
                println("Операция невозможна : превышен лимит по месяцу по карте $cardType!")
                amountCommission = 0.0
            }
        } else {
            amountCommission = 0.0
            println("Операция невозможна : превышен лимит на операцию по карте $cardType!")
        }
    }


    if (cardType == "VK Pay") {
        if (maxVKPayOperation > amountCurrent) {
            if (maxVKPayMonthOperation > (amountCurrent+amountsPrevious)) {
                println("В планируете перечислить с карты $cardType сумму $amountCurrent  руб., ранее перечисляли $amountsPrevious руб.")
                println("Комиссия по операции $amountCommission руб.")
                println("")
            }
            else println("Операция невозможна : превышена макс.сумма по месяцу по карте $cardType!")
        }
        else println("Операция невозможна : превышена макс.сумма на операцию по карте $cardType!")
    }
    return amountCommission
}
