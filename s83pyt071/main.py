import tkinter as tk
from tkinter import ttk

def salveaza():
    # Deschidem fișierul și scriem textul din căsuțe adunând valorile cu +
    with open("date.txt", "a") as f:
        text = (f"{e1.get()}, {e2.get()}, {e3.get()}, {e4.get()}, "
                f"{e5.get()}, {e6.get()}, {combo.get()}\n")
        f.write(text)
    print("Salvat cu succes!")

root = tk.Tk()
root.title("Pyt-071")

# Meniu simplu
bara_meniu = tk.Menu(root)
meniu_principal = tk.Menu(bara_meniu, tearoff=0)
meniu_principal.add_command(label="Salvează", command=salveaza)
bara_meniu.add_cascade(label="Meniu", menu=meniu_principal)
root.config(menu=bara_meniu)

# Câmpurile text (Entry)
tk.Label(root, text="Companie:").pack()
e1 = tk.Entry(root); e1.pack()

tk.Label(root, text="Departament:").pack()
e2 = tk.Entry(root); e2.pack()

tk.Label(root, text="Nume:").pack()
e3 = tk.Entry(root); e3.pack()

tk.Label(root, text="Data naștere:").pack()
e4 = tk.Entry(root); e4.pack()

tk.Label(root, text="Funcție:").pack()
e5 = tk.Entry(root); e5.pack()

tk.Label(root, text="Salariu:").pack()
e6 = tk.Entry(root); e6.pack()

# Combobox pentru lună
tk.Label(root, text="Lună:").pack()
luni = ["Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie", "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"]
combo = ttk.Combobox(root, values=luni, state="readonly")
combo.pack()

# Buton salvare
tk.Button(root, text="Trimite", command=salveaza).pack(pady=10)

root.mainloop()