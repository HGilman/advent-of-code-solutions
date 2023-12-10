package y2022.day2

import y2022.day2.Chain.getFightPoints
import y2022.day2.Chain.getSignForResult
import readInput

sealed class Result {
    companion object {
        fun fromSymbol(s: Char): Result {
            return when(s) {
                'X' -> Lose
                'Y' -> Draw
                else -> Win
            }
        }
    }
}
object Win : Result()
object Lose : Result()
object Draw : Result()


sealed class Sign(val id: Int, val points: Int) {

    companion object {

        fun fromSymbol(s: Char): Sign {
            return when (s) {
                'A', 'X' -> {
                    Rock
                }
                'B', 'Y' -> {
                    Paper
                }
                else -> {
                    Scissors
                }
            }
        }
    }
}

object Paper : Sign(0, 2)
object Rock : Sign(1, 1)
object Scissors : Sign(2, 3)

object Chain {
    private val chain = listOf(Paper, Rock, Scissors)

    fun Sign.beatSign(): Sign {
        return chain[(id + 1) % chain.size]
    }

    fun Sign.isBeatenBySign(): Sign {
        val newId = id - 1
        return if (newId < 0) chain[chain.size - 1] else chain[newId]
    }

    fun Sign.getFightPoints(opponent: Sign): Int {
        return when (opponent) {
            this -> {
                3
            }
            beatSign() -> {
                6
            }
            isBeatenBySign() -> {
                0
            }
            else -> 0
        }
    }

    fun Sign.getSignForResult(wantedResult: Result): Sign {
        return when (wantedResult) {
            Draw -> this
            Win -> isBeatenBySign()
            Lose -> beatSign()
        }
    }
}

fun main() {

    fun getSymbols(input: List<String>): List<Pair<Char, Char>> {
        return input.map {
            val (first, second) = it.split(" ").map { s -> s.first() }
            Pair(first, second)
        }
    }

    fun getPoints(signs: List<Pair<Sign, Sign>>): Int {
        return signs.fold(0) { points, pair ->
            val (opponent, me) = pair
            points + me.points + me.getFightPoints(opponent)
        }
    }

    fun part1(input: List<String>): Int {
        val signs = getSymbols(input).map {
            val opponent = Sign.fromSymbol(it.first)
            val me = Sign.fromSymbol(it.second)
            Pair(opponent, me)
        }
        return getPoints(signs)
    }

    fun part2(input: List<String>): Int {
        val signs = getSymbols(input).map {
            val opponent = Sign.fromSymbol(it.first)
            val wantedResult = Result.fromSymbol(it.second)
            val me = opponent.getSignForResult(wantedResult)
            Pair(opponent, me)
        }
        return getPoints(signs)
    }

    val testInput = readInput("day2/Day02_test")
    println(part1(testInput))
    check(part1(testInput) == 15)

    val input = readInput("day2/Day02")
    println(part1(input))
    println(part2(input))
}





