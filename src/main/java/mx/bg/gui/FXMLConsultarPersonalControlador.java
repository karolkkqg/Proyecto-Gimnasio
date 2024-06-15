/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.bg.gui;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import mx.bg.logica.Personal;
import mx.bg.logica.PersonalDAO;

/**
 * FXML Controller class
 *
 * @author CARA
 */
public class FXMLConsultarPersonalControlador implements Initializable {

    @FXML
    private TableView<Personal> tablaPersonal;

    @FXML
    private TableColumn<Personal, String> columnaId;
    
    @FXML
    private TableColumn<Personal, String> columnaNombre;
    
    @FXML
    private TableColumn<Personal, String> columnaCorreo;

    @FXML
    private TableColumn<Personal, Void> columnaAcciones;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarColumnas();
        try {
            cargarDatos();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLConsultarPersonalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void inicializarColumnas() {
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idPersonal"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        TableColumn<Personal, Void> columnaBotones = new TableColumn<>("Acciones");

        columnaBotones.setCellFactory(param -> new TableCell<>() {
            private final Button botonConsultar = new Button("Consultar");

            {
                botonConsultar.setOnAction(event -> {
                    Personal personal = getTableView().getItems().get(getIndex());
                    abrirVentanaConsulta(personal);
                    
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(botonConsultar));
                }
            }
        });

        tablaPersonal.getColumns().add(columnaBotones);
        tablaPersonal.refresh();
    }


    private void cargarDatos() throws SQLException {
        PersonalDAO personalDAO = new PersonalDAO();
        List<Personal> listaPersonal = personalDAO.consultaTotalPersonal();
        tablaPersonal.getItems().clear();
        List<Personal> datosAMostrar = new ArrayList<>();

        for (Personal personal : listaPersonal) {
            Personal personalParaMostrar = new Personal();
            personalParaMostrar.setIdPersonal(personal.getIdPersonal());
            personalParaMostrar.setNombre(personal.getNombre());
            personalParaMostrar.setCorreo(personal.getCorreo());

            datosAMostrar.add(personalParaMostrar);
        }
        
        
        tablaPersonal.getItems().addAll(datosAMostrar);
    }

    
    private void abrirVentanaConsulta(Personal personal) {
        try {
            int idPersonal = personal.getIdPersonal();

            PersonalDAO personalDAO = new PersonalDAO();
            List<Personal> listaPersonal = personalDAO.consultarPersonalPorId(idPersonal);

            Personal personalCompleto = listaPersonal.get(0); 

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLPersonalConsultado.fxml"));
            Parent root = loader.load();
            FXMLPersonalConsultadoControlador controller = loader.getController();
            controller.mostrarPersonal(personalCompleto); 

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
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
