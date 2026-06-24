import threading
import time

# 1. Diferența LOCK vs RLOCK (Blocare recursivă)
def demonstreaza_lock_vs_rlock():
    lock = threading.Lock()
    rlock = threading.RLock()
    
    print("--- Test RLock ---")
    rlock.acquire()
    rlock.acquire()  # MERGE! RLock permite aceleiași funcții să se blocheze de 2 ori.
    print("RLock a funcționat dublu.")
    
    print("\n--- Test Lock ---")
    lock.acquire()
    print("Prima blocare OK. Urmează blocajul (deadlock)...")
    lock.acquire(timeout=1)  # ÎNGHEAȚĂ aici dacă nu punem timeout! Lock simplu nu permite dublă blocare.
    print("Lock-ul simplu s-a blocat pe sine însuși.")

# 2. Diferența SEMAFOR (Acces multiplu) vs LOCK (Acces unic)
def viziteaza_resursa(id_fir, semafor):
    with semafor:
        print(f"Fir {id_fir} a intrat")
        time.sleep(0.5)

def demonstreaza_semafor():
    print("\n--- Test Semafor ---")
    # Permite exact 2 fire în același timp (Lock permitea doar 1)
    semafor = threading.BoundedSemaphore(2)
    
    # Pornim 4 fire deodată; primele 2 vor intra împreună, ultimele 2 vor aștepta
    for i in range(4):
        threading.Thread(target=viziteaza_resursa, args=(i, semafor)).start()

if __name__ == "__main__":
    demonstreaza_lock_vs_rlock()
    demonstreaza_semafor()