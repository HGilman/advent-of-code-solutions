package y2020.d3

import AbstractSolution

class Solution : AbstractSolution("src/main/kotlin/y2020/d3/input.txt") {

    val slopes = listOf(
        Slope(1, 1),
        Slope(3, 1),
        Slope(5, 1),
        Slope(7, 1),
        Slope(1, 2)
    )

    override fun solveFirstPart() {
        println(calculateAmountOfTrees(lines, slope = slopes[1]))
    }

    override fun solveSecondPart() {

        val treesOnEachSlope = slopes.map {
            calculateAmountOfTrees(lines, it)
        }.map {
            it.toLong()
        }

        println(treesOnEachSlope)

        val multiply = treesOnEachSlope.reduce { acc, i ->
            println(acc)
            acc * i
        }
        println(multiply)

    }

    private fun calculateAmountOfTrees(lines: List<String>, slope: Slope): Int {
        val width = lines[0].length
        var amountOfTrees = 0
        var x = 0

        // go down by slope.down
        for (y in lines.indices step slope.down) {

            if (lines[y][x % width] == '#') {
                amountOfTrees++
            }
            // go right by slope.right
            x += slope.right
        }
        return amountOfTrees
    }

    data class Slope(val right: Int, val down: Int)
}
