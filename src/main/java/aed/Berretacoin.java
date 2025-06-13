package aed;

public class Berretacoin {
    Usuarios usuarios;
    Blockchain blockchain;

    public Berretacoin(int cantUsuarios) { // O(p)
        usuarios = new Usuarios(cantUsuarios); // O(p)
        blockchain = new Blockchain(); // O(1)
    }

    public void agregarBloque(Transaccion[] transacciones){ // O(n * log(p))
        blockchain.agregarBloque(transacciones); // O(n)

        for (int i = 0; i < transacciones.length; i++){ // O (n * log(p))
            if(transacciones[i].id_comprador() != 0){
                usuarios.actualizarUsuario(transacciones[i].id_comprador(), -transacciones[i].monto());
            }
            usuarios.actualizarUsuario(transacciones[i].id_vendedor(), transacciones[i].monto());
        }
    }

    public Transaccion txMayorValorUltimoBloque(){ // O(1)
        return blockchain.ultimoBloque().txMayorValor();
    }

    public Transaccion[] txUltimoBloque(){ // O(n)
        return blockchain.ultimoBloque().getSecuenciaTransacciones();
    }

    public int maximoTenedor(){ // O(1)
        return usuarios.maximoTenedor();
    }

    public int montoMedioUltimoBloque(){ // O(1)
        return blockchain.ultimoBloque().montoPromedio();
    }

    public void hackearTx(){ //O(log(n) + O(log(p))
        Transaccion eliminada = blockchain.ultimoBloque().hackearTx(); // O(log(n))

        if(eliminada.id_comprador() != 0){
            usuarios.actualizarUsuario(eliminada.id_comprador(), eliminada.monto()); // O(log(p))
        }
        usuarios.actualizarUsuario(eliminada.id_vendedor(), -eliminada.monto()); // O(log(p))

    }
}
