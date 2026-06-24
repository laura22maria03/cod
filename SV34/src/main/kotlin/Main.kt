package org.example
import java.io.File
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val textinitial= File("src/text.txt").readText()

    val cuvinte = textinitial.split (Regex("\\W+"))

    val final= cuvinte.filter { it.isNotEmpty() } .map{ if (it.length >= 4) it.drop(2) else it}
    println(final)
}