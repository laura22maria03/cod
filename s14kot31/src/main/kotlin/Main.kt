package org.example

import org.jsoup.Jsoup
import java.io.File
import java.net.URL

fun main() {
    val adresaSite = "https://www.wikipedia.org/"

    // PĂCĂLIRE BROWSER: Adăugăm .userAgent ca Wikipedia să creadă că suntem pe Chrome
    val paginaWeb = Jsoup.connect(adresaSite)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
        .get()

    val toatePozele = paginaWeb.select("img")

    // Am reparat spațiul de la eroare aici:
    var numar = 1

    for (poza in toatePozele) {
        val adresaPoza = poza.absUrl("src")

        // Verificăm doar să nu fie link gol, altfel dă altă eroare
        if (adresaPoza.isNotEmpty()) {
            println("Descarc poza numărul $numar ...")

            // Creăm o conexiune manuală pentru a adăuga User-Agent și la descărcarea pozei propriu-zise
            val conexiuneInternet = URL(adresaPoza).openConnection()
            conexiuneInternet.setRequestProperty("User-Agent", "Mozilla/5.0")

            val fluxDate = conexiuneInternet.getInputStream()
            val octetiPoza = fluxDate.readBytes()

            File("poza_$numar.jpg").writeBytes(octetiPoza)
            fluxDate.close()

            numar = numar + 1
        }
    }
    println("Gata! Am terminat.")
}