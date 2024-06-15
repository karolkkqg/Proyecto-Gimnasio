package mx.bg.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
public class FXMLActualizarMiembroController implements Initializable {

    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TableView<Membresia> tblMembresia;
    @FXML
    private TableColumn<Membresia, String> tblcNombre;
    @FXML
    private TableColumn<Membresia, String> tblcPrecio;
    @FXML
    private TextField txtIdMembresia;

    
    ArrayList<Miembro> listaMiembro = new ArrayList<>();
    @FXML
    private TextField txtApellidoPaterno;
    @FXML
    private TextField txtApellidoMaterno;
    @FXML
    private PasswordField pwdContrasena;
    
    int idMiembro = 0;
    @FXML
    private Button btnRegresar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tblcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        
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
    String correoMiembro;
    String correoRegreso;

    

    @FXML
    void regresarPantallaAnterior(ActionEvent event) throws IOException {
        Node vistaAnterior = (Node) event.getSource();
        Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
        FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("/FXMLPantallaPrincipalMiembro.fxml"));
        Parent vistaRaiz = cargadorVista.load();
        FXMLPantallaPrincipalMiembroController fXMLPantallaPrincipalMiembroController = cargadorVista.getController();
        fXMLPantallaPrincipalMiembroController.getCorreoInicio(correoRegreso);
        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();
    }

    private void manejadorExcepcionDAO(ExcepcionDAO ex) throws IOException {
        GeneradorDialogo.getDialogo(new MensajeAlerta(ex.getMessage(), ex.getEstado()));

    }
    
    private void manejadorValidarExcepcion(IllegalArgumentException ex) {
        FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(ex.getMessage(), EstadosDAO.ADVERTENCIA));
    }



    public void getCorreoParaActualizar(String correo) throws SQLException {
        correoMiembro = correo;
        listaMiembro = MIEMBRO_DAO.consultarMiembroPorCorreo(correo);
        idMiembro = listaMiembro.get(0).getIdMiembro();
        txtIdMembresia.setText(Integer.toString(listaMiembro.get(0).getIdMembresia()));
        txtNombre.setText(listaMiembro.get(0).getNombre());
        txtApellidoPaterno.setText(listaMiembro.get(0).getApellidoPaterno());
        txtApellidoMaterno.setText(listaMiembro.get(0).getApellidoMaterno());
        pwdContrasena.setText(listaMiembro.get(0).getContrasena());
        txtCorreo.setText(listaMiembro.get(0).getCorreo());
        txtEdad.setText(Integer.toString(listaMiembro.get(0).getEdad()));
        txtTelefono.setText(listaMiembro.get(0).getTelefono());
        txtDireccion.setText(listaMiembro.get(0).getDireccion());
        correoRegreso = correo;
    }

    
    

    @FXML
    private void seleccionaFila(MouseEvent event) {
        int idMembresia = tblMembresia.getSelectionModel().getSelectedItem().getIdMembresia();
        txtIdMembresia.setText(String.valueOf(idMembresia));
    }

    @FXML
    private void actualizarMiembro(ActionEvent event) throws ExcepcionDAO, SQLException {
        Membresia membresialeccionada = tblMembresia.getSelectionModel().getSelectedItem();
        String nombre = txtNombre.getText();
        String apellidoP = txtApellidoPaterno.getText();
        String apellidoM = txtApellidoMaterno.getText();
        String contrasena = pwdContrasena.getText();
        String correo = txtCorreo.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();
        int membresiaActualizacion;
        membresiaActualizacion = membresialeccionada.getIdMembresia();
        String estado = "Activo";
        Miembro miembro = new Miembro();
        Usuario usuario = new Usuario();
        if(null == membresialeccionada){
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "Seleccione una membresia",
                    EstadosDAO.ERROR));
        }else{
            try {
                
                miembro.setNombre(nombre);
                miembro.setApellidoPaterno(apellidoP);
                miembro.setApellidoMaterno(apellidoM);
                miembro.setContrasena(contrasena);
                miembro.setCorreo(correo);
                miembro.setEdad(edad);
                miembro.setTelefono(telefono);
                miembro.setDireccion(direccion);
                miembro.setIdMembresia(membresialeccionada.getIdMembresia());
                miembro.setEstado(estado);
                usuario.setCorreoUsuario(correo);
                usuario.setContrasena(contrasena);
                usuario.setTipoUsuario("Miembro");
                
                
                
            } catch (IllegalArgumentException e) {
                manejadorValidarExcepcion(e);
            }
            int filasAfectadas = MIEMBRO_DAO.actualizarMiembro(idMiembro, miembro);
            
            if (filasAfectadas > 0) {
                FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                        "Miembro actualizado con exito.",
                        EstadosDAO.EXITOSO));
                USUARIO_DAO.archivarUsuario(correoMiembro);
                USUARIO_DAO.registrarUsuario(usuario);
                correoRegreso = correo;
                txtNombre.clear();
                pwdContrasena.clear();
                txtApellidoPaterno.clear();
                txtApellidoMaterno.clear();
                txtCorreo.clear();
                txtEdad.clear();
                txtTelefono.clear();
                txtDireccion.clear();
            } else {
                FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                        "Error al registrar el miembro.",
                        EstadosDAO.ERROR));
            }
        }
        
        
            

}
}
