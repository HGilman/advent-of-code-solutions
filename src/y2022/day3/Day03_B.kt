package y2022.day3

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.chunked(it.length / 2) }
            .map { (a, b) -> a.first(b::contains) }
            .sumOf { it.priority() }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .map { (a, b, c) -> a.first { it in b && it in c } }
            .sumOf { it.priority() }
    }

//  test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)

    val input = readInput("Day03")

    println(part1(input))
    println(part2(input))
}