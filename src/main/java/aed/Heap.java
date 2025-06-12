package aed;

public class Heap<T extends Comparable<T>> {

    private Nodo raiz;
    private int cantNodos; // Integer.toBinaryString(num)

    private class Nodo{
        T valor;
        Nodo izq, der, padre;

        public Nodo(T valor, Nodo padre){
            this.valor = valor;
            this.padre = padre;
        }
    }

    public class Handle {
        private Nodo nodo;

        private Handle(Heap<T>.Nodo nodo) {
            this.nodo = nodo;
        }

        public T getValor() {
            return nodo.valor;
        }

        public void setValor(T nuevoValor) {
            nodo.valor = nuevoValor;
        }

    }

    public Heap(){
        this.raiz = null;
        cantNodos = 0;
    }

    @SuppressWarnings("unchecked")
    public Heap(T[] array, Handle[] handleArray){
        cantNodos = array.length;

        Object[] nodos = new Object[cantNodos];

        for(int i = 0; i < cantNodos; i++){
            nodos[i] = new Nodo(array[i], null);
            handleArray[i] = new Handle((Nodo) nodos[i]);
        }

        for(int i = 0; i < cantNodos; i++){
            int izq = 2 * i + 1, der = 2 * i + 2;

            if(izq < cantNodos){
                ((Nodo)nodos[i]).izq = (Nodo)nodos[izq];
                ((Nodo)nodos[izq]).padre = (Nodo)nodos[i];
            }

            if(der < cantNodos){
                ((Nodo)nodos[i]).der = (Nodo)nodos[der];
                ((Nodo)nodos[der]).padre = (Nodo)nodos[i];
            }
        }

        for(int i = cantNodos/2-1; i >= 0; i--){
            heapify((Nodo)nodos[i]);
        }

        raiz = (Nodo)nodos[0];
    }

    private void heapify(Nodo nodo) {
        Nodo mayor = nodo;

        if (nodo.izq != null && nodo.izq.valor.compareTo(mayor.valor) > 0) {
            mayor = nodo.izq;
        }

        if (nodo.der != null && nodo.der.valor.compareTo(mayor.valor) > 0) {
            mayor = nodo.der;
        }

        if (mayor != nodo) {
            T temp = nodo.valor;
            nodo.valor = mayor.valor;
            mayor.valor = temp;

            heapify(mayor);
        }
    }

    public void agregar(T valor){
        String recorrido = Integer.toBinaryString(cantNodos+1);

        if(cantNodos == 0){
            this.raiz = new Nodo(valor, null);
        }else {
            Nodo nodo = this.raiz;

            for (int i = 1; i < recorrido.length() - 1; i++) {
                nodo = (recorrido.charAt(i) == '1') ? nodo.der : nodo.izq;
            }

            if (recorrido.charAt(recorrido.length()-1) == '0') {
                nodo.izq = new Nodo(valor, nodo);
                heapifyUp(nodo.izq);
            } else {
                nodo.der = new Nodo(valor, nodo);
                heapifyUp(nodo.der);
            }
        }
        cantNodos++;
    }

    private void heapifyUp(Nodo nodo) {
        while (nodo.padre != null && nodo.valor.compareTo(nodo.padre.valor) > 0) {
            T temp = nodo.valor;
            nodo.valor = nodo.padre.valor;
            nodo.padre.valor = temp;

            nodo = nodo.padre;
        }
    }

    public T verMaximo() {
        return (raiz == null) ? null : raiz.valor;
    }

    public T sacarMaximo(){
        if (raiz == null){
            return null;
        }
        T max = raiz.valor;

        if (cantNodos == 1) {
            raiz = null;
        }else{
            String recorrido = Integer.toBinaryString(cantNodos);
            Nodo ultimo = raiz;

            for (int i = 1; i < recorrido.length(); i++) {
                ultimo = (recorrido.charAt(i) == '1') ? ultimo.der : ultimo.izq;
            }
            raiz.valor = ultimo.valor;

            if(ultimo.padre.der == ultimo){
                ultimo.padre.der = null;
            }else{
                ultimo.padre.izq = null;
            }
        }
        cantNodos--;

        heapify(raiz);

        return max;
    }

    public void reubicar(Handle handle) {
        heapifyUp(handle.nodo);
        heapify(handle.nodo);
    }

    // Esto es debug habría que borrarlo
    public void imprimirPorNiveles() {
        if (raiz == null) {
            System.out.println("Heap vacío.");
            return;
        }

        java.util.Queue<Nodo> cola = new java.util.LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            int nivel = cola.size();

            while (nivel-- > 0) {
                Nodo actual = cola.poll();
                System.out.print(actual.valor + " ");

                if (actual.izq != null) cola.add(actual.izq);
                if (actual.der != null) cola.add(actual.der);
            }

            System.out.println();
        }
    }

}
