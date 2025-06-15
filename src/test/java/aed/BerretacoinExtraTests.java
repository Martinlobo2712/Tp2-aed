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
    @Test
    public void testTransaccionesConMontoCero() {
    Berretacoin bc = new Berretacoin(5);
    Transaccion[] transacciones = {
        new Transaccion(1, 1, 2, 0),
        new Transaccion(2, 3, 4, 0),
        new Transaccion(3, 0, 5, 0) // Minero recibe 0
    };
    
    bc.agregarBloque(transacciones);
    
    // Verificar que el monto promedio es 0
    assertEquals(0, bc.montoMedioUltimoBloque());
    
    // Verificar que el máximo tenedor sigue siendo 1 (todos con saldo 0)
    assertEquals(1, bc.maximoTenedor());
}

   @Test
    public void testTxMayorValor() {
    Transaccion[] txs = {
        new Transaccion(1, 0, 1, 100),
        new Transaccion(2, 1, 2, 200),
        new Transaccion(3, 2, 3, 150)
    };
    
    Bloque bloque = new Bloque(txs);
    
    assertEquals(200, bloque.txMayorValor().monto());
    assertEquals(2, bloque.txMayorValor().id_transaccion());
}

    @Test
    public void testHackearTx() {
    Transaccion txEspecial = new Transaccion(999, 1, 2, 500);
    Transaccion[] txs = {
        new Transaccion(1, 0, 1, 100),
        txEspecial,
        new Transaccion(2, 2, 3, 200)
    };
    
    Bloque bloque = new Bloque(txs);
    
    Transaccion hackeada = bloque.hackearTx();
    
    assertEquals(500, hackeada.monto());
    assertEquals(999, hackeada.id_transaccion());
    assertEquals(2, bloque.getSecuenciaTransacciones().length);
    assertEquals(200, bloque.txMayorValor().monto());
}
    @Test
    public void testMontoPromedio() {
    Transaccion[] txs = {
        new Transaccion(1, 0, 1, 100), // Transacción de creación (no cuenta)
        new Transaccion(2, 1, 2, 200),
        new Transaccion(3, 2, 3, 300),
        new Transaccion(4, 3, 4, 400)
    };
    
    Bloque bloque = new Bloque(txs);
    
    // Promedio de 200+300+400 = 900 / 3 = 300
    assertEquals(300, bloque.montoPromedio());
    
    bloque.hackearTx(); // Elimina la de 400
    // Nuevo promedio: 200+300 = 500 / 2 = 250
    assertEquals(250, bloque.montoPromedio());
}

    @Test
    public void testMontoMedioBloqueMixto() {
    Berretacoin bc = new Berretacoin(10);
    Transaccion[] txs = {
        new Transaccion(1, 0, 1, 1000), // Creación
        new Transaccion(2, 1, 2, 100),  // Normal
        new Transaccion(3, 2, 3, 200),  // Normal
        new Transaccion(4, 0, 4, 500)   // Creación
    };
    
    bc.agregarBloque(txs);
    
    // Promedio de solo las normales (100 + 200)/2
    assertEquals(150, bc.montoMedioUltimoBloque());
}

    @Test
    public void testMontoMedioPostHackeo() {
    Berretacoin bc = new Berretacoin(10);
    Transaccion[] txs = {
        new Transaccion(1, 1, 2, 100),
        new Transaccion(2, 3, 4, 200),
        new Transaccion(3, 5, 6, 300)
    };
    
    bc.agregarBloque(txs);
    
    // Promedio inicial (100+200+300)/3 = 200
    assertEquals(200, bc.montoMedioUltimoBloque());
    
    // Hackear la transacción de mayor valor (300)
    bc.hackearTx();
    
    // Nuevo promedio (100+200)/2 = 150
    assertEquals(150, bc.montoMedioUltimoBloque());
}
    @Test
    public void testPrecisionPromedio() {
    Berretacoin bc = new Berretacoin(5);
    Transaccion[] txs = {
        new Transaccion(1, 1, 2, 101),
        new Transaccion(2, 2, 3, 102),
        new Transaccion(3, 3, 4, 103)
    };
    
    bc.agregarBloque(txs);
    
    // Promedio debería ser 102 (truncado, no redondeado)
    assertEquals(102, bc.montoMedioUltimoBloque());
}
    @Test
    public void testTxUltimoBloqueCopiaDefensiva() {
    Berretacoin bc = new Berretacoin(5);
    Transaccion tx = new Transaccion(1, 1, 2, 50);
    bc.agregarBloque(new Transaccion[]{tx});
    
    Transaccion[] result = bc.txUltimoBloque();
    result[0] = new Transaccion(99, 0, 0, 0); // Modificar la copia
    
    // Verificar que el original no cambió
    assertEquals(1, bc.txUltimoBloque()[0].id_transaccion());
}


    @Test
    public void testTxUltimoBloqueMultiplesBloques() {
    Berretacoin bc = new Berretacoin(10);
    bc.agregarBloque(new Transaccion[]{
        new Transaccion(1, 1, 2, 100)
    });
    bc.agregarBloque(new Transaccion[]{
        new Transaccion(2, 2, 3, 200),
        new Transaccion(3, 3, 4, 300)
    });
    
    Transaccion[] result = bc.txUltimoBloque();
    assertEquals(2, result.length);
    assertEquals(2, result[0].id_transaccion());
    assertEquals(3, result[1].id_transaccion());
}

    @Test
    public void testTxUltimoBloqueConCreaciones() {
    Berretacoin bc = new Berretacoin(5);
    bc.agregarBloque(new Transaccion[]{
        new Transaccion(1, 0, 1, 1000),
        new Transaccion(2, 0, 2, 2000)
    });
    
    Transaccion[] result = bc.txUltimoBloque();
    assertEquals(2, result.length);
    assertEquals(0, result[0].id_comprador());
    assertEquals(0, result[1].id_comprador());
}

    @Test
    public void testMaximoTenedorMultiplesTransacciones() {
    Berretacoin bc = new Berretacoin(10);
    bc.agregarBloque(new Transaccion[]{
        new Transaccion(1, 0, 1, 100),
        new Transaccion(2, 1, 2, 50),  // Usuario 1: 100-50=50, Usuario 2: 50
        new Transaccion(3, 2, 3, 25), // Usuario 2: 50-25=25, Usuario 3: 25
        new Transaccion(4, 0, 4, 60)  // Usuario 4: 60
    });
    
    // Usuario 1: 50, Usuario 2: 25, Usuario 3: 25, Usuario 4: 60
    assertEquals(4, bc.maximoTenedor());
}
    @Test
    public void testMaximoTenedorUsuariosSinTransacciones() {
    Berretacoin bc = new Berretacoin(100);
    bc.agregarBloque(new Transaccion[]{
        new Transaccion(1, 0, 99, 10) // Solo usuario 99 recibe fondos
    });
    
    assertEquals(99, bc.maximoTenedor());
}
    @Test
    public void testMaximoTenedorConActualizacion() {
    Berretacoin bc = new Berretacoin(5);
    bc.agregarBloque(new Transaccion[]{
        new Transaccion(1, 0, 1, 100),
        new Transaccion(2, 0, 2, 200)
    });
    
    assertEquals(2, bc.maximoTenedor()); // Usuario 2 con 200
    
    // Nueva transacción que hace que usuario 1 supere a usuario 2
    bc.agregarBloque(new Transaccion[]{
        new Transaccion(3, 0, 1, 150) // Usuario 1 ahora tiene 250
    });
    
    assertEquals(1, bc.maximoTenedor());
}
    @Test
    public void testTxMayorValorMultiplesBloques() {
    Berretacoin bc = new Berretacoin(10);
    
    // Bloque 1
    bc.agregarBloque(new Transaccion[]{
        new Transaccion(1, 1, 2, 1000)
    });
    
    // Bloque 2
    bc.agregarBloque(new Transaccion[]{
        new Transaccion(2, 3, 4, 500),
        new Transaccion(3, 5, 6, 1500) // Mayor valor
    });
    
    Transaccion result = bc.txMayorValorUltimoBloque();
    assertEquals(3, result.id_transaccion());
    assertEquals(1500, result.monto());
}
    @Test
    public void testTxMayorValorEmpateMontos() {
    Berretacoin bc = new Berretacoin(10);
    Transaccion[] txs = {
        new Transaccion(1, 1, 2, 200),
        new Transaccion(2, 3, 4, 200), // Mismo monto, mayor id
        new Transaccion(3, 5, 6, 100)
    };
    bc.agregarBloque(txs);
    
    Transaccion result = bc.txMayorValorUltimoBloque();
    assertEquals(2, result.id_transaccion()); // Debería devolver la de mayor id en empate
}
    @Test
    public void testTxMayorValorMontoCero() {
    Berretacoin bc = new Berretacoin(5);
    Transaccion[] txs = {
        new Transaccion(1, 1, 2, 0),
        new Transaccion(2, 3, 4, 0)
    };
    bc.agregarBloque(txs);
    
    Transaccion result = bc.txMayorValorUltimoBloque();
    assertEquals(0, result.monto());
    assertEquals(2, result.id_transaccion()); // En empate, mayor id
}

    @Test
    public void testTxMayorValorTransaccionASiMismo() {
    Berretacoin bc = new Berretacoin(5);
    Transaccion[] txs = {
        new Transaccion(1, 1, 1, 1000), // Transacción a sí mismo
        new Transaccion(2, 2, 3, 500)
    };
    bc.agregarBloque(txs);
    
    Transaccion result = bc.txMayorValorUltimoBloque();
    assertEquals(1, result.id_transaccion());
    assertEquals(1000, result.monto());
}
    /*
    @Test
    public void testMaximoTenedorPostHackeo() {
    Berretacoin bc = new Berretacoin(10);
    bc.agregarBloque(new Transaccion[]{
        new Transaccion(1, 0, 1, 1000),
        new Transaccion(2, 1, 2, 500)
    });
    
    assertEquals(2, bc.maximoTenedor()); // Usuario 2 tiene 500
    
    bc.hackearTx(); // Revierte la transacción de 500
    
    assertEquals(1, bc.maximoTenedor()); // Usuario 1 vuelve a tener 1000
}*/


    
}
