/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.bg.logica;



/**
 *
 * @author CARA
 */
public interface IGeneradorIdDAO {
     public String generarIDUV() throws ExcepcionDAO;

    public String generarIDEX() throws ExcepcionDAO;

    public String generarIDRI() throws ExcepcionDAO;

    public String generarIDPC() throws ExcepcionDAO;

    public String generarIDIP() throws ExcepcionDAO;

    public void reiniciarContadores(String tipo) throws ExcepcionDAO;

    public String retrocederContador(String tipo) throws ExcepcionDAO;

    public String actuzalizarContadorID(int i, String ip) throws ExcepcionDAO;
}
