package y2022.day18

import y2022.day18.Cube.Companion.normals
import readInput
import java.util.Stack

fun main() {
    val day = 18
    val testInput = readInput("day$day/testInput")
//    check(part1B(testInput) == 64)

//    check(part2B(testInput) == 58)

    val input = readInput("day$day/input")
//    println(part1B(input))
    println(part2B(input))
}


fun part1B(input: List<String>): Int {

    val cubesOrigins = input.map {
        val (x, y, z) = it.split(',').map { it.toInt() }
        Point3D(x, y, z)
    }.toSet()

    var counter = 0

    cubesOrigins.forEach { origin ->
        normals.forEach { n ->
            val checkPoint = origin + n
            if (!cubesOrigins.contains(checkPoint)) {
                counter++
            }
        }

    }
    return counter
}

fun part2B(input: List<String>): Int {

    val cubesOrigins = input.map {
        val (x, y, z) = it.split(',').map { it.toInt() }
        Point3D(x + 2, y + 2, z + 2)
    }.toSet()

    val delta = 2
    val maxX = cubesOrigins.maxOf { it.x } + delta
    val maxY = cubesOrigins.maxOf { it.y } + delta
    val maxZ = cubesOrigins.maxOf { it.z } + delta

    val field: MutableList<MutableList<MutableList<Int>>> = MutableList(maxZ) { z ->
        MutableList(maxY) { y ->
            MutableList(maxX) { x ->
                0
            }
        }
    }

    // fill map with 1, where are cubes stay
    cubesOrigins.forEach { o ->
        field[o.z][o.y][o.x] = 1
    }


    val cubes = mutableListOf<Cube>()

    cubesOrigins.forEach { origin ->
        val cube = Cube(origin)
        normals.forEach { n ->
            val checkPoint = origin + n
            val isFreeFace = !cubesOrigins.contains(checkPoint)
            cube.faces[n] = isFreeFace
        }
        cubes.add(cube)
    }

    fun isPointOutOfBoundaries(p: Point3D): Boolean {
        return (p.x !in (0 until maxX)) || (p.y !in (0 until maxY)) || (p.z !in (0 until maxZ))
    }

    fun isFreePoint(p: Point3D): Boolean {
        return field[p.z][p.y][p.x] == 0
    }

    // point - for this point we check if there is the path in field from this point to outside of field (out of field's boundaries)
    fun dfsStackbased(point: Point3D, num: Int) {

        val stack = Stack<Point3D>()
        stack.add(point)

        var stackMaxDepth = stack.size

        while (!stack.isEmpty()) {
            val p = stack.pop()

            if (isPointOutOfBoundaries(p)) {
                continue
            }

            if (!isFreePoint(p)) {
                continue
            }

            // mark as visited
            field[p.z][p.y][p.x] = num

            normals.forEach { n ->
                stack.add(p + n)
            }

            if (stackMaxDepth < stack.size) {
                stackMaxDepth = stack.size
            }
        }
    }

    val checked = 2

    // 1, 1, 1 - definitely is not part of cubes array,
    // so now all space except closed area should be filled with 2
    dfsStackbased(Point3D(1, 1, 1), checked)

    // not only closed area points (air points) will be 0

    var counter = 0
    cubes.forEach { c ->
        normals.forEach { normal ->
            val checkPoint = c.origin + normal
            if (field[checkPoint.z][checkPoint.y][checkPoint.x] == checked) {
                counter++
            }
        }
    }
    return counter
}



