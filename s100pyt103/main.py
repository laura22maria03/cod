import multiprocessing
import time

# Funcția pe care o va executa fiecare dintre cele 3 procese separate
def proces_lucrator(nume_proces, coada):
    while True:
        cuvant = coada.get() # Așteaptă și preia următorul cuvânt din coadă
        if cuvant == "STOP": # Semnalul de oprire a procesului
            break
        print(f"[{nume_proces}] Am primit cuvântul: {cuvant}")
        time.sleep(0.1)

if __name__ == "__main__":
    # 1. Textul primit (simulăm citirea dintr-un fișier text)
    text_fisier = "Python multiprocessing distribuie cuvintele in mod ciclic catre procese"
    cuvinte = text_fisier.split()

    # 2. Creăm cozile dedicate pentru cele 3 procese ajutătoare
    cozi = [multiprocessing.Queue() for _ in range(3)]
    nume_procese = ["Proces-A", "Proces-B", "Proces-C"]
    
    # 3. Creăm și pornim cele 3 procese
    procese = []
    for i in range(3):
        p = multiprocessing.Process(target=proces_lucrator, args=(nume_procese[i], cozi[i]))
        procese.append(p)
        p.start()

    # 4. Distribuirea CICLICĂ a cuvintelor (folosind operatorul modulo %)
    for index, cuvant in enumerate(cuvinte):
        index_coada = index % 3 # Va fi mereu 0, 1, 2, 0, 1, 2... (ciclic)
        cozi[index_coada].put(cuvant)

    # 5. Trimitem semnalul de STOP fiecărui proces ca să se poată opri curat
    for q in cozi:
        q.put("STOP")

    # Așteptăm terminarea tuturor proceselor
    for p in procese:
        p.join()

    print("\nToate cuvintele au fost procesate!")