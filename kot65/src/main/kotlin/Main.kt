package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    var x= 0.0
    val A= mutableListOf<Double>()
    val B= mutableListOf<Double>()
    val AxB= mutableListOf<Pair<Double, Double>>()
    val C= mutableListOf<Double>()
    for (n in 0..100)
    {
        x= (8*n.toDouble()-18)/(2*n-1)
        A.add(x)
    }
    for (n in 0..100)
    {
        x= ((9*n.toDouble()*n- 48*n + 16)/(3*n-8))
        B.add(x)
    }
    for (n in 0..100)
    {
        AxB.add(Pair(A[n], B[n]))
    }
    for (n in 0..100)
    {
        if (A.contains(B[n]))
            C.add(B[n])
    }
    val rez= C+AxB
    println(rez)

}