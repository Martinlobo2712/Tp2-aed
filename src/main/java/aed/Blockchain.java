package aed;

public class Blockchain {
    ListaEnlazada<Bloque> blockchain;
    Bloque ultimoBloque;

    public Blockchain(){
        blockchain= new ListaEnlazada<>();
        ultimoBloque=null;
    }

    public void agregarBloque(Bloque bloque){
        blockchain.agregar(bloque);
        ultimoBloque=bloque;
    }

    public Bloque ultimoBloque() {
        return ultimoBloque;
    }

}
