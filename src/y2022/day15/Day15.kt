package y2022.day15

import lib.Point2D
import lib.Vector2D
import readInput
import kotlin.math.abs

fun main() {
    val day = 15
    val testInput = readInput("day$day/testInput")
//    check(part1(testInput, 10) == 26)

//    val testRes = part2(testInput, 20)
//    check(testRes == 56000011)

    val input = readInput("day$day/input")
//    println(part1(input, 2000000))
    println(part2(input, 4000000))
}

fun part1(input: List<String>, lineNumber: Int): Int {
    val pairs = pairs(input)

    val occupiedPoints = mutableSetOf<Point2D>()
    pairs.forEach {
        val distance = it.first.manhattanDistanceTo(it.second)
        val distanceToLine = abs(it.first.y - lineNumber)
        if (distanceToLine <= distance) {
            val diff = abs(distance - distanceToLine)
            for (i in -diff until diff + 1) {
                occupiedPoints.add(Point2D(it.first.x + i, lineNumber))
            }
        }
    }

    // removing beacons from occupied set
    pairs.forEach {
        if (occupiedPoints.contains(it.second)) {
            occupiedPoints.remove(it.second)
        }
    }
    return occupiedPoints.size
}

fun part2(input: List<String>, borderSize: Int): Long {

    val pairs = pairs(input)
    val xRange = (0..borderSize)
    val yRange = (0..borderSize)

    pairs.forEach { p ->
        val distance = p.first.manhattanDistanceTo(p.second) + 1
        (0..distance).forEach { dx ->
            val borderPoints = listOf<Point2D>(
                // left top
                p.first + Vector2D(-distance + dx, dx),

                // right top
                p.first + Vector2D(distance - dx, dx),

                // left bottom
                p.first + Vector2D(-distance + dx, -dx),

                // right bottom
                p.first + Vector2D(distance - dx, -dx)
            )
            for (border in borderPoints) {
                if (border.x in xRange && border.y in yRange) {
                    if (pairs.all { it.first.manhattanDistanceTo(it.second) < it.first.manhattanDistanceTo(border) }) {
                        return getTuningFrequency(border)
                    }
                }
            }
        }
    }
    return -1L
}

private fun pairs(input: List<String>): List<Pair<Point2D, Point2D>> {
    val pairs = input.map {

        val sensX = it.substringAfter("x=").substringBefore(",").toInt()
        val sensY = it.substringAfter("y=").substringBefore(":").toInt()

        val beaconX = it.substringAfterLast("x=").substringBefore(",").toInt()
        val beaconY = it.substringAfterLast("y=").toInt()
        Pair(Point2D(sensX, sensY), Point2D(beaconX, beaconY))
    }
    return pairs
}

fun getTuningFrequency(p: Point2D): Long {
    return p.x * 4_000_000L + p.y
}
