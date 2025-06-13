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

    public Transaccion(Transaccion transaccion) {
        this.id = transaccion.id_transaccion();
        this.id_comprador = transaccion.id_comprador();
        this.id_vendedor = transaccion.id_vendedor();
        this.monto = transaccion.monto();
    }

    @Override
    public int compareTo(Transaccion otra) {
        if (this.monto > otra.monto) return 1;
        if (this.monto < otra.monto) return -1;
        if (this.id > otra.id) return 1;
        if (this.id < otra.id) return -1;
        return 0;
    }

    public boolean equals(Object otra) {
        boolean otraIsNull=(otra==null);
        boolean claseDistinta=otra.getClass()!=this.getClass();
        if (otraIsNull||claseDistinta){
            return false;
        }
        Transaccion otraTx=(Transaccion) otra;

        return otraTx.id==id;
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

    public boolean esCreacion(){
        return id_comprador==0;
    }   
}