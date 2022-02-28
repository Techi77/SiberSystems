import java.io.File

fun main() {
    val arrayOfCoordinates = readingTxtFile()
    try {
        writingHTMLFile(arrayOfCoordinates)
        readingNewFile()
    } catch (e: NumberFormatException) {
        println(
            "Проверьте вводимые данные. Возможные ошибки:"
                    + "\n1. Данные не соотетствуют формату \"х1, у1; х2, у2;\"(в самом конце файла \";\" ставить не нужно)."
                    + "\n2. Прямоугольники перекрывают друг друга."
                    + "\n3. Указаны отрицательные значения"
        )
    }
}