import java.io.File

fun main() {
    println("Hi, I'm working")
    readTxtFile()
}

fun readTxtFile() {
    val fileString: String = File("src/main/resources/rectangles.txt").readText()
    println("Text in file: $fileString")
    val arr = fileString.replace("\\s".toRegex(), "").split(";")
    var minX: Int = -1
    var minY: Int = -1
    arr.forEach {
        if (minX > it.substringBefore(',').toInt() || minX < 0) {
            minX = it.substringBefore(',').toInt()
        }
        if (minY > it.substringAfter(',').toInt() || minY < 0) {
            minY = it.substringAfter(',').toInt()
        }
    }
    println("maxX: $minX")
    println("maxY: $minY")
}