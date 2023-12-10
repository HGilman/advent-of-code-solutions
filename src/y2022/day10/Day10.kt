package y2022.day10

import readInput

fun main() {
    val day = 10
    val testInput = readInput("day$day/testInput")
    check(part1(testInput) == 13140)

    val input = readInput("day$day/input")
    println(part1(input))
    part2(input)
}

sealed class Command(val cycles: Int)
object Noop : Command(1)
class AddX(val x: Int) : Command(2)

fun parseInput(input: List<String>): List<Command> {
    return input.map {
        if (it.startsWith("addx")) {
            val value = it.substringAfter(" ").toInt()
            AddX(value)
        } else {
            Noop
        }
    }
}

fun part1(input: List<String>): Int {
    val commandQueue = ArrayDeque(parseInput(input))
    var cycle = 0
    var value = 1
    var res = 0
    var commandCycle = 0

    val interestCycles = listOf(20, 60, 100, 140, 180, 220)

    while (commandQueue.isNotEmpty()) {
        cycle++
        commandCycle++

        if (cycle in interestCycles) {
            res += value * cycle
        }

        val command = commandQueue.first()
        if (commandCycle == command.cycles) {
            if (command is AddX) {
                value += command.x
            }
            commandQueue.removeFirst()
            commandCycle = 0
        }
    }
    return res
}

fun part2(input: List<String>) {
    val commandQueue = ArrayDeque(parseInput(input))
    var cycle = 0
    var commandCycle = 0
    var spritePos = 1

    fun printCurrent() {
        val crtPos = (cycle - 1) % 40

        if (crtPos == 0) {
            println()
        }
        if (crtPos in spritePos - 1..spritePos + 1) {
            print("#")
        } else {
            print(".")
        }
    }

    while (!commandQueue.isEmpty()) {
        cycle++
        commandCycle++

        printCurrent()

        val command = commandQueue.first()
        if (commandCycle == command.cycles) {
            if (command is AddX) {
                spritePos += command.x
            }
            commandQueue.removeFirst()
            commandCycle = 0
        }
    }
}