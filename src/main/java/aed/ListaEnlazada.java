package aed;

import java.util.Iterator;

public class ListaEnlazada<T extends Comparable<T>> implements Iterable<T> {
    private Nodo primero;
    private Nodo ultimo;

    private class Nodo {
        private Nodo sig;
        private Nodo ant;
        private T valor;

        Nodo(T v) {
            valor = v;
            sig = null;
            ant = null;
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
    }

    public Handle agregar(T elem) {
        Nodo nuevo_nodo = new Nodo(elem);

        if (primero == null) {
            primero = nuevo_nodo;
            ultimo = nuevo_nodo;
        } else {
            ultimo.sig = nuevo_nodo;
            nuevo_nodo.ant = ultimo;
            ultimo = nuevo_nodo;
        }
        return new Handle(nuevo_nodo);
    }

    public void eliminar(Handle handle) {
        Nodo nodo = handle.nodo;
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
    }

    public T obtenerUltimo() {
        return ultimo.valor;
    }

    public void eliminar(int i) {
        if (primero == null) return;

        if (i == 0) {
            if (primero == ultimo) {
                primero = null;
                ultimo = null;
            } else {
                primero = primero.sig;
                primero.ant = null;
            }
            return;
        }

        Nodo actual = primero;
        int j = 0;
        while (j < i && actual != null) {
            actual = actual.sig;
            j++;
        }

        if (actual != null) {
            if (actual == ultimo) {
                ultimo = actual.ant;
                if (ultimo != null) {
                    ultimo.sig = null;
                }
            } else {
                if (actual.ant != null) {
                    actual.ant.sig = actual.sig;
                }
                if (actual.sig != null) {
                    actual.sig.ant = actual.ant;
                }
            }
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
