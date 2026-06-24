import java.util.concurrent.ConcurrentHashMap

// Hashmap-ul partajat, thread-safe
val sharedMap = ConcurrentHashMap<String, Int>()

// Clasa de bază activă (Thread)
abstract class ActiveOperation : Thread() {
    // Forțăm toate subclasele să își ruleze codul în funcția run() din Thread
    abstract override fun run()
}

// 1. Subclasă pentru Aflare Maxim
class FindMaxOperation : ActiveOperation() {
    override fun run() {
        val max = sharedMap.values.maxOrNull()
        println("[Thread Maxim] Valoarea maximă este: $max")
    }
}

// 2. Subclasă pentru Aflare Minim
class FindMinOperation : ActiveOperation() {
    override fun run() {
        val min = sharedMap.values.minOrNull()
        println("[Thread Minim] Valoarea minimă este: $min")
    }
}

// 3. Subclasă pentru Numărul de apariții / Duplicate
class FindDuplicatesOperation : ActiveOperation() {
    override fun run() {
        val frecvente = sharedMap.values.groupingBy { it }.eachCount()
        println("[Thread Duplicate] Frecvența valorilor: $frecvente")
    }
}

// 4. Subclasă pentru Eliminarea duplicatelor (păstrează doar valori unice)
class RemoveDuplicatesOperation : ActiveOperation() {
    override fun run() {
        val valoriVazute = mutableSetOf<Int>()
        for ((cheie, valoare) in sharedMap) {
            if (valoare in valoriVazute) {
                sharedMap.remove(cheie) // Elimină duplicatul din hashmap-ul partajat
                println("[Thread Eliminare] S-a șters cheia '$cheie' (valoare duplicat: $valoare)")
            } else {
                valoriVazute.add(valoare)
            }
        }
    }
}

fun main() {
    // Simulăm preluarea datelor dintr-un fișier text (cheie=valoare)
    val dateFisier = listOf("A=10", "B=20", "C=10", "D=40", "E=20")

    // Inițializăm Hashmap-ul cu datele citite
    dateFisier.forEach { linie ->
        val parti = linie.split("=")
        sharedMap[parti[0]] = parti[1].toInt()
    }
    println("Hashmap Inițial: $sharedMap\n")

    // Creăm colecția de obiecte active (subclasele)
    val operatii = listOf(
        FindMaxOperation(),
        FindMinOperation(),
        FindDuplicatesOperation(),
        RemoveDuplicatesOperation()
    )

    // Pornim toate firele de execuție în mod separat/simultan
    operatii.forEach { it.start() }

    // Așteptăm ca toate firele să își termine execuția
    operatii.forEach { it.join() }

    println("\nHashmap Final (după eliminarea duplicatelor): $sharedMap")
}

/* +-------------------------+
       |    java.lang.Thread     |
       +-------------------------+
                    ^
                    | (moștenește)
       +-------------------------+
       |   <<Abstract>>          |
       |   ActiveOperation       |
       +-------------------------+
       |  + abstract run()       |
       +-------------------------+
         ^      ^         ^      ^
         |      |         |      | (extind clasa de bază)
         |      |         |      +--------------------------+
         |      |         +-----------------------+         |
         |      +-------------------+             |         |
+------------------+       +------------------+ +----------------------+ +---------------------------+
| FindMaxOperation |       | FindMinOperation | | FindDuplicatesOper.  | | RemoveDuplicatesOperation |
+------------------+       +------------------+ +----------------------+ +---------------------------+
| + run()          |       | + run()          | | + run()              | | + run()                   |
+------------------+       +------------------+ +----------------------+ +---------------------------+

[ Obiect: FindMaxOperation ] --------┐
                                     │
[ Obiect: FindMinOperation ] --------┼─> Citesc/Modifică ─> [ Resursă Partajată: sharedMap ]
                                     │
[ Obiect: FindDuplicatesOper ] ------┼─> (ConcurrentHashMap)
                                     │
[ Obiect: RemoveDuplicatesOper ] ----┘
 */