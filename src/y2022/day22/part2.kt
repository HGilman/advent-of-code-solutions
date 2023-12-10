package y2022.day22

import lib.DecreaseYDirection
import lib.IncreaseYDirection
import lib.LeftDirection
import lib.Point2D
import lib.RightDirection
import lib.Vector2D
import lib.directionsClockwise

private const val faceSize = 50

class Face(val id: Int) {

    companion object {
        private val originsMap = mapOf(
            1 to Point2D(faceSize, faceSize),
            2 to Point2D(faceSize, 0),
            3 to Point2D(faceSize, faceSize * 2),
            4 to Point2D(0, faceSize * 2),
            5 to Point2D(0, faceSize * 3),
            6 to Point2D(faceSize * 2, 0)
        )

        private val facesById = mapOf(
            1 to Face(1),
            2 to Face(2),
            3 to Face(3),
            4 to Face(4),
            5 to Face(5),
            6 to Face(6)
        )

        private fun findFace(p: Point2D): Face {
            val x = (p.x / faceSize) * faceSize
            val y = (p.y / faceSize) * faceSize

            println("find face x: $x, y: $y")

            val origin = Point2D(x, y)

            for (e in originsMap) {
                if (e.value == origin) {
                    return facesById[e.key]!!
                }
            }
            error("Wrong point $p, couldn't find face")
        }

        fun getNextPositionWhenReachedAndOfCurrentFace(p: Point2D, direction: Vector2D): Pair<Int, Point2D> {

            val face = findFace(p)
            print("Going next face, direction: $direction, ")

            return when (face.id) {
                1 -> {
                    when (direction) {

                        // 1 -> 6
                        RightDirection -> {
                            val newPoint = Point2D(p.y + 50, 49)
                            print("current Face: ${face.id}, go to 6, position: $p, newPosition: $newPoint")
                            Pair(-1, Point2D(p.y + 50, 49))
                        }
                        // 1 -> 3
                        IncreaseYDirection -> {
                            error("Impossible, point: $p, try to go from 1 to 3")
                        }

                        // 1 -> 4
                        LeftDirection -> {
                            val newPoint = Point2D(p.y - 50, 100)
                            print("Going next face, current Face: ${face.id}, go to 4, position: $p, newPosition: $newPoint")
                            Pair(-1, Point2D(p.y - 50, 100))
                        }

                        DecreaseYDirection -> {
                            error("Impossible, point: $p, try to go from 1 to 2")
                        }

                        else -> error("wrong direction")
                    }
                }

                2 -> {
                    when (direction) {

                        // 2 -> 6
                        RightDirection -> {
                            error("Impossible, point: $p, try to go from 2 to 6")
                        }

                        // 2 -> 1
                        IncreaseYDirection -> {
                            error("Impossible, point: $p, try to go from 2 to 1")
                        }

                        // 2 -> 4
                        LeftDirection -> {
                            val newPoint = Point2D(0, 149 - p.y)
                            print("Going next face, current Face: ${face.id}, go to 4, position: $p, newPosition: $newPoint")
                            Pair(-2, Point2D(0, 149 - p.y))
                        }

                        // 2 -> 5
                        DecreaseYDirection -> {
                            val newPoint = Point2D(0, 100 + p.x)
                            print("Going next face, current Face: ${face.id}, go to 5, position: $p, newPosition: $newPoint")
                            Pair(-3, newPoint)
                        }

                        else -> error("wrong direction")
                    }
                }

                3 -> {
                    when (direction) {

                        // 3 -> 6
                        RightDirection -> {
                            val newPoint = Point2D(149, 149 - p.y)
                            print("Going next face, current Face: ${face.id}, go to 6, position: $p, newPosition: $newPoint")
                            Pair(-2, Point2D(149, 149 - p.y))
                        }

                        // 3 -> 5
                        IncreaseYDirection -> {
                            val newPoint = Point2D(49, 100 + p.x)
                            print("Going next face, current Face: ${face.id}, go to 5, position: $p, newPosition: $newPoint")
                            Pair(1, Point2D(49, 100 + p.x))
                        }

                        // 3 -> 4
                        LeftDirection -> {
                            error("Impossible, point: $p, try to go from 3 to 4")
                        }

                        // 3 -> 1
                        DecreaseYDirection -> {
                            error("Impossible, point: $p, try to go from 3 to 1")
                        }

                        else -> error("wrong direction")
                    }
                }

                4 -> {
                    when (direction) {

                        // 4 -> 3
                        RightDirection -> {
                            error("Impossible, point: $p, try to go from 4 to 3")
                        }

                        // 4 -> 5
                        IncreaseYDirection -> {
                            error("Impossible, point: $p, try to go from 4 to 5")
                        }

                        // 4 -> 2
                        LeftDirection -> {
                            val newPoint = Point2D(50, 149 - p.y)
                            print("Going next face, current Face: ${face.id}, go to 2, position: $p, newPosition: $newPoint")
                            Pair(2, Point2D(50, 149 - p.y))
                        }

                        // 4 -> 1
                        DecreaseYDirection -> {
                            val newPoint = Point2D(50, p.x + 50)
                            print("Going next face, current Face: ${face.id}, go to 1, position: $p, newPosition: $newPoint")
                            Pair(1, Point2D(50, p.x + 50))
                        }

                        else -> error("wrong direction")
                    }
                }

                5 -> {
                    when (direction) {

                        // 5 -> 3
                        RightDirection -> {

                            val newPoint = Point2D(p.y - 100, 149)
                            print("Going next face, current Face: ${face.id}, go to 3, position: $p, newPosition: $newPoint")

                            Pair(-1, Point2D(p.y - 100, 149))
                        }

                        // 5 -> 6
                        IncreaseYDirection -> {

                            val newPoint = Point2D(p.x + 100, 0)
                            print("Going next face, current Face: ${face.id}, go to 6, position: $p, newPosition: $newPoint")
                            Pair(4, Point2D(p.x + 100, 0))
                        }

                        // 5 -> 2
                        LeftDirection -> {

                            val newPoint = Point2D(p.y - 100, 0)
                            print("Going next face, current Face: ${face.id}, go to 2, position: $p, newPosition: $newPoint")

                            Pair(3, Point2D(p.y - 100, 0))
                        }

                        // 5 -> 4
                        DecreaseYDirection -> {
                            error("Impossible, point: $p, try to go from 5 to 4")
                        }

                        else -> error("wrong direction")
                    }
                }

                6 -> {
                    when (direction) {

                        // 6 -> 3
                        RightDirection -> {

                            val newPoint = Point2D(99, 149 - p.y)
                            print("Going next face, current Face: ${face.id}, go to 3, position: $p, newPosition: $newPoint")

                            Pair(2, Point2D(99, 149 - p.y))
                        }

                        // 6 -> 1
                        IncreaseYDirection -> {
                            val newPoint = Point2D(99, p.x - 50)
                            print("Going next face, current Face: ${face.id}, go to 1, position: $p, newPosition: $newPoint")
                            Pair(1, Point2D(99, p.x - 50))
                        }

                        // 6 -> 2
                        LeftDirection -> {
                            error("Impossible, point: $p, try to go from 6 to 2")
                        }

                        // 6 -> 5
                        DecreaseYDirection -> {

                            val newPoint = Point2D(p.x - 100, 199)
                            print("Going next face, current Face: ${face.id}, go to 5, position: $p, newPosition: $newPoint")

                            Pair(-4, Point2D(p.x - 100, 199))
                        }
                        else -> error("wrong direction")
                    }
                }
                else -> error("wrong face id")
            }

        }
    }
}

