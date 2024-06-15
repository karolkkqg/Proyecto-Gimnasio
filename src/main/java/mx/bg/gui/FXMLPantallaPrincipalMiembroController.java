/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.bg.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.bg.logica.EstadosDAO;
import mx.bg.logica.ExcepcionDAO;
import mx.bg.logica.MensajeAlerta;
import mx.bg.logica.Usuario;

/**
 * FXML Controller class
 *
 * @author nmh14
 */
public class FXMLPantallaPrincipalMiembroController implements Initializable {

    String correoInicioSesion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    void actualizarDatos(ActionEvent event) throws IOException, SQLException {
        Node vistaAnterior = (Node) event.getSource();
        Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
        FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("/FXMLActualizarMiembro.fxml"));
        Parent vistaRaiz = cargadorVista.load();
        FXMLActualizarMiembroController fXMLActualizarMiembroController = cargadorVista.getController();
        fXMLActualizarMiembroController.getCorreoParaActualizar(correoInicioSesion);
        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();
    }
    
    
    public void getCorreoInicio (String correo){
        correoInicioSesion = correo;
    }

    @FXML
    private void salirPantallaAcceso(ActionEvent event) throws IOException {
        Usuario.limpiarSesion();
        Node vistaAnterior = (Node) event.getSource();
        Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
        FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("/FXMLAcceso.fxml"));
        Parent vistaRaiz = cargadorVista.load();
        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();
    }
}
