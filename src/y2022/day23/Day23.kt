package y2022.day23

import lib.Point2D
import lib.Vector2D
import readInput

fun main() {
    val day = 23
    val testInput = readInput("day$day/testInput")
//    check(part1(testInput) == 110)
//    check(part2(testInput) == 20)

    val input = readInput("day$day/input")
//    println(part1(input))
    println(part2(input))
}

val N = Vector2D(0, -1)
val NE = Vector2D(1, -1)
val NW = Vector2D(-1, -1)

val S = Vector2D(0, 1)
val SE = Vector2D(1, 1)
val SW = Vector2D(-1, 1)

val W = Vector2D(-1, 0)
val E = Vector2D(1, 0)

val allDirections = listOf(E, SE, S, SW, W, NW, N, NE)

val moveDirections = listOf(N, S, W, E)

val checkDirectionsMap = mapOf(
    N to listOf(N, NE, NW),
    S to listOf(S, SE, SW),
    W to listOf(W, NW, SW),
    E to listOf(E, NE, SE),
)

sealed class GroovyObject
object Empty : GroovyObject()

data class Elve(var position: Point2D) : GroovyObject() {

    var firstDirectionIndex: Int = 0

    fun proposedDirections() = (0..3).map { it ->
        val i = (firstDirectionIndex + it) % 4
        moveDirections[i]
    }

    fun getCheckDirections(direction: Vector2D) = checkDirectionsMap[direction]!!

    // if isAlone - it is surrounded by empty objects
    var isAlone = false

    var proposedNewPosition: Point2D? = null
}

fun part1(input: List<String>): Int {

    val initialHeight = input.size
    val initialWidth = input[0].length

    // from left and right
    val additionalX = 10

    // from top and bottom
    val additionalY = 10

    val elves = mutableListOf<Elve>()

    val width = additionalX + initialWidth + additionalX
    val height = additionalY + initialHeight + additionalY


    val field = MutableList(height) { y ->
        MutableList(width) { x ->
            if (x in (additionalX until (additionalX + initialWidth)) && y in (additionalY until (additionalY + initialHeight))) {

                val inicialX = x - additionalX
                val inicialY = y - additionalY
                val char = input[inicialY][inicialX]

                if (char == '#') {
                    Elve(Point2D(x, y)).apply {
                        elves.add(this)
                    }
                } else  {
                    Empty
                }
            } else {
                Empty
            }
        }
    }

    fun printField() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(if (field[y][x] is Empty) "." else "#")
            }
            println()
        }
    }

    printField()

    repeat(10) { r ->

        // firstHalf
        elves.forEach { e ->
            e.isAlone = true

            // look up 8 directions
            for (d in allDirections) {
                val lookUpPoint = e.position + d
                if (field[lookUpPoint.y][lookUpPoint.x] is Elve) {
                    e.isAlone = false
                    break
                }
            }
            println("Elve ${e.position} is alone?: ${e.isAlone}")

            // calculate proposed position
            if (!e.isAlone) {

                for (d in e.proposedDirections()) {

                    val checkDirections = e.getCheckDirections(d)
                    val isValidDirection = checkDirections.all { checkDir ->
                        val checkPoint = e.position + checkDir
                        field[checkPoint.y][checkPoint.x] !is Elve
                    }

                    if (isValidDirection) {
                        e.proposedNewPosition = e.position + d
                        println("Found direction for elve: ${e.position}, direction: $d")
                        break
                    }
                }

            }
        }

        // second half, trying to move
        val notAloneElves = elves.filter { !it.isAlone }
        val proposedPositions = notAloneElves.mapNotNull { it.proposedNewPosition }

        notAloneElves.forEach { e ->
            val newPosition = e.proposedNewPosition

            if (newPosition != null) {
                val otherPositions = proposedPositions - newPosition

                val isBlocked = otherPositions.contains(newPosition)

                println("Elve: ${e.position} isBlocked?: $isBlocked")

                if (!isBlocked) {
                    // previous position becomes empty
                    field[e.position.y][e.position.x] = Empty

                    // updating position
                    e.position = newPosition

                    // also update field
                    field[newPosition.y][newPosition.x] = e
                }
            }

        }

        elves.forEach {
            it.proposedNewPosition = null
            it.firstDirectionIndex++
        }

        println()
        println("Round: ${r+1}")
//        printField()
    }

    // finding rectangle coordinates, which contains all elves
    val startX: Int = elves.minOf { it.position.x }
    val endX = elves.maxOf { it.position.x }

    val startY = elves.minOf { it.position.y }
    val endY = elves.maxOf { it.position.y }

    var countEmpty = 0
    for (y in startY..endY) {
        for (x in startX..endX) {

            if (field[y][x] is Empty) {
                countEmpty++
            }
        }
    }
    println("rectangle: startX: $startX, endX: $endX; startY: $startY, endY: $endY")
    println(countEmpty)
    return countEmpty
}


