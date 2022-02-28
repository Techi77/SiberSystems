import java.io.File

fun main() {
    val arrayOfCoordinates = readingTxtFile()
    try {
        writingHTMLFile(arrayOfCoordinates)
    } catch (e: NumberFormatException) {
        println(
            "Проверьте вводимые данные. Возможные ошибки:"
                    + "\n1. Данные не соотетствуют формату \"х1, у1; х2, у2;\"(в самом конце файла \";\" ставить не нужно)."
                    + "\n2. Прямоугольники перекрывают друг друга."
                    + "\n3. Указаны отрицательные значения"
        )
    }
}

fun readingTxtFile(): List<String> {
    val fileString: String = File("src/main/resources/rectangles.txt").readText()
    return fileString.replace("\\s".toRegex(), "").split(";")
}

fun divisionIntoXAndY(arrayOfCoordinates: List<String>): Array<MutableList<String>> {
    val arrayOfY = mutableListOf<String>()
    val arrayOfX = mutableListOf<String>()
    for (i in 0..arrayOfCoordinates.lastIndex step 2) {
        arrayOfY.add("${arrayOfCoordinates[i].substringAfter(',')},${arrayOfCoordinates[i + 1].substringAfter(',')}")
        arrayOfX.add("${arrayOfCoordinates[i].substringBefore(',')},${arrayOfCoordinates[i + 1].substringBefore(',')}")
    }
    return arrayOf(arrayOfX, arrayOfY)
}

fun minXMinYMaxY(arrayOfCoordinates: List<String>): ArrayList<Int> {
    val minXMinYMaxYArr = arrayListOf(-1, -1, -1)
    var minX: Int = -1
    var minY: Int = -1
    var maxY: Int = -1
    arrayOfCoordinates.forEach {
        if (minX > it.substringBefore(',').toInt() || minX < 0) {
            minX = it.substringBefore(',').toInt()
        }
        if (minY > it.substringAfter(',').toInt() || minY < 0) {
            minY = it.substringAfter(',').toInt()
        }
        if (maxY < it.substringAfter(',').toInt() || maxY < 0)
            maxY = it.substringAfter(',').toInt()
    }
    minXMinYMaxYArr[0] = minX
    minXMinYMaxYArr[1] = minY
    minXMinYMaxYArr[2] = maxY
    return minXMinYMaxYArr
}