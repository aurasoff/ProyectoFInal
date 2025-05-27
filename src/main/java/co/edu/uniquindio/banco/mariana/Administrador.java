package co.edu.uniquindio.banco.mariana;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Administrador extends Persona {
    private String idAdministrador;
    private List<Empleado> empleados;
    private List<Reporte> reportesGenerados;
    private Banco banco;
    private Empleado empleado;
    private Cajero cajero;
    private Empleado listarEmpleados;

    public Administrador(String nombre, String cedula, String correo, String telefono, String direccion, String idAdministrador) {
        super(nombre, cedula, correo, telefono, direccion);
        this.idAdministrador = idAdministrador;
        this.empleados = new ArrayList<>();
        this.reportesGenerados = new ArrayList<>();
    }

    // Gestión de empleados
    public void registrarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    public void eliminarEmpleado(String cedula) {
        empleados.removeIf(e -> e.getCedula().equals(cedula));
    }

    public void modificarEmpleado(String cedula, Empleado datosActualizados) {
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getCedula().equals(cedula)) {
                empleados.set(i, datosActualizados);
                break;
            }
        }
    }

    public boolean actualizarEmpleado(String cedula, String nuevoCorreo, String nuevoTelefono, String nuevaDireccion) {
        Empleado emp = cajero.obtenerEmpleadoPorCedula(cedula);
        if (emp != null) {
            emp.setCorreo(nuevoCorreo);
            emp.setTelefono(nuevoTelefono);
            emp.setDireccion(nuevaDireccion);
            return true;
        }
        return false;
    }

    // Seguridad y autenticación (simulada)
    public boolean autenticar(String usuario, String contraseña) {
        // Aquí puedes usar un mapa o base de datos real
        return usuario.equals("admin") && contraseña.equals("1234");
    }

    // Monitoreo de transacciones (simulado)
    public void monitorearTransacciones(List<Transaccion> transacciones) {
        System.out.println("Transacciones sospechosas:");
        for (Transaccion t : transacciones) {
            if (t.getMonto() > 10000) {
                System.out.println("⚠️ " + t);
            }
        }
    }

    // Generación de reportes
    public Reporte generarReporte(String tipo, String contenido, String titulo) {
        Reporte nuevo = new Reporte(tipo, contenido, this, titulo);
        reportesGenerados.add(nuevo);
        return nuevo;
    }


    public String getIdAdministrador() {
        return idAdministrador;
    }

    public List<Empleado> listarEmpleados() {
        return empleados;
    }

    public List<Reporte> getReportesGenerados() {
        return reportesGenerados;
    }


    public String almacenarCajero(String nombre, String apellido, String correo, String telefono, String cc, String usuario, String contrasenia) {
        // Validar si ya existe un empleado con esa cédula
        for (Empleado e : empleados) {
            if (e.getCedula().equals(cedula)) {

            }
        }

        return "Ya existe un empleado registrado con esa cédula.";    }
}