data class Walker(
    var directionIndex: Int = 0,
    var position: Point2D
) {

    fun updateDirection(increment: Int) {
        directionIndex += increment
        directionIndex %= directionsClockwise.size

        if (directionIndex < 0) {
            directionIndex += directionsClockwise.size
        }
    }

    fun getDirection() = directionsClockwise[directionIndex]

    fun getNewPosition(): Point2D {
        return position + getDirection()
    }

    fun getPassword(): Int {
        return (position.y + 1) * 1000 + (position.x + 1) * 4 + directionIndex
    }
}


fun part2SecondTry(input: List<String>): Int {
    val fieldData = input.dropLast(2)

    val field = mutableListOf<MutableList<GameObject>>()
    val height = fieldData.size
    val width = fieldData.maxOf { it.length }

    for (y in 0 until height) {
        val lineArray = arrayListOf<GameObject>()
        for (x in 0 until width) {
            val line = fieldData[y]
            val gameChar = if (x < line.length) line[x] else ' '
            val gameObject = GameObject.fromChar(gameChar)
            lineArray.add(gameObject)
        }
        field.add(lineArray)
    }

    fun isEndOfMap(p: Point2D): Boolean {
        val isOutOfField = p.x !in 0 until width || p.y !in 0 until height
        return if (isOutOfField) {
            true
        } else {
            field[p.y][p.x] == GameObject.EMPTY
        }
    }

    val commands = parseCommands(input.last())
    val walker = Walker(0, Point2D(50, 0))

    for (command in commands) {

        if (command == "R" || command == "L") {
            walker.updateDirection(if (command == "R") 1 else -1)

        } else {
            for (step in 0 until command.toInt()) {

                var newPosition = walker.getNewPosition()
                var orientationChange: Int? = null

                if (isEndOfMap(newPosition)) {

                    val (rotationDiff, nextPosition) = Face.getNextPositionWhenReachedAndOfCurrentFace(walker.position, walker.getDirection())
                    orientationChange = rotationDiff
                    newPosition = nextPosition
                }

                val gameObject = field[newPosition.y][newPosition.x]

                when (gameObject) {
                    GameObject.EMPTY -> error("it is impossible, newPosition: $newPosition - Object must be OPEN or WALL")
                    GameObject.OPEN -> {
                        println("updating position to: $newPosition")
                        walker.position = newPosition

                        if (orientationChange != null) {
                            walker.updateDirection(orientationChange)
                        }
                    }
                    GameObject.WALL -> {
                        break
                    }
                }
            }
        }
    }
    return walker.getPassword()
}




















