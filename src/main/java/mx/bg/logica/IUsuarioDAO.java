
package mx.bg.logica;

import java.sql.SQLException;


/**
 *
 * @author nmh14
 */
public interface IUsuarioDAO {
    public int registrarUsuario(Usuario usuario) throws ExcepcionDAO;
    public Usuario autenticarUsuario(String email, String contrasena) throws ExcepcionDAO;
    public int archivarUsuario(String idUsuario) throws SQLException;
}
