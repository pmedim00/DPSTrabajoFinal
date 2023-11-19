package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import java.time.LocalDate;

/**
 * Implementacion de la clase vinculacion
 * 
 */
public class VinculacionConLaEmpresa {
    
    private LocalDate inicio;
    private TipoDeVinculacion vinculacion;
    
    /**
     * Constructor de vinculacion
     * @param inicio Fecha de inicio
     * @param vinculacion tipo de vinculacion
     */
    public VinculacionConLaEmpresa(LocalDate inicio, TipoDeVinculacion vinculacion) {
        this.inicio = inicio;
        this.vinculacion = vinculacion;
    }
    
    /**
     * Obtiene vinculacion
     * @return vinculacion con la empresa
     */
    public TipoDeVinculacion getVinculacion(){
        return vinculacion;
    }
    
    /**
     * Obtiene fecha de inicio
     * @return fecha de inicio
     */
    public LocalDate getInicio() {
        return inicio;
    }
    
}
