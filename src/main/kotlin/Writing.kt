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
        File("src/main/resources/result.html").writeText("<table>")

        // Отрисовка прозрачного topPadding
        if (minY > 0) {
            topPadding =
                "\n  <tr>\n" + "    <td style=\"width: ${if (minX > 0) minX * 50 else 1}; height: ${minY * 50}\"></td>\n" + "  </tr>"
        }
        File("src/main/resources/result.html").appendText(topPadding)

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
            greenLine.removeAll() { it.contains("") }
        }

        // Конец отрисовки
        File("src/main/resources/result.html").appendText("\n</table>")
    }

    fun newLine(minX: Int, greenLine: MutableList<String>) {
        var lineInFile = "\n  <tr style=\"height: 50\">\n"
        // left padding
        if (minX > 0) {
            lineInFile = "$lineInFile    <td></td>"
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
        File("src/main/resources/result.html").appendText(lineInFile)
    }