package y2022.day4

import readInput

fun main() {
    val testInput = readInput("day4/Day04_test")
    check(Day4B.part1(testInput) == 2)

    val input = readInput("day4/Day04")

    println(Day4B.part1(input))
    println(Day4B.part2(input))
}

object Day4B {

    fun part1(input: List<String>) = getState(input).count { state ->
        state == RangesRelativeState.ONE_CONTAINS_OTHER
    }

    fun part2(input: List<String>) = getState(input).count { state ->
        state == RangesRelativeState.ONE_CONTAINS_OTHER || state == RangesRelativeState.PART_INTERSECTION
    }

    private fun getState(input: List<String>): List<RangesRelativeState> {
        return input.map { it.split(',') }.map { (a: String, b: String) ->
            val (a1, a2) = a.split('-').map { it.toInt() }
            val (b1, b2) = b.split('-').map { it.toInt() }

            getRelativeState(a1..a2, b1..b2)
        }
    }

    enum class RangesRelativeState {
        NO_INTERSECTION,
        PART_INTERSECTION,
        ONE_CONTAINS_OTHER
    }

    private fun getRelativeState(f: IntRange, s: IntRange): RangesRelativeState {
        return if (f.last < s.first || s.last < f.first) {
            RangesRelativeState.NO_INTERSECTION
        } else if ((f.first >= s.first && f.last <= s.last) ||
            (s.first >= f.first && s.last <= f.last)) {
            RangesRelativeState.ONE_CONTAINS_OTHER
        } else {
            RangesRelativeState.PART_INTERSECTION
        }
    }
}

