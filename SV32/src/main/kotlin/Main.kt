package org.example

fun main() {
    val hashmap = hashMapOf<Int,Int>()
    for (i in 0..100) {
        hashmap[i] = i
    }

   val hashmap2= hashmap.values.map { i -> 3*i -1 }.map { it.toString() }
    println(hashmap2)
}