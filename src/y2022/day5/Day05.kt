package y2022.day5

import readTextGroups
import java.util.*

fun main() {
    val (testStacksInput, testProcedureInput) = readTextGroups("day5/Day05_test")
    check(Day.part1(getStacks(testStacksInput), getProcedure(testProcedureInput)) == "CMZ")

    val (stackInput, procedureInput)= readTextGroups("day5/Day05")
    println(Day.part1(getStacks(stackInput), getProcedure(procedureInput)))
    println(Day.part2(getStacks(stackInput), getProcedure(procedureInput)))
}

fun getStacks(firstPartInput: String): List<Stack<Char>> {
    val firstPartData: List<String> = firstPartInput.split("\n")

    val stackAmount = firstPartData
        .last()
        .last { it.isDigit() }
        .digitToInt()

    val stacks = List<Stack<Char>>(stackAmount) {
        Stack()
    }

    // don't take last string
    val stackData = firstPartData.dropLast(1)

    // go from bottom to top
    stackData
        .reversed()
        .forEach { line ->
            for (i in 0 until stackAmount) {
                val symbolIndex = (1 + i * 4)

                if (symbolIndex < line.length) {
                    val symbol = line[symbolIndex]
                    if (!symbol.isWhitespace()) {
                        stacks[i].push(symbol)
                    }
                }
            }
        }

    return stacks
}

fun getProcedure(secondPartInput: String): List<Triple<Int, Int, Int>> {
    return secondPartInput
        .split("\n")
        .map {
            val parts = it.split(' ')
            Triple(parts[1].toInt(), parts[3].toInt() - 1, parts[5].toInt() - 1)
        }
}

object Day {

    private fun getResult(stacks: List<Stack<Char>>): String {
        return stacks.map { s ->
            s.peek()
        }.fold("") { acc, c ->
            acc + c
        }
    }

    fun part1(stacks: List<Stack<Char>>, procedure: List<Triple<Int, Int, Int>>): String {

        procedure.forEach { p ->
            val (amount, from, to) = p
            val fromStack = stacks[from]
            val toStack = stacks[to]

            repeat(amount) {
                if (fromStack.isNotEmpty()) {
                    toStack.push(fromStack.pop())
                }
            }
        }

        return getResult(stacks)
    }

    fun part2(stacks: List<Stack<Char>>, procedure: List<Triple<Int, Int, Int>>): String {

        procedure.forEach { p ->
            val (amount, from, to) = p
            val fromStack = stacks[from]
            val toStack = stacks[to]

            val popArray = mutableListOf<Char>()

            repeat(amount) {
                if (fromStack.isNotEmpty()) {
                    popArray.add(fromStack.pop())
                }
            }

            popArray.reversed().forEach { pop ->
                toStack.push(pop)
            }
        }
        return getResult(stacks)
    }
}
