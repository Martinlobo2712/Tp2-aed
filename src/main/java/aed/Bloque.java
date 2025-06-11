package aed;

public class Bloque implements Comparable<Bloque> {
    int suma = 0, cantidad = 0;
    ListaEnlazada<Transaccion> transacciones;
    Heap<ListaEnlazada<Transaccion>.Handle> heap;



    @Override
    public int compareTo(Bloque o) {
        return 0;
    }
}
