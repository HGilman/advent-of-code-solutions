package y2022.day18

import y2022.day18.Cube.Companion.normals
import readInput
import kotlin.math.sqrt

fun main() {
    val day = 18
    val testInput = readInput("day$day/testInput")
//    check(part1(testInput) == 64)

    check(part2(testInput) == 58)

    val input = readInput("day$day/input")
    println(part1(input))
//    println(part2(input))
}


fun part1(input: List<String>): Int {

    val cubes = input.map {
        val (x, y, z) = it.split(',').map { it.toInt() }
        Cube(Point3D(x, y, z))
    }

    val addedCubes = mutableListOf<Cube>()

    cubes.forEach { c ->
        addedCubes.forEach { added ->

            val diff = added.origin - c.origin

            if (added.faces.containsKey(diff)) {
                added.faces[diff] = true
            }

            // for second we invert diff
            val invertedDiff = Point3D(-diff.x, -diff.y, -diff.z)
            if (c.faces.containsKey(invertedDiff)) {
                c.faces[invertedDiff] = true
            }
        }
        addedCubes.add(c)
    }

    val res = addedCubes.map {
        it.faces.values.count { isConnected -> !isConnected }
    }.sum()

    println(res)
    return res
}

fun part2(input: List<String>): Int {
    val cubes = input.map {
        val (x, y, z) = it.split(',').map { it.toInt() }
        Cube(Point3D(x, y, z))
    }

    val addedCubes = mutableSetOf<Cube>()

    cubes.forEach { c ->
        addedCubes.forEach { added ->

            val diff = added.origin - c.origin

            if (added.faces.containsKey(diff)) {
                added.faces[diff] = true
            }

            // for second we invert diff
            val invertedDiff = Point3D(-diff.x, -diff.y, -diff.z)
            if (c.faces.containsKey(invertedDiff)) {
                c.faces[invertedDiff] = true
            }
        }
        addedCubes.add(c)
    }


    addedCubes.forEach { c ->
        c.faces.filter { !it.value }.keys.forEach { normal ->

            // checking is there cube standing in each direction, if so then it is trap
            // don't check opposite to current normal

            var isBlocked = true

            // it should be air
            val checkPoint = c.origin + normal

            normals.forEach { n ->
                val check = checkPoint + n
                if (!addedCubes.contains(Cube(check))) {
                    isBlocked = false
                }
            }

            if (addedCubes.contains(Cube(checkPoint))) {
                isBlocked = true
            }

            c.faces[normal] = isBlocked
        }
    }

    // additional check for every side
    val res = addedCubes.map {
        it.faces.values.count { isConnected -> !isConnected }
    }.sum()

    println(res)
    return res}

data class Point3D(val x: Int, val y: Int, val z: Int) {

    operator fun minus(p: Point3D): Point3D {
        return Point3D(p.x - x, p.y - y, p.z - z)
    }

    operator fun plus(p: Point3D): Point3D {
        return Point3D(p.x + x, p.y + y, p.z + z)
    }
}

data class Cube(val origin: Point3D, private val sideSize: Int = 1) {

    companion object {
        val rightNormal = Point3D(1, 0, 0)
        val leftNormal = Point3D(-1, 0, 0)

        val frontNormal = Point3D(0, 1, 0)
        val backNormal = Point3D(0, -1, 0)

        val upNormal = Point3D(0, 0, 1)
        val downNormal = Point3D(0, 0, -1)

        val normals = listOf(rightNormal, leftNormal, frontNormal, backNormal, upNormal, downNormal)
    }

    /**
     * @param key - normal direction
     * @param value - is side, defined by this normal is part of external space
     * (not connected with other cubes' size and is not trapped)
     */
    val faces: MutableMap<Point3D, Boolean> = closedFacesMap()

    // for now only for 1 size cubes
    fun distance(cube: Cube): Double {
        return sqrt(
            (cube.origin.x - origin.x) * (cube.origin.x - origin.x).toDouble() +
                    (cube.origin.y - origin.y) * (cube.origin.y - origin.y) +
                    (cube.origin.z - origin.z) * (cube.origin.z - origin.z)
        )
    }

    private fun closedFacesMap(): MutableMap<Point3D, Boolean> {
        val res = mutableMapOf<Point3D, Boolean>()
        normals.forEach { v ->
            res[v] = false
        }
        return res
    }

}

