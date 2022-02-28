import java.io.File

fun main() {
    println("Hi, I'm working")
    val arrayOfCoordinates = readingTxtFile()
    writingHTMLFile(arrayOfCoordinates)
}

fun readingTxtFile(): List<String> {
    val fileString: String = File("src/main/resources/rectangles.txt").readText()
    println("Text in file: $fileString")
    return fileString.replace("\\s".toRegex(), "").split(";")
}

fun writingHTMLFile(arrayOfCoordinates: List<String>) {
    val minXMinYMaxYArr = minXAndMinY(arrayOfCoordinates)
    File("src/main/resources/result.html").writeText("<table>")
    var topPadding = ""
    if (minXMinYMaxYArr[1] > 0) {
        topPadding =
            "\n  <tr>\n" + "    <td style=\"width: ${if (minXMinYMaxYArr[0] > 0) minXMinYMaxYArr[0] else 1}; height: ${minXMinYMaxYArr[1]}\"></td>\n" + "  </tr>"
    }
    File("src/main/resources/result.html").appendText(topPadding)
    val arrayOfXAndY = divisionIntoXAndY(arrayOfCoordinates)
    val arrayOfX = arrayOfXAndY[0]
    val arrayOfY = arrayOfXAndY[1]
    var drawingLineNum = 0
    val redLine = mutableListOf<String>()
    var i = 0
    repeat(minXMinYMaxYArr[2] - minXMinYMaxYArr[1] + 1) {
        drawingLineNum = it + minXMinYMaxYArr[1]
        println("drawingLineNum: $drawingLineNum")
        arrayOfY.forEach {
            if (drawingLineNum >= it.substringBefore(',').toInt() && drawingLineNum <= it.substringAfter(',').toInt()) {
                redLine.add(arrayOfX[i])
                println("  redLine: $redLine")
            }
            i += 1
        }
        //newLine(minXMinYMaxYArr[0] > 0, redLine)
        i = 0
        redLine.removeAll() { it.contains("") }
    }
    File("src/main/resources/result.html").appendText("\n</table>")
}

fun newLine(hasLeftPadding: Boolean, redLine: MutableList<String>) {
    var lineInFile = "  <tr style=\"height: 50\">\n"
    if (hasLeftPadding) {
        lineInFile = "$lineInFile    <td></td>"
    }
    lineInFile = "$lineInFile  </tr>"
    File("src/main/resources/result.html").appendText(lineInFile)
}

fun divisionIntoXAndY(arrayOfCoordinates: List<String>): Array<MutableList<String>> {
    val arrayOfY = mutableListOf<String>()
    val arrayOfX = mutableListOf<String>()
    for (i in 0..arrayOfCoordinates.lastIndex step 2) {
        arrayOfY.add("${arrayOfCoordinates[i].substringAfter(',')},${arrayOfCoordinates[i + 1].substringAfter(',')}")
        arrayOfX.add("${arrayOfCoordinates[i].substringBefore(',')},${arrayOfCoordinates[i + 1].substringBefore(',')}")
    }
    println("arrayOfY: $arrayOfY")
    println("arrayOfX: $arrayOfX")
    return arrayOf(arrayOfX, arrayOfY)
}

fun minXAndMinY(arrayOfCoordinates: List<String>): ArrayList<Int> {
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