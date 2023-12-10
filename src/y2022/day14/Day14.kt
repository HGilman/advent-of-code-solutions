package y2022.day14

import lib.Path
import lib.Point2D
import readInput

fun main() {
    val day = 14
    val testInput = readInput("day$day/testInput")
//    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInput("day$day/input")
//    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {

    val paths: List<Path> = input.map {
        Path(it.split(" -> ").map {
            val (x, y) = it.split(",").map { it.toInt() }
            Point2D(x, y)
        })
    }

    val minX: Int = paths.minOf { it.points.minOf { it.x } }
    val maxX = paths.maxOf { it.points.maxOf { it.x } }

    val minY = 0
    val maxY = paths.maxOf { it.points.maxOf { it.y } }

    val field = List(maxY + 1) {
        mutableListOf<PlayObject>()
    }

    for (y in 0 until maxY + 1) {
        for (x in 0 until maxX - minX + 1) {
            field[y].add(Air)
        }
    }

    paths.forEach { path ->
        path.getAllPoints().forEach { p ->
            field[p.y][p.x - minX] = Rock
        }
    }

    field[0][500 - minX] = Source

    val startPosition = Point2D(500 - minX, 0)
    var sand = startPosition

    var shouldAddSand = true
    var amountAdded = 0

    while (shouldAddSand) {

        if (field[sand.y + 1][sand.x] == Air) {

            // down pos is not blocked, go down
            sand = Point2D(sand.x, sand.y + 1)
//            println("go down, newPos: $sand")
        } else if (field[sand.y + 1][sand.x - 1] == Air) {

            // left is not blocked go diagonally left
            sand = Point2D(sand.x - 1, sand.y + 1)
//            println("go left, newPos: $sand")
        } else if (field[sand.y + 1][sand.x + 1] == Air) {

            // right is not blocked to diagonally right
            sand = Point2D(sand.x + 1, sand.y + 1)
//            println("go right, newPos: $sand")
        } else {
            // all option tried
            field[sand.y][sand.x] = Sand
//            println("added sand, $sand")
            sand = startPosition
            amountAdded++
        }

        // sand is out
        if (sand.x > maxX - minX || sand.x - 1 < 0 || sand.y + 1 > maxY) {
            shouldAddSand = false
        }
    }

    for (y in 0 until maxY + 1) {

        var line = ""
        for (x in minX until maxX + 1) {
            line += field[y][x - minX].symbol
        }
        println(line)
    }
    println("added: $amountAdded")

    return 1
}

fun part2(input: List<String>): Int {

    val paths1: List<Path> = input.map {
        Path(it.split(" -> ").map {
            val (x, y) = it.split(",").map { it.toInt() }
            Point2D(x, y)
        })
    }

    val maxY1 = paths1.maxOf { it.points.maxOf { it.y } }

    val floor = Path(
        listOf(
            Point2D(500 - (maxY1 + 2), maxY1 + 2),
            Point2D(500 + maxY1 + 2, maxY1 + 2)
        )
    )

    val maxY = maxY1 + 2

    val paths = paths1 + floor

    val minX = -maxY + 500
    val maxX = maxY + 500


    val field = List(maxY + 1) {
        mutableListOf<PlayObject>()
    }

    for (y in 0 until maxY + 1) {
        for (x in 0 until maxX - minX + 1) {
            field[y].add(Air)
        }
    }

    paths.forEach { path ->
        path.getAllPoints().forEach { p ->
            field[p.y][p.x - minX] = Rock
        }
    }

    field[0][500 - minX] = Source

    val startPosition = Point2D(500 - minX, 0)
    var sand = startPosition

    var shouldAddSand = true
    var amountAdded = 0

    for (y in 0 until maxY + 1) {

        var line = ""
        for (x in minX until maxX + 1) {
            line += field[y][x - minX].symbol
        }
        println(line)
    }
    println()
    println()

    while (shouldAddSand) {

        if (field[sand.y + 1][sand.x] == Air) {

            // down pos is not blocked, go down
            sand = Point2D(sand.x, sand.y + 1)
//            println("go down, newPos: $sand")
        } else if (field[sand.y + 1][sand.x - 1] == Air) {

            // left is not blocked go diagonally left
            sand = Point2D(sand.x - 1, sand.y + 1)
//            println("go left, newPos: $sand")
        } else if (field[sand.y + 1][sand.x + 1] == Air) {

            // right is not blocked to diagonally right
            sand = Point2D(sand.x + 1, sand.y + 1)
//            println("go right, newPos: $sand")
        } else {
            // all option tried
            field[sand.y][sand.x] = Sand
//            println("added sand, $sand")
            amountAdded++

            if (sand == startPosition) {
                break
            }
            sand = startPosition
        }

        // sand is out
        if (sand.x + 1 > maxX - minX || sand.x - 1 < 0 || sand.y + 1 > maxY) {
            shouldAddSand = false
        }
    }

    for (y in 0 until maxY + 1) {

        var line = ""
        for (x in minX until maxX + 1) {
            line += field[y][x - minX].symbol
        }
        println(line)
    }
    println("added: $amountAdded")

    return 1
}

sealed class PlayObject(val symbol: Char)
object Sand : PlayObject('o')
object Source : PlayObject('+')
object Air : PlayObject('.')
object Rock : PlayObject('#')