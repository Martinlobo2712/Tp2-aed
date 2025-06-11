package aed;

public class Bloque implements Comparable<Bloque> {
    private int suma = 0;
    private int cantidad = 0;
    private ListaEnlazada<Transaccion> transacciones;
    private Heap<ListaEnlazada<Transaccion>.Handle> heap;


    @Override
    public int compareTo(Bloque o) {
        return 0;
    }
    public Bloque(ListaEnlazada<Transaccion> transacciones) {
        this.transacciones = new ListaEnlazada<>();
        this.heap = new Heap<>();

        for (Transaccion tx : transacciones) {
            ListaEnlazada<Transaccion>.Handle handle = this.transacciones.agregar(tx);
            heap.agregar(handle);

            if (!tx.esCreacion()) {
                suma += tx.monto();
                cantidad++;
            }
        }
    }
    public Transaccion txMayorValor() {
        return heap.verMaximo().getValor();
    }


    public Transaccion hackearTx() {
        ListaEnlazada<Transaccion>.Handle handle = heap.sacarMaximo();
        Transaccion tx = handle.getValor();
        
        transacciones.eliminar(handle);

        if (!tx.esCreacion()) {
            suma -= tx.monto();
            cantidad--;
        }
        return tx;
    }

    public ListaEnlazada<Transaccion> obtenerTransacciones() {
        ListaEnlazada<Transaccion> copia = new ListaEnlazada<>();
        for (Transaccion tx : transacciones) {
            copia.agregar(tx);
        }
        return copia;
    }

    public int montoPromedio() {
        if (cantidad == 0) return 0;
        return suma / cantidad;
    }
}