fun part2(input: List<String>): Int {

    val initialHeight = input.size
    val initialWidth = input[0].length

    // from left and right
    val additionalX = 1000

    // from top and bottom
    val additionalY = 1000

    val elves = mutableListOf<Elve>()

    val width = additionalX + initialWidth + additionalX
    val height = additionalY + initialHeight + additionalY


    val field = MutableList(height) { y ->
        MutableList(width) { x ->
            if (x in (additionalX until (additionalX + initialWidth)) && y in (additionalY until (additionalY + initialHeight))) {

                val inicialX = x - additionalX
                val inicialY = y - additionalY
                val char = input[inicialY][inicialX]

                if (char == '#') {
                    Elve(Point2D(x, y)).apply {
                        elves.add(this)
                    }
                } else  {
                    Empty
                }
            } else {
                Empty
            }
        }
    }

    fun printField() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(if (field[y][x] is Empty) "." else "#")
            }
            println()
        }
    }

//    printField()

    var round = 1

    while(true) {
        // firstHalf
        elves.forEach { e ->
            e.isAlone = true

            // look up 8 directions
            for (d in allDirections) {
                val lookUpPoint = e.position + d
                if (field[lookUpPoint.y][lookUpPoint.x] is Elve) {
                    e.isAlone = false
                    break
                }
            }
//            println("Elve ${e.position} is alone?: ${e.isAlone}")

            // calculate proposed position
            if (!e.isAlone) {

                for (d in e.proposedDirections()) {

                    val checkDirections = e.getCheckDirections(d)
                    val isValidDirection = checkDirections.all { checkDir ->
                        val checkPoint = e.position + checkDir
                        field[checkPoint.y][checkPoint.x] !is Elve
                    }

                    if (isValidDirection) {
                        e.proposedNewPosition = e.position + d
//                        println("Found direction for elve: ${e.position}, direction: $d")
                        break
                    }
                }

            }
        }

        // second half, trying to move
        val notAloneElves = elves.filter { !it.isAlone }
        val proposedPositions = notAloneElves.mapNotNull { it.proposedNewPosition }

        notAloneElves.forEach { e ->
            val newPosition = e.proposedNewPosition

            if (newPosition != null) {
                val otherPositions = proposedPositions - newPosition

                val isBlocked = otherPositions.contains(newPosition)

//                println("Elve: ${e.position} isBlocked?: $isBlocked")

                if (!isBlocked) {
                    // previous position becomes empty
                    field[e.position.y][e.position.x] = Empty

                    // updating position
                    e.position = newPosition

                    // also update field
                    field[newPosition.y][newPosition.x] = e
                }
            }

        }

        elves.forEach {
            it.proposedNewPosition = null
            it.firstDirectionIndex++
        }

        println()
        println("Round: $round, notAloneSize: ${notAloneElves.size}")

        if (notAloneElves.isEmpty()) {
            break
        }

        round++
    }

    return round
}
