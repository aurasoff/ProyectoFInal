package co.edu.uniquindio.banco.mariana;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BancoTest {

    private Banco banco;

    @BeforeEach
    public void setUp() {
        banco = Banco.getInstancia(); // Singleton
    }

    @Test
    public void testRegistrarUsuario() throws Exception {
        banco.registrarUsuario("1001", "Carlos", "Calle 123", "carlos@email.com", "1234");

        Usuario usuario = banco.buscarUsuario("1001");
        assertNotNull(usuario);
        assertEquals("Carlos", usuario.getNombre());
    }

    @Test
    public void testRegistrarUsuarioDuplicado() {
        Exception exception = assertThrows(Exception.class, () -> {
            banco.registrarUsuario("1002", "Ana", "Calle 45", "ana@email.com", "pass");
            banco.registrarUsuario("1002", "Ana", "Calle 45", "ana@email.com", "pass");
        });

        assertTrue(exception.getMessage().contains("ya existe"));
    }

    @Test
    public void testIniciarSesionCorrecto() throws Exception {
        banco.registrarUsuario("1003", "Luis", "Calle 8", "luis@email.com", "admin");

        Usuario usuario = banco.IniciarSesion("1003", "admin");
        assertNotNull(usuario);
        assertEquals("Luis", usuario.getNombre());
    }

    @Test
    public void testIniciarSesionIncorrecto() throws Exception {
        banco.registrarUsuario("1004", "Paula", "Cra 7", "paula@email.com", "clave");

        Usuario usuario = banco.IniciarSesion("1004", "claveIncorrecta");
        assertNull(usuario);
    }


    @Test
    public void testRecargarBilletera() throws Exception {
        banco.registrarUsuario("1005", "Mario", "Avenida 10", "mario@email.com", "secret");

        BilleteraVirtual billetera = banco.buscarBilleteraUsuario("1005");
        assertNotNull(billetera);

        float saldoAntes = billetera.consultarSaldo();
        banco.recargarBilletera(billetera.getNumero(), 50000);

        float saldoDespues = billetera.consultarSaldo();
        assertTrue(saldoDespues > saldoAntes);
    }

    @Test
    public void testRealizarTransferencia() throws Exception {
        banco.registrarUsuario("1006", "Juan", "Calle A", "juan@email.com", "1234");
        banco.registrarUsuario("1007", "Laura", "Calle B", "laura@email.com", "5678");

        BilleteraVirtual origen = banco.buscarBilleteraUsuario("1006");
        BilleteraVirtual destino = banco.buscarBilleteraUsuario("1007");

        banco.recargarBilletera(origen.getNumero(), 20000);
        banco.realizarTransferencia(origen.getNumero(), destino.getNumero(), 10000, Categoria.ALIMENTOS);

        List<Transaccion> transaccionesOrigen = banco.obtenerTransacciones(origen.getNumero());
        List<Transaccion> transaccionesDestino = banco.obtenerTransacciones(destino.getNumero());

        assertFalse(transaccionesOrigen.isEmpty());
        assertFalse(transaccionesDestino.isEmpty());
    }
}
