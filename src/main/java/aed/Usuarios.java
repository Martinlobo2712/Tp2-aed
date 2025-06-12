package aed;

public class Usuarios {
    Heap<Usuario>.Handle[] usuarios;
    Heap<Usuario> heap;

    @SuppressWarnings("unchecked")
    public Usuarios(int cantUsuarios){
        Usuario[] usuarios = new Usuario[cantUsuarios];
        this.usuarios = (Heap<Usuario>.Handle[]) new Heap.Handle[cantUsuarios];

        for(int i = 0; i < usuarios.length; i++) {
            usuarios[i] = new Usuario(i + 1);
        }

        heap = new Heap<Usuario>(usuarios, this.usuarios);
    }

    public void actualizarUsuario(int id, int montoASumar){
        this.usuarios[id-1].getValor().aumentarSaldo(montoASumar);
        heap.reubicar(usuarios[id-1]);
    }

}
