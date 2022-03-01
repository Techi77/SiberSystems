import java.io.File

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
    var i = 0
    var j: Int
    var countOfAppearances = 1
    val widthArray: MutableList<MutableList<Int>> = mutableListOf()
    val positiveWidthArray: MutableList<MutableList<Int>> = mutableListOf()
    File("src/main/resources/filteredByY.html").readLines().forEach {
        if (it.contains("<tr")) {
            widthArray.add(mutableListOf())
            positiveWidthArray.add(mutableListOf())
        } else if (it.contains("</tr>")) {
            j = if (oldTr.contains("green")) 1 else -1
            widthArray[i].add(50 * countOfAppearances * j)
            positiveWidthArray[i].add(50 * countOfAppearances)
            countOfAppearances = 1
            oldTr = "1"
            newTr = "2"
            i += 1
        } else if (it.contains("<td")) {
            newTr = it
            if (oldTr == newTr) {
                countOfAppearances += 1
            } else if (countOfAppearances > 1) {
                j = if (oldTr.contains("green")) 1 else -1
                widthArray[i].add(50 * countOfAppearances * j)
                positiveWidthArray[i].add(50 * countOfAppearances)
                countOfAppearances = 1
                oldTr = newTr
            } else {
                oldTr = newTr
            }
        }
    }
    println("widthArray:\n$widthArray")
    println("positiveWidthArray: \n$positiveWidthArray")
    var minInTr = positiveWidthArray[0][0]
    var maxSizeOfArr = 0
    var difference = 0
    positiveWidthArray.forEach {
        if (maxSizeOfArr < it.size) maxSizeOfArr = it.size
    }
    positiveWidthArray.forEach {
        if (minInTr > it[0]) minInTr = it[0]
    }
    for (i in 0..positiveWidthArray.lastIndex) {
        difference = positiveWidthArray[i][0] - minInTr
        if (difference != 0) {
            if (widthArray[i][0] >= 0 && widthArray[i][1] >= 0) {
                widthArray[i][1] += difference
                positiveWidthArray[i][1] += difference
            } else if (widthArray[i][0] < 0 && widthArray[i][1] < 0) {
                widthArray[i][1] -= difference
                positiveWidthArray[i][1] += difference
            } else if (widthArray[i][0] >= 0) {
                widthArray[i].add(1, difference)
                positiveWidthArray[i].add(1, difference)
            } else if (widthArray[i][0] < 0) {
                widthArray[i].add(1, -difference)
                positiveWidthArray[i].add(1, difference)
            }

            widthArray[i][0] += if (widthArray[i][0] >= 0) {
                -difference
            } else {
                difference
            }
            positiveWidthArray[i][0] -= difference
        }
    }
    println("minInTr: $minInTr")
    println("minInTr: $maxSizeOfArr")
    println("widthArray:\n$widthArray")
    println("positiveWidthArray: \n$positiveWidthArray")
}