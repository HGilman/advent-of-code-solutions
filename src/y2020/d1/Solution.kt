package y2020.d1

import AbstractSolution
import java.io.File

class Solution : AbstractSolution("src/main/kotlin/y2020/d1/input.txt") {

    override fun solveFirstPart() {
        val numbers = File(inputPath).readLines().map { it.toInt() }
        val pair = numbers.findPairOfSum(2020)
        println(pair)
        println(pair?.let { (x, y) -> x * y })
    }

    override fun solveSecondPart() {
        val numbers = File(inputPath).readLines().map { it.toInt() }
        val triple = numbers.findTripleOfSum()
        println(triple)
        println(triple?.let { (x, y, z) -> x * y * z })
    }

    private fun List<Int>.findTripleOfSum(): Triple<Int, Int, Int>? =
        firstNotNullOfOrNull { x ->
            findPairOfSum(2020 - x)?.let { pair ->
                Triple(x, pair.first, pair.second)
            }
        }

    private fun List<Int>.findPairOfSum(sum: Int): Pair<Int, Int>? {
        val complements: Map<Int, Int> = associateBy { sum - it }
        return firstNotNullOfOrNull { number ->
            complements[number]?.let { complement ->
                Pair(number, complement)
            }
        }
    }
}
