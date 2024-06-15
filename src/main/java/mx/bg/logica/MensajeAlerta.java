
package mx.bg.logica;

/**
 *
 * @author nmh14
 */
public class MensajeAlerta {
    private String contenido = "La operación se ha realizado exitosamente";
    private EstadosDAO tipoAlerta = EstadosDAO.EXITOSO;
    
    public MensajeAlerta(String contenido, EstadosDAO tipoAlerta) {
        if (contenido.isEmpty() || tipoAlerta == null) {
            throw new IllegalArgumentException("Debes ingresar un mensaje y un estádo en el mensaje de alerta");
        }
        this.contenido = contenido;
        this.tipoAlerta = tipoAlerta;
    }
    
    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public EstadosDAO getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(EstadosDAO tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }
    
}
