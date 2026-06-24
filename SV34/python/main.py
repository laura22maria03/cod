import tkinter as tk

# 1. Inițializare fereastră principală (ajustată doar pentru grafic)
root = tk.Tk()
root.title("Grafic SV34")
root.geometry("420x280")  # Dimensiune compactă, perfectă pentru canvas

# 2. Zona de desenare (Canvas) - ocupă toată fereastra
canvas = tk.Canvas(root, width=400, height=260, bg='white', highlightthickness=0)
canvas.pack(pady=10)

# Desenare axe coordonate (X și Y)
canvas.create_line(50, 220, 370, 220, width=2, arrow=tk.LAST)  # Axa X
canvas.create_line(50, 220, 50, 20, width=2, arrow=tk.LAST)    # Axa Y
canvas.create_text(360, 235, text="n", font=("Arial", 10, "bold"))
canvas.create_text(35, 25, text="S", font=("Arial", 10, "bold"))

# 3. Calcul și desenare puncte
for n in range(1, 15):
    s = (n * (n + 1)) // 2  # Formula matematică directă pentru sumă
    
    # Verificăm condiția: 20 < S < 50
    if 20 < s < 50:
        # Transformare în pixeli
        x_pixel = 50 + (n * 25)
        y_pixel = 220 - (s * 4) 
        
        # Desenare grafică (bare albastre și puncte roșii)
        canvas.create_line(x_pixel, 220, x_pixel, y_pixel, fill="royalblue", width=2)
        canvas.create_oval(x_pixel-4, y_pixel-4, x_pixel+4, y_pixel+4, fill="red", outline="darkred")
        
        # Etichete valori pe axe și puncte
        canvas.create_text(x_pixel, y_pixel-12, text=str(s), font=("Arial", 8))
        canvas.create_text(x_pixel, 235, text=str(n), font=("Arial", 8))

root.mainloop()