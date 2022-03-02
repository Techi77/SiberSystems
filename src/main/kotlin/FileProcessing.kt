import java.io.File

fun writingNewFileSortedByY() {
    var oldTr = mutableListOf<String>()
    var newTr = mutableListOf<String>()
    var countOfAppearances = 1
    File("src/main/resources/notFiltered.html").readLines().forEach {
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
            countOfAppearances = 0
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
            } else if (countOfAppearances >= 1) {
                j = if (oldTr.contains("green")) 1 else -1
                widthArray[i].add(50 * countOfAppearances * j)
                positiveWidthArray[i].add(50 * countOfAppearances)
                countOfAppearances = 1
                oldTr = newTr
            } else {
                countOfAppearances = 1
                oldTr = newTr
            }
        }
    }
    var minInTr = 0
    var maxLastIndexOfArr = 0
    var difference = 0
    positiveWidthArray.forEach {
        if (maxLastIndexOfArr < it.lastIndex) maxLastIndexOfArr = it.lastIndex
    }
    println(
        "old positiveWidthArray:\n$positiveWidthArray\nold widthArray:\n$widthArray"
    )
    for (j in 0..maxLastIndexOfArr) {
        positiveWidthArray.forEach {
            if (it.lastIndex >= j && (minInTr > it[j] || minInTr == 0)) {
                minInTr = it[j]
            }
        }
        for (i in 0..positiveWidthArray.lastIndex) {
            if (widthArray[i].lastIndex >= j) {
                difference = positiveWidthArray[i][j] - minInTr
                if (difference != 0) {
                    if (widthArray[i].lastIndex > j && widthArray[i][j] >= 0 && widthArray[i][j + 1] >= 0) {
                        widthArray[i][j + 1] += difference
                        positiveWidthArray[i][j + 1] += difference
                    } else if (widthArray[i].lastIndex > j && widthArray[i][j] < 0 && widthArray[i][j + 1] < 0) {
                        widthArray[i][1] -= difference
                        positiveWidthArray[i][j + 1] += difference
                    } else if (widthArray[i][j] >= 0) {
                        widthArray[i].add(j + 1, difference)
                        positiveWidthArray[i].add(j + 1, difference)
                    } else if (widthArray[i][j] < 0) {
                        widthArray[i].add(j + 1, -difference)
                        positiveWidthArray[i].add(j + 1, difference)
                    }
                    widthArray[i][j] += if (widthArray[i][j] >= 0) {
                        -difference
                    } else {
                        difference
                    }
                    positiveWidthArray[i][j] -= difference
                }
            }
        }
        minInTr = 0
    }
    println(
        "new positiveWidthArray:\n$positiveWidthArray\nnew widthArray:\n$widthArray"
    )
    i = 0
    File("src/main/resources/filteredByY.html").readLines().forEach {
        if (it.contains("<table>")) {
            File("src/main/resources/filteredByXAndY.html").writeText(it)
        } else if (it.contains("</table>") || it.contains("</tr>")) {
            File("src/main/resources/filteredByXAndY.html").appendText("\n${it}")
        } else if (it.contains("<tr")) {
            File("src/main/resources/filteredByXAndY.html").appendText("\n${it}")
            widthArray[i].forEach {
                if (it < 0) {
                    File("src/main/resources/filteredByXAndY.html").appendText("\n    <td style=\"width: ${it*(-1)}\"></td>")
                } else {
                    File("src/main/resources/filteredByXAndY.html").appendText("\n    <td style=\"background-color: green; width: ${it}\"></td>")
                }
            }
            i += 1
        }
    }
}