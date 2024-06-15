package mx.bg.gui;



import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.bg.logica.ExcepcionDAO;
import mx.bg.logica.IMiembroDAO;
import mx.bg.logica.Miembro;
import mx.bg.logica.MiembroDAO;
/**
 * FXML Controller class
 *
 * @author kahun
 */
public class FXMLCambiarEstadoMiembroController implements Initializable {


    @FXML
    private TableView<Miembro> tblMiembro;
    @FXML
    private TableColumn<Miembro, Integer> tblcIDMiembro;
    
    @FXML
    private TableColumn<Miembro, String> tblcNombre;
    
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnInactivo;
    /**
     * Initializes the controller class.
     */
    private final IMiembroDAO MIEMBRO_DAO = new MiembroDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ArrayList<Miembro> listaMiembros = MIEMBRO_DAO.consultarTotalMiembro();
            ObservableList<Miembro> listaObservable = FXCollections.observableArrayList(listaMiembros);
            tblMiembro.setItems(listaObservable);
            tblcIDMiembro.setCellValueFactory(new PropertyValueFactory<>("idMiembro"));
            tblcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    
    
    @FXML
    private void cambiarEstado(ActionEvent event) {
        Miembro miembroSeleccionado = tblMiembro.getSelectionModel().getSelectedItem();
        if (miembroSeleccionado != null) {
            // Cambiar el estado del miembro, por ejemplo:
            miembroSeleccionado.setEstado("Inactivo");
            
            // Actualizar la base de datos
            try {
                int filasAfectadas = MIEMBRO_DAO.cambiarEstadoMiembro(miembroSeleccionado.getNombre(), "Inactivo");
                if (filasAfectadas > 0) {
                    // Actualizar la tabla o mostrar un mensaje de éxito
                    tblMiembro.refresh();
                    System.out.println("Estado actualizado correctamente.");
                }
            } catch (ExcepcionDAO e) {
                e.printStackTrace();
                // Manejar la excepción, mostrar mensaje de error, etc.
            }
        } else {
            System.out.println("No se ha seleccionado ningún miembro.");
            // Mostrar un mensaje de advertencia al usuario
        }
    }

    @FXML
    private void regresar(ActionEvent event) throws IOException {
        Node vistaAnterior = (Node) event.getSource();
        Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
        FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("/FXMLPantallaPrincipalGerente.fxml"));
        Parent vistaRaiz = cargadorVista.load();
        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();

    }
    
}
