package mx.bg.gui;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mx.bg.logica.EstadosDAO;
import mx.bg.logica.ExcepcionDAO;
import mx.bg.logica.IMembresiaDAO;
import mx.bg.logica.IMiembroDAO;
import mx.bg.logica.IUsuarioDAO;
import mx.bg.logica.Membresia;
import mx.bg.logica.MembresiaDAO;
import mx.bg.logica.MensajeAlerta;
import mx.bg.logica.Miembro;
import mx.bg.logica.MiembroDAO;
import mx.bg.logica.Usuario;
import mx.bg.logica.UsuarioDAO;

/**
 * FXML Controller class
 *
 * @author nmh14
 */
public class FXMLRegistroMiembroController implements Initializable {

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtContrasena;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDireccion;
    
    @FXML
    private TextField txtIdMembresia;
    
    @FXML
    private TableView<Membresia> tblMembresia;
    @FXML
    private TableColumn<Membresia, String> tblcNombre;
    @FXML
    private TableColumn<Membresia, String> tblcPrecio;
    @FXML
    private TextField txtApellidoPaterno;
    @FXML
    private TextField txtApellidoMaterno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar las columnas
        tblcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tblcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Obtener la lista de membresías
        ArrayList<Membresia> listaMembresias;
        try {
            listaMembresias = MEMBRESIA_DAO.consultarTotalMembresia();
            // Llenar la tabla con la lista de membresías
            tblMembresia.setItems(FXCollections.observableArrayList(listaMembresias));
        } catch (ExcepcionDAO e) {
            // Manejar la excepción si ocurre
            e.printStackTrace();
        }
    }

    private final IMembresiaDAO MEMBRESIA_DAO = new MembresiaDAO();

    private final IMiembroDAO MIEMBRO_DAO = new MiembroDAO();
    private final IUsuarioDAO USUARIO_DAO = new UsuarioDAO();


    @FXML
    void regresarPantallaAnterior(ActionEvent event) throws IOException {
        Node vistaAnterior = (Node) event.getSource();
        Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
        FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("/FXMLAcceso.fxml"));
        Parent vistaRaiz = cargadorVista.load();
        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();
    }

    private void manejadorExcepcionDAO(ExcepcionDAO ex) throws IOException {
        GeneradorDialogo.getDialogo(new MensajeAlerta(ex.getMessage(), ex.getEstado()));

    }
    
    private void manejadorValidarExcepcion(IllegalArgumentException ex) {
        FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(ex.getMessage(), EstadosDAO.ADVERTENCIA));
    }
    
    @FXML
    void seleccionaFila(MouseEvent event) {
        int idMembresia = tblMembresia.getSelectionModel().getSelectedItem().getIdMembresia();
        txtIdMembresia.setText(String.valueOf(idMembresia));
    }

    @FXML
    private void registrarMiembro(ActionEvent event) throws ExcepcionDAO, IOException {
        String nombre = txtNombre.getText();
        String apellidoPaterno = txtApellidoPaterno.getText();
        String apellidoMaterno = txtApellidoMaterno.getText();
        String contrasena = txtContrasena.getText();
        String correo = txtCorreo.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();
        String estado = "Activo";
        Miembro miembro = new Miembro();
        Usuario usuario = new Usuario();
        Membresia mebresiaSeleccionada = tblMembresia.getSelectionModel().getSelectedItem();
        if(mebresiaSeleccionada != null){
        
        try {

            miembro.setNombre(nombre);
            miembro.setApellidoPaterno(apellidoPaterno);
            miembro.setApellidoMaterno(apellidoMaterno);
            miembro.setContrasena(contrasena);
            miembro.setCorreo(correo);
            miembro.setEdad(edad);
            miembro.setTelefono(telefono);
            miembro.setDireccion(direccion);
            miembro.setIdMembresia(mebresiaSeleccionada.getIdMembresia());
            miembro.setEstado(estado);
            usuario.setTipoUsuario("Miembro");
            usuario.setCorreoUsuario(correo);
            usuario.setContrasena(contrasena);
            
        } catch (IllegalArgumentException e) {
            manejadorValidarExcepcion(e);
        }
        int filasAfectadas = 0;
        try{
            filasAfectadas = MIEMBRO_DAO.registrarMiembro(miembro);
        }catch(ExcepcionDAO ex){
            manejadorExcepcionDAO(ex);
        }
        

        if (filasAfectadas > 0) {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "Miembro registrado con exito.",
                    EstadosDAO.EXITOSO));
            
            USUARIO_DAO.registrarUsuario(usuario);
            
            txtNombre.clear();
            txtApellidoPaterno.clear();
            txtApellidoMaterno.clear();
            txtContrasena.clear();
            txtCorreo.clear();
            txtEdad.clear();
            txtTelefono.clear();
            txtDireccion.clear();
        } else {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "Error al registrar el miembro.",
                    EstadosDAO.ERROR));
        }
        }else{
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "Seleccione una membresia",
                    EstadosDAO.ERROR));
        }
    }

    
}
