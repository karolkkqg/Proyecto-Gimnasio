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

public class FXMLModificarMembresiaController implements Initializable {

    @FXML
    private RadioButton btnrAcceso;

    @FXML
    private RadioButton btnrClases;

    @FXML
    private RadioButton btnrEstacionamiento;

    @FXML
    private RadioButton btnrInternet;
    @FXML
    private RadioButton btnrSpa;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;
    
    @FXML
    private TextField txtIdentificador;

    private final IMembresiaDAO MEMBRESIA_DAO = new MembresiaDAO();

    @FXML
    void actualizarMembresia(ActionEvent event) throws IOException {
        try {
            String nombre = txtNombre.getText();
            String precio = txtPrecio.getText();
            boolean accesoIlimitado = btnrAcceso.isSelected();
            boolean claseGrupal = btnrClases.isSelected();
            boolean estacionamiento = btnrEstacionamiento.isSelected();
            boolean internet = btnrInternet.isSelected();
            boolean spa = btnrSpa.isSelected();

            Membresia membresia = new Membresia();
            membresia.setIdMembresia(Integer.parseInt(txtIdentificador.getText()));
            membresia.setNombre(nombre);
            membresia.setPrecio(precio);
            membresia.setCaracteristicaAccesoIlimitado(accesoIlimitado ? "Sí" : "No");
            membresia.setCaracteristicaClaseGrupal(claseGrupal ? "Sí" : "No");
            membresia.setCaracteristicaEstacionamiento(estacionamiento ? "Sí" : "No");
            membresia.setCaracteristicaInternet(internet ? "Sí" : "No");
            membresia.setCaracteristicaSpa(spa ? "Sí" : "No");

            int filasAfectadas = MEMBRESIA_DAO.actualizarMembresia(membresia);
            if (filasAfectadas > 0) {
                FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                        "Membresía actualizada exitosamente.",
                        EstadosDAO.EXITOSO));
            } else {
                FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(
                        "Error al actualizar la membresía.",
                        EstadosDAO.EXITOSO));
            }
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
        FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("/FXMLConsultarMembresia.fxml"));
        Parent vistaRaiz = cargadorVista.load();
        vistaActual.setScene(new Scene(vistaRaiz));
        vistaActual.show();
    }

    public void getIdMembresia(int identificador) throws ExcepcionDAO {
        Membresia membresia = MEMBRESIA_DAO.consultarTotalMembresiaporId(identificador);
        if (membresia != null) {
            txtIdentificador.setText(String.valueOf(membresia.getIdMembresia()));
            txtNombre.setText(membresia.getNombre());
            txtPrecio.setText(membresia.getPrecio());
            btnrAcceso.setSelected("Sí".equalsIgnoreCase(membresia.getCaracteristicaAccesoIlimitado()));
            btnrClases.setSelected("Sí".equalsIgnoreCase(membresia.getCaracteristicaClaseGrupal()));
            btnrEstacionamiento.setSelected("Sí".equalsIgnoreCase(membresia.getCaracteristicaEstacionamiento()));
            btnrInternet.setSelected("Sí".equalsIgnoreCase(membresia.getCaracteristicaInternet()));
            btnrSpa.setSelected("Sí".equalsIgnoreCase(membresia.getCaracteristicaSpa()));
        }
    }

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
    
    private void manejadorExcepcionDAO(ExcepcionDAO ex) throws IOException {
        GeneradorDialogo.getDialogo(new MensajeAlerta(ex.getMessage(), ex.getEstado()));

    }

    private void manejadorValidarExcepcion(IllegalArgumentException ex) {
        FXMLGeneradorDialogo.getDialogo(new MensajeAlerta(ex.getMessage(), EstadosDAO.ADVERTENCIA));
    }

}
