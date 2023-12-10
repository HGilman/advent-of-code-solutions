package y2022.day22

import readInput

enum class GameObject(val c: Char) {
    EMPTY(' '), OPEN('.'), WALL('#');

    companion object {
        fun fromChar(c: Char): GameObject {
            return when(c) {
                '.' -> OPEN
                ' ' -> EMPTY
                '#' -> WALL
                else -> error("not supported char")
            }
        }
    }
}

fun main() {
    val day = 22
    val testInput = readInput("day$day/testInput")

//    val res1 = part1(testInput)
//    println(res1)
//    check(res1 == 6032)

//    val res2 = part2(testInput)
//    println(res2)
//    check(res2 == 5031)

    val input = readInput("day$day/input")
//    println(part1(input))
    println(part2SecondTry(input))
}


fun parseCommands(s: String): List<String> {
    val commands = mutableListOf<String>()
    var prevCharIndex = 0

    for (i in s.indices) {
        if (s[i] == 'R' || s[i] == 'L') {

            commands.add(s.substring(if (prevCharIndex == 0) 0 else prevCharIndex + 1, i))
            commands.add(s[i].toString())
            prevCharIndex = i
        }
        if (i == s.length - 1) {
            commands.add(s.substring(prevCharIndex + 1, i + 1))
        }
    }
    return commands
}