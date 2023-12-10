package y2020.d6

import AbstractSolution
import java.io.File

class Solution : AbstractSolution("src/main/kotlin/y2020/d6/input.txt") {

    private val groups: List<String> = File(inputPath).readText().split("\n\n")

    override fun solveFirstPart() {
        val result: Int = groups.sumOf { it.replace("\n", "").toSet().size }
        println(result)
    }

    override fun solveSecondPart() {

        val groupSets: List<List<Set<Char>>> = groups.map { group ->
            group.split("\n").map(String::toSet)
        }

        val intersectionAmounts = groupSets.map { it: List<Set<Char>> ->
            val intersectionSet: Set<Char> = it.reduce { acc, chars: Set<Char> ->
                acc intersect chars
            }
            intersectionSet.count()
        }
        println(intersectionAmounts.sum())
    }

}