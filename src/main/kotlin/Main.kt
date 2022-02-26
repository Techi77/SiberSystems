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
    val minXAndMinYArr = minXAndMinY(arrayOfCoordinates)
    File("src/main/resources/result.html").writeText("<table>")
    var topPadding: String = ""
    if (minXAndMinYArr[1] > 0) {
        topPadding =
            "\n  <tr>\n" + "    <td style=\"width: ${minXAndMinYArr[0]}; height: ${minXAndMinYArr[1]}\"></td>\n" + "  </tr>"
    }
    File("src/main/resources/result.html").appendText(topPadding)
    File("src/main/resources/result.html").appendText("\n</table>")
}

fun minXAndMinY(arrayOfCoordinates: List<String>): ArrayList<Int> {
    val minXAndMinYArr = arrayListOf(-1, -1)
    var minX: Int = -1
    var minY: Int = -1
    arrayOfCoordinates.forEach {
        if (minX > it.substringBefore(',').toInt() || minX < 0) {
            minX = it.substringBefore(',').toInt()
        }
        if (minY > it.substringAfter(',').toInt() || minY < 0) {
            minY = it.substringAfter(',').toInt()
        }
    }
    minXAndMinYArr[0] = minX
    minXAndMinYArr[1] = minY
    return minXAndMinYArr
}