/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.bg.logica;

import mx.bg.logica.Personal;
import mx.bg.logica.IPersonalDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.bg.basededatos.AdministradorBaseDeDatos;

/**
 *
 * @author CARA
 */
public class PersonalDAO implements IPersonalDAO{
    AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();

    @Override
    public int registrarPersonal(Personal personal) throws ExcepcionDAO {
        try {
            Personal PersonalValidado = getPersonalValidado(personal);
            return ejecutarRegistroDePersonal(PersonalValidado);

        } catch (IllegalArgumentException ex) {
            throw new ExcepcionDAO(ex.getMessage(), EstadosDAO.ADVERTENCIA);
        }
    }
    
    private Personal getPersonalValidado(Personal personal) throws ExcepcionDAO {
        validarIdPersonal(personal.getIdPersonal());
        return personal;
    }

    private void validarIdPersonal(int id) throws ExcepcionDAO {
        Connection conexion = null;
        AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();

        String consultaMySQL = "SELECT * FROM personal WHERE idPersonal=?";
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setInt(1, id);
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();
            
            if (respuestaConsulta.next()) {
                throw new IllegalArgumentException("El id ya se encuentra registrado en el sistema");
            }
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("No fue posible validar tu información con la base de datos del sistema", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
    }
    
    public int ejecutarRegistroDePersonal(Personal personal) throws ExcepcionDAO {
        Connection conexion = null;
        int filasAfectadas = 0;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoPersonal = conexion.prepareStatement("INSERT INTO personal VALUES (?,?,?,?,?,?,?)");

            objetoPersonal.setInt(1, personal.getIdPersonal());
            objetoPersonal.setString(2, personal.getNombre());
            objetoPersonal.setString(3, personal.getApellidoPaterno());
            objetoPersonal.setString(4, personal.getApellidoMaterno());
            objetoPersonal.setString(5,personal.getFechaNacimiento());
            objetoPersonal.setString(6, personal.getCorreo());
            objetoPersonal.setString(7, personal.getTelefono());

            filasAfectadas = objetoPersonal.executeUpdate();
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO(excepcion.getMessage(), EstadosDAO.ADVERTENCIA);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasAfectadas;
    }

    
    @Override
    public int archivarPersonalPorId(int idPersonal) throws SQLException {
        Connection conexion = null;
        AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();
        int filasObtenidas = 0;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoPersonal = conexion.prepareStatement("DELETE FROM personal WHERE idPersonal=?");
            objetoPersonal.setInt(1, idPersonal);
            filasObtenidas = objetoPersonal.executeUpdate();
        } catch (SQLException excepcion) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, excepcion);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasObtenidas;
    }
    
    
    @Override
    public ArrayList<Personal> consultarPersonalPorId(int idPersonal) throws SQLException {
        Connection conexion = null;
        ArrayList<Personal> listaPersonal = new ArrayList<>();
        AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();

        String consultaMySQL = "SELECT * FROM personal WHERE idPersonal = ?";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setInt(1, idPersonal);
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();
            while (respuestaConsulta.next()) {
                int id = respuestaConsulta.getInt(1);
                String nombre = respuestaConsulta.getString(2);
                String apellidoPaterno = respuestaConsulta.getString(3);
                String apellidoMaterno = respuestaConsulta.getString(4);
                String fechaNacimiento = respuestaConsulta.getString(5);
                String correo = respuestaConsulta.getString(6);
                String telefono = respuestaConsulta.getString(7);

                Personal objetoPersonal = new Personal();
                objetoPersonal.setIdPersonal(id);
                objetoPersonal.setNombre(nombre);
                objetoPersonal.setApellidoPaterno(apellidoPaterno);
                objetoPersonal.setApellidoMaterno(apellidoMaterno);
                objetoPersonal.setFechaNacimiento(fechaNacimiento);
                objetoPersonal.setCorreo(correo);
                objetoPersonal.setTelefono(telefono);

                listaPersonal.add(objetoPersonal);
            }

        } catch (SQLException excepcion) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, excepcion);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return listaPersonal;
    }
    
    @Override
    public ArrayList<Personal> consultaTotalPersonal() throws SQLException {
        Connection conexion = null;
        ArrayList<Personal> listaPersonal = new ArrayList<>();
        AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();

        String consultaMySQL = "SELECT * FROM personal";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();
            while (respuestaConsulta.next()) {
                int id = respuestaConsulta.getInt(1);
                String nombre = respuestaConsulta.getString(2);
                String apellidoPaterno = respuestaConsulta.getString(3);
                String apellidoMaterno = respuestaConsulta.getString(4);
                String fechaNacimiento = respuestaConsulta.getString(5);
                String correo = respuestaConsulta.getString(6);
                String telefono = respuestaConsulta.getString(7);
                

                Personal objetoPersonal = new Personal();
                
                objetoPersonal.setIdPersonal(id);
                objetoPersonal.setNombre(nombre);
                objetoPersonal.setApellidoPaterno(apellidoPaterno);
                objetoPersonal.setApellidoMaterno(apellidoMaterno);
                objetoPersonal.setFechaNacimiento(fechaNacimiento);
                objetoPersonal.setCorreo(correo);
                objetoPersonal.setTelefono(telefono);

                listaPersonal.add(objetoPersonal);
            }

        } catch (SQLException excepcion) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, excepcion);
        }finally {
            administradorAccesoBase.cerrarConexion();
        }
        return listaPersonal;
    }
    
    @Override
    public int actualizarPersonal(Personal personal) throws ExcepcionDAO {
        try {
            Personal personalValidado = validarIdPersonalParaActualizar(personal);
            return ejecutarActualizacionPersonal(personalValidado);
        } catch (IllegalArgumentException ex) {
            throw new ExcepcionDAO(ex.getMessage(), EstadosDAO.ADVERTENCIA);
        }
    }
    
    public int ejecutarActualizacionPersonal(Personal personal) throws ExcepcionDAO {
        Connection conexion = null;
        AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();
        int filasAfectadas = 0;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoPersonal = conexion.prepareStatement("UPDATE personal SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, fechaDeNacimiento = ?, correo = ?, noTelefono = ? WHERE idPersonal = ? ");

            objetoPersonal.setString(1, personal.getNombre());
            objetoPersonal.setString(2, personal.getApellidoPaterno());
            objetoPersonal.setString(3, personal.getApellidoMaterno());
            objetoPersonal.setString(4,personal.getFechaNacimiento());
            objetoPersonal.setString(5, personal.getCorreo());
            objetoPersonal.setString(6, personal.getTelefono());
            objetoPersonal.setInt(7, personal.getIdPersonal());

            filasAfectadas = objetoPersonal.executeUpdate();
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO(excepcion.getMessage(), EstadosDAO.ADVERTENCIA);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return filasAfectadas;
    }
    
    private Personal validarIdPersonalParaActualizar(Personal personal) throws ExcepcionDAO {
        Personal personalAnterior = getIdRegistrado(personal.getIdPersonal());
        if (personalAnterior.getIdPersonal()==0) {
            throw new ExcepcionDAO("No se encontró ningún personal con ese Id", EstadosDAO.FATAL);
        }
        return personal;
    }

    private Personal getIdRegistrado(int id) throws ExcepcionDAO {
        Personal personal = Personal.getNuevaInstancia();
        Connection conexion = null;
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement objetoPersonal = conexion.prepareStatement("SELECT * FROM personal WHERE idPersonal=?");
            objetoPersonal.setInt(1, id);
            ResultSet respuestaConsulta = objetoPersonal.executeQuery();
            if (respuestaConsulta.next()) {
                personal.setIdPersonal(respuestaConsulta.getInt("idPersonal"));
                personal.setNombre(respuestaConsulta.getString("nombre"));
                personal.setApellidoPaterno(respuestaConsulta.getString("apellidoPaterno"));
                personal.setApellidoMaterno(respuestaConsulta.getString("apellidoMaterno"));
                personal.setFechaNacimiento(respuestaConsulta.getString("fechaDeNacimiento"));
                personal.setCorreo(respuestaConsulta.getString("correo"));
                personal.setTelefono(respuestaConsulta.getString("noTelefono"));    
            }

        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("No fue posible obtener la información del personal", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return personal;
    }
    
    @Override
    public boolean verificarCorreo(Personal personal) throws ExcepcionDAO {
        Connection conexion = null;

        String consultaMySQL = "SELECT COUNT(*) FROM personal WHERE correo = ?";
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setString(1, personal.getCorreo());

            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();
            if (respuestaConsulta.next()) {
                return respuestaConsulta.getInt(1) > 0;
            }
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Error al verificar la existencia del correo o teléfono del personal", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return false;
    }
    
    @Override
    public boolean verificarTelefono(Personal personal) throws ExcepcionDAO {
        Connection conexion = null;

        String consultaMySQL = "SELECT COUNT(*) FROM personal WHERE noTelefono = ?";
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setString(1, personal.getTelefono());

            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();
            if (respuestaConsulta.next()) {
                return respuestaConsulta.getInt(1) > 0;
            }
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Error al verificar la existencia del correo o teléfono del personal", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return false;
    }
    
    @Override
    public boolean verificaCorreoActualizacion(Personal personal) throws ExcepcionDAO {
        Connection conexion = null;

        String consultaMySQL = "SELECT COUNT(*) FROM personal WHERE (correo = ?) AND idPersonal != ?";
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setString(1, personal.getCorreo());
            enunciadoConsulta.setInt(2, personal.getIdPersonal());

            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();
            if (respuestaConsulta.next()) {
                return respuestaConsulta.getInt(1) > 0;
            }
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Error al verificar la existencia del correo o teléfono del personal", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return false;
    }
    
    @Override
    public boolean verificaTelefonoActualizacion(Personal personal) throws ExcepcionDAO {
        Connection conexion = null;

        String consultaMySQL = "SELECT COUNT(*) FROM personal WHERE (noTelefono = ?) AND idPersonal != ?";
        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            enunciadoConsulta.setString(1, personal.getTelefono());
            enunciadoConsulta.setInt(2, personal.getIdPersonal());

            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();
            if (respuestaConsulta.next()) {
                return respuestaConsulta.getInt(1) > 0;
            }
        } catch (SQLException excepcion) {
            throw new ExcepcionDAO("Error al verificar la existencia del correo o teléfono del personal", EstadosDAO.ERROR);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }
        return false;
    }
}
