package org.example
import kotlin.random.Random
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val A = mutableSetOf<Int>()
    val B = mutableSetOf<Int>()
    val C= mutableListOf<Pair<Int, Int>>()

    for (i in (0..15))
    {
        A.add(Random.nextInt())
        B.add(Random.nextInt())
    }
    val lA = A.toList()
    val lB = B.toList()


    for (i in (0..15))
    {
        val pereche = Pair(lA[i], lB[i])
        C.add(pereche)
    }

    println(C)
}