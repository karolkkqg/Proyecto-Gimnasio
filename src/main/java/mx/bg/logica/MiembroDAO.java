/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.bg.logica;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.bg.basededatos.AdministradorBaseDeDatos;

public class MiembroDAO implements IMiembroDAO{
    AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();

    public int registrarMiembro(Miembro miembro) throws ExcepcionDAO {
        Connection conexion = null;
        int filasAfectadas = 0;
        try {

            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoMiembro = conexion.prepareStatement("INSERT INTO miembro (nombre,apellidoPaterno, apellidoMaterno,contrasena,correo,edad,telefono,direccion,idMembresia,estado) VALUES (?,?,?,?,?,?,?,?,?,?)");
            

            objetoMiembro.setString(1, miembro.getNombre());
            objetoMiembro.setString(2, miembro.getApellidoPaterno());
            objetoMiembro.setString(3, miembro.getApellidoMaterno());
            objetoMiembro.setString(4, miembro.getContrasena());
            objetoMiembro.setString(5, miembro.getCorreo());
            objetoMiembro.setInt(6, miembro.getEdad());
            objetoMiembro.setString(7, miembro.getTelefono());
            objetoMiembro.setString(8, miembro.getDireccion());
            objetoMiembro.setInt(9, miembro.getIdMembresia());
            objetoMiembro.setString(10, miembro.getEstado());
            
            try{
                validarCorreo(miembro.getCorreo());
            }catch(IllegalArgumentException ex){
                throw new ExcepcionDAO(ex.getMessage(), EstadosDAO.ERROR);
            }
            try{
                validarTelefono(miembro.getTelefono());
            }catch(IllegalArgumentException ex){
                throw new ExcepcionDAO(ex.getMessage(), EstadosDAO.ERROR);
            }

            filasAfectadas = objetoMiembro.executeUpdate();
                    
        } catch (SQLException excepcion) {
            Logger.getLogger(MiembroDAO.class.getName()).log(Level.SEVERE, null, excepcion);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasAfectadas;
    }
    
    @Override
    public ArrayList<Miembro> consultarTotalMiembro() throws SQLException{
        Connection conexion = null;
        ArrayList<Miembro> listaMiembros = new ArrayList<>();
        
        String consultaMySQL = "SELECT \n"
                + "    miembro.idMiembro,\n"
                + "    miembro.nombre,\n"
                + "    miembro.apellidoPaterno,\n"
                + "    miembro.apellidoMaterno,\n"
                + "    miembro.estado,\n"
                + "    membresia.nombre AS nombreMembresia,\n"
                + "    pago.totalAPagar\n"
                + "FROM \n"
                + "    miembro\n"
                + "JOIN \n"
                + "    membresia ON miembro.idMembresia = membresia.idMembresia\n"
                + "JOIN \n"
                + "    pago ON miembro.idMiembro = pago.idMiembro\n"
                + "WHERE \n"
                + "    miembro.estado = 'Activo';";
        
        try{
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();
            while (respuestaConsulta.next()) {
                int idMiembro = respuestaConsulta.getInt(1);
                String nombre = respuestaConsulta.getString(2);
                String apellidoPaterno = respuestaConsulta.getString(3);
                String apellidoMaterno = respuestaConsulta.getString(4);
                String estado = respuestaConsulta.getString(5);
                String nombreMembresia = respuestaConsulta.getString(6);
                String totalAPagar = respuestaConsulta.getString(7);
                
                Membresia objetoMembresia = new Membresia();
                objetoMembresia.setNombre(nombreMembresia);
                
                Pago objetoPago = new Pago();
                objetoPago.setTotalAPagar(totalAPagar);
                
                Miembro objetoMiembro = new Miembro();
                objetoMiembro.setIdMiembro(idMiembro);
                objetoMiembro.setNombre(nombre);
                objetoMiembro.setApellidoPaterno(apellidoPaterno);
                objetoMiembro.setApellidoMaterno(apellidoMaterno);
                objetoMiembro.setEstado(estado);
                objetoMiembro.setNombreMembresia(nombreMembresia);
                objetoMiembro.setTotalAPagar(totalAPagar);
                
                listaMiembros.add(objetoMiembro);
            }
        }catch (SQLException excepcion) {
            Logger.getLogger(MiembroDAO.class.getName()).log(Level.SEVERE, null, excepcion);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return listaMiembros;

    }

    
    public ArrayList<Miembro> consultarMiembroPorCorreo(String correo) throws SQLException{
        Connection conexion = null;
        ArrayList<Miembro> listaMiembros = new ArrayList<>();
        
        String consultaMySQL = "SELECT * FROM miembro WHERE correo=?";
        
        try{
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setString(1,correo);
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();
            while (respuestaConsulta.next()) {
                int idMiembro = respuestaConsulta.getInt(1);
                String nombre = respuestaConsulta.getString(2);
                String apellidoPaterno = respuestaConsulta.getString(3);
                String apellidoMaterno = respuestaConsulta.getString(4);
                String contrasena = respuestaConsulta.getString(5);
                String correoMiembro = respuestaConsulta.getString(6);
                int edad = respuestaConsulta.getInt(7);
                String telefono = respuestaConsulta.getString(8);
                String direccion = respuestaConsulta.getString(9);
                int idMembresia = respuestaConsulta.getInt(10);
                String estado = respuestaConsulta.getString(11);
                
                Membresia objetoMembresia = new Membresia();
                objetoMembresia.setIdMembresia(idMembresia);
                
                Miembro objetoMiembro = new Miembro();
                objetoMiembro.setIdMiembro(idMiembro);
                objetoMiembro.setNombre(nombre);
                objetoMiembro.setApellidoPaterno(apellidoPaterno);
                objetoMiembro.setApellidoMaterno(apellidoMaterno);
                objetoMiembro.setContrasena(contrasena);
                objetoMiembro.setCorreo(correoMiembro);
                objetoMiembro.setEdad(edad);
                objetoMiembro.setTelefono(telefono);
                objetoMiembro.setDireccion(direccion);
                objetoMiembro.setIdMembresia(idMembresia);
                objetoMiembro.setEstado(estado);
                
                listaMiembros.add(objetoMiembro);
            }
        }catch (SQLException excepcion) {
            Logger.getLogger(MiembroDAO.class.getName()).log(Level.SEVERE, null, excepcion);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return listaMiembros;

    }
    
   private void validarCorreo(String correo) throws ExcepcionDAO {
        Connection conexion = null;
        AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();

        String consultaMySQL = "SELECT * FROM miembro WHERE correo=? ";
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setString(1, correo);
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();
            
            if (respuestaConsulta.next()) {
                throw new IllegalArgumentException("El correo ya se encuentra registrado en el sistema");
            }
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("No fue posible validar tu información con la base de datos del sistema - correo", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
    }
   
   private void validarTelefono(String telefono) throws ExcepcionDAO {
        Connection conexion = null;
        AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();

        String consultaMySQL = "SELECT * FROM miembro WHERE telefono=? ";
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setString(1, telefono);
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();
            
            if (respuestaConsulta.next()) {
                throw new IllegalArgumentException("El telefono ya se encuentra registrado en el sistema");
            }
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("No fue posible validar tu información con la base de datos del sistema - correo", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
    }
   
   

    
    public int archivarMiembro(String nombre) throws SQLException{
        Connection conexion = null;
        int filasObtenidas = 0;
        
        String consultaMySQL = "DELETE FROM miembro WHERE nombre = ?";
        
        try{
             conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoMiembro = conexion.prepareStatement(consultaMySQL);
            objetoMiembro.setString(1, nombre);
            filasObtenidas = objetoMiembro.executeUpdate();
            reducirContador();
        } catch (SQLException excepcion) {
            Logger.getLogger(MiembroDAO.class.getName()).log(Level.SEVERE, null, excepcion);
        } catch (ExcepcionDAO ex) {
            Logger.getLogger(MiembroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasObtenidas;
    }
    
    public int actualizarMiembro(int id, Miembro miembro) throws ExcepcionDAO{
        Connection conexion = null;
        AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();
        int filasAfectadas = 0;
        
        String consultaMySQL = "UPDATE miembro SET nombre=?, apellidoPaterno=?, apellidoMaterno=?, contrasena=?, correo=?, edad=?, telefono=?, direccion=?, idMembresia=? WHERE idMiembro=?";
        try{
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoMiembro = conexion.prepareStatement(consultaMySQL);
            objetoMiembro.setString(1, miembro.getNombre());
            objetoMiembro.setString(2, miembro.getApellidoPaterno());
            objetoMiembro.setString(3, miembro.getApellidoMaterno());
            objetoMiembro.setString(4, miembro.getContrasena());
            objetoMiembro.setString(5, miembro.getCorreo());
            objetoMiembro.setInt(6, miembro.getEdad());
            objetoMiembro.setString(7, miembro.getTelefono());
            objetoMiembro.setString(8, miembro.getDireccion());
            objetoMiembro.setInt(9, miembro.getIdMembresia());
            objetoMiembro.setInt(10,id);
            
                    
            filasAfectadas = objetoMiembro.executeUpdate();
        }catch (SQLException excepcion) {
            throw new ExcepcionDAO(excepcion.getMessage(), EstadosDAO.ADVERTENCIA);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasAfectadas;
    }
    
    public int cambiarEstadoMiembro(String nombre, String nuevoEstado) throws ExcepcionDAO {
    Connection conexion = null;
    AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();
    int filasAfectadas = 0;
    
    String consultaMySQL = "UPDATE miembro SET estado=? WHERE nombre = ?";
    try {
        conexion = administradorAccesoBase.obtenerConexion();
        PreparedStatement objetoMiembro = conexion.prepareStatement(consultaMySQL);
        objetoMiembro.setString(1, nuevoEstado);
        objetoMiembro.setString(2, nombre);
        
        filasAfectadas = objetoMiembro.executeUpdate();
    } catch (SQLException excepcion) {
        throw new ExcepcionDAO(excepcion.getMessage(), EstadosDAO.ADVERTENCIA);
    } finally {
        administradorAccesoBase.cerrarConexion();
    }
    return filasAfectadas;
}

    
    private int reducirContador() throws ExcepcionDAO {
        Connection conexion = null;
        PreparedStatement obtenerMaxId = null;
        Statement ajustarAutoIncrement = null;
        ResultSet resultSet = null;
        int filasAfectadas = 0;

        try {
            conexion = administradorAccesoBase.obtenerConexion();

            String obtenerMaxIdSQL = "SELECT IFNULL(MAX(idMembresia), 0) AS max_id FROM membresia";
            obtenerMaxId = conexion.prepareStatement(obtenerMaxIdSQL);
            resultSet = obtenerMaxId.executeQuery();
            int maxId = 0;
            if (resultSet.next()) {
                maxId = resultSet.getInt("max_id");
            }

            String ajustarAutoIncrementSQL = "ALTER TABLE membresia AUTO_INCREMENT = " + (maxId + 1);
            ajustarAutoIncrement = conexion.createStatement();
            ajustarAutoIncrement.executeUpdate(ajustarAutoIncrementSQL);
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Se ha perdido la conexión con la base de datos", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasAfectadas;
    }
    
}
