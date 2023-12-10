package y2022.day20

import readInput

fun main() {
    val day = 20
    val testInput = readInput("day$day/testInput")
//    check(part1(testInput) == 3L)
//    check(part2(testInput) == 1623178306L)

    val input = readInput("day$day/input")
//    println(part1B(input))
    println(part2(input))
}

data class MarkedNumber(val number: Long, var isMarked: Boolean = false, val orderNum: Int = -1)

fun part1(input: List<String>): Long {

    val markedNumbers: MutableList<MarkedNumber> = input.map { it.toLong() }.map { MarkedNumber(it) }.toMutableList()

    fun getNextNotMarkedIndex(): Int = markedNumbers.indexOfFirst { !it.isMarked }

    var index = getNextNotMarkedIndex()

    while (index != -1) {

        val number = markedNumbers[index].number
        var newIndex = index + number

        newIndex %= (markedNumbers.size - 1)

        if (newIndex <= 0) {
            newIndex += markedNumbers.size - 1
        }

        val intNewIndex = newIndex.toInt()

        if (intNewIndex > index) {
            for (i in index..intNewIndex) {

                if (i == intNewIndex) {
                    markedNumbers[i] = MarkedNumber(number, true)
                } else {
                    markedNumbers[i] = markedNumbers[i + 1]
                }
            }
        } else {
            for (i in index downTo intNewIndex) {
                if (i == intNewIndex) {
                    markedNumbers[i] = MarkedNumber(number, true)
                } else {
                    markedNumbers[i] = markedNumbers[i - 1]
                }
            }
        }
        index = getNextNotMarkedIndex()
    }

    println(markedNumbers.map { it.number })

    val zeroIndex = markedNumbers.indexOfFirst { it.number == 0L }
    println("zeroIndex: $zeroIndex")

    val x = markedNumbers[(zeroIndex + 1000) % markedNumbers.size].number
    val y = markedNumbers[(zeroIndex + 2000) % markedNumbers.size].number
    val z = markedNumbers[(zeroIndex + 3000) % markedNumbers.size].number

    return x + y + z
}

fun part1B(input: List<String>): Long {

    val markedNumbers: MutableList<MarkedNumber> = input.map { it.toLong() }
        .mapIndexed { index, num -> MarkedNumber(num, orderNum = index) }
        .toMutableList()

    var orderNum = 0
    while (orderNum != markedNumbers.size) {

        val index = markedNumbers.indexOfFirst { it.orderNum == orderNum }

        val number = markedNumbers[index].number
        var newIndex = index + number

        newIndex %= (markedNumbers.size - 1)

        if (newIndex <= 0) {
            newIndex += markedNumbers.size - 1
        }

        val intNewIndex = newIndex.toInt()

        if (intNewIndex > index) {
            for (i in index..intNewIndex) {

                if (i == intNewIndex) {
                    markedNumbers[i] = MarkedNumber(number, orderNum = orderNum)
                } else {
                    markedNumbers[i] = markedNumbers[i + 1]
                }
            }
        } else {
            for (i in index downTo intNewIndex) {
                if (i == intNewIndex) {
                    markedNumbers[i] = MarkedNumber(number, orderNum = orderNum)
                } else {
                    markedNumbers[i] = markedNumbers[i - 1]
                }
            }
        }
        orderNum++
    }

    println(markedNumbers.map { it.number })

    val zeroIndex = markedNumbers.indexOfFirst { it.number == 0L }
    println("zeroIndex: $zeroIndex")

    val x = markedNumbers[(zeroIndex + 1000) % markedNumbers.size].number
    val y = markedNumbers[(zeroIndex + 2000) % markedNumbers.size].number
    val z = markedNumbers[(zeroIndex + 3000) % markedNumbers.size].number

    return x + y + z
}

fun part2(input: List<String>): Long {

    val decryptionKey = 811589153L

    val markedNumbers = input.map { it.toInt() * decryptionKey }
        .mapIndexed { i, num -> MarkedNumber(num, orderNum = i) }
        .toMutableList()

    println("Initial:")
    println(markedNumbers.map { it.number })

    repeat(10) { round ->
        var orderNum = 0

        while (orderNum != markedNumbers.size) {

            val index = markedNumbers.indexOfFirst { it.orderNum == orderNum }

            val number = markedNumbers[index].number
            var newIndex: Long = index + number

            newIndex %= (markedNumbers.size - 1)

            if (newIndex <= 0) {
                newIndex += markedNumbers.size - 1
            }

            val intNewIndex = newIndex.toInt()

            if (intNewIndex > index) {

                for (i in index..intNewIndex) {

                    if (i == intNewIndex) {
                        markedNumbers[i] = MarkedNumber(number, orderNum = orderNum)
                    } else {
                        markedNumbers[i] = markedNumbers[i + 1]
                    }
                }
            } else {
                for (i in index downTo intNewIndex) {
                    if (i == intNewIndex) {
                        markedNumbers[i] = MarkedNumber(number, orderNum = orderNum)
                    } else {
                        markedNumbers[i] = markedNumbers[i - 1]
                    }
                }
            }
            orderNum++
        }

        println("round: ${round + 1}")
        println(markedNumbers.map { it.number })
    }

    val zeroIndex = markedNumbers.indexOfFirst { it.number == 0L }
    println("zeroIndex: $zeroIndex")

    val x = markedNumbers[(zeroIndex + 1000) % markedNumbers.size].number
    val y = markedNumbers[(zeroIndex + 2000) % markedNumbers.size].number
    val z = markedNumbers[(zeroIndex + 3000) % markedNumbers.size].number

    println("x: $x")
    println("y: $y")
    println("z: $z")

    return x + y + z
}