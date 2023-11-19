package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

/**
 * Implementacion de componente en configuracion
 * 
 */
public class ComponenteEnConfiguracion {
    private int idDescripcion;
    private int idConfiguracion;
    
    /**
     * Constructor
     * @param idDescripcion identificador de la descripcion
     * @param idConfiguracion identificador de la configuracion
     */
    public ComponenteEnConfiguracion(int idDescripcion, int idConfiguracion) {
        this.idDescripcion = idDescripcion;
        this.idConfiguracion= idConfiguracion;
    }

     /**
     * Obtener idDescripcion
     * @return idDescripcion
     */
    public int getIdDescripcion() {
        return idDescripcion;
    }

     /**
     * Obtener idConfiguracion
     * @return idConfiguracion
     */
    public int getIdConfiguracion() {
        return idConfiguracion;
    }
    
}
