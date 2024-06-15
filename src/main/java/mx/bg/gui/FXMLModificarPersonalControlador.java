package mx.bg.gui;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import mx.bg.gui.App;
import mx.bg.gui.FXMLGeneradorDialogo;
import mx.bg.logica.EstadosDAO;
import mx.bg.logica.ExcepcionDAO;
import mx.bg.logica.MensajeAlerta;
import mx.bg.logica.Personal;
import mx.bg.logica.PersonalDAO;

/**
 * FXML Controller class
 *
 * @author CARA
 */
public class FXMLModificarPersonalControlador implements Initializable {

    @FXML
    TextField nombreTextField;   
    @FXML
    TextField paternoTextField;
    @FXML
    TextField maternoTextField;
    @FXML
    DatePicker fechaNacimientoDatePicker;
    @FXML
    TextField correoTextField;
    @FXML
    TextField telefonoTextField;
    
    @FXML
    Label errorNombre;
    @FXML
    Label errorPaterno;
    @FXML
    Label errorMaterno;
    @FXML
    Label errorCorreo;
    @FXML
    Label errorTelefono;
    @FXML
    Label errorNacimiento;
    
    
    @FXML
    Button cancelarButton;
    
    public int idPersonalObtenido;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaNacimientoDatePicker.setDayCellFactory(getDayCellFactory());
        
        
        errorNombre.setVisible(false);
        errorPaterno.setVisible(false);
        errorMaterno.setVisible(false);
        errorCorreo.setVisible(false);
        errorTelefono.setVisible(false);
        errorNacimiento.setVisible(false);
    }    
    
    public void inicializarPersonal(Personal personal) {
    
        nombreTextField.setText(personal.getNombre());
        paternoTextField.setText(personal.getApellidoPaterno());
        maternoTextField.setText(personal.getApellidoMaterno());
        
        if (personal.getFechaNacimiento() != null && !personal.getFechaNacimiento().isEmpty()) {
            LocalDate fechaNacimiento = LocalDate.parse(personal.getFechaNacimiento());
            fechaNacimientoDatePicker.setValue(fechaNacimiento);
        }
        
        correoTextField.setText(personal.getCorreo());
        telefonoTextField.setText(personal.getTelefono());
        
        idPersonalObtenido = personal.getIdPersonal();
    }
    
    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Color para fechas deshabilitadas
                        }
                    }
                };
            }
        };
    }
    
    @FXML
    private void aceptarActualizacion() throws IOException {
        if (estaConfirmadoParaActualizar()) {
            errorNombre.setVisible(false);
            errorPaterno.setVisible(false);
            errorMaterno.setVisible(false);
            errorCorreo.setVisible(false);
            errorTelefono.setVisible(false);
            errorNacimiento.setVisible(false);
            
            String nombre = nombreTextField.getText();
            String paterno = paternoTextField.getText();
            String materno = maternoTextField.getText();
            
            LocalDate fechaNacimiento = fechaNacimientoDatePicker.getValue();
            
            String correo = correoTextField.getText();
            String telefono = telefonoTextField.getText();
            
            
            Personal nuevoPersonal = new Personal();
            
            try {
                nuevoPersonal.setIdPersonal(idPersonalObtenido);
                nuevoPersonal.setNombre(nombre);
                nuevoPersonal.setApellidoPaterno(paterno);
                nuevoPersonal.setApellidoMaterno(materno);

                if (fechaNacimiento != null) {
                    String fechaNacimientoFormatted = fechaNacimiento.toString();
                    nuevoPersonal.setFechaNacimiento(fechaNacimientoFormatted);
                }

                nuevoPersonal.setCorreo(correo);
                nuevoPersonal.setTelefono(telefono);

                PersonalDAO personalDAO = new PersonalDAO();

                
                if (personalDAO.verificaCorreoActualizacion(nuevoPersonal)) {
                    FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                            "El correo ya están registrados con otro personal",
                            EstadosDAO.ADVERTENCIA));
                } else if(personalDAO.verificaTelefonoActualizacion(nuevoPersonal)){
                    FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                            "El telefono ya están registrados con otro personal",
                            EstadosDAO.ADVERTENCIA));
                } else {
                    int idPersonal = personalDAO.actualizarPersonal(nuevoPersonal);
                    if (idPersonal > 0) {
                        confirmacionActualizacion();
                    }
                }
            } catch (IllegalArgumentException ex ) {
                manejadorValidacionExcepciones(ex);
            } catch (ExcepcionDAO  ex ) {
                manejadorExcepcionDAO(ex);
            }
            
        }
    }
    
    private void manejadorValidacionExcepciones(IllegalArgumentException ex) throws IOException {
        switch (ex.getMessage()) {
            case "El nombre tiene un formato incorrecto":
                errorNombre.setVisible(true);
                break;
            case "El apellido paterno tiene un formato incorrecto":
                errorPaterno.setVisible(true);
                break;
            case "El apellido materno tiene un formato incorrecto":
                errorMaterno.setVisible(true);
                break;
            case "La fecha de nacimiento tiene un formato incorrecto o un valor irreal":
                errorNacimiento.setVisible(true);
                break;
            case "El correo tiene un formato incorrecto":
                errorCorreo.setVisible(true);
                break;
            case "El telefono tiene un formato incorrecto":
                errorTelefono.setVisible(true);
                break;
            default:
                break;
        }
        FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                ex.getMessage(),
                EstadosDAO.ADVERTENCIA));
    }
    
    private void manejadorExcepcionDAO(ExcepcionDAO ex) throws IOException {
        
        FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                ex.getMessage(),
                ex.getEstado()));
        switch (ex.getEstado()) {
            case ERROR:
            case FATAL:
                App.setRoot("/FXMLAcceso");
                break;
        }
    }
    
    private void confirmacionActualizacion(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("Personal actualizado exitosamente.");
        alert.showAndWait();
        
    }
    
    @FXML
    private void cancelarActualizacion(ActionEvent event) throws IOException {
        if (estaConfirmadoParaCancelar()) {
            Node vistaAnterior = (Node) event.getSource();
            Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
            FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("/FXMLConsultarPersonal.fxml"));
            Parent vistaRaiz = cargadorVista.load();
            vistaActual.setScene(new Scene(vistaRaiz));
            vistaActual.show();
        }
    }

    private boolean estaConfirmadoParaActualizar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Realmente deseas actualizar el personal?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
    private boolean estaConfirmadoParaCancelar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Realmente deseas cancelar la actualizacion?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
}
