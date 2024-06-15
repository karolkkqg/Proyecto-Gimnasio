package mx.bg.logica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Membresia {

    private int idMembresia;
    private String nombre;
    private String precio;
    private String caracteristicaAccesoIlimitado;
    private String caracteristicaClaseGrupal;
    private String caracteristicaEstacionamiento;
    private String caracteristicaInternet;
    private String caracteristicaSpa;

    public static Membresia getNuevaInstancia() {
        return new Membresia();
    }

    public int getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(int idMembresia) {
        this.idMembresia = idMembresia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        verificarCadena(nombre);
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        verificarNumero(precio);
        this.precio = precio;
    }

    public String getCaracteristicaAccesoIlimitado() {
        return caracteristicaAccesoIlimitado;
    }

    public void setCaracteristicaAccesoIlimitado(String caracterusticaUno) {
        this.caracteristicaAccesoIlimitado = caracterusticaUno;
    }

    public String getCaracteristicaClaseGrupal() {
        return caracteristicaClaseGrupal;
    }

    public void setCaracteristicaClaseGrupal(String caracterusticaDos) {
        this.caracteristicaClaseGrupal = caracterusticaDos;
    }

    public String getCaracteristicaEstacionamiento() {
        return caracteristicaEstacionamiento;
    }

    public void setCaracteristicaEstacionamiento(String caracterusticaTres) {
        this.caracteristicaEstacionamiento = caracterusticaTres;
    }

    public String getCaracteristicaInternet() {
        return caracteristicaInternet;
    }

    public void setCaracteristicaInternet(String caracterusticaCuatro) {
        this.caracteristicaInternet = caracterusticaCuatro;
    }

    public String getCaracteristicaSpa() {
        return caracteristicaSpa;
    }

    public void setCaracteristicaSpa(String caracterusticaCinco) {
        this.caracteristicaSpa = caracterusticaCinco;
    }

    private void verificarCadena(String nombre) {
        String nombreRegex = "^(?!.*[\\!\\$%\\&'\\(\\)\\*\\+\\/\\:\\;<\\=\\>\\?\\@\\[\\\\\\]\\^_`\\{\\|\\}\\~])(?!.*  )(?!^ $)^.{3,255}$";
        Pattern patron = Pattern.compile(nombreRegex);
        Matcher coincide = patron.matcher(nombre);
        if (!coincide.matches()) {
            throw new IllegalArgumentException("Verifique el nombre de de la membresia, verifique que: \n"
                    + "1.- No tenga doble espacio.\n"
                    + "2.- No permite caracteres especiales\n"
                    + "3.- No puede estar vacio");
        }
    }

    private void verificarNumero(String numero) {
        String nombreRegex = "^\\d+(\\.\\d+)?$";
        Pattern patron = Pattern.compile(nombreRegex);
        Matcher coincide = patron.matcher(numero);
        if (!coincide.matches()) {
            throw new IllegalArgumentException("El precio no pueden ser negativos y tiene que ser digitos");
        }
    }

}
