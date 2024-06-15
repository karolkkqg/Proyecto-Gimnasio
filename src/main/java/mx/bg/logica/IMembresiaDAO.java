
package mx.bg.logica;

import java.util.ArrayList;

/**
 *
 * @author nmh14
 */
public interface IMembresiaDAO {
    public int registrarMembresia (Membresia membresia) throws ExcepcionDAO; 
    public int eliminarMembresia(int idMembresia) throws ExcepcionDAO; 
    public int actualizarMembresia (Membresia membresia) throws ExcepcionDAO; 
    public ArrayList<Membresia> consultarTotalMembresia () throws ExcepcionDAO; 
    public Membresia consultarTotalMembresiaporId (int id) throws ExcepcionDAO; 
}
