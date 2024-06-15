/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.bg.logica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Miembro {
    private Pago pago;
    private Membresia membresia;
    private int idMiembro;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String contrasena;
    private String correo;
    private int edad;
    private String telefono;
    private String direccion;
    private int idMembresia;
    private String estado;
    private String nombreMembresia;
    private String TotalAPagar;

    
    public static Miembro getNuevaInstancia() {
        return new Miembro();
    }

    public int getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(int idMembresia) {
        verificarIdMembresia(idMembresia);

        this.idMembresia = idMembresia;
    }

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        verificarNombre(nombre);
        this.nombre = nombre;
    }
    
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        verificarApellidoPaterno(apellidoPaterno);
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        verificarApellidoMaterno(apellidoMaterno);
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        verificarContrasena(contrasena);
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        verificarCorreo(correo);
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        verificarEdad(edad);
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        verificarTelefono(telefono);
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        verificarDireccion(direccion);
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        checarEstado(estado);
        this.estado = estado;
    }
    
    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }
    
    public String getNombreMembresia() {
        return nombreMembresia;
    }

    public void setNombreMembresia(String nombreMembresia) {
        this.nombreMembresia = nombreMembresia;
    }

    public String getTotalAPagar() {
        return TotalAPagar;
    }

    public void setTotalAPagar(String TotalAPagar) {
        this.TotalAPagar = TotalAPagar;
    }
    
    private void verificarNombre(String nombre){
        String regex = "^(?!.*\\s\\s)(?!^\\s)[- '´.,:a-zA-Z0-9À-ÿ]{4,255}$";
        Pattern patron = Pattern.compile(regex);
        Matcher auxiliar = patron.matcher(nombre);
        if (!auxiliar.matches()) {
            throw new IllegalArgumentException("El nombre tiene un formato incorrecto:\n"
                    + "1. No debee contener espacion consecutivos\n"
                    + "2. No debe comenzar con espacio en blanco");
        }
    }
    
    private void verificarApellidoPaterno(String nombre){
        String regex = "^(?!.*\\s\\s)(?!^\\s)[- '´.,:a-zA-Z0-9À-ÿ]{3,255}$";
        Pattern patron = Pattern.compile(regex);
        Matcher auxiliar = patron.matcher(nombre);
        if (!auxiliar.matches()) {
            throw new IllegalArgumentException("El apellido paterno tiene un formato incorrecto:"
                    + "1. No debee contener espacion consecutivos"
                    + "2. No debe comenzar con espacio en blanco");
        }
    }
    
    private void verificarApellidoMaterno(String nombre){
        String regex = "^(?!.*\\s\\s)(?!^\\s)[- '´.,:a-zA-Z0-9À-ÿ]{3,255}$";
        Pattern patron = Pattern.compile(regex);
        Matcher auxiliar = patron.matcher(nombre);
        if (!auxiliar.matches()) {
            throw new IllegalArgumentException("El apellido materno tiene un formato incorrecto:"
                    + "1. No debee contener espacion consecutivos"
                    + "2. No debe comenzar con espacio en blanco");
        }
    }
    
    private void verificarContrasena(String contrasena){
        String regex = "^(?!.*\\s\\s)(?!^\\s)[- '´.,:a-zA-Z0-9À-ÿ]{7,255}$";
        Pattern patron = Pattern.compile(regex);
        Matcher auxiliar = patron.matcher(contrasena);
        if (!auxiliar.matches()) {
            throw new IllegalArgumentException("La contrasena tiene un formato incorrecto");
        }
    }
    
    private void verificarCorreo(String correo) {
        String correoRegex ="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$";
        Pattern patron = Pattern.compile(correoRegex);
        Matcher auxiliar = patron.matcher(correo);
        if (!auxiliar.matches()) {
            throw new IllegalArgumentException("El formato que ha ingresado de correo es incorrecto \n");
        }

    }
    
    private void verificarEdad(int edad) {
        if (edad <= 0) {
            throw new IllegalArgumentException("La edad tiene un formato incorrecto");
        }
    }

    
    private void verificarTelefono(String telefono){
        String numeroString = String.valueOf(telefono);
        String numeroRegex="^[0-9]{10,14}$";
        Pattern patron = Pattern.compile(numeroRegex);
        Matcher auxiliar = patron.matcher(telefono);
        if (!auxiliar.matches()) {
            throw new IllegalArgumentException("El telefono tiene un formato incorrecto:"
                    + "1. Sólo son 10 digitos");
        }
    }
    
     private void verificarDireccion(String direccion){
        String regex = "^(?!.*\\s\\s)(?!^\\s)[- '´.,:a-zA-Z0-9À-ÿ#]{1,45}$";
        Pattern patron = Pattern.compile(regex);
        Matcher auxiliar = patron.matcher(direccion);
        if (!auxiliar.matches()) {
            throw new IllegalArgumentException("El telefono tiene un formato incorrecto");
        }
    }
     
    private void verificarIdMembresia(int idMembresia) {
        if (idMembresia <= 0) {
            throw new IllegalArgumentException("La edad tiene un formato incorrecto");
        }
    }
    
    private void checarEstado(String estado) {
        String estadoRegex="^(Activo|Inactivo)$";
        Pattern patron = Pattern.compile(estadoRegex);
        Matcher auxiliar = patron.matcher(estado);
        if(!auxiliar.matches()){
            throw new IllegalArgumentException ("El estado tiene un formato incorrecto, solo es Activo o Inactivo\n");
        }
    }
    
}
