package aed;

import java.util.Iterator;

public class ListaEnlazada<T extends Comparable<T>> implements Iterable<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;

    private class Nodo {
        private Nodo sig;
        private Nodo ant;
        private T valor;

        Nodo(T v) {
            valor = v;
        }
    }

    public class Handle implements Comparable<Handle> {
        private Nodo nodo;

        private Handle(Nodo nodo) {
            this.nodo = nodo;
        }

        public T getValor() {
            return nodo.valor;
        }

        public void setValor(T nuevoValor) {
            nodo.valor = nuevoValor;
        }

        @Override
        public int compareTo(Handle o) {
            return nodo.valor.compareTo(o.nodo.valor);
        }
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        longitud = 0;
    }

    public int getLongitud() {
        return longitud;
    }

    public Handle agregar(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.sig = nuevo;
            nuevo.ant = ultimo;
            ultimo = nuevo;
        }
        longitud++;
        return new Handle(nuevo);
    }

    public void eliminar(Handle handle) {
        Nodo nodo = handle.nodo;
        if (nodo == null) return;
        if (nodo.ant != null) {
            nodo.ant.sig = nodo.sig;
        } else {
            primero = nodo.sig;
        }
        if (nodo.sig != null) {
            nodo.sig.ant = nodo.ant;
        } else {
            ultimo = nodo.ant;
        }
        longitud--;
    }

    public T obtenerUltimo() {
        return (ultimo != null) ? ultimo.valor : null;
    }

    public void eliminar(int i) {
        if (i < 0 || primero == null) return;

        if (i == 0) {
            if (primero == ultimo) {
                primero = null;
                ultimo = null;
            } else {
                primero = primero.sig;
                primero.ant = null;
            }
            longitud--;
            return;
        }

        Nodo actual = primero.sig;
        int j = 1;
        while (actual != null && j < i) {
            actual = actual.sig;
            j++;
        }

        if (actual != null) {
            if (actual.sig != null) {
                actual.sig.ant = actual.ant;
            } else {
                ultimo = actual.ant;
            }
            if (actual.ant != null) {
                actual.ant.sig = actual.sig;
            }
            longitud--;
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
