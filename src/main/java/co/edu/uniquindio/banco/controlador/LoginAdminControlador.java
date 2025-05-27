package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.Model.BancoU;
import co.edu.uniquindio.banco.mariana.Cliente;
import co.edu.uniquindio.banco.modelo.entidades.Banco;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class LoginAdminControlador {


    public Button btnNuevaCuenta;
    public Button btnMostrarReporte;
    public Button btnBuscar;
    public TextField txtNuevoNumCuenta;
    public TableColumn<Cliente, String> colID;
    public TableColumn<Cliente, String> colNombres;
    public TableColumn<Cliente, String> colCorreo;
    public TextField txtSaldoInicialCuenta;
    private Banco banco;
    private BancoU bancoU;
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    public TableView tableClientes;
    @FXML
    private TextField txtbuscar;
    @FXML
    private TextField txtINgreseUsuario;

    @FXML
    private PasswordField txtIngreseContraseña;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnRegresar;
    public LoginAdminControlador() {banco = Banco.getInstancia();}

    @FXML
    void IngresarComoCajero(ActionEvent event) {

    }

    @FXML
    void RegresarAElccion(ActionEvent event) {
        System.out.println("Regresando al menú anterior...");
        // Aquí deberías cargar la ventana anterior (por ejemplo, la de selección de rol)
    }
    @FXML
    public void initialize() {
        initDataBinding();
        tablaClientes();



    }

    @FXML
    public void irbuscarc(ActionEvent event) {
        String textoBuscar = txtbuscar.getText().toLowerCase();
        bancoU.buscarCuenta(textoBuscar);
    }
    @FXML
    public void irbuscar(ActionEvent event) {

    }
    @FXML

    private void initDataBinding() {
        colID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        colNombres.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        // Usamos SimpleObjectProperty para manejar Double y Integer correctamente
    }


    public void mostrarReporte(ActionEvent actionEvent) {
        navegarVentana("/reporte.fxml", "Banco - Reporte de Clientes");
    }

    public void mostrarNuevaCuenta(ActionEvent actionEvent) {
        navegarVentana("/registrarEmpleado.fxml", "Banco - Gestion Empleados");
    }

    public void BuscarUsuario(ActionEvent actionEvent) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cerrarVentana(){
        Stage stage = (Stage) btnRegresar.getScene().getWindow();
        stage.close();
    }

    public void tablaClientes() {

            List<Cliente> listay = banco.listaClientes();
            System.out.println("Clientes encontrados: " + listay.size());

            ObservableList<Cliente> clientes = FXCollections.observableArrayList(listay);

            colID.setCellValueFactory(new PropertyValueFactory<>("cedula"));  // Usa los nombres correctos
            colNombres.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colCorreo.setCellValueFactory(new PropertyValueFactory<>("email"));

            tableClientes.setItems(clientes);

    }
}
