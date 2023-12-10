package y2022.day25

import readInput
import kotlin.math.pow

fun main() {
    val day = 25
    val testInput = readInput("day$day/testInput")
    check(part1(testInput) == "2=-1=0")

    val input = readInput("day$day/input")
    println(part1(input))

}

fun part1(input: List<String>): String {
    return convertDigitToSnafu(input.sumOf { convertFromSnafuToDigit(it) })
}

fun part2(input: List<String>): Int {
    return input.size
}

fun convertFromSnafuToDigit(snafu: String): Long {
    val snafuDigits = snafu.toCharArray().reversedArray()

    val digit = snafuDigits.mapIndexed { index, snafuDigit ->

        var digit = 5.0.pow(index).toLong()

        if (snafuDigit == '-') {
            digit *= -1
        } else if (snafuDigit == '=') {
            digit *= -2
        } else {
            digit *= snafuDigit.digitToInt()
        }
        digit
    }.sum()
    return digit
}
fun convertDigitToSnafu(digit: Long): String {
    // convert to 5
    val fivesRadixNums = digit.toString(5).map { it.digitToInt() }.toMutableList()

    val res = mutableListOf<String>()

    for (i in fivesRadixNums.size - 1 downTo  0) {

        val r: Int = fivesRadixNums[i]

        if (r <= 2) {
            res.add(0, r.toString())
        } else {

            if (r == 3) {
                res.add(0, "=")
            } else if (r == 4) {
                res.add(0, "-")
            } else if (r == 5) {
                res.add(0, "0")
            }

            if (i != 0) {
                fivesRadixNums[i - 1] += 1
            } else {
                res.add(0, 1.toString())
            }
        }
    }

    return res.joinToString(separator = "")
}