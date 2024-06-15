
package mx.bg.logica;

import mx.bg.logica.EstadosDAO;
/**
 *
 * @author nmh14
 */
public class ExcepcionDAO extends Exception{
    
    private final EstadosDAO estado;

    public ExcepcionDAO(String mensaje, EstadosDAO estado) {
        super(mensaje);
        this.estado = estado;
    }

    public EstadosDAO getEstado() {
        return estado;
    }
    
}
