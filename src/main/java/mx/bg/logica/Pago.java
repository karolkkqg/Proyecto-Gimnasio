
package mx.bg.logica;


public class Pago {
    private int idPago;
    private String asunto;
    private String cantidadADeber;
    private String cantidadAPagar;
    private String totalAPagar;
    private Membresia membresia;

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCantidadADeber() {
        return cantidadADeber;
    }

    public void setCantidadADeber(String cantidadADeber) {
        this.cantidadADeber = cantidadADeber;
    }

    public String getCantidadAPagar() {
        return cantidadAPagar;
    }

    public void setCantidadAPagar(String cantidadAPagar) {
        this.cantidadAPagar = cantidadAPagar;
    }

    public String getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(String totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }
   
}
