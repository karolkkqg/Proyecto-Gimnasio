package mx.bg.logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.bg.basededatos.AdministradorBaseDeDatos;


/**
 *
 * @author nmh14
 */
public class UsuarioDAO implements IUsuarioDAO {
    
    AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();

    public int registrarUsuario(Usuario usuario) throws ExcepcionDAO {
        Connection conexion = null;
        int filasAfectadas = 0;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoAcademico = conexion.prepareStatement("INSERT INTO usuario (correo, contrasena,tipousuario) VALUES (?,?,?)");

            
            objetoAcademico.setString(1, usuario.getCorreoUsuario());
            objetoAcademico.setString(2, usuario.getContrasena());
            objetoAcademico.setString(3, usuario.getTipoUsuario());
            filasAfectadas = objetoAcademico.executeUpdate();
            
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("La conexión con la base de datos se ha perdido", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasAfectadas;
    }

    @Override
    public Usuario autenticarUsuario(String email, String contrasena) throws ExcepcionDAO {
        try {
            Usuario usuarioValido = crearUsuarioValidoParaInicio(email, contrasena);
            usuarioValido = ejecutarValidacionUsuario(usuarioValido);
            return usuarioValido;
        } catch (IllegalArgumentException ex ) {
            throw new ExcepcionDAO("El usuario o contraseña son incorrectos", EstadosDAO.ADVERTENCIA);
        }
    }

    private Usuario ejecutarValidacionUsuario(Usuario usuarioValido) throws ExcepcionDAO {
        Connection conexion = null;
        AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();
        String consultaMySQL = "SELECT * FROM Usuario WHERE correo=? AND contrasena=?";
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setString(1, usuarioValido.getCorreoUsuario());
            enunciadoConsulta.setString(2, usuarioValido.getContrasena());
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();

            if (respuestaConsulta.next()) {
                usuarioValido.setIdUsuario(respuestaConsulta.getString("idUsuario"));
                usuarioValido.setCorreoUsuario(respuestaConsulta.getString("correo"));
                usuarioValido.setContrasena(respuestaConsulta.getString("contrasena"));
                usuarioValido.setTipoUsuario(respuestaConsulta.getString("tipoUsuario"));
            }
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("La conexión con la base de datos se ha perdido", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return usuarioValido;
    }

    private Usuario crearUsuarioValidoParaInicio(String email, String contrasena) {
        Usuario usuario = Usuario.limpiarSesion();
        usuario.setCorreoUsuario(email);
        usuario.setContrasena(contrasena);
        return usuario;
    }
    @Override
    public int archivarUsuario(String idUsuario) throws SQLException {
        Connection conexion = null;
        int filasObtenidas = 0;

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoUsuario = conexion.prepareStatement("DELETE FROM usuario WHERE correo=?");
            objetoUsuario.setString(1, idUsuario);

            filasObtenidas = objetoUsuario.executeUpdate();

        } catch (SQLException excepcion) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, excepcion);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasObtenidas;
    }
    
   
}
