import java.io.File

fun readingTxtFile(): List<String> {
    val fileString: String = File("src/main/resources/rectangles.txt").readText()
    return fileString.replace("\\s".toRegex(), "").split(";")
}