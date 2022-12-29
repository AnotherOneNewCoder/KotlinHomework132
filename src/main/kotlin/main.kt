//В прошлый раз мы рассматривали упрощённый вариант вычисления комиссии. Давайте усложним задачу.
//
//За MasterCard и Maestro вообще не нужно платить, пока не превысили лимит (замечание от 300 убираем), а для VK Pay всегда бесплатно:
//Напишите алгоритм расчёта в виде функции, передавая в функцию:
//
//тип карты/счёта (по умолчанию VK Pay);
//сумму предыдущих переводов в этом месяце (по умолчанию 0 рублей);
//сумму совершаемого перевода.
import java.util.*
import java.math.RoundingMode
import java.text.DecimalFormat
fun main() {

    // для округления
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN


    val amount = Scanner(System.`in`).useLocale(Locale.US) // использую точку как delimiter, а не запятую
    // лимиты в копейках
    val dayCardLimitTransfer = 150_000_00
    val monthCardLimitTransfer = 600_000_00
    val beforeFee = 75_000_00
    val vkPerOnceLimit = 15_000_00
    val vkPerMonthLimit = 40_000_00

    fun calculate(typeCard :String = "VK Pay", curentMonthTrasfer: Double = 0.0, amountTransfer : Double): String = when {
        (typeCard == "MasterCard" || typeCard =="Maestro") -> when {
            (amountTransfer < dayCardLimitTransfer && amountTransfer < beforeFee && curentMonthTrasfer < monthCardLimitTransfer &&
                    (amountTransfer + curentMonthTrasfer) < monthCardLimitTransfer) -> "сумма перевода составит - ${amountTransfer/100} без комиссии"
            (amountTransfer < dayCardLimitTransfer && amountTransfer >= beforeFee && curentMonthTrasfer < monthCardLimitTransfer &&
                    (amountTransfer + curentMonthTrasfer) < monthCardLimitTransfer) -> "сумма перевода составит - ${df.format((amountTransfer * 1.006 + 2000)/100)}руб, комиссия - " +
                    "${df.format((amountTransfer * 0.006 + 2000)/100)} руб"
            else -> "Вы превысили лимиты! Перевод не осуществлён!"
        }
        (typeCard == "Visa"|| typeCard == "Мир") -> when {
            (amountTransfer < dayCardLimitTransfer && curentMonthTrasfer < monthCardLimitTransfer &&
                    (amountTransfer + curentMonthTrasfer) < monthCardLimitTransfer) -> when {
                        (amountTransfer * 0.0075) < 3500 -> "сумма перевода составит - ${(amountTransfer + 3500) / 100}руб, комиссия - 35 руб"
                        else -> "сумма перевода составит - ${df.format((amountTransfer * 1.0075)/ 100)}руб, комиссия - ${df.format((amountTransfer * 0.0075)/100)} руб"
            }
            else -> "Вы превысили лимиты! Перевод не осуществлён!"

        }
        else -> when {
            (amountTransfer < vkPerOnceLimit && curentMonthTrasfer < vkPerMonthLimit && (amountTransfer + curentMonthTrasfer) < vkPerMonthLimit) ->
                "сумма перевода составит - ${amountTransfer/100} без комиссии"
            else -> "Вы превысили лимиты! Перевод не осуществлён!"
        }


    }


    while (true){
        println("Введите сумму перевода в рублях: ")
        val userInput = (amount.nextDouble()*100); // сразу перевожу в копейки.
        println(calculate("MasterCard", 500_000_00.00,amountTransfer = userInput))
        println(calculate("Мир",amountTransfer = userInput))
        println(calculate(amountTransfer = userInput))

    }

}
