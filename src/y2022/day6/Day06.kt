package y2022.day6

import readText

fun main() {
    val testInput = readText("day6/Day06_test")
    check(Day.part1(testInput) == 11)
    check(Day.part2(testInput) == 26)

    val input = readText("day6/Day06")

    println(Day.part1(input))
    println(Day.part2(input))
}

object Day {
    private fun getIndex(windowSize: Int, input: String): Int {
        return input
            .windowed(windowSize)
            .map { it.toSet().size }
            .indexOfFirst { it == windowSize } + windowSize
    }

    fun part1(input: String) = getIndex(4, input)

    fun part2(input: String) = getIndex(14, input)
}