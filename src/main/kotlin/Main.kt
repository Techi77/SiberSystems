import java.io.File

fun main() {
    val arrayOfCoordinates = readingTxtFile()
    try {
        writingHTMLFile(arrayOfCoordinates)
    } catch (e: NumberFormatException) {
        println("Проверьте вводимые данные. Возможные ошибки:"
                + "\n1. Данные не соотетствуют формату \"х1, у1; х2, у2;\"(в самом конце файла \";\" ставить не нужно)."
                + "\n2. Прямоугольники перекрывают друг друга."
                + "\n3. Указаны отрицательные значения")
    }
}

fun readingTxtFile(): List<String> {
    val fileString: String = File("src/main/resources/rectangles.txt").readText()
    return fileString.replace("\\s".toRegex(), "").split(";")
}

fun writingHTMLFile(arrayOfCoordinates: List<String>) {
    val minXMinYMaxYArr = minXMinYMaxY(arrayOfCoordinates)
    File("src/main/resources/result.html").writeText("<table>")
    var topPadding = ""
    if (minXMinYMaxYArr[1] > 0) {
        topPadding =
            "\n  <tr>\n" + "    <td style=\"width: ${if (minXMinYMaxYArr[0] > 0) minXMinYMaxYArr[0] * 50 else 1}; height: ${minXMinYMaxYArr[1] * 50}\"></td>\n" + "  </tr>"
    }
    File("src/main/resources/result.html").appendText(topPadding)
    val arrayOfXAndY = divisionIntoXAndY(arrayOfCoordinates)
    val arrayOfX = arrayOfXAndY[0]
    val arrayOfY = arrayOfXAndY[1]
    var drawingLineNum: Int
    val redLine = mutableListOf<String>()
    var i = 0
    repeat(minXMinYMaxYArr[2] - minXMinYMaxYArr[1]) {
        drawingLineNum = it + minXMinYMaxYArr[1]
        arrayOfY.forEach {
            if (drawingLineNum >= it.substringBefore(',').toInt() && drawingLineNum <= it.substringAfter(',').toInt()) {
                if (drawingLineNum < arrayOfY[i].substringAfter(',').toInt()) redLine.add(arrayOfX[i])
            }
            i += 1
        }
        redLine.sort()
        newLine(minXMinYMaxYArr[0], redLine)
        i = 0
        redLine.removeAll() { it.contains("") }
    }
    File("src/main/resources/result.html").appendText("\n</table>")
}

fun newLine(minX: Int, redLine: MutableList<String>) {
    var lineInFile = "\n  <tr style=\"height: 50\">\n"
    if (minX > 0) {
        lineInFile = "$lineInFile    <td></td>"
    }
    if (redLine.size > 0 && (redLine[0].substringBefore(',').toInt() - minX) > 0) {
        repeat(redLine[0].substringBefore(',').toInt() - minX) {
            lineInFile = "$lineInFile\n    <td style=\"width: 50\"></td>"
        }
    }
    for (i in 0..redLine.lastIndex) {
        repeat(redLine[i].substringAfter(',').toInt() - redLine[i].substringBefore(',').toInt()) {
            lineInFile = "$lineInFile\n    <td style=\"background-color: green; width: 50\"></td>"
        }
        if (i < redLine.lastIndex) {
            repeat(redLine[i + 1].substringBefore(',').toInt() - redLine[i].substringAfter(',').toInt()) {
                lineInFile = "$lineInFile\n    <td style=\"width: 50\"></td>"
            }
        }
    }
    lineInFile = "$lineInFile\n  </tr>"
    File("src/main/resources/result.html").appendText(lineInFile)
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