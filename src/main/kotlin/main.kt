import java.util.*

fun main() {

    val fee: Float = 0.75f // размер комиссии
    val transferMin: UInt = 3500u // минимальный размер платежа в копейках
    val amount = Scanner(System.`in`).useLocale(Locale.US) // использую точку как delimiter, а не запятую

    while (true){
        println("Введите сумму перевода в рублях. Минимальный размер перевода составляет ${transferMin/100u} рублей.")
        val userInput = (amount.nextDouble()*100).toUInt(); // сразу перевожу в копейки.
        if (userInput < transferMin) { // проверка на мин платеж
            println("Минимальный размер перевода составляет ${transferMin / 100u}\"!")
            continue;
        } else {
            val amountFee = userInput.toInt() * fee / 100
            println("Комиссия за перевод в ${userInput.toDouble() /100} рублей составит: ${amountFee /100} руб.")
            break
        }

    }

}
