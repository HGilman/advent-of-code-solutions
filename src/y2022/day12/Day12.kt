package y2022.day12

import lib.Point2D
import lib.directionsClockwise
import readInput
import java.util.LinkedList
import kotlin.math.min

fun main() {

    val input = readInput("day12/input")
    val h = input.size
    val w = input[0].length

    var start = Point2D(0, 0)
    var end = Point2D(0, 0)

    val heights = List(h) { y ->
        List(w) { x ->
            val char = input[y][x]
            val point = Point2D(x, y)
            if (char == 'S') {
                start = point
            }
            if (char == 'E') {
                end = point
            }
            height(char)
        }
    }

    fun shortestDistance(start: Point2D, end: Point2D): Int {

        val distances: List<MutableList<Int>> = List(h) {
            MutableList(w) { Int.MAX_VALUE }
        }
        distances[start.y][start.x] = 0

        val queue = LinkedList<Point2D>()
        queue.offer(start)

        var res = Int.MAX_VALUE

        while (queue.isNotEmpty()) {

            val currentPoint = queue.poll()

            if (currentPoint == end) {
                res = distances[currentPoint.y][currentPoint.x]
                break
            }

            for (d in directionsClockwise) {
                val newPoint = currentPoint + d
                if (newPoint.x in 0 until w && newPoint.y in 0 until h) {

                    if (heights[newPoint.y][newPoint.x] - heights[currentPoint.y][currentPoint.x] <= 1) {
                        val newDistance = distances[currentPoint.y][currentPoint.x] + 1

                        if (newDistance < distances[newPoint.y][newPoint.x]) {
                            distances[newPoint.y][newPoint.x] = newDistance
                            queue.offer(newPoint)
                        }
                    }
                }
            }
        }
        return res
    }
    println(shortestDistance(start, end))

    var ans = Int.MAX_VALUE

    for (y in 0 until h) {
        for (x in 0 until w) {
            if (input[y][x] == 'a') {
                ans = min(ans, shortestDistance(Point2D(x, y), end))
            }
        }
    }
    println(ans)
}

fun height(c: Char): Int {
    val char: Char = when (c) {
        'S' -> {
            'a'
        }

        'E' -> {
            'z'
        }

        else -> {
            c
        }
    }
    return char - 'a'
}