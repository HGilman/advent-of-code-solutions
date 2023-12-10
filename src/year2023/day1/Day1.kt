package year2023.day1

import readInput


fun main() {
    val day = 1
    val testInput = readInput("year2023/day$day/testInput")
    val testInput2 = readInput("year2023/day$day/testInput2")
//    check(part1(testInput) == 142)
    check(part2(testInput2) == 281)

//    val input = readInput("year2023/day$day/input")
//    println(part1(input))
//    println(part2(input))
}

fun part1(input: List<String>): Int {

    return input.map { line ->
        val firstDigit: Char = line.first { it.isDigit() }
        val lastDigit: Char = line.last { it.isDigit() }

        "$firstDigit$lastDigit".toInt()
    }.sum()
}


val digitWords = mapOf<String, Int>(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun part2(input: List<String>): Int {

    return input.map { line ->

        var l = line
        digitWords.forEach {
           l = l.replace(it.key, it.value.toString())
        }

        println("$line: $l")

        val firstDigit: Char = l.first { it.isDigit() }
        val lastDigit: Char = l.last { it.isDigit() }

        "$firstDigit$lastDigit".toInt()
    }.sum()
}