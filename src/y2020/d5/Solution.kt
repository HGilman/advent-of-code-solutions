package y2020.d5

import AbstractSolution

class Solution : AbstractSolution("src/main/kotlin/y2020/d5/input.txt") {

    override fun solveFirstPart() {
       val maxSeatId = lines.map {
            calculateRowNumber(it) * 8 + calculateColumnNumber(it)
        }.maxOrNull()

        println(maxSeatId)
    }

    override fun solveSecondPart() {
        val ids = lines.map {
            calculateRowNumber(it) * 8 + calculateColumnNumber(it)
        }

        val min = ids.minOrNull() ?: 0
        val max = ids.maxOrNull() ?: 0

        val fullSet = IntRange(min, max).toSet()

        println(fullSet.minus(ids.toSet()))
    }

    private fun calculateRowNumber(pass: String): Int {
        return pass
            .substring(0, 7)
            .toCharArray()
            .reversed()
            .mapIndexed { index, c ->
                if (c == 'B') {
                    2.pow(index)
                } else {
                    0
                }
            }.sum()
    }

    private fun calculateColumnNumber(pass: String): Int {
        return pass
            .substring(7, 10)
            .toCharArray()
            .reversed()
            .mapIndexed { index, c ->
                if (c == 'R') {
                    2.pow(index)
                } else {
                    0
                }
            }.sum()
    }


    fun Int.pow(x: Int): Int {
        return (0 until x).fold(1) { acc, i ->
            acc * this
        }
    }


}
