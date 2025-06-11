package aed;

public class Blockchain {
    ListaEnlazada<Bloque> blockchain;
    Bloque ultimoBloque;


    public Blockchain(){
    blockchain= new ListaEnlazada<>();
    ultimoBloque=null; }
    //crea nueva lista de bloques
    // todavia no hay ultimo bloque(pq esta vacia)
   
    public void agregarBloque(Bloque bloque){
        blockchain.agregar(bloque);
        ultimoBloque=bloque;
             }
    //agrega al final
    //actualiza la referencia

    public Bloque ultimoBloque() {
        return ultimoBloque;
        }
    }
//devuelve el ultimo bloque,y no recorro la lista(O(1))
