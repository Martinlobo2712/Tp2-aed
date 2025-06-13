package aed;

public class Usuarios {
    Heap<Usuario>.Handle[] usuarios;
    Heap<Usuario> heap;

    @SuppressWarnings("unchecked")
    public Usuarios(int cantUsuarios){ // O(p)
        Usuario[] usuarios = new Usuario[cantUsuarios];

        this.usuarios = (Heap<Usuario>.Handle[]) new Heap.Handle[cantUsuarios];

        for(int i = 0; i < usuarios.length; i++) { // O(p)
            usuarios[i] = new Usuario(i + 1);
        }

        heap = new Heap<Usuario>(usuarios, this.usuarios); // O(p)
    }

    public void actualizarUsuario(int id, int montoASumar){ // O(log(p))
        this.usuarios[id-1].getValor().aumentarSaldo(montoASumar);
        heap.reubicar(usuarios[id-1]);
    }

    public int maximoTenedor(){ // O(1)
        return heap.verMaximo().obtenerId();
    }

}
