package mx.bg.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mx.bg.logica.EstadosDAO;
import mx.bg.logica.ExcepcionDAO;
import mx.bg.logica.IMembresiaDAO;
import mx.bg.logica.Membresia;
import mx.bg.logica.MembresiaDAO;
import mx.bg.logica.MensajeAlerta;

/**
 * FXML Controller class
 *
 * @author nmh14
 */
public class FXMLConsultarMembresiaController implements Initializable {

    @FXML
    private TableView<Membresia> tableView;

    @FXML
    private TableColumn<Membresia, String> tblcNombre;

    @FXML
    private TableColumn<Membresia, String> tblcAcceso;

    @FXML
    private TableColumn<Membresia, String> tblcClases;

    @FXML
    private TableColumn<Membresia, String> tblcEstacionamiento;

    @FXML
    private TableColumn<Membresia, String> tblcInternet;

    @FXML
    private TableColumn<Membresia, String> tblcSpa;

    @FXML
    private TextField txtPrecio;

    private final IMembresiaDAO MEMBRESIA_DAO = new MembresiaDAO();

    @FXML
    void actualizarMembresia(ActionEvent event) throws IOException, ExcepcionDAO {
        Node vistaAnterior = (Node) event.getSource();
            Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
            FXMLLoader cargarVista = new FXMLLoader(getClass().getResource("/FXMLModificarMembresia.fxml"));
            Parent vistaRaiz = cargarVista.load();
            FXMLModificarMembresiaController fXMLModificarMembresiaControllerInstancia = cargarVista.getController();
            Membresia membresiaSeleccionada = tableView.getSelectionModel().getSelectedItem();
            fXMLModificarMembresiaControllerInstancia.getIdMembresia(membresiaSeleccionada.getIdMembresia());
            vistaActual.setScene(new Scene(vistaRaiz));
            vistaActual.show();
    }

    @FXML
    void eliminarMembresia(ActionEvent event) throws IOException{
        Membresia selectedMembresia = tableView.getSelectionModel().getSelectedItem();
        if (selectedMembresia != null) {
            int idMembresia = selectedMembresia.getIdMembresia();
            int filasAfectadas;
            try {
                filasAfectadas = MEMBRESIA_DAO.eliminarMembresia(idMembresia);
                if (filasAfectadas > 0) {
                    tableView.getItems().remove(selectedMembresia);
                }
            } catch (ExcepcionDAO ex) {
                manejadorExcepcionDAO(ex);
            }

        }
    }

    @FXML
    void regresarPantallaAnterior(ActionEvent event) throws IOException {
        Node vistaAnterior = (Node) event.getSource();
        Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
        FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("/FXMLPantallaPrincipalGerente.fxml"));
        Parent vistaRaiz = cargadorVista.load();
        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        tblcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tblcAcceso.setCellValueFactory(new PropertyValueFactory<>("caracteristicaAccesoIlimitado"));
        tblcClases.setCellValueFactory(new PropertyValueFactory<>("caracteristicaClaseGrupal"));
        tblcEstacionamiento.setCellValueFactory(new PropertyValueFactory<>("caracteristicaEstacionamiento"));
        tblcInternet.setCellValueFactory(new PropertyValueFactory<>("caracteristicaInternet"));
        tblcSpa.setCellValueFactory(new PropertyValueFactory<>("caracteristicaSpa"));
        

        try {
            ArrayList<Membresia> membresias = MEMBRESIA_DAO.consultarTotalMembresia();
            ObservableList<Membresia> membresiaData = FXCollections.observableArrayList(membresias);
            tableView.setItems(membresiaData);
        } catch (ExcepcionDAO e) {
            try {
                manejadorExcepcionDAO(e);
            } catch (IOException ex) {
                Logger.getLogger(FXMLConsultarMembresiaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void manejadorExcepcionDAO(ExcepcionDAO ex) throws IOException {
        GeneradorDialogo.getDialogo(new MensajeAlerta(ex.getMessage(), ex.getEstado()));

    }
    
    @FXML
    void llenartxt(MouseEvent event) {
     String precio = tableView.getSelectionModel().getSelectedItem().getPrecio();
     txtPrecio.setText(precio);
    }

}
