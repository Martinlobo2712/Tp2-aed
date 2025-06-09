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

    public void disminuirSaldo(int monto) {
        saldo -= monto;
    }

    @Override // esto va aca ???? por las dudas lo agrego
    public int compareTo(Usuario otro) {
        if (this.saldo != otro.saldo) {
            return Integer.compare(this.saldo, otro.saldo);
        }
        return Integer.compare(otro.id, this.id); // prioriza el de menor id
    }
}
