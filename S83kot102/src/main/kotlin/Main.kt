import kotlinx.coroutines.*

// Structura de date partajată
val sharedMap = HashMap<String, Int>()

// Clasa de bază care definește operația (fără fire de execuție în ea)
abstract class Operation(val value: Int) {
    abstract fun execute(current: Int): Int
}

// Subclasele active (doar logica matematică)
class Add(value: Int) : Operation(value) { override fun execute(current: Int) = current + value }
class Subtract(value: Int) : Operation(value) { override fun execute(current: Int) = current - value }
class Multiply(value: Int) : Operation(value) { override fun execute(current: Int) = current * value }

// Funcție ajutătoare pentru a rula operația în mod sigur (cu Mutex/Lock prin synchronized)
fun applyOperation(key: String, op: Operation) {
    // synchronized() blochează accesul altor fire de execuție pe durata modificării
    synchronized(sharedMap) {
        val current = sharedMap[key] ?: 0
        sharedMap[key] = op.execute(current)
        println("[${Thread.currentThread().name}] Modificat în: ${sharedMap[key]}")
    }
}

fun main() = runBlocking {
    sharedMap["scor"] = 10 // Valoare inițială

    // Lansăm operațiile în paralel (în fire de execuție separate din fundal)
    val job1 = launch(Dispatchers.Default) { applyOperation("scor", Add(5)) }       // 10 + 5
    val job2 = launch(Dispatchers.Default) { applyOperation("scor", Subtract(3)) }  // 15 - 3
    val job3 = launch(Dispatchers.Default) { applyOperation("scor", Multiply(2)) }  // 12 * 2

    // Așteptăm să se termine toate
    joinAll(job1, job2, job3)

    println("\nValoare finală: ${sharedMap["scor"]}")
}