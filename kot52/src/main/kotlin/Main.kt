package org.example

fun main() {
    // 1. Definim mulțimea inițială A ca o listă de la 1 la 100 (ADT - Abstract Data Type)
    val setA = (1..100).toList()

    var count = 0

    // 2. Generăm toate submulțimile unice de 4 elemente folosind bucle imbricate
    // Ne asigurăm că i < j < k < l pentru a evita duplicatele (de ex: [1,2,3,4] este aceeași submulțime cu [4,3,2,1])
    for (i in 0 until setA.size) {
        for (j in i + 1 until setA.size) {
            for (k in j + 1 until setA.size) {
                for (l in k + 1 until setA.size) {

                    // Creăm submulțimea curentă de 4 elemente
                    val subset = listOf(setA[i], setA[j], setA[k], setA[l])

                    // 3. Aplicăm o transformare/filtru folosind o funcție lambda
                    // Verificăm dacă submulțimea conține numărul 1
                    if (subset.any { it == 1 }) {
                        count++
                    }
                }
            }
        }
    }

    // 4. Afișăm rezultatul determinat
    println("Numărul submulțimilor cu 4 elemente care conțin numărul 1 este: $count")
}