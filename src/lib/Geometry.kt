package lib

import kotlin.math.abs
import kotlin.math.sign
import kotlin.math.sqrt

data class Point2D(val x: Int, val y: Int) {

    override fun toString(): String {
        return "x: $x, y: $y"
    }

    fun toRight() = Point2D(x + 1, y)

    fun toLeft() = Point2D(x - 1, y)

    fun higher() = Point2D(x, y + 1)

    fun lower() = Point2D(x, y - 1)

    fun follow(p: Point2D): Point2D {
        val dx = (p.x - x).sign
        val dy = (p.y - y).sign
        return Point2D(x + dx, y + dy)
    }

    fun distanceTo(p: Point2D): Double {
        return distance(this, p)
    }

    operator fun plus(vec: Vector2D): Point2D {
        return Point2D(x + vec.x, y + vec.y)
    }

    operator fun minus(other: Point2D): Vector2D {
        return Vector2D(x - other.x, y - other.y)
    }

    fun manhattanDistanceTo(p: Point2D): Int {
        return abs(p.x - x) + abs(p.y - y)
    }

    companion object {
        fun distance(p1: Point2D, p2: Point2D): Double {
            return sqrt((p2.x.toDouble() - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y))
        }
    }
}

data class Vector2D(val x: Int, val y: Int)

val LeftDirection = Vector2D(-1, 0)
val RightDirection = Vector2D(1, 0)
val IncreaseYDirection = Vector2D(0, 1)
val DecreaseYDirection = Vector2D(0, -1)

val directionsClockwise = listOf<Vector2D>(
    RightDirection, IncreaseYDirection, LeftDirection, DecreaseYDirection
)

/**
 * @param s - start point
 * @param e - end point
 */
data class Segment(val s: Point2D, val e: Point2D) {

    fun intersects(other: Segment): List<PrecisePoint> {

        val k1 = (e.y.toDouble() - s.y) / (e.x - s.x)
        val k2 = (other.e.y.toDouble() - other.s.y) / (other.e.x - other.s.x)

        val b1 = s.y - k1 * s.x
        val b2 = other.s.y - k2 * other.s.x

        if (k1 == k2 && b1 == b2) {
            val res = mutableListOf<PrecisePoint>()
            if (other.s.x in (s.x.. e.x)) {
                res.add(PrecisePoint(other.s.x.toDouble(), other.s.y.toDouble()))
            }

            if (other.e.x in (s.x.. e.x)) {
                res.add(PrecisePoint(other.e.x.toDouble(), other.e.y.toDouble()))
            }

            if (s.x in (other.s.x.. other.e.x)) {
                res.add(PrecisePoint(s.x.toDouble(), s.y.toDouble()))
            }

            if (e.x in (other.s.x.. other.e.x)) {
                res.add(PrecisePoint(e.x.toDouble(), e.y.toDouble()))
            }
            return res
        }


        val x = (b2 - b1) / (k1 - k2)
        val y = b1 + k1 * x

        return if ((x >= s.x && x <= e.x) && (x >= other.s.x && x <= other.e.x)) {
            listOf(PrecisePoint(x, y))
        } else {
            emptyList()
        }
    }
}



/**
 * Suppose path goes only vertically or horizontally, but not by diagonal
 * */
data class Path(val points: List<Point2D>) {

    fun getAllPoints(): List<Point2D> {

        val res = mutableListOf<Point2D>()
        if (points.isEmpty()) {
            return res
        }

        var prevPoint = points.first()
        res.add(prevPoint)

        for (p in points.drop(1)) {

            if (p == prevPoint) {
                continue
            }

            var followPoint = prevPoint.follow(p)

            while (followPoint != p) {
                res.add(followPoint)
                followPoint = followPoint.follow(p)
            }
            res.add(followPoint)
            prevPoint = p
        }
        return res
    }

}

data class PrecisePoint(val x: Double, val y: Double)