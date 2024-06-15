
package mx.bg.basededatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.bg.logica.EstadosDAO;
import mx.bg.logica.ExcepcionDAO;

/**
 *
 * @author nmh14
 */
public class AdministradorBaseDeDatos {
    private Connection conexion;
    private final String BASEDEDATOS_NOMBRE="jdbc:mysql://127.0.0.1/black_gym";
    private final String BASEDEDATOS_USUARIO="admiblackgym";
    private final String BASEDEDATOS_CONTRASENA="1234567";
    
    public Connection obtenerConexion() throws SQLException{
        conectar();
        return conexion;
    }
    
    private void conectar() throws SQLException{
        conexion=DriverManager.getConnection(BASEDEDATOS_NOMBRE,BASEDEDATOS_USUARIO,BASEDEDATOS_CONTRASENA);
    }
    
    public void cerrarConexion(){
        if(conexion!=null){
            try {
                if(!conexion.isClosed()){
                    conexion.close();
                }
            } catch (SQLException exception) {                
                Logger.getLogger(AdministradorBaseDeDatos.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
    }
    
    public  Connection getInstancia() throws ExcepcionDAO {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = obtenerConexion();
            }
        } catch (SQLException ex) {
            throw new ExcepcionDAO(
                    "Lo sentimos, no fue posible conectarse con la base de datos del sistema",
                    EstadosDAO.FATAL);
        }
        return conexion;
    }
}
