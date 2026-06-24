import tkinter as tk
from tkinter import ttk

# 1. Funcția care încarcă liniile și spune câte sunt
def incarca():
    with open("linii.txt", "w") as f: # Creăm rapid un fișier de test cu 50 de linii
        for i in range(1, 51): f.write(f"Linia {i}\n")
        
    with open("linii.txt", "r") as f:
        linii = f.readlines()
        
    box.delete(0, tk.END)
    for l in linii: box.insert(tk.END, l.strip())
    lbl.config(text=f"Total linii: {len(linii)}")

# 2. Funcția care sare direct la linia scrisă în căsuța de text (Entry)
def sari():
    index = int(entry.get()) - 1
    box.see(index) # Mută scroll-ul la acea linie

root = tk.Tk()
root.title("Pyt-073 Scurt")

# Meniu simplu
bara = tk.Menu(root)
meniu = tk.Menu(bara, tearoff=0)
meniu.add_command(label="Încarcă", command=incarca)
meniu.add_command(label="Sari", command=sari)
bara.add_cascade(label="Opțiuni", menu=meniu)
root.config(menu=bara)

# Elemente interfață
lbl = tk.Label(root, text="Apasă Opțiuni -> Încarcă")
lbl.pack()

# Căsuța unde scrii numărul liniei la care vrei să sari
entry = tk.Entry(root)
entry.pack(pady=5)

# Zona de text (Listbox) + Scrollbar (Bară de derulare)
scroll = tk.Scrollbar(root)
scroll.pack(side=tk.RIGHT, fill=tk.Y)

box = tk.Listbox(root, yscrollcommand=scroll.set)
box.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)

scroll.config(command=box.yview)

root.mainloop()