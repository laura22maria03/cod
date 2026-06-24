import socket
import threading
import json
import time

def node(name, port, next_port):
    def handle(conn):
        data = json.loads(conn.recv(1024).decode())
        print(name, "primit:", data["msg"])

        data["msg"] += " -> " + name

        if next_port:
            s = socket.socket()
            s.connect(("localhost", next_port))
            s.send(json.dumps(data).encode())
            s.close()
        else:
            print("FINAL:", data["msg"])

        conn.close()

    server = socket.socket()
    server.bind(("localhost", port))
    server.listen()

    while True:
        conn, _ = server.accept()
        threading.Thread(target=handle, args=(conn,)).start()
    
threading.Thread(target=node, args=("A", 5001, 5002), daemon=True).start()
threading.Thread(target=node, args=("B", 5002, 5003), daemon=True).start()
threading.Thread(target=node, args=("C", 5003, None), daemon=True).start()

time.sleep(1)


def send(port, text):
    s = socket.socket()
    s.connect(("localhost", port))
    s.send(json.dumps({"msg": text}).encode())
    s.close()


print("=== UP ===")
send(5001, "START")

time.sleep(2)