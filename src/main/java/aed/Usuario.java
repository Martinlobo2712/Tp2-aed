package aed;

public class Usuario implements Comparable<Usuario> {
    private int id;
    private int saldo;

    public Usuario(int id) {
        this.id = id;
        this.saldo = 0;
    }

    public int obtenerId() {
        return id;
    }

    public int obtenerSaldo() {
        return saldo;
    }

    public void aumentarSaldo(int monto) {
        saldo += monto;
    }

    @Override
    public int compareTo(Usuario otro) {
        if (this.saldo > otro.saldo) return 1;
        if (this.saldo < otro.saldo) return -1;
        if (this.id < otro.id) return 1;
        if (this.id > otro.id) return -1;
        return 0;

    }
}
