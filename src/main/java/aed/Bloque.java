package aed;

public class Bloque implements Comparable<Bloque> {
    private int suma = 0;
    private int cantidad = 0;
    private ListaEnlazada<Transaccion> transacciones;
    private Heap<ListaEnlazada<Transaccion>.Handle> heap;

    @SuppressWarnings("unchecked")
    public Bloque(Transaccion[] transacciones) { // O(n)
        this.transacciones = new ListaEnlazada<Transaccion>();
        this.heap = new Heap<ListaEnlazada<Transaccion>.Handle>();
        ListaEnlazada<Transaccion>.Handle[] handles = (ListaEnlazada<Transaccion>.Handle[]) new ListaEnlazada.Handle[transacciones.length];

        int i = 0;
        for (Transaccion tx : transacciones) { // O(n)
            ListaEnlazada<Transaccion>.Handle handle = this.transacciones.agregar(tx);
            handles[i] = handle;

            if (!tx.esCreacion()) {
                suma += tx.monto();
                cantidad++;
            }

            i++;
        }

        heap = new Heap<>(handles); // O(n)
    }

    public Transaccion txMayorValor() { // O(1)
        return heap.verMaximo().getValor();
    }

    public Transaccion hackearTx() { //
        ListaEnlazada<Transaccion>.Handle handle = heap.sacarMaximo(); // O(log(n))
        Transaccion tx = handle.getValor();
        
        transacciones.eliminar(handle); // O(1)

        if (!tx.esCreacion()) {
            suma -= tx.monto();
            cantidad--;
        }
        return tx;
    }

    public int montoPromedio() { // O(1)
        if (cantidad == 0) return 0;
        return suma / cantidad;
    }

    @Override
    public int compareTo(Bloque o) {
        return 0;
    }

    public Transaccion[] getSecuenciaTransacciones() { // O(n)
        Transaccion[] transacciones = new Transaccion[this.transacciones.getLongitud()];

        int i = 0;
        for(Transaccion tx: this.transacciones){
            transacciones[i] = new Transaccion(tx);
            i++;
        }

        return transacciones;
    }
}
