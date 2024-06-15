
package mx.bg.logica;

/**
 *
 * @author nmh14
 */
public enum EstadosDAO {

    EXITOSO("100"),
    ADVERTENCIA("200"),
    ERROR("300"),
    FATAL("400");

    private final String CODIGO;

    private EstadosDAO (String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getCODIGO() {
        return CODIGO;
    }

}