package y2022.day13

import readTextGroups
import java.util.Stack
import kotlin.math.min

fun main() {
    val day = 13
    val testInput = readTextGroups("day$day/testInput")
//    check(part1(testInput) == 1)

    getSubLists("[1,[2,[3,[4,[5,6,7]]]],8,9]")
    println()
    getSubLists("[[1],[2,3,4]]")
    println()
    getSubLists("[[10,10,10,4,[8,[8],6,[]]]]")
}


fun part1(input: List<String>): Int {

    val pairs: List<Pair<String, String>> = input.map {
        val (fl, sl) = it.split("\n")
        Pair(fl, sl)
    }

    val rightOrderIndexes = mutableListOf<Int>()

//    pairs.forEach { (f, s) ->
//    }

    println(pairs)


    return 1
}

fun part2(input: List<String>): Int {
    return input.size
}

/**
 *  -1 wrong order
 *  0 same
 *  1 rightOrder
 */
fun areListsInRightOrder(list1: List<Int>, list2: List<Int>): Int {

    val minSize = min(list1.size, list2.size)
    for (i in 0 until minSize) {
        if (list1[i] < list2[i]) {
            return 1
        } else if (list1[i] > list2[i]) {
            return -1
        }
    }
    return if (list1.size > list2.size) -1 else 1
}

fun getSubLists(s: String) {

    val leftBracketsStack = Stack<Int>()
    val innerLists = mutableListOf<String>()

    s.toCharArray().forEachIndexed { i, c ->

        if (c == '[') {
            leftBracketsStack.push(i)
        } else if (c == ']') {
            val lastLeftBracketIndex = leftBracketsStack.pop()
            innerLists.add(s.substring(lastLeftBracketIndex + 1, i))
        }
    }
    innerLists.forEach {
        println(it)
    }
}

//fun getNextInt(s: String): Iterable<Int> {
//
//    val leftBracketsStack = Stack<Int>()
//    val innerLists = mutableListOf<String>()
//
//    s.toCharArray().forEachIndexed { i, c ->
//
//        if (c == '[') {
//            leftBracketsStack.push(i)
//        } else if (c == ']') {
//            val lastLeftBracketIndex = leftBracketsStack.pop()
//            innerLists.add(s.substring(lastLeftBracketIndex + 1, i))
//        }
//    }
//
//
//    return object : Iterable<Int> {
//
//        override fun iterator() = object : Iterator<Int> {
//            override fun hasNext(): Boolean {
//            }
//
//            override fun next(): Int {
//            }
//        }
//    }
//}

fun areInputsInRightOrder(s1: String, s2: String): Boolean {

    val s1LeftBracketStack = Stack<Int>()
    return true
}




















