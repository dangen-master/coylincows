import kotlin.random.Random

// Функция для генерации тайного числа
fun generateSecretNumber(): String {
    var number: String
    do {
        number = (0..9).shuffled().take(4).joinToString("") // Генерация 4-значного числа с неповторяющимися цифрами
    } while (number[0] == '0') // Число не должно начинаться с 0
    return number
}

// Функция для подсчета быков и коров
fun calculateBullsAndCows(secret: String, guess: String): Pair<Int, Int> {
    var bulls = 0
    var cows = 0
    val secretList = secret.toMutableList()
    val guessList = guess.toMutableList()

    // Сначала считаем быков
    for (i in secret.indices) {
        if (secret[i] == guess[i]) {
            bulls++
            secretList[i] = '*'
            guessList[i] = '*'
        }
    }

    // Теперь считаем коров
    for (i in secretList.indices) {
        if (guessList[i] != '*' && secretList.contains(guessList[i])) {
            cows++
            secretList[secretList.indexOf(guessList[i])] = '*'
        }
    }

    return bulls to cows
}

// Основная функция игры
fun main() {
    val secretNumber = generateSecretNumber() // Компьютер загадывает число
    var attempts = 0
    var guessed = false

    println("Добро пожаловать в игру 'Быки и коровы'!")
    println("Компьютер загадал 4-значное число, начинающееся с нуля. Попробуйте угадать его!")

    // Игра продолжается, пока игрок не угадает число
    while (!guessed) {
        println("Введите вашу попытку (4 цифры без повторений):")
        val guess = readLine()?.trim()

        // Проверка корректности ввода
        if (guess == null || guess.length != 4 || guess.any { it !in '0'..'9' } || guess.toSet().size != 4) {
            println("Неверный ввод. Введите 4 различные цифры.")
            continue
        }

        // Подсчитываем быков и коров
        val (bulls, cows) = calculateBullsAndCows(secretNumber, guess)

        // Выводим результат
        println("Ваши попытки: $bulls быков и $cows коров")

        attempts++

        if (bulls == 4) {
            guessed = true
            println("Поздравляем! Вы угадали число за $attempts попыток.")
        }
    }
}
