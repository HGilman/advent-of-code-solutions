package y2022.day8

import readInput

fun main() {
    val testInput = readInput("day8/Day08_test")
    val testField = parseInput(testInput)
    check(part1(testField) == 21)
    check(part2(testField) == 8)

    val input = readInput("day8/Day08")
    val field = parseInput(input)
    println(part1(field))
    println(part2(field))
}

fun parseInput(input: List<String>) = input.map { it.map { c -> c.digitToInt() } }

fun part1(field: List<List<Int>>): Int {
    val width = field.first().size
    val height = field.size
    var count = 0

    for (y in 0 until height) {
        for (x in 0 until width) {
            val h = field[y][x]

            if (((y == 0 || y == height - 1 || x == 0 || x == width - 1))
                // left
                || (0 until x).all { field[y][it] < h }
                // right
                || (x + 1 until width).all { field[y][it] < h }
                // top
                || (0 until y).all { field[it][x] < h }
                // bottom
                || (y + 1 until height).all { field[it][x] < h }
            ) {
                count++
            }
        }
    }
    return count
}

fun part2(field: List<List<Int>>): Int {

    val width = field.first().size
    val height = field.size

    val distances = mutableListOf<Int>()

    for (y in 0 until height) {
        for (x in 0 until width) {

            if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                continue
            }
            val h = field[y][x]

            val leftPart = (x - 1 downTo   0).map { field[y][it] }
            val left = leftPart.indexOfFirst { it >= h }
            val l = if (left == -1) leftPart.size else left + 1

            val rightPart = (x + 1 until  width).map { field[y][it] }
            val right = rightPart.indexOfFirst { it >= h }
            val r = if (right == -1) rightPart.size else right + 1

            val topPart = (y - 1  downTo  0).map { field[it][x] }
            val top = topPart.indexOfFirst { it >= h }
            val t = if (top == -1) topPart.size else top + 1

            val bottomPart = (y + 1 until height).map { field[it][x] }
            val bottom = bottomPart.indexOfFirst { it >= h }
            val b = if (bottom == -1) bottomPart.size else bottom + 1

            distances.add(l * r * t * b)
        }
    }
    return distances.max()
}