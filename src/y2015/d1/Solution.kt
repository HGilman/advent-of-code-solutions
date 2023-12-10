package y2015.d1

import AbstractSolution
import java.io.File

class Solution : AbstractSolution("src/main/kotlin/y2015/d1/input.txt"){

    override fun solveFirstPart() {
        var floor = 0
        getChars().forEach {
            floor += getDiff(it)
        }
        println(floor)
    }

    override fun solveSecondPart() {
        var floor = 0
        getChars().forEachIndexed { i, c ->
            floor += getDiff(c)
            if (floor == -1) {
                println(i + 1)
                return
            }
        }
    }

    private fun getDiff(char: Char) = if (char == '(') 1 else -1

    private fun getChars(): CharArray {
        return File(inputPath).readLines().map { it.toCharArray() }.first()
    }

}
