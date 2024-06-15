package mx.bg.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.bg.logica.EstadosDAO;
import mx.bg.logica.ExcepcionDAO;
import mx.bg.logica.IUsuarioDAO;
import mx.bg.logica.MensajeAlerta;
import mx.bg.logica.Usuario;
import mx.bg.logica.UsuarioDAO;

public class FXMLAccesoControlador implements Initializable {

    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField pwdContrasena;
    private final IUsuarioDAO USUARIO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Puedes agregar inicializaciones adicionales aquí si es necesario.
    }

    @FXML
    private void registroUsuarioNuevo(ActionEvent event) throws IOException {
        Node vistaAnterior = (Node) event.getSource();
        Stage vistaActual = (Stage) vistaAnterior.getScene().getWindow();
        FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("/FXMLRegistroMiembro.fxml"));
        Parent vistaRaiz = cargadorVista.load();
        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();
    }

    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {
        if (txtCorreo.getText().isEmpty() || pwdContrasena.getText().isEmpty()) {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "Por favor, ingrese el correo y la contraseña",
                    EstadosDAO.ADVERTENCIA));
            return;
        }

        try {
            invocarAutenticacionUsuario(
                    txtCorreo.getText(),
                    pwdContrasena.getText());
        } catch (ExcepcionDAO ex) {
            manejadorExcepcionDAO(ex);
        }
    }

    private void invocarAutenticacionUsuario(String email, String contrasena) throws IOException, ExcepcionDAO {
        Usuario usuario = USUARIO.autenticarUsuario(email, contrasena);
        if (usuario == null || usuario.getIdUsuario().equals("AC00000")) {
            FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                    "No se encontró ningún usuario con ese correo y contraseña",
                    EstadosDAO.ADVERTENCIA));
            limpiarCampos();
        } else {
            switch (usuario.getTipoUsuario()) {
                case "Gerente":
                    Stage vistaActualParaGerente = (Stage) txtCorreo.getScene().getWindow();
                    FXMLLoader cargadorArchivo = new FXMLLoader(App.class.getResource("/FXMLPantallaPrincipalGerente.fxml"));
                    Parent vistaRaiz = cargadorArchivo.load();
                    vistaActualParaGerente.setScene(new Scene(vistaRaiz));
                    vistaActualParaGerente.show();
                    break;

                case "Miembro":
                    Stage vistaActualParaMiembro = (Stage) txtCorreo.getScene().getWindow();
                    FXMLLoader cargadorArchivoMiembro = new FXMLLoader(App.class.getResource("/FXMLPantallaPrincipalMiembro.fxml"));
                    Parent vistaRaizMiembro = cargadorArchivoMiembro.load();
                    FXMLPantallaPrincipalMiembroController fXMLPantallaPrincipalMiembroController = cargadorArchivoMiembro.getController();

                    fXMLPantallaPrincipalMiembroController.getCorreoInicio(txtCorreo.getText());
                    vistaActualParaMiembro.setScene(new Scene(vistaRaizMiembro));
                    vistaActualParaMiembro.show();
                    break;
                default:
                    FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                            "Tipo de usuario desconocido: " + usuario.getTipoUsuario(),
                            EstadosDAO.ERROR));
                    limpiarCampos();
            }
        }
    }

    private void limpiarCampos() {
        txtCorreo.clear();
        pwdContrasena.clear();
    }

    private void manejadorExcepcionDAO(ExcepcionDAO ex) {
        FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                ex.getMessage(),
                ex.getEstado()));
        limpiarCampos();
    }
}
