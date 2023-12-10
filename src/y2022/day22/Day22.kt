package y2022.day22

import lib.DecreaseYDirection
import lib.LeftDirection
import lib.Point2D
import lib.RightDirection
import lib.IncreaseYDirection
import lib.Vector2D
import lib.directionsClockwise


fun part1(input: List<String>): Int {

    val filedInput = input.dropLast(2)

    val height = filedInput.size
    val width = filedInput.maxOf { it.length }

    val field = arrayListOf<ArrayList<GameObject>>()

    for (y in 0 until height) {
        val lineArray = arrayListOf<GameObject>()
        for (x in 0 until width) {

            val line = filedInput[y]
            val gameChar = if (x < line.length) line[x] else ' '
            val gameObject = GameObject.fromChar(gameChar)
            lineArray.add(gameObject)
        }
        field.add(lineArray)
    }

    val ruleLine = input.last()

    val commands = arrayListOf<String>()

    var prevCharIndex = 0
    for (i in ruleLine.indices) {
        if (ruleLine[i] == 'R' || ruleLine[i] == 'L') {

            commands.add(ruleLine.substring(if (prevCharIndex == 0) 0 else prevCharIndex + 1, i))

            commands.add(ruleLine[i].toString())
            prevCharIndex = i
        }
        if (i == ruleLine.length - 1) {
            commands.add(ruleLine.substring(prevCharIndex + 1, i + 1))
        }
    }

    val initialX = field[0].indexOfFirst { it != GameObject.EMPTY }
    var position = Point2D(initialX, 0)
    var directionIndex = 0

    fun updateDirection(isClockwise: Boolean) {

        val increment: Int = if (isClockwise) 1 else -1
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

    fun getFirstNotEmpty(newPosition: Point2D, direction: Vector2D): Point2D {

        fun getFirstNotEmptyPointForY(y: Int, isLeft: Boolean): Point2D {
            val x =  if (isLeft) {
                field[y].indexOfFirst { it != GameObject.EMPTY }
            } else {
                field[y].indexOfLast { it != GameObject.EMPTY }
            }
            return Point2D(x, y)
        }

        fun getFirstNotEmptyPointForX(x: Int, isTop: Boolean): Point2D {
            var y = 0

            if (isTop) {
                for (j in 0 until height) {
                    val gameObject = field[j][x]
                    if (gameObject != GameObject.EMPTY) {
                        y = j
                        break
                    }
                }
            } else {
                for (j in height - 1 downTo  0) {
                    val gameObject = field[j][x]
                    if (gameObject != GameObject.EMPTY) {
                        y = j
                        break
                    }
                }
            }
            return Point2D(x, y)
        }
        return when(direction) {
            RightDirection -> getFirstNotEmptyPointForY(newPosition.y, true)
            LeftDirection -> getFirstNotEmptyPointForY(newPosition.y, false)
            IncreaseYDirection -> getFirstNotEmptyPointForX(newPosition.x, true)
            DecreaseYDirection -> getFirstNotEmptyPointForX(newPosition.x, false)
            else -> error("Unsupported direction")
        }
    }



    fun getRangeForY(y: Int): IntRange {
        val start = field[y].indexOfFirst { it != GameObject.EMPTY }
        val end = field[y].indexOfLast { it != GameObject.EMPTY }
        return start..end
    }

    fun getRangeForX(x: Int): IntRange {
        var start = -1
        var end = -1

        for (i in 0 until height) {
            if (field[i][x] != GameObject.EMPTY && start == -1) {
                start = i
            }
        }

        for (i in height - 1 downTo  0) {
            if (field[i][x] != GameObject.EMPTY && end == -1) {
                end = i
            }
        }
        return start..end
    }

    fun printLog(msg: String) {
//        println(msg)
    }

    for (c in commands) {

        if (c.first() == 'R' || c.first() == 'L') {

            printLog("updating direction to: ${c.first()}")
            updateDirection(c.first() == 'R')
            printLog("new direction: ${getDirection()}")

        } else {
            val stepsAmount = c.toInt()

            printLog("position: $position, going to ${getDirection()} $stepsAmount times")

            for (s in 0 until stepsAmount) {

                var newPosition = getNewPosition()

                if (newPosition.x !in getRangeForY(position.y) || newPosition.y !in getRangeForX(position.x)) {
                    printLog("hit end of field")
                    newPosition = getFirstNotEmpty(newPosition, getDirection())
                }

                val gameObject = field[newPosition.y][newPosition.x]

                when(gameObject) {
                    GameObject.EMPTY -> error("it is impossible, newPosition: $newPosition - Object must be OPEN or WALL")
                    GameObject.OPEN -> {
                        printLog("updating position to: $newPosition")
                        position = newPosition
                    }
                    GameObject.WALL -> {
                        // don't update position, stand where we are and finish current round
                        printLog("Wall! stop current round")
                        break
                    }
                }

            }
        }
    }

    printLog("Finally position: $position, directionIndex: $directionIndex")
    val res = (position.y + 1) * 1000 + (position.x + 1) * 4 + directionIndex

    return res
}
