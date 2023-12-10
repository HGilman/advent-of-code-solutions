package y2020.d2

import AbstractSolution
import java.io.File

class Solution : AbstractSolution("src/main/kotlin/y2020/d2/input.txt") {


    override fun solveFirstPart() {
        val lines = File(inputPath).readLines().map { line ->
            val password = line.substringAfter(": ")
            val symbol = line.substringAfter(" ").substringBefore(":").single()
            val limit = line.substringBefore(" ").let {
                val (start, end) = it.split("-")
                Pair(start.toInt(), end.toInt())
            }
            Line(symbol= symbol, amountLimit = limit, password )
        }

        var validAmount = 0
        lines.forEach {
            if (it.isValidFirstPart()) {
                validAmount++
            }
        }
        println(validAmount)
    }

    override fun solveSecondPart() {
        val lines = File(inputPath).readLines().map { it ->
            val elements: List<String> = it.trim().split(" ").map { it.trim() }
            val minAmount = elements[0].split('-')[0].toInt()
            val maxAmount = elements[0].split('-')[1].toInt()
            Line(symbol= elements[1][0], amountLimit = Pair(minAmount, maxAmount), password = elements[2] )
        }

        var validAmount = 0
        lines.forEach {
            if (it.isValidSecondPart()) {
                validAmount++
            }
        }
        println(validAmount)
    }
}


data class Line(val symbol: Char, val amountLimit: Pair<Int, Int>, val password: String) {

    fun isValidFirstPart()  = password.count { it == symbol } in amountLimit.first..amountLimit.second

    fun isValidSecondPart() = (password[amountLimit.first - 1] == symbol) xor (password[amountLimit.second - 1] == symbol)

}
