import java.io.File
import java.util.LinkedList

fun writingNewFileSortedByY() {
    var oldTr = mutableListOf<String>()
    var newTr = mutableListOf<String>()
    var countOfAppearances = 1
    File("src/main/resources/notFilteredHtml.html").readLines().forEach {
        if (it.contains("<table>")) {
            File("src/main/resources/filteredByY.html").writeText(it)
        } else if (it.contains("</table>")) {
            oldTr.forEach {
                File("src/main/resources/filteredByY.html").appendText(
                    "\n${
                        it.replace(
                            "\"height: 50\"",
                            "\"height: ${50 * countOfAppearances}\""
                        )
                    }"
                )
            }
            File("src/main/resources/filteredByY.html").appendText("\n$it")
        } else if (it.contains("<tr") or it.contains("<td")) {
            newTr.add(it)
        } else if (it.contains("</tr>")) {
            newTr.add(it)
            if (oldTr == newTr) {
                countOfAppearances += 1
                newTr.clear()
            } else {
                oldTr.forEach {
                    File("src/main/resources/filteredByY.html").appendText(
                        "\n${
                            it.replace(
                                "\"height: 50\"",
                                "\"height: ${50 * countOfAppearances}\""
                            )
                        }"
                    )
                }
                oldTr.clear()
                oldTr.addAll(newTr)
                newTr.clear()
                countOfAppearances = 1
            }
        }
    }
}

fun writingNewFileSortedByX() {
    var oldTr = "1"
    var newTr = "2"
    var i: Int = -1
    var y: Int
    var countOfAppearances = 1
    val widthML: MutableList<Int> = mutableListOf()
    val widthMLML: MutableList<MutableList<Int>> = mutableListOf()
    File("src/main/resources/filteredByY.html").readLines().forEach {
        if (it.contains("<tr")) {
            i += 1
        }
        else if (it.contains("</tr>")) {
            oldTr = "1"
            newTr = "2"
            widthMLML.add(widthML)
            widthML.clear()
        } else if (it.contains("<td")) {
            if (oldTr == newTr) {
                countOfAppearances += 1
            } else {
                y = if (it.contains("green")) 1 else -1
                widthML.add(50 * countOfAppearances * y)
                countOfAppearances = 1
            }
        }
    }
}