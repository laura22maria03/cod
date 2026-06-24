import kotlinx.coroutines.*

// 1. Funcția extensie cerută pentru String (transformă cuvintele în PascalCase)
// Exemplu: "test masina" -> "TestMasina"
fun String.toPascalCase(): String {
    return this.split(" ")
        .filter { it.isNotEmpty() }
        .joinToString("") { cuvant -> cuvant.replaceFirstChar { it.uppercase() } }
}

// 2. Functorul (O clasă care aplică o transformare pe un MutableMap)
class MapFunctor(val transform: (String) -> String) {
    // Operatorul invoke ne permite să apelăm obiectul ca pe o funcție: functor(map)
    operator fun invoke(map: MutableMap<Int, String>) {
        // Sincronizăm accesul deoarece lucrăm cu corutine în paralel
        synchronized(map) {
            for ((cheie, valoare) in map) {
                map[cheie] = transform(valoare)
            }
        }
    }
}

fun main() = runBlocking {
    // Colecția inițială de test
    val date: MutableMap<Int, String> = mutableMapOf(
        1 to "salut din kotlin",
        2 to "programare concurenta"
    )

    println("Inițial: $date")

    // Definirea celor doi functori ceruți
    val primulFunctor = MapFunctor { valoare -> "Test $valoare" }
    val alDoileaFunctor = MapFunctor { valoare -> valoare.toPascalCase() }

    // Lansăm mai multe corutine care rulează în paralel
    val job1 = launch(Dispatchers.Default) { primulFunctor(date) }

    // Așteptăm să se termine primul pas înainte de a-l aplica pe al doilea
    job1.join()

    val job2 = launch(Dispatchers.Default) { alDoileaFunctor(date) }
    job2.join()

    // Afișăm rezultatul final
    println("Final:   $date")
}