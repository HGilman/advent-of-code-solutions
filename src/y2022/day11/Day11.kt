package y2022.day11

fun main() {
    partTwo()
//    partOne()
}


fun part1Test() {

    //testing

    val m1 = Monkey(
        listOf(79, 98),
        { it * 19 },
        23,
        {
            if (it) 2 else 3
        })

    val m2 = Monkey(
        listOf(54, 65, 75, 74),
        { it + 6 },
        19,
        {
            if (it) 2 else 0
        })

    val m3 = Monkey(
        listOf(79, 60, 97),
        { it * it },
        13,
        {
            if (it) 1 else 3
        })

    val m4 = Monkey(
        listOf(74),
        { it + 3 },
        17,
        {
            if (it) 0 else 1
        })

    val monkeys = listOf(m1, m2, m3, m4)

    for (r in 0 until 20) {
        for (m in monkeys) {
            m.round(monkeys, r)
        }
    }

    println(m1.items.joinToString(" ") { it.toString() })
    println(m2.items.joinToString(" ") { it.toString() })
    println(m3.items.joinToString(" ") { it.toString() })
    println(m4.items.joinToString(" ") { it.toString() })

    val mostActive = monkeys.map { it.processedAmount }.sorted().takeLast(2)
    println(mostActive[0] * mostActive[1])

}

fun part2Test() {

    val leastCommonDivisor: Long = listOf(23L, 19, 13, 17).reduce(Long::times)

    val worryLevelReduceOp = { l: Long ->
        l % leastCommonDivisor
    }

    //testing
    val m1 = Monkey(
        listOf(79, 98),
        {
            it * 19
        },
        23,
        {
            if (it) 2 else 3
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val m2 = Monkey(
        listOf(54, 65, 75, 74),
        { it + 6 },
        19,
        {
            if (it) 2 else 0
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val m3 = Monkey(
        listOf(79, 60, 97),
        {
            it * it
        },
        13,
        {
            if (it) 1 else 3
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val m4 = Monkey(
        listOf(74),
        { it + 3 },
        17,
        {
            if (it) 0 else 1
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val monkeys = listOf(m1, m2, m3, m4)

    for (r in 0 until 10000) {
        for (m in monkeys) {
            m.round(monkeys, r)
        }
    }

    println(m1.items.joinToString(" ") { it.toString() })
    println(m2.items.joinToString(" ") { it.toString() })
    println(m3.items.joinToString(" ") { it.toString() })
    println(m4.items.joinToString(" ") { it.toString() })

    println(m1.processedAmount)
    println(m2.processedAmount)
    println(m3.processedAmount)
    println(m4.processedAmount)

    val mostActive = monkeys.map { it.processedAmount }.sorted().takeLast(2)
    println(mostActive[0] * mostActive[1])
}


fun solve(worryLevelReduceOp: (level: Long) -> Long, roundsAmount: Int) {
    val m1 = Monkey(
        listOf(66, 79),
        { it * 11 },
        7,
        {
            if (it) 6 else 7
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val m2 = Monkey(
        listOf(84, 94, 94, 81, 98, 75),
        { it * 17 },
        13,
        {
            if (it) 5 else 2
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val m3 = Monkey(
        listOf(85, 79, 59, 64, 79, 95, 67),
        { it + 8 },
        5,
        {
            if (it) 4 else 5
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val m4 = Monkey(
        listOf(70),
        { it + 3 },
        19,
        {
            if (it) 6 else 0
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val m5 = Monkey(
        listOf(57, 69, 78, 78),
        { it + 4 },
        2,
        {
            if (it) 0 else 3
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val m6 = Monkey(
        listOf(65, 92, 60, 74, 72),
        { it + 7 },
        11,
        {
            if (it) 3 else 4
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val m7 = Monkey(
        listOf(77, 91, 91),
        { it * it },
        17,
        {
            if (it) 1 else 7
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val m8 = Monkey(
        listOf(76, 58, 57, 55, 67, 77, 54, 99),
        { it + 6 },
        3,
        {
            if (it) 2 else 1
        },
        worryLevelReduceOp = worryLevelReduceOp
    )

    val monkeys = listOf(m1, m2, m3, m4, m5, m6, m7, m8)

    for (r in 0 until roundsAmount) {
        for (m in monkeys) {
            m.round(monkeys, r)
        }
    }

    val mostActive = monkeys.map { it.processedAmount }.sorted().takeLast(2)
    println(mostActive[0] * mostActive[1])
}

fun partOne() {
    solve({ l: Long -> l / 3 }, 20)
}

fun partTwo() {
    val leastCommonDivisor: Long = listOf(7, 13, 5, 19, 2, 11, 17, 3L).reduce(Long::times)
    solve({ l: Long -> l % leastCommonDivisor }, 10000)
}

class Monkey(
    initItems: List<Long>,
    private val op: (Long) -> Long,
    private val divisibleBy: Long,
    private val throwTo: (Boolean) -> Int,
    private val worryLevelReduceOp: (level: Long) -> Long = { level -> level / 3 }
) {
    private val id: Int

    init {
        globalId++
        id = globalId
    }

    companion object {
        var globalId: Int = 0
    }

    var processedAmount = 0L
        private set

    val items = ArrayDeque<Long>(initItems)

    fun receiveItem(item: Long) {
        items.addLast(item)
    }

    // processing all items in queue
    fun round(monkeys: List<Monkey>, r: Int) {
        while (items.isNotEmpty()) {
            val item = items.removeFirst()
            val inspectedItem = worryLevelReduceOp.invoke(op.invoke(item))

            val isDivisible = inspectedItem % divisibleBy == 0L

            val monkeyNumber = throwTo.invoke(isDivisible)

            monkeys[monkeyNumber].receiveItem(inspectedItem)
            processedAmount++
        }
    }
}