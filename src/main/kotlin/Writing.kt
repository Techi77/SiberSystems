import java.io.File

fun writingHTMLFile(arrayOfCoordinates: List<String>) {

    val minXMinYMaxYArr = minXMinYMaxY(arrayOfCoordinates)
    val minX = minXMinYMaxYArr[0]
    val minY = minXMinYMaxYArr[1]
    val maxY = minXMinYMaxYArr[2]

    var topPadding = ""

    val arrayOfXAndY = divisionIntoXAndY(arrayOfCoordinates)
    val arrayOfX = arrayOfXAndY[0]
    val arrayOfY = arrayOfXAndY[1]

    var drawingLineNum: Int
    val greenLine = mutableListOf<String>()

    // Начало отрисовки
    File("src/main/resources/notFiltered.html").writeText("<table>")

    // Отрисовка прозрачного topPadding
    if (minY > 0) {
        topPadding =
            "\n  <tr style=\"height: ${minY * 50}\">"
        repeat(if (minX > 0) minX else 1) {
            topPadding += "\n    <td style=\"width: 50\"></td>"
        }
        topPadding += "\n  </tr>"
    }
    File("src/main/resources/notFiltered.html").appendText(topPadding)

    //отрисовка оставшейся части
    var i = 0
    repeat(maxY - minY) {
        drawingLineNum = it + minY
        arrayOfY.forEach {
            if (drawingLineNum >= it.substringBefore(',').toInt() && drawingLineNum <= it.substringAfter(',').toInt()) {
                if (drawingLineNum < arrayOfY[i].substringAfter(',').toInt()) greenLine.add(arrayOfX[i])
            }
            i += 1
        }
        greenLine.sort()
        // отрисовка конкретной строки html таблицы
        newLine(minX, greenLine)
        // обнуление переменных
        i = 0
        greenLine.clear()
    }

    // Конец отрисовки
    File("src/main/resources/notFiltered.html").appendText("\n</table>")
}

fun newLine(minX: Int, greenLine: MutableList<String>) {
    var lineInFile = "\n  <tr style=\"height: 50\">"
    // left padding
    if (minX > 0) {
        repeat(minX) {
            lineInFile += "\n    <td style=\"width: 50\"></td>"
        }
    }
    // прозрачный отступ
    if (greenLine.size > 0 && (greenLine[0].substringBefore(',').toInt() - minX) > 0) {
        repeat(greenLine[0].substringBefore(',').toInt() - minX) {
            lineInFile = "$lineInFile\n    <td style=\"width: 50\"></td>"
        }
    }
    // закрашенная часть
    for (i in 0..greenLine.lastIndex) {
        repeat(greenLine[i].substringAfter(',').toInt() - greenLine[i].substringBefore(',').toInt()) {
            lineInFile = "$lineInFile\n    <td style=\"background-color: green; width: 50\"></td>"
        }
        if (i < greenLine.lastIndex) {
            repeat(greenLine[i + 1].substringBefore(',').toInt() - greenLine[i].substringAfter(',').toInt()) {
                lineInFile = "$lineInFile\n    <td style=\"width: 50\"></td>"
            }
        }
    }
    lineInFile = "$lineInFile\n  </tr>"
    File("src/main/resources/notFiltered.html").appendText(lineInFile)
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