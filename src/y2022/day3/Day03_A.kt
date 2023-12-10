package y2022.day3

import readInput

fun main() {
    fun part1(input: List<String>): Int {

        return input.sumOf { rucksack ->
            val compartmentSize = rucksack.length / 2
            val firstCompartment = rucksack.substring(0, compartmentSize)
            val secondCompartment = rucksack.substring(compartmentSize)

            // actually there is no need to make full intersection,
            // it is enough to find first symbol, that is contained in both parts
            val commonSymbols = firstCompartment.toSet() intersect secondCompartment.toSet()
            commonSymbols.first().priority()

        }
    }

    fun part2(input: List<String>): Int {

        val elfGroups: List<List<String>> = input.chunked(3)

        return elfGroups.sumOf { group: List<String> ->

            val firstElf = group[0].toSet()
            val secondElf = group[1].toSet()
            val thirdElf = group[2].toSet()

            val intersection = firstElf intersect secondElf intersect thirdElf

            intersection.first().priority()
        }
    }

    val testInput = readInput("day3/Day03_test")
    check(part1(testInput) == 157)

    val input = readInput("day3/Day03")

    println(part1(input))
    println(part2(input))
}

fun Char.priority(): Int {
    return if (isLowerCase()) {
        this - 'a' + 1
    } else {
        this - 'A' + 27
    }
}