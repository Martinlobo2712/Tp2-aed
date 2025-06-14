package aed;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;


import org.junit.jupiter.api.Test;


public class BerretacoinExtraTests {

    @Test
    public void inicialMaximoTenedor() {
        // Todos empiezan con saldo 0, y compareTo da preferencia al id menor
        Berretacoin bc = new Berretacoin(5);
        assertEquals(1, bc.maximoTenedor());
    }

    @Test
    public void secuenciaYCopiasDeTransacciones() {
        Berretacoin bc = new Berretacoin(2);
        Transaccion t1 = new Transaccion(1, 0, 1, 50);
        Transaccion t2 = new Transaccion(2, 1, 2, 20);
        bc.agregarBloque(new Transaccion[]{ t1, t2 });

        Transaccion[] seq1 = bc.txUltimoBloque();
        Transaccion[] seq2 = bc.txUltimoBloque();
        // Mismo contenido en orden, pero distintos objetos
        assertEquals(2, seq1.length);
        assertEquals(50, seq1[0].monto());
        assertEquals(20, seq1[1].monto());
        assertNotSame(seq1, seq2);
        assertNotSame(seq1[0], seq2[0]);
    }

    @Test
    public void mayorValorYMontoMedio() {
        Berretacoin bc = new Berretacoin(3);
        Transaccion[] txs = new Transaccion[] {
            new Transaccion(1, 0, 1, 90),  // creación para user 1
            new Transaccion(2, 2, 1, 10),  // user2->user1:10
            new Transaccion(3, 1, 2, 30)   // user1->user2:30
        };
        bc.agregarBloque(txs);

        // La tx de mayor monto es la id=1 (90)
        Transaccion mayor = bc.txMayorValorUltimoBloque();
        assertEquals(1, mayor.id_transaccion());   // creación -> id_comprador = 0
        assertEquals(90, mayor.monto());

        // Promedio: (10 + 30) / 3 = 20
        assertEquals(20, bc.montoMedioUltimoBloque());
    }

    @Test
    public void hackearQuitaMayorYActualizaBloque() {
        Berretacoin bc = new Berretacoin(3);
        Transaccion[] txs = new Transaccion[] {
            new Transaccion(1, 0, 1, 90),
            new Transaccion(2, 2, 1, 10),
            new Transaccion(3, 1, 2, 30)
        };
        bc.agregarBloque(txs);

        // Primero, la mayor es 90
        assertEquals(90, bc.txMayorValorUltimoBloque().monto());
        // Al hackear, devuelve la tx de 90 y la elimina del bloque
        Transaccion hackeada = bc.txMayorValorUltimoBloque();
        bc.hackearTx();  // remueve la de monto=90

        // Ahora la mayor debe ser la de 30
        assertEquals(30, bc.txMayorValorUltimoBloque().monto());
        // Y el promedio recálcula sobre los dos restantes: (10 + 30) / 2 = 20
        assertEquals(20, bc.montoMedioUltimoBloque());
    }

    @Test
    public void multiplesBloquesYmaximoGlobal() {
        Berretacoin bc = new Berretacoin(2);
        // Bloque 1
        Transaccion[] b1 = {
            new Transaccion(1, 0, 1, 40),
            new Transaccion(2, 1, 2, 15)
        };
        bc.agregarBloque(b1);
        // Tras b1: user1 = 40 − 15 = 25, user2 = 15 -> max = 1
        assertEquals(1, bc.maximoTenedor());

        // Bloque 2
        Transaccion[] b2 = {
            new Transaccion(3, 0, 2, 30)
        };
        bc.agregarBloque(b2);
        // Tras b2: user1=25; user2=15+30=45 → max = 2
        assertEquals(2, bc.maximoTenedor());
    }
    @Test
    public void testTransaccionCompareTo() {
        Transaccion t1 = new Transaccion(1, 0, 0, 100);
        Transaccion t2 = new Transaccion(2, 0, 0, 200);
        Transaccion t3 = new Transaccion(3, 0, 0, 100);
        assertTrue(t1.compareTo(t2) < 0);
        assertTrue(t2.compareTo(t1) > 0);
        // Equal monto, compare by id
        assertTrue(t1.compareTo(t3) < 0);
        assertTrue(t3.compareTo(t1) > 0);
        assertEquals(0, new Transaccion(4,0,0,50).compareTo(new Transaccion(4,1,2,50)));
    }

