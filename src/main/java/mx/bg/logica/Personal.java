/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.bg.logica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author CARA
 */
public class Personal {
    private int idPersonal;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNacimiento;
    private String correo;
    private String telefono;
            
            
    public static Personal getNuevaInstancia(){
        return new Personal();
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getApellidoPaterno(){
        return apellidoPaterno;
    }
    
    public String getApellidoMaterno(){
        return apellidoMaterno;
    }
    
    public int getIdPersonal(){
        return idPersonal;
    }
    
    public String getCorreo(){
        return correo;
    }
    
    public String getFechaNacimiento(){
        return fechaNacimiento;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    public void setNombre(String nombre){
        verificarFormatoNombre(nombre);
        this.nombre = nombre;
    }
    
    public void setApellidoPaterno(String apellidoPaterno){
        verificarFormatoApellidoPaterno(apellidoPaterno);
        this.apellidoPaterno = apellidoPaterno;
    }
    
    public void setApellidoMaterno(String apellidoMaterno){
        verificarFormatoApellidoMaterno(apellidoMaterno);
        this.apellidoMaterno = apellidoMaterno;
    }
    
    public void setIdPersonal(int idPersonal){
        this.idPersonal = idPersonal;
    }
    
    public void setCorreo(String correo){
        verificarFormatoCorreo(correo);
        this.correo = correo;
    }
    
    public void setFechaNacimiento(String fechaNacimiento){
        verificarFormatoFechaNacimiento(fechaNacimiento);
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public void setTelefono(String telefono){
        verificarFormatoTelefono(telefono);
        this.telefono = telefono;
    }
    
    private void verificarFormatoNombre(String nombre) {
        String regex = "^(?! )(?![\\s\\S]* {2,})(?=.*\\S)[\\p{L}\\d\\s,.-]+$";
        Pattern patron = Pattern.compile(regex);
        Matcher coincide = patron.matcher(nombre);
        if (!coincide.matches()) {
            throw new IllegalArgumentException("El nombre tiene un formato incorrecto");
        }
    }
    
    private void verificarFormatoApellidoPaterno(String apellidoPaterno) { //algunos apellidos tienes mas de una palabra, como "De Guevara"
        String regex = "^(?! )(?![\\s\\S]* {2,})(?=.*\\S)[\\p{L}\\d\\s,.-]+$";
        Pattern patron = Pattern.compile(regex);
        Matcher coincide = patron.matcher(apellidoPaterno);
        if (!coincide.matches()) {
            throw new IllegalArgumentException("El apellido paterno tiene un formato incorrecto");
        }
    }
    
    private void verificarFormatoApellidoMaterno(String apellidoMaterno) {
        String regex = "^(?! )(?![\\s\\S]* {2,})(?=.*\\S)[\\p{L}\\d\\s,.-]+$";
        Pattern patron = Pattern.compile(regex);
        Matcher coincide = patron.matcher(apellidoMaterno);
        if (!coincide.matches()) {
            throw new IllegalArgumentException("El apellido materno tiene un formato incorrecto");
        }
    }
    
    private void verificarFormatoFechaNacimiento(String fechaNacimiento) {
        String regex = "^(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        Pattern patron = Pattern.compile(regex);
        Matcher coincide = patron.matcher(fechaNacimiento);
        if (!coincide.matches()) {
            throw new IllegalArgumentException("La fecha de nacimiento tiene un formato incorrecto o un valor irreal");
        }
    }
    
    private void verificarFormatoCorreo(String correo) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$";
        Pattern patron = Pattern.compile(regex);
        Matcher coincide = patron.matcher(correo);
        if (!coincide.matches()) {
            throw new IllegalArgumentException("El correo tiene un formato incorrecto");
        }
    }
    
    private void verificarFormatoTelefono(String telefono) {
        String regex = "^\\+?[0-9]+$";
        Pattern patron = Pattern.compile(regex);
        Matcher coincide = patron.matcher(telefono);
        if (!coincide.matches()) {
            throw new IllegalArgumentException("El telefono tiene un formato incorrecto");
        }
    }
}

