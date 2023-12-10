package y2022.day21

import readInput
import kotlin.math.abs


val addOp = { a: Long, b: Long -> a + b }
val subtractOp = { a: Long, b: Long -> a - b }
val multiplyOp = { a: Long, b: Long -> a * b }
val divideOp = { a: Long, b: Long -> a / b }

val symbolToOp = mapOf(
    '+' to addOp,
    '-' to subtractOp,
    '*' to multiplyOp,
    '/' to divideOp
)

sealed class Operand(val name: String)
class SimpleOperand(name: String, var num: Long) : Operand(name)

class CompositeOperand(
    name: String,
    val leftOperandName: String,
    val rightOperandName: String,
    opChar: Char
) : Operand(name) {
    val operation = symbolToOp[opChar]!!
}

fun calcOperand(operand: String, allOperands: Map<String, Operand>): Long {
    val op = allOperands[operand]!!
    return when (op) {
        is SimpleOperand -> op.num
        is CompositeOperand -> {
            op.operation(calcOperand(op.leftOperandName, allOperands), calcOperand(op.rightOperandName, allOperands))
        }
    }
}

fun main() {
    val day = 21
    val testInput = readInput("day$day/testInput")
    check(part1(testInput) == 152L)
//    check(part2(testInput) == 301L)

    val input = readInput("day$day/input")
//    println(part1(input))
    println(part2(input))
}


fun part1(input: List<String>): Long {

    val opRegexp = """([a-z]{4}) ([+\-*/]) ([a-z]{4})""".toRegex()

    val operands: List<Operand> = input.map { line ->
        val operandName = line.substringBefore(":")
        val secondPart = line.substringAfter(": ")

        if (secondPart.matches(opRegexp)) {
            val (_, leftOpName, opChar, rightOpName) = opRegexp.find(secondPart)!!.groupValues
            CompositeOperand(operandName, leftOpName, rightOpName, opChar.first())
        } else {
            SimpleOperand(operandName, secondPart.toLong())
        }
    }

    val operandsMap = operands.associateBy { it.name }

    val res = calcOperand("root", operandsMap)
    return res
}


fun part2(input: List<String>): Long {

    val opRegexp = """([a-z]{4}) ([+\-*/]) ([a-z]{4})""".toRegex()

    val operands: List<Operand> = input.map { line ->
        val operandName = line.substringBefore(":")
        val secondPart = line.substringAfter(": ")

        if (secondPart.matches(opRegexp)) {
            val (_, leftOpName, opChar, rightOpName) = opRegexp.find(secondPart)!!.groupValues
            CompositeOperand(operandName, leftOpName, rightOpName, opChar.first())
        } else {
            SimpleOperand(operandName, secondPart.toLong())
        }
    }

    val operandsMap = operands.associateBy { it.name }

    fun calcRootDiff(): Long {
        val rootOperand = operandsMap["root"] as CompositeOperand
        val leftResult = calcOperand(rootOperand.leftOperandName, operandsMap)
        val rightResult = calcOperand(rootOperand.rightOperandName, operandsMap)
        return rightResult - leftResult
    }

    var coeficient = 50
    val humnOperand = (operandsMap["humn"]!! as SimpleOperand)

    var diff = calcRootDiff()
    var counter = 1
    while (diff != 0L) {

        println("counter: $counter, humn: ${humnOperand.num}, diff: $diff")

        if (abs(diff) < coeficient) {
            coeficient /= 2
        }

        val newHumn1 = if (diff == 1L) humnOperand.num - diff else humnOperand.num - diff / coeficient
        val newHumn2 = if (diff == 1L) humnOperand.num + diff else humnOperand.num + diff / coeficient

        // setting new humn to humn1
        humnOperand.num = newHumn1
        val diff1 = calcRootDiff()

        // setting new humn to humn2
        humnOperand.num = newHumn2
        val diff2 = calcRootDiff()

        if (abs(diff1) < abs(diff2)) {
            diff = diff1
            humnOperand.num = newHumn1
        } else {
            diff = diff2
            humnOperand.num = newHumn2
        }
        counter++
    }

    val res = humnOperand.num
    return res
}