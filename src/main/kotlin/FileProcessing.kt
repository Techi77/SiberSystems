import java.io.File

fun readingNewFile() {
    val notFilteredHtml = mutableListOf<String>()
    var oldTr = mutableListOf<String>()
    var newTr = mutableListOf<String>()
    var countOfAppearances = 1
    val filteredTr = ""
    File("src/main/resources/notFilteredHtml.html").readLines().forEach {
        notFilteredHtml.add(it)
    }
    notFilteredHtml.forEach {
        if (it.contains("<table>")) {
            File("src/main/resources/result.html").writeText(it)
        } else if (it.contains("</table>")) {
            oldTr.forEach {
                File("src/main/resources/result.html").appendText(
                    "\n${
                        it.replace(
                            "\"height: 50\"",
                            "\"height: ${50 * countOfAppearances}\""
                        )
                    }"
                )
            }
            File("src/main/resources/result.html").appendText("\n$it")
        } else if (it.contains("<tr") or it.contains("<td")) {
            newTr.add(it)
        } else if (it.contains("</tr>")) {
            newTr.add(it)
            if (oldTr == newTr) {
                countOfAppearances += 1
                println("countOfRepeat=$countOfAppearances")
                newTr.removeAll() { it.contains("") }
            } else {
                oldTr.forEach {
                    File("src/main/resources/result.html").appendText(
                        "\n${
                            it.replace(
                                "\"height: 50\"",
                                "\"height: ${50 * countOfAppearances}\""
                            )
                        }"
                    )
                }
                oldTr.removeAll() { it.contains("") }
                oldTr.addAll(newTr)
                newTr.removeAll() { it.contains("") }
                countOfAppearances = 1
            }
        }
    }
}