// 1. Clasa de date și Proxy-ul (pentru verificarea parolei)
data class StudentData(val nume: String, val notePicate: Int)

class StudentProxy(private val date: StudentData) {
    fun acceseazaDate(parola: String): StudentData? {
        return if (parola == "1234") date else { println("Parola gresita!"); null }
    }
}

// 2. Clasa de bază (Thread) și tipurile de studenți
abstract class Student(val proxy: StudentProxy) : Thread()

class Integralist(proxy: StudentProxy) : Student(proxy) {
    override fun run() { println("${proxy.acceseazaDate("1234")?.nume} -> INTEGRALIST") }
}

class Restantier(proxy: StudentProxy) : Student(proxy) {
    override fun run() { println("${proxy.acceseazaDate("1234")?.nume} -> RESTANTIER") }
}

class Repetent(proxy: StudentProxy) : Student(proxy) {
    override fun run() { println("${proxy.acceseazaDate("1234")?.nume} -> REPETENT") }
}

// 3. Fabrica de obiecte (Factory)
object StudentFactory {
    fun creeaza(nume: String, notePicate: Int): Student {
        val proxy = StudentProxy(StudentData(nume, notePicate))
        return when {
            notePicate == 0 -> Integralist(proxy)
            notePicate in 2..4 -> Restantier(proxy)
            else -> Repetent(proxy)
        }
    }
}

// 4. Funcția principală (Simularea datelor dintr-un fișier)
fun main() {
    // Simulăm linii dintr-un fișier text (Nume, Număr de note < 5)
    val dateFisier = listOf("Popescu Ion, 0", "Ionescu Dan, 3", "Vasilescu Ana, 5")

    val studenti = dateFisier.map { linie ->
        val parti = linie.split(", ")
        StudentFactory.creeaza(parti[0], parti[1].toInt())
    }

    // Fiecare stare e gestionată pe thread-ul ei
    studenti.forEach { it.start() }
}

/* [ StudentFactory ]
          | (creează)
          v
     [ Student ] <------- Moștenesc ------+----------------+
    (clasa Thread)                        |                |
          | (folosește)             [ Integralist ]  [ Restantier ]  [ Repetent ]
          v
   [ StudentProxy ] -------> Protejează -------> [ StudentData ]

2. Diagrama de obiecte (Exemplu în execuție)

[ Fabrica ] ──> creează ──> [ Obiect: Restantier ]
                                  │
                                  └──> [ Obiect: StudentProxy ]
                                             │  (cere parola "1234")
                                             └──> [ Obiect: StudentData (nume="Ionescu Dan") ]*/