package y2022.day1

import readTextGroups

fun main() {
    fun part1(input: List<String>): Int {

        return input
            .asSequence()
            .map { it -> it.split("\n").sumOf { it.toInt() } }
            .max()
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { it -> it.split("\n").sumOf { it.toInt() } }
            .sortedDescending()
            .take(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(day9.part1(testInput) == 1)

    val input = readTextGroups("day1/Day01")

    println(part1(input))
    println(part2(input))
}