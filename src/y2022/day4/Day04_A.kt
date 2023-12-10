package y2022.day4

import readInput
import kotlin.math.max

fun main() {
    val testInput = readInput("day4/Day04_test")
    check(part1(testInput) == 2)

    val input = readInput("day4/Day04")

    println(part1(input))
    println(part2(input))
}

fun parseInput(input: List<String>): List<List<IntRange>> {
    return input.map { it.split(',') }.map { (a: String, b: String) ->
        val (a1, a2) = a.split('-').map { it.toInt() }
        val (b1, b2) = b.split('-').map { it.toInt() }

        listOf(a1..a2, b1..b2)
    }
}

/**
 * if one range contains other, then union of them will be same as bigger range
 */
fun part1(input: List<String>) = parseInput(input).count { (f, s) ->
    f.union(s).size == max(f.last - f.first, s.last - s.first) + 1
}

fun part2(input: List<String>) = parseInput(input).count { (f, s) ->
    f.intersect(s).isNotEmpty()
}