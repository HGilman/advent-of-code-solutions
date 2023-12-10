package y2022.day7

import y2022.day7.Day.File.Companion.isFile
import y2022.day7.Day.File.Companion.toFile
import y2022.day7.Day.Folder.Companion.isFolder
import y2022.day7.Day.Folder.Companion.toFolder
import y2022.day7.Day.buildTree
import readInput
import java.util.Stack


fun main() {
    val testInput = readInput("day7/Day07_test")
    val testRoot = buildTree(testInput)
//    testRoot.print()

    check(Day.part1(testRoot) == 95437)
    check(Day.part2(testRoot) == 24933642)

    val input = readInput("day7/Day07")
    val root = buildTree(input)
//    root.print()

    println(Day.part1(root))
    println(Day.part2(root))
}

object Day {

    fun buildTree(input: List<String>): Folder {
        val root = "/".toFolder()

        val folderStack = Stack<Folder>()
        folderStack.push(root)

        fun String.isLS() = equals("$ ls")
        fun String.isCD() = startsWith("$ cd")
        fun String.getCdName() = substringAfter("$ cd ")
        fun String.isBack() = getCdName() == ".."

        input.drop(1).forEach { line ->
            val currentFolder = folderStack.peek()
            when {
                line.isLS() -> return@forEach
                line.isFolder() -> currentFolder.nodes.add(line.toFolder())
                line.isFile() -> currentFolder.nodes.add(line.toFile())
                line.isCD() -> {
                    if (line.isBack()) {
                        folderStack.pop()
                    } else {
                        val innerFolder = currentFolder.nodes.first { it.name == line.getCdName() } as Folder
                        folderStack.push(innerFolder)
                    }
                }
            }
        }
        return root
    }

    fun part1(root: Folder): Int {
        val acc = mutableListOf<Pair<Node, Int>>()
        root.findFoldersInSizeOf(100000, acc)
        return acc.sumOf { it.second }
    }

    fun part2(root: Folder): Int {
        val acc = mutableListOf<Pair<Node, Int>>()

        val currentFreeMemory = 70_000_000 - root.getTotalSize()
        val requiredFreeMemory = 30_000_000
        root.findFoldersBiggerThen(requiredFreeMemory - currentFreeMemory, acc)
        acc.sortBy { it.second }

        return acc.first().second
    }

    sealed class Node(val name: String) {

        fun print() {
            println(getString())
        }

        private fun getString(indentCount: Int = 0): String {
            val indention = (0 until indentCount).joinToString("") { " " }
            return indention + (when (this) {
                is Folder -> {
                    val childPrint = nodes.joinToString("") { it.getString(indentCount + 2) }
                    "- $name (dir)\n$childPrint"
                }

                is File -> {
                    "- $name (file, size = $size) \n"
                }
            })
        }

        abstract fun getTotalSize(): Int

        fun findFoldersInSizeOf(maxTotal: Int, acc: MutableList<Pair<Node, Int>>) {
            when (this) {
                is File -> return
                is Folder -> {
                    val total = getTotalSize()

                    if (total <= maxTotal) {
                        acc.add(this to total)
                    }
                    nodes.forEach {
                        it.findFoldersInSizeOf(maxTotal, acc)
                    }
                }
            }
        }

        fun findFoldersBiggerThen(amount: Int, acc: MutableList<Pair<Node, Int>>) {
            when (this) {
                is File -> return
                is Folder -> {
                    val total = getTotalSize()

                    if (total >= amount) {
                        acc.add(this to total)

                        nodes.forEach {
                            it.findFoldersBiggerThen(amount, acc)
                        }
                    }
                }
            }
        }

    }

    class File(name: String, val size: Int) : Node(name) {

        override fun getTotalSize() = size

        companion object {
            private val fileRegexp = """(\d+)\s([a-z]+\.*[a-z]*)""".toRegex()

            fun String.isFile(): Boolean {
                return matches(fileRegexp)
            }

            fun String.toFile(): File {
                val (_, fileSize, fileName) = fileRegexp.find(this)!!.groupValues
                return File(fileName, fileSize.toInt())
            }
        }
    }

    class Folder(name: String, val nodes: MutableList<Node>) : Node(name) {

        override fun getTotalSize() = nodes.sumOf { it.getTotalSize() }

        companion object {
            fun String.isFolder() = startsWith("dir") || equals("/")

            private fun String.folderName(): String? {
                return if (!isFolder()) {
                    null
                } else {
                    if (equals("/")) {
                        this
                    } else {
                        split(" ")[1]
                    }
                }
            }

            fun String.toFolder() = Folder(folderName()!!, mutableListOf())
        }
    }
}

