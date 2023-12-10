package y2022.day17

import lib.LeftDirection
import lib.DecreaseYDirection
import lib.Point2D
import lib.RightDirection
import lib.Vector2D
import readText

const val chamberWidth = 7
const val xStart = 2 // from 0 counting
const val yOffsetFromTop = 3


fun main() {
    val day = 17

    val testInput = readText("day$day/testInput")
//    val res1 = part2(testInput, 2022)
//    println(res1)
//    check(res1 == 3068L)

    val input = readText("day$day/input")
//    println(part2(input, 20_000))

    println(part2(input, 1752 + 1408))
}


// system of coordinate from bottom to top
// origin point of rocks is the leftest bottom point
data class Rock(val id: Int, val vectors: List<Vector2D>) {

    fun width(): Int = vectors.maxOf { it.x }

    fun height(): Int = vectors.maxOf { it.y } + 1
}

val firstRock = Rock(
    0,
    listOf(
        Vector2D(0, 0), Vector2D(1, 0), Vector2D(2, 0), Vector2D(3, 0)
    )
)

val secondRock = Rock(
    1,
    listOf(
        Vector2D(1, 0), Vector2D(0, 1), Vector2D(1, 1), Vector2D(2, 1), Vector2D(1, 2)
    )
)

val thirdRock = Rock(
    2,
    listOf(
        Vector2D(0, 0), Vector2D(1, 0), Vector2D(2, 0), Vector2D(2, 1), Vector2D(2, 2)
    )
)

val fouthRock = Rock(
    3,
    listOf(
        Vector2D(0, 0), Vector2D(0, 1), Vector2D(0, 2), Vector2D(0, 3)
    )
)

val fivthRock = Rock(
    4,
    listOf(
        Vector2D(0, 0), Vector2D(1, 0), Vector2D(0, 1), Vector2D(1, 1)
    )
)

val rocks = listOf<Rock>(
    firstRock, secondRock, thirdRock, fouthRock, fivthRock
)


enum class PushDirection {
    LEFT, RIGHT
}

data class TypedPoint(val point: Point2D, val isRock: Boolean) {
    private val rockSymbol = '#'
    private val airSymbol = '.'
    fun getSymbol() = if (isRock) rockSymbol else airSymbol
}

fun parseInput(input: String): List<PushDirection> {
    return input.map {
        if (it == '<') {
            PushDirection.LEFT
        } else {
            PushDirection.RIGHT
        }
    }
}

fun getRock(index: Long): Rock = rocks[(index % rocks.size).toInt()]

fun part1(input: String, rockAmount: Long): Int {
    val dirs = parseInput(input)

    var rockCounter = 1L
    var directionIndex = 0
    var currentTop = 0

    val chamber: MutableList<MutableList<TypedPoint>> = MutableList(3) { y ->
        MutableList(chamberWidth) { x ->
            TypedPoint(Point2D(x, y), false)
        }
    }

    while (rockCounter != rockAmount + 1) {

        val rock = getRock(rockCounter - 1L)

        val neededHeight = currentTop + yOffsetFromTop + rock.height()
        val addAmount = neededHeight - chamber.size

        // increase chamber height to rock's height
        val currentChamberHeight = chamber.size
        (1..addAmount).forEach { dy ->
            chamber.add(MutableList(chamberWidth) { x ->
                TypedPoint(Point2D(x, currentChamberHeight + dy), isRock = false)
            })
        }

        // origin position of current rock (left, bottom point)
        var position = Point2D(xStart, currentTop + yOffsetFromTop)

        var rockIsStill = false

        while (!rockIsStill) {

            // first process direction
            when (dirs[directionIndex]) {
                PushDirection.LEFT -> {
                    val newPosition = position.toLeft()

                    // first check if there is wall
                    if (newPosition.x >= 0) {
                        // iterate over each point of rock, and try to move it left, if there is rock -> it is not possible
                        if (
                            rock.vectors.all { v ->
                                val checkPoint = position + v + LeftDirection
                                (checkPoint.x in (0 until chamberWidth) && !chamber[checkPoint.y][checkPoint.x].isRock)
                            }
                        ) {
                            position = newPosition
                        }
                    }
                }

                PushDirection.RIGHT -> {
                    val newPosition = position.toRight()

                    // first check if there is wall
                    if (newPosition.x < chamberWidth) {

                        // iterate over each point of rock, and try to move it right, if there is rock -> it is not possible
                        if (
                            rock.vectors.all { v ->
                                val checkPoint = position + v + RightDirection
                                (checkPoint.x in (0 until chamberWidth) && !chamber[checkPoint.y][checkPoint.x].isRock)
                            }
                        ) {
                            position = newPosition
                        }
                    }
                }
            }
            directionIndex = (directionIndex + 1) % dirs.size

            // try to move one step down
            val newPosition = position.lower()

            if (newPosition.y >= 0
                && rock.vectors.all { v ->
                    val checkPoint = position + v + DecreaseYDirection
                    !chamber[checkPoint.y][checkPoint.x].isRock
                }
            ) {
                position = newPosition
            } else {
                rockIsStill = true
            }
        }
        // saving rock position to chamber array
        rock.vectors.forEach { v ->
            val p = position + v
            val currentChamberPointState = chamber[p.y][p.x]
            chamber[p.y][p.x] = currentChamberPointState.copy(isRock = true)
        }
        rockCounter++

        val newTop = position.y + rock.height()
        if (newTop > currentTop) {
            currentTop = newTop
        }
    }

    println()
    println("rockIndex: $rockCounter")
    println()
    printChamber(chamber)
    Thread.sleep(10L)

    return currentTop
}

fun printChamber(chamber: List<List<TypedPoint>>) {
    for (y in chamber.size - 1 downTo 0) {
        for (x in 0 until chamberWidth) {
            print(chamber[y][x].getSymbol() + " ")
        }
        println()
    }
}

fun part2(input: String, rockAmount: Long): Long {
    val dirs = parseInput(input)

    var rockCounter = 1L
    var lastEndDirRockCounter = 0L

    var directionIndex = 0
    var currentTop = 0

    val tops = mutableListOf<Int>()

    fun generateInitialState() = MutableList(3) { y ->
        MutableList(chamberWidth) { x ->
            TypedPoint(Point2D(x, y), false)
        }
    }

    var chamber: MutableList<MutableList<TypedPoint>> = generateInitialState()

    while (rockCounter != rockAmount + 1) {

        val rock = getRock(rockCounter - 1)

        val neededHeight = currentTop + yOffsetFromTop + rock.height()
        val addAmount = neededHeight - chamber.size

        // increase chamber height to rock's height
        val currentChamberHeight = chamber.size
        (1..addAmount).forEach { dy ->
            chamber.add(MutableList(chamberWidth) { x ->
                TypedPoint(Point2D(x, currentChamberHeight + dy), isRock = false)
            })
        }

        // origin position of current rock (left, bottom point)
        var position = Point2D(xStart, currentTop + yOffsetFromTop)

        var rockIsStill = false



        while (!rockIsStill) {

            // first process direction
            when (dirs[directionIndex]) {
                PushDirection.LEFT -> {
                    val newPosition = position.toLeft()

                    // first check if there is wall
                    if (newPosition.x >= 0) {
                        // iterate over each point of rock, and try to move it left, if there is rock -> it is not possible
                        if (
                            rock.vectors.all { v ->
                                val checkPoint = position + v + LeftDirection
                                (checkPoint.x in (0 until chamberWidth) && !chamber[checkPoint.y][checkPoint.x].isRock)
                            }
                        ) {
                            position = newPosition
                        }
                    }
                }

                PushDirection.RIGHT -> {
                    val newPosition = position.toRight()

                    // first check if there is wall
                    if (newPosition.x < chamberWidth) {

                        // iterate over each point of rock, and try to move it right, if there is rock -> it is not possible
                        if (
                            rock.vectors.all { v ->
                                val checkPoint = position + v + RightDirection
                                (checkPoint.x in (0 until chamberWidth) && !chamber[checkPoint.y][checkPoint.x].isRock)
                            }
                        ) {
                            position = newPosition
                        }
                    }
                }
            }
            directionIndex = (directionIndex + 1) % dirs.size

            // try to move one step down
            val newPosition = position.lower()

            if (newPosition.y >= 0
                && rock.vectors.all { v ->
                    val checkPoint = position + v + DecreaseYDirection
                    !chamber[checkPoint.y][checkPoint.x].isRock
                }
            ) {
                position = newPosition
            } else {
                rockIsStill = true
            }
        }

        // saving rock position to chamber array
        rock.vectors.forEach { v ->
            val p = position + v
            val currentChamberPointState = chamber[p.y][p.x]
            chamber[p.y][p.x] = currentChamberPointState.copy(isRock = true)
        }

        rockCounter++

        val newTop = position.y + rock.height()
        if (newTop > currentTop) {
            currentTop = newTop
        }

        // cutoff chamber
        if ((0 until 7).all { x ->
                chamber[position.y][x].isRock
            }) {

//            println()
//            println("Cutting off chamber, chamber now: ")
//            printChamber(chamber)

            val newChamberHeight = chamber.size - position.y - 1

//            println("newChamberHeight: $newChamberHeight")

            // copy all elements above cut offline
            val leftPart = chamber.takeLast(newChamberHeight)
            chamber = MutableList(newChamberHeight) { y ->
                MutableList(chamberWidth) { x ->
                    TypedPoint(Point2D(x, y), isRock = leftPart[y][x].isRock)
                }
            }

//            println("chamber after cutoff: ")
//            printChamber(chamber)
//            println("currentTop $currentTop")
//            println()

            val newCurrentTop = chamber.mapNotNull { it.find { it.isRock } }.lastOrNull()?.point?.y?.let { it + 1 } ?: 0

            val newToAdd = currentTop - newCurrentTop
            tops.add(newToAdd)

            currentTop = newCurrentTop

//            println("new currentTop $currentTop")
//            println()
        }            // we processed last direction, if now surface is flat (new Current top == 0) then we found period

        if (directionIndex == dirs.size - 1) {
            println("end of directions, dirSize: ${dirs.size}")
            println(tops)
            printChamber(chamber)

            println("rockcounter Diff: ${rockCounter - lastEndDirRockCounter}")

            lastEndDirRockCounter = rockCounter
        }
    }

    tops.add(currentTop)

    println("tops: $tops")

    return tops.fold(0L) { acc, it -> acc + it }
}

fun test(size: Long) {

    var counter = 0L
//    val list = (0..100).map { it * 2 }

    while (counter < size) {
        counter++
        println(counter)
//        list.forEach {}
    }
}