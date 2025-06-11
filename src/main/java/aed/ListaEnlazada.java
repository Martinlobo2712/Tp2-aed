package aed;

import java.util.Iterator;

public class ListaEnlazada<T> implements Iterable<T> {
    private Nodo primero;
    private Nodo ultimo;

    private class Nodo {
        private Nodo sig;
        private T valor;

        Nodo(T v) {
            valor = v;
        }
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
    }

    public void agregar(T elem) {
        Nodo nuevo_nodo = new Nodo(elem);

        if (primero == null) {
            primero = nuevo_nodo;
            ultimo = nuevo_nodo;
        } else {
            ultimo.sig = nuevo_nodo;
            ultimo = nuevo_nodo;
        }
    }

    public T obtenerUltimo() {
        return ultimo.valor;
    }

    public void eliminar(int i) {
        if (i == 0) {
            primero = primero.sig;
            if (primero == null) {
                ultimo = null;
            }
            return;
        }
        Nodo prev = primero;
        int j = 0;
        while (j < i - 1 && prev != null && prev.sig != null) {
            prev = prev.sig;
            j++;
        }
        if (prev != null && prev.sig != null) {
            if (prev.sig == ultimo) {
                ultimo = prev;
            }
            prev.sig = prev.sig.sig;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new IteradorLista();
    }

    private class IteradorLista implements Iterator<T> {
        private Nodo actual = primero;

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public T next() {
            T valor = actual.valor;
            actual = actual.sig;
            return valor;
        }
    }
}
