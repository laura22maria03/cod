import java.io.File
import kotlin.system.exitProcess

// --- 1. EXCEPȚII PERSONALIZATE ---
class CriticalException(msg: String) : Exception(msg)

// --- 2. INTERFAȚA STRATEGY ȘI IMPLEMENTĂRILE ---
interface LogStrategy {
    fun handle(message: String)
}

class WarningStrategy : LogStrategy {
    override fun handle(message: String) {
        File("src/main/warnings.txt").appendText("[WARNING] $message\n")
        println("Salvat în warnings.txt")
    }
}

class ErrorStrategy : LogStrategy {
    override fun handle(message: String) {
        println("[ERROR CONSOLA] $message") // Afișează la consolă
        File("errors.txt").appendText("[ERROR] $message\n") // Scrie în fișier
    }
}

class CriticalStrategy : LogStrategy {
    override fun handle(message: String) {
        File("critical_errors.txt").appendText("[CRITICAL] $message\n")
        println("[CRITICAL] S-a scris în fișier. Oprire program...")
        throw CriticalException("Programul s-a oprit din cauza unei erori critice!")
    }
}

// --- 3. CONTEXTUL (Clasa care folosește strategia) ---
class LogContext(var strategy: LogStrategy) {
    fun executeStrategy(message: String) {
        strategy.handle(message)
    }
}

// --- FUNCȚIA PRINCIPALĂ ---
fun main() {
    val context = LogContext(WarningStrategy())

    // Simulăm primirea diferitelor coduri de eroare (2, 1, 0)
    val mesajeEroare = listOf(
        Pair(2, "Conexiune instabilă la rețea"),
        Pair(1, "Fișierul de configurare lipsește"),
        Pair(0, "Baza de date a picat complet")
    )

    for ((cod, text) in mesajeEroare) {
        // Schimbăm strategia în mod dinamic în funcție de cod
        context.strategy = when (cod) {
            2 -> WarningStrategy()
            1 -> ErrorStrategy()
            0 -> CriticalStrategy()
            else -> WarningStrategy()
        }

        try {
            context.executeStrategy(text)
        } catch (e: CriticalException) {
            println("Prins excepție: ${e.message}")
            exitProcess(1) // Oprim programul conform cerinței
        }
    }
}

/*
+----------------------------------+
|            LogContext            |
+----------------------------------+
| - strategy: LogStrategy          |           +-----------------------+
+----------------------------------+           |     <<Interface>>     |
| + executeStrategy(msg: String)   |---------->|       LogStrategy     |
+----------------------------------+           +-----------------------+
                                               | + handle(msg: String) |
                                               +-----------------------+
                                                           ^
                                                           | (implementează)
                                     +---------------------+---------------------+
                                     |                     |                     |
                        +-----------------+   +---------------+   +------------------+
                        | WarningStrategy |   | ErrorStrategy |   | CriticalStrategy |
                        +-----------------+   +---------------+   +------------------+
                        | + handle()      |   | + handle()    |   | + handle()       |
                        +-----------------+   +---------------+   +------------------+

Diagrama de Obiecte


                       ┌──> [ Obiect : WarningStrategy ]
                       │
[ Obiect : LogContext ]┼──> [ Obiect : ErrorStrategy ]
 (strategy ref)        │
                       └──> [ Obiect : CriticalStrategy ]

3. Respectarea Principiilor SOLID

    Single Responsibility Principle (SRP): Fiecare clasă de tip strategie are o singură responsabilitate clară (tratarea exclusivă a unui singur nivel de eroare: scriere fișier, afișare consolă sau oprire).

    Open/Closed Principle (OCP): Codul este deschis pentru extindere și închis pentru modificări. Dacă pe viitor vrem să adăugăm un nou tip de eroare (de ex: InfoStrategy), doar creăm o nouă clasă ce implementează LogStrategy, fără să modificăm clasele existente.

Gemini este un AI și poate să facă greșeli, inclusiv în legătură cu persoane. Confidențialitatea ta și GeminiSe deschide într-o fereastră nouă
 */