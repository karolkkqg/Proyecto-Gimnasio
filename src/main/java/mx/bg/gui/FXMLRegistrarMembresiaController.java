package mx.bg.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import mx.bg.logica.EstadosDAO;
import mx.bg.logica.ExcepcionDAO;
import mx.bg.logica.IMembresiaDAO;
import mx.bg.logica.Membresia;
import mx.bg.logica.MembresiaDAO;
import mx.bg.logica.MensajeAlerta;

public class FXMLRegistrarMembresiaController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Pattern formato = Pattern.compile("\\d*");
        TextFormatter<String> formatoDeTexto = new TextFormatter<>(change -> {
            if (formato.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        });
        txtPrecio.setTextFormatter(formatoDeTexto);
    }

    @FXML
    private RadioButton btnrAccesoLimitado;

    @FXML
    private RadioButton btnrClaseGrupal;

    @FXML
    private RadioButton btnrEstacionamiento;

    @FXML
    private RadioButton btnrInternet;

    @FXML
    private RadioButton btnrSpa;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtTitulo;

    private final IMembresiaDAO MEMBRESIA_DAO = new MembresiaDAO();

    @FXML
    void registrarMembresia(ActionEvent event) throws IOException {
        try {
            String nombre = txtTitulo.getText();
            String precio = txtPrecio.getText();
            boolean accesoIlimitado = btnrAccesoLimitado.isSelected();
            boolean claseGrupal = btnrClaseGrupal.isSelected();
            boolean estacionamiento = btnrEstacionamiento.isSelected();
            boolean internet = btnrInternet.isSelected();
            boolean spa = btnrSpa.isSelected();

            Membresia membresia = new Membresia();
            membresia.setNombre(nombre);
            membresia.setPrecio(precio);
            membresia.setCaracteristicaAccesoIlimitado(accesoIlimitado ? "Sí" : "No");
            membresia.setCaracteristicaClaseGrupal(claseGrupal ? "Sí" : "No");
            membresia.setCaracteristicaEstacionamiento(estacionamiento ? "Sí" : "No");
            membresia.setCaracteristicaInternet(internet ? "Sí" : "No");
            membresia.setCaracteristicaSpa(spa ? "Sí" : "No");

            int filasAfectadas = MEMBRESIA_DAO.registrarMembresia(membresia);
            if (filasAfectadas > 0) {
                FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                        "Membresía registrada exitosamente.",
                        EstadosDAO.EXITOSO));
            } else {
                FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                        "Error al registrar la membresía.",
                        EstadosDAO.EXITOSO));
            }
            camposEstanLimpios();
        } catch (ExcepcionDAO e) {
            manejadorExcepcionDAO(e);
        } catch (IllegalArgumentException e) {
            manejadorValidarExcepcion(e);
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

    private void manejadorExcepcionDAO(ExcepcionDAO ex) throws IOException {
        GeneradorDialogo.getDialogo(new MensajeAlerta(ex.getMessage(), ex.getEstado()));

    }

    private void manejadorValidarExcepcion(IllegalArgumentException ex) {
        FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(ex.getMessage(), EstadosDAO.ADVERTENCIA));
    }

    private void camposEstanLimpios() {
        txtTitulo.clear();
        txtPrecio.clear();
        btnrAccesoLimitado.setSelected(false);
        btnrClaseGrupal.setSelected(false);
        btnrEstacionamiento.setSelected(false);
        btnrInternet.setSelected(false);
        btnrSpa.setSelected(false);
    }

}