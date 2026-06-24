import threading
import time

# Lista primită în constructor și starea de pornire
lista = [1, 3, 4, 7, 8, 9, 2]
starea_curenta = "s0"

# Funcții lambda cerute pentru identificarea numerelor pare/impare
este_par = lambda x: x % 2 == 0
este_impar = lambda x: x % 2 != 0

def rulare_s0():
    global starea_curenta
    while True:
        if starea_curenta == "s0":
            if len(lista) > 0:
                starea_curenta = "s1"
            else:
                print("Automat: Nu mai există elemente. Programul se termină.")
                starea_curenta = "STOP"
                break
        time.sleep(0.1)

def rulare_s1():
    global starea_curenta
    while starea_curenta != "STOP":
        if starea_curenta == "s1":
            # Găsim primul element par folosind lambda
            par = next((x for x in lista if este_par(x)), None)
            if par is not None:
                lista.remove(par)
                print(f"[s1] Sters element par: {par} -> Lista rămasă: {lista}")
            starea_curenta = "s2"
        time.sleep(0.1)

def rulare_s2():
    global starea_curenta
    while starea_curenta != "STOP":
        if starea_curenta == "s2":
            # Găsim primul element impar folosind lambda
            impar = next((x for x in lista if este_impar(x)), None)
            if impar is not None:
                lista.remove(impar)
                print(f"[s2] Sters element impar: {impar} -> Lista rămasă: {lista}")
            starea_curenta = "s0"
        time.sleep(0.1)

# Pornirea celor 3 thread-uri (fiecare stare are thread-ul ei)
t0 = threading.Thread(target=rulare_s0)
t1 = threading.Thread(target=rulare_s1)
t2 = threading.Thread(target=rulare_s2)

print(f"Lista inițială: {lista}\n")
t0.start(); t1.start(); t2.start()

# Așteptăm ca procesul complet să se termine în s0
t0.join(); t1.join(); t2.join()