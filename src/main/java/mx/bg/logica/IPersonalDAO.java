/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.bg.logica;


import mx.bg.logica.Personal;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author CARA
 */
public interface IPersonalDAO {
    public int registrarPersonal(Personal personal)throws ExcepcionDAO;
    public int archivarPersonalPorId(int idPersonal) throws SQLException;
    public ArrayList<Personal> consultarPersonalPorId(int idPersonal) throws SQLException;
    public ArrayList<Personal> consultaTotalPersonal() throws SQLException; 
    public int actualizarPersonal(Personal personal) throws ExcepcionDAO;
    boolean verificarCorreo(Personal personal) throws ExcepcionDAO;
    boolean verificarTelefono(Personal personal) throws ExcepcionDAO;
    boolean verificaCorreoActualizacion(Personal personal) throws ExcepcionDAO;
    boolean verificaTelefonoActualizacion(Personal personal) throws ExcepcionDAO;
}