    @Test
    public void testUsuarioCompareTo() {
        Usuario u1 = new Usuario(1);
        Usuario u2 = new Usuario(2);
        u1.aumentarSaldo(150);
        u2.aumentarSaldo(100);
        assertTrue(u1.compareTo(u2) > 0);
        assertTrue(u2.compareTo(u1) < 0);
        // Mismo saldo, menor id gana
        Usuario u3 = new Usuario(3);
        u3.aumentarSaldo(150);
        assertTrue(u1.compareTo(u3) > 0);
        assertTrue(u3.compareTo(u1) < 0);
    }

    @Test
    public void testOperacionesListaEnlazada() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();
        ListaEnlazada<String>.Handle h1 = lista.agregar("A");
        ListaEnlazada<String>.Handle h2 = lista.agregar("B");
        ListaEnlazada<String>.Handle h3 = lista.agregar("C");
        assertEquals(3, lista.getLongitud());
        assertEquals("C", lista.obtenerUltimo());
        // Pruebo el iterador
        Iterator<String> it = lista.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertEquals("B", it.next());
        assertEquals("C", it.next());
        assertFalse(it.hasNext());
        // Elimino el elemento del medio
        lista.eliminar(h2);
        assertEquals(2, lista.getLongitud());
        // Elimino por indice
        lista.eliminar(0);
        assertEquals(1, lista.getLongitud());
        assertEquals("C", lista.obtenerUltimo());
    }


    @Test
    public void testUsuariosMaximoTenedor() {
        Usuarios us = new Usuarios(3);
        // Empiezan todos en 0, maximo tenerdor -> id=1
        assertEquals(1, us.maximoTenedor());
        us.actualizarUsuario(2, 50);
        assertEquals(2, us.maximoTenedor());
        us.actualizarUsuario(3, 100);
        assertEquals(3, us.maximoTenedor());
        us.actualizarUsuario(1, 100);
        // Empate entre saldos de 1 y 3 -> id=1
        assertEquals(1, us.maximoTenedor());
    }

    @Test
    public void testBlockchainYBloqueBasicos() {
        Blockchain bc = new Blockchain();
        Transaccion[] txs = new Transaccion[] {
            new Transaccion(1, 0, 1, 40),
            new Transaccion(2, 1, 2, 20),
            new Transaccion(3, 2, 3, 20)
        };
        bc.agregarBloque(txs);
        Bloque blk = bc.ultimoBloque();
        // Test de copia de secuencias
        Transaccion[] seq1 = blk.getSecuenciaTransacciones();
        Transaccion[] seq2 = blk.getSecuenciaTransacciones();
        assertArrayEquals(new int[]{1,2,3}, Arrays.stream(seq1).mapToInt(Transaccion::id_transaccion).toArray());
        assertNotSame(seq1, seq2);
        // txMayorValor
        Transaccion mayor = blk.txMayorValor();
        assertEquals(1, mayor.id_transaccion());
        // montoPromedio = (20+20)/2 = 20
        assertEquals(20, blk.montoPromedio());
    }
    @Test
    public void testBerretacoinHackearTx() {
        Berretacoin bc = new Berretacoin(2);
        Transaccion[] txs = new Transaccion[]{
            new Transaccion(10, 0, 2, 50)
        };
        bc.agregarBloque(txs);
        // txMayorValor es una tx de creacion
        assertEquals(10, bc.txMayorValorUltimoBloque().id_transaccion());
        // hackear: devuelve el saldo solo al vendedor
        bc.hackearTx();
        // Despues de hackear,el saldo de user2 se reduce en 50 -> user1 es el maximo tenedor
        assertEquals(1, bc.maximoTenedor());
    }

}
