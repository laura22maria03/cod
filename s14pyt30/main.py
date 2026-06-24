import time

# --- 1. DECORATORII (Funcții care modifică alte funcții) ---

def decorator_stare(functie_originala):
    """ Verifică dacă procesul are voie să ruleze sau e în așteptare """
    def wrapper(proces):
        if proces["stare"] == "in_asteptare":
            print(f"[⏸] {proces['nume']} este oprit. Nu scrie nimic.")
            return # Oprim execuția aici
        return functie_originala(proces)
    return wrapper

def decorator_semafor(functie_originala):
    """ Simulează un semafor: lasă doar un proces să scrie pe rând """
    def wrapper(proces):
        print(f"[ Semafor]: Se blochează fișierul pentru {proces['nume']}...")
        functie_originala(proces)
        print(f"[ Semafor]: S-a deblocat fișierul.")
    return wrapper


# --- 2. FUNCȚIA DE SCRIERE (Decorată) ---

@decorator_stare
@decorator_semafor
def scrie_in_fisier(proces):
    with open("fisier_simplu.txt", "a") as f:
        f.write(f"{proces['nume']} a scris datele: {proces['date']}\n")
    print(f"[ Done] {proces['nume']} a scris în fișier.")


# --- 3. COADA CIRCULARĂ (Folosim o listă simplă) ---

# Creăm procesele ca niște simple dicționare (obiecte de date)
proces_A = {"nume": "Proces-A", "date": "Mesaj A", "stare": "pornit"}
proces_B = {"nume": "Proces-B", "date": "Mesaj B", "stare": "in_asteptare"} # Ăsta e oprit
proces_C = {"nume": "Proces-C", "date": "Mesaj C", "stare": "pornit"}

# Coada noastră circulară este o listă
coada = [proces_A, proces_B, proces_C]

print("--- Începem simularea circulară (3 pași) ---")

# Simulăm parcurgerea circulară
for i in range(5): # Rulăm de 5 ori ca să vezi cum se repetă
    # Luăm procesul curent folosind împărțirea cu rest (%) la lungimea cozii
    pozitie = i % len(coada)
    proces_curent = coada[pozitie]
    
    print(f"\n--- Pasul {i+1} | La rând este: {proces_curent['nume']} ---")
    
    # Încercăm să scriem
    scrie_in_fisier(proces_curent)