package aed;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int id_comprador;
    private int id_vendedor;
    private int monto;

    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    @Override
    public int compareTo(Transaccion otra) {
        if (this.monto > otra.monto) return 1;
        if (this.monto < otra.monto) return -1;
        if (this.id > otra.id) return 1;
        if (this.id < otra.id) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;
        if (!(otro instanceof Transaccion)) return false;
        Transaccion otra = (Transaccion) otro;
        return this.id == otra.id;
    }

    public int id_transaccion(){
        return id;
    }

    public int monto() {
        return monto;
    }

    public int id_comprador() {
        return id_comprador;
    }
    
    public int id_vendedor() {
        return id_vendedor;
    }

    public boolean esCreacion(Transaccion tx){
        return tx.id_comprador==0;
    }
}