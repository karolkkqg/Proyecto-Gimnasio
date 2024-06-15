/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.bg.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import mx.bg.logica.EstadosDAO;
import mx.bg.logica.ExcepcionDAO;
import mx.bg.logica.GeneradorIdDAO;
import mx.bg.logica.IPersonalDAO;
import mx.bg.logica.MensajeAlerta;
import mx.bg.logica.Personal;
import mx.bg.logica.PersonalDAO;

/**
 * FXML Controller class
 *
 * @author CARA
 */
public class FXMLRegistroPersonalControlador implements Initializable {

    @FXML
    TextField nombreTextField;
    @FXML
    TextField paternoTextField;
    @FXML
    TextField maternoTextField;
    @FXML
    TextField correoTextField;
    @FXML
    TextField telefonoTextField;
    @FXML
    DatePicker fechaNacimientoDatePicker;
    
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
    
    static GeneradorIdDAO ID_PERSONAL = new GeneradorIdDAO();
    
    private final IPersonalDAO PERSONAL_DAO = new PersonalDAO();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaNacimientoDatePicker.setDayCellFactory(getDayCellFactory());
        
        fechaNacimientoDatePicker.setValue(LocalDate.now());
        
        errorNombre.setVisible(false);
        errorPaterno.setVisible(false);
        errorMaterno.setVisible(false);
        errorCorreo.setVisible(false);
        errorTelefono.setVisible(false);
        errorNacimiento.setVisible(false);
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
    private void confirmarRegistroPersonal(ActionEvent event) throws IOException {
        errorNombre.setVisible(false);
        errorPaterno.setVisible(false);
        errorMaterno.setVisible(false);
        errorCorreo.setVisible(false);
        errorTelefono.setVisible(false);
        errorNacimiento.setVisible(false);
        try {
           int idPersonal = generarIDPersonal();
           Personal personal = getInformacionPersonal();
           personal.setIdPersonal(idPersonal);
           invocarRegistroPersonal(personal);
          
        } catch (IllegalArgumentException ex) {
            manejadorValidacionExcepciones(ex);
        } catch (ExcepcionDAO ex) {
            manejadorExcepcionDAO(ex);
        }
    }   
    
    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        if (confirmacionCancelacion()) {
            Node vistaAnterior = (Node) event.getSource();
        Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
        FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("/FXMLPantallaPrincipalGerente.fxml"));
        Parent vistaRaiz = cargadorVista.load();
        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();
        }
    }
    
    @FXML
    private Personal getInformacionPersonal() {
        Personal personal = new Personal();
        personal.setNombre(nombreTextField.getText());
        personal.setApellidoPaterno(paternoTextField.getText());
        personal.setApellidoMaterno(maternoTextField.getText());
        
        LocalDate fechaNacimiento = fechaNacimientoDatePicker.getValue();
        if (fechaNacimiento != null) {
            personal.setFechaNacimiento(fechaNacimiento.toString());
        }
        
        personal.setCorreo(correoTextField.getText());
        personal.setTelefono(telefonoTextField.getText());
        return personal;
    }
    
    private void invocarRegistroPersonal(Personal personal) throws IOException, ExcepcionDAO {
        if (PERSONAL_DAO.verificarCorreo(personal)) {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "Ya existe el mismo correo registrado",
                    EstadosDAO.ADVERTENCIA));
        } else if(PERSONAL_DAO.verificarTelefono(personal)){
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "Ya existe el mismo numero de telefono registrado",
                    EstadosDAO.ADVERTENCIA));
        } else {    
            int personalRegistrado = PERSONAL_DAO.registrarPersonal(personal);
            if (personalRegistrado > 0) {
                FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                        "Se ha registrado con éxito",
                        EstadosDAO.EXITOSO));
                App.cambioDeVista("/FXMLPantallaPrincipalGerente");
            }
        }
    }
    
    private int generarIDPersonal(){
        int idGenerado= -1;
        try {
            idGenerado = ID_PERSONAL.obtenerNuevoIdPersonal();
        } catch (SQLException ex) {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "No fue posible generar el id",
                    EstadosDAO.ADVERTENCIA));
        }
        return idGenerado;
    }
    
    private void manejadorExcepcionDAO(ExcepcionDAO ex) throws IOException {
        
        FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                ex.getMessage(),
                ex.getEstado()));
        switch (ex.getEstado()) {
            case ERROR:
            case FATAL:
                App.setRoot("/gym/vistas/FXMLAcceso");
                break;
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

    private boolean confirmacionCancelacion() {
        Optional<ButtonType> response = FXMLGeneradorDialogo.getConfirmacionDialogo(
                "¿Deseas salir del registro?");
        return (response.get() == FXMLGeneradorDialogo.BOTON_SI);
    }
    
}
