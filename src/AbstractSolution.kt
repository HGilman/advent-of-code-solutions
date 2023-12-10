import java.io.File

open class AbstractSolution(
    val inputPath: String
) {

    val lines: List<String> = File(inputPath).readLines()

    open fun solveFirstPart() {}

    open fun solveSecondPart() {}
}
