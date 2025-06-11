package aed;
//Esta incompleta!!!
public class Bloque {
    private int suma = 0;
    private int cantidad = 0;
    private ListaEnlazada<Transaccion> transacciones;
    private Heap<Transaccion> heap; //faltaria el handle o ver si hacemos array o otra cosa

    public Bloque(ListaEnlazada<Transaccion> transacciones) {
        this.transacciones = new ListaEnlazada<>();
        this.heap = new Heap<>();

        for (Transaccion tx : transacciones) {
            this.transacciones.agregar(tx);
            heap.agregar(tx);

            if (!tx.esCreacion()) {
                suma += tx.monto();
                cantidad++;
            }
        }
    }
    public Transaccion txMayorValor() {
        return heap.verMaximo();
    }

    
    public Transaccion hackearTx() {
        Transaccion tx = heap.sacarMaximo(); //falta ver como sacar de la lista
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
