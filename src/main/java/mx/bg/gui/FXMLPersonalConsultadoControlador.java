/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.bg.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.bg.logica.Personal;
import mx.bg.logica.PersonalDAO;

/**
 * FXML Controller class
 *
 * @author CARA
 */
public class FXMLPersonalConsultadoControlador implements Initializable {

    @FXML
    private Label idLabel;
    
    @FXML
    private Label nombreLabel;
    
    @FXML
    private Label paternoLabel;
    
    @FXML
    private Label maternoLabel;
    
    @FXML
    private Label nacimientoLabel;
    
    @FXML
    private Label correoLabel;
    
    @FXML
    private Label telefonoLabel;
    
    public int ID_PERSONAL_OBTENIDO;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void mostrarPersonal (Personal personal) {
        ID_PERSONAL_OBTENIDO = personal.getIdPersonal();
        
        idLabel.setText("ID: " + personal.getIdPersonal());
        nombreLabel.setText("Nombre: " + personal.getNombre());
        paternoLabel.setText("Apellido paterno: "+ personal.getApellidoPaterno());
        maternoLabel.setText("Apellido materno: "+ personal.getApellidoMaterno());
        nacimientoLabel.setText("Fecha de nacimiento: " + personal.getFechaNacimiento());
        correoLabel.setText("Correo: " + personal.getCorreo());
        telefonoLabel.setText("Telefono: " + personal.getTelefono());
    }
    
    @FXML
    private void eliminarPersonal(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("¿Estás seguro de que deseas eliminar este personal?");
        alert.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                PersonalDAO personalDAO = new PersonalDAO();
                personalDAO.archivarPersonalPorId(ID_PERSONAL_OBTENIDO);
                
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
                App.setRoot("/FXMLConsultarPersonal");
            } catch (SQLException | IOException ex) {
                Logger.getLogger(FXMLPersonalConsultadoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void abrirVentanaActualizacion(ActionEvent event) {
        try {
            PersonalDAO personalDAO = new PersonalDAO();
            List<Personal> listaPersonal = personalDAO.consultarPersonalPorId(ID_PERSONAL_OBTENIDO);

            Personal personalCompleto = listaPersonal.get(0); 

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLModificarPersonal.fxml"));
            Parent root = loader.load();
            FXMLModificarPersonalControlador controller = loader.getController();
            controller.inicializarPersonal(personalCompleto); 

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void regresar(ActionEvent event) throws IOException {
        Node vistaAnterior = (Node) event.getSource();
        Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
        FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("/FXMLConsultarPersonal.fxml"));
        Parent vistaRaiz = cargadorVista.load();
        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();
    }
}
