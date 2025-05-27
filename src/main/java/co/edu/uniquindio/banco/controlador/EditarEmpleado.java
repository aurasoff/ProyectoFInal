package co.edu.uniquindio.banco.controlador;
import co.edu.uniquindio.banco.mariana.Administrador;
import co.edu.uniquindio.banco.mariana.Cajero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class EditarEmpleado {
        @FXML private TextField txtNombreEmpleado;
        @FXML private TextField txtApellidoEmpleado;
        @FXML private TextField txtCorreoEmpleado;
        @FXML private TextField txtTelefonoEmpleado;
        @FXML private TextField txtCCEmpleado;
        @FXML private TextField txtUsuarioEmpleado;
        @FXML private PasswordField txtClaveDeEmpleado;
        @FXML private TextField txtDireccionEmpleado;
        @FXML private DatePicker dateFechaNacimientoEmpleado;
        @FXML private Button btnEditarEmpleado;
        @FXML private Button btnCancelarEdicionEmpleado;
        @FXML private ImageView imgEmpleado;
        @FXML private Button btnAdjuntarImagen;

        @Setter
        private Administrador administradorActual;
        private Cajero cajeroAEditar;

    public void cargarCajero(Cajero cajero) {
            this.cajeroAEditar = cajero;
            txtNombreEmpleado.setText(cajero.getNombre());
            txtApellidoEmpleado.setText(cajero.getNombre());
            txtCorreoEmpleado.setText(cajero.getCorreo());
            txtTelefonoEmpleado.setText(cajero.getTelefono());
            txtCCEmpleado.setText(cajero.getCedula());
            txtUsuarioEmpleado.setText(cajero.getCargo());
            txtClaveDeEmpleado.setText(cajero.getContrasena());
            txtDireccionEmpleado.setText(""); // Si tienes dirección, cámbialo
            // Puedes cargar una imagen por defecto si la tienes guardada
        }

        @FXML
        private void initialize() {
            // Opcional: configuración inicial
        }

    @FXML
    private void guardarCambiosEmpleado() {
        String nombre = txtNombreEmpleado.getText();
        String correo = txtCorreoEmpleado.getText();
        String telefono = txtTelefonoEmpleado.getText();
        String cedula = txtCCEmpleado.getText(); // identificador único
        String direccion = txtDireccionEmpleado.getText();
        String codigoEmpleado = txtUsuarioEmpleado.getText(); // suponiendo que esto representa el código
        String contrasenia = txtClaveDeEmpleado.getText();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || cedula.isEmpty() || direccion.isEmpty() || codigoEmpleado.isEmpty() || contrasenia.isEmpty()) {
            mostrarAlerta("Error", "Por favor, completa todos los campos.");
            return;
        }

        Cajero cajeroActualizado = new Cajero(
                nombre,
                cedula,
                correo,
                telefono,
                direccion,
                codigoEmpleado,
                contrasenia
        );

        administradorActual.modificarEmpleado(cedula, cajeroActualizado);

        mostrarAlerta("Éxito", "Empleado actualizado correctamente.");

        // Cierra la ventana después de guardar (opcional)
        Stage stage = (Stage) txtNombreEmpleado.getScene().getWindow();
        stage.close();
    }


    @FXML
        private void cancelarEdicion() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Java/Intermedio/gestionarEmpleados.fxml"));
                Parent root = loader.load();

                // Pasar el administrador actual a la vista administrador
                GestionmpleadosControlador controlador = loader.getController();
                controlador.setAdministradorActual(administradorActual);

                // Obtener la escena actual desde el botón
                Stage stage = (Stage) btnCancelarEdicionEmpleado.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar la vista del administrador.");
            }
        }



        private void limpiarCampos() {
            txtNombreEmpleado.clear();
            txtApellidoEmpleado.clear();
            txtCorreoEmpleado.clear();
            txtTelefonoEmpleado.clear();
            txtCCEmpleado.clear();
            txtUsuarioEmpleado.clear();
            txtClaveDeEmpleado.clear();
            txtDireccionEmpleado.clear();
            dateFechaNacimientoEmpleado.setValue(null);
        }

        private void mostrarAlerta(String titulo, String mensaje) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }

    public void editarEmpleado(ActionEvent actionEvent) {
    }
}


