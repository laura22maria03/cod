package org.example
import java.io.File
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val text= File("text.txt").readText()
    val cuvinte= text.split(Regex("\\W+"))
    val final= cuvinte.filter{it.length>= 4 }.map{it.substring(it.length/2-1,it.length/2+1)}
    println(final)
}