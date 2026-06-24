import java.io.File

fun main() {
    val fisier = File("src/main/intrare.txt")

    // Verificăm dacă fișierul există înainte de a-l citi
    if (!fisier.exists()) {
        println("Eroare: Fișierul 'intrare.txt' nu a fost găsit!")
        return
    }

    // Citire din fișier + Procesare funcțională + Afișare directă
    fisier.readLines()
        .map { linie ->
            linie.split("\\s+".toRegex())
                .filter { it.isNotEmpty() }
                .joinToString(" ")
        }
        .filter { it.isNotEmpty() }
        .forEach { linieCuratata ->
            println(linieCuratata)
        }
}