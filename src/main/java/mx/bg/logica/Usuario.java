
package mx.bg.logica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nmh14
 */
public class Usuario {
    private static Usuario usuario;
    
    public static Usuario getNuevaInstancia() {
        return new Usuario();
    }
    
    public String idUsuario;
    public String correoUsuario;
    public String contrasena;
    public String tipoUsuario;
    
    public static Usuario getUsuario() {
        return usuario;
    }
    public String getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getCorreoUsuario() {
        return correoUsuario;
    }
    public void setCorreoUsuario(String correoUsuario) {
        verificarCorreo(correoUsuario);
        this.correoUsuario = correoUsuario;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
      
    private void verificarCorreo(String correo) {
        String correoRegex ="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$";
        Pattern patron = Pattern.compile(correoRegex);
        Matcher coincide = patron.matcher(correo);
        if (!coincide.matches()) {
            throw new IllegalArgumentException("El formato que ha ingresado de correo es incorrecto \n");
        }

    }
    public static Usuario getSesion() {
        if (usuario == null) {
            usuario = new Usuario();
            usuario.setIdUsuario("AC00000");
        }
        return usuario;
    }
    public static Usuario limpiarSesion() {
        getSesion();
        usuario.setIdUsuario("AC00000");
        usuario.setCorreoUsuario("anonimo@uv.mx");
        usuario.setContrasena("sin_contraseña");
        usuario.setTipoUsuario("sin_usuario");
        return usuario;
    }

    
    
}
