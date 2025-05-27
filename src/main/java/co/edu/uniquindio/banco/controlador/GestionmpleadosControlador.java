package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.mariana.Administrador;
import co.edu.uniquindio.banco.mariana.Cajero;
import co.edu.uniquindio.banco.mariana.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GestionmpleadosControlador {
    private Empleado listarEmpleados;
    private Cajero listarCajeros;

    @FXML
    private TableView<Cajero> TablaDatosEmpleados;


    @FXML
    private Button btnCancelarGestion, btnEliminarEmpleado, btnEditarEmpleado, btnAgregarNuevoEmpleado;

    @FXML
    private TableColumn<Cajero, String> colNombre, colIdentificacion, colCorreo;

    private Administrador administradorActual;

    public void setAdministradorActual(Administrador admin) {
        this.administradorActual = admin;
        cargarTabla();
    }

    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colIdentificacion.setCellValueFactory(new PropertyValueFactory<>("cedula"));
    }

    private void cargarTabla() {
        if (administradorActual == null) return;
        List<Cajero> listaCajeros = administradorActual.listarEmpleados()
                .stream()
                .filter(Cajero.class::isInstance)
                .map(Cajero.class::cast)
                .toList();
        ObservableList<Cajero> cajeros = FXCollections.observableArrayList(listaCajeros);
        TablaDatosEmpleados.setItems(cajeros);
    }

    @FXML
    private void eliminarEmpleado() {
        Cajero seleccionado = TablaDatosEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Seleccione un empleado para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setContentText("¿Está seguro de eliminar al cliente " + seleccionado.getNombre() + "?");
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                administradorActual.eliminarEmpleado(seleccionado.getCedula());
                boolean eliminado = true; // Assuming operation success if no exception occurred
                if (eliminado) {
                    mostrarAlerta("Cajero eliminado correctamente.");
                    cargarTabla();
                } else {
                    mostrarAlerta("No se pudo eliminar el cajero.");
                }
            }
        });
    }

    @FXML
    private void editarEmpleado() {
        Cajero seleccionado = TablaDatosEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Seleccione un empleado para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditarEmpleado.fxml"));
            Parent root = loader.load();
            EditarEmpleado controller = loader.getController();
            controller.setAdministradorActual(administradorActual);
            controller.cargarCajero(seleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Empleado");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("No se pudo abrir la ventana de edición.");
        }
    }

    @FXML
    private void agregarNuevoEmpleado() {
        navegarVentana("/NuevoEmpleado.fxml", "Agregar Empleado");
    }

    @FXML
    private void cancelarGestion() {
        try {
            Stage stageActual = (Stage) btnCancelarGestion.getScene().getWindow();
            stageActual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Java/Intermedio/ventanaInicio.fxml"));
            Parent root = loader.load();

            Stage stageInicio = new Stage();
            stageInicio.setTitle("Inicio");
            stageInicio.setScene(new Scene(root));
            stageInicio.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("No se pudo cargar la ventana de inicio.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    public void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);

            // Mostrar la nueva ventana
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            mostrarAlerta("No se pudo abrir la ventana de registro.");
        }
    }
}
