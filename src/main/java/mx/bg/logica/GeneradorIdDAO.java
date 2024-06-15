/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.bg.logica;


import mx.bg.logica.GeneradorId;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.bg.basededatos.AdministradorBaseDeDatos;

/**
 *
 * @author CARA
 */
public class GeneradorIdDAO {
    AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();
    
    public int obtenerNuevoIdPersonal() throws SQLException {
        Connection conexion = null;
        int nuevoId = 0;
        AdministradorBaseDeDatos administradorAccesoBase = new AdministradorBaseDeDatos();

        String consultaMySQL = "SELECT MAX(idPersonal) FROM personal";

        try {
            conexion = administradorAccesoBase.obtenerConexion();
            PreparedStatement enunciadoConsulta = conexion.prepareStatement(consultaMySQL);
            ResultSet respuestaConsulta = enunciadoConsulta.executeQuery();

            if (respuestaConsulta.next()) {
                int maxId = respuestaConsulta.getInt(1);
                nuevoId = maxId + 1;
            }

        } catch (SQLException excepcion) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, excepcion);
        } finally {
            administradorAccesoBase.cerrarConexion();
        }

        return nuevoId;
    }

    
}
