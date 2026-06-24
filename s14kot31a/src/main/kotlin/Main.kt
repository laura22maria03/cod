import org.jsoup.Jsoup
import java.io.File
import java.net.URL

fun main() {
    val urlPagina = "https://example.com" // Pune aici site-ul dorit
    println("--- Începem descărcarea paginii HTML ---")

    try {
        // 1. Descărcăm și analizăm pagina HTML
        val document = Jsoup.connect(urlPagina).get()

        // 2. Căutăm toate tag-urile de tip 'img'
        val imagini = document.select("img")

        var contor = 1

        // 3. Luăm fiecare imagine la rând
        for (img in imagini) {
            // Extragem valoarea atributului 'src' (care poate fi o legătură absolută)
            val urlImagine = img.absUrl("src")

            // Dacă src-ul nu e gol, o descărcăm
            if (urlImagine.isNotEmpty()) {
                println("Am găsit imaginea: $urlImagine")

                // 4. Salvare separată cu o cerere GET (băbește, prin URL.readBytes())
                val numeFisier = "imagine_$contor.jpg"

                print("Descărcăm în $numeFisier... ")
                val bytes = URL(urlImagine).readBytes() // Face un GET automat în spate
                File(numeFisier).writeBytes(bytes)     // Salvează fișierul pe disc

                println("✅ Succes!")
                contor++
            }
        }

        println("\n--- Gata! Toate imaginile au fost descărcate. ---")

    } catch (e: Exception) {
        println("A apărut o eroare: ${e.message}")
    }
}