/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.bg.logica;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kahun
 */
public interface IMiembroDAO {
    public int registrarMiembro(Miembro miembro) throws ExcepcionDAO;
    public int cambiarEstadoMiembro(String nombre, String nuevoEstado) throws ExcepcionDAO;
    public ArrayList<Miembro> consultarTotalMiembro() throws SQLException;
    public int actualizarMiembro(int id, Miembro miembro) throws ExcepcionDAO;
    public int archivarMiembro(String nombre) throws SQLException;
    public ArrayList<Miembro> consultarMiembroPorCorreo(String correo) throws SQLException;
}
