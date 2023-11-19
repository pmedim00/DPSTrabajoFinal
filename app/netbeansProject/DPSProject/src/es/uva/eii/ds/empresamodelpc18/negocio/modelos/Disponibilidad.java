package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import java.time.LocalDate;

/**
 * Implementacion de la disponibilidad
 * 
 */
public class Disponibilidad {
    
    private LocalDate comienzo;
    private LocalDate finalPrevisto;
    private TipoDeDisponibilidad tipoDeDisponibilidad;
    
    /**
     * Constructor de la clase disponibilidad
     * @param comienzo Fecha de inicio
     * @param finalPrevisto Fecha de finalizacion
     * @param disponibilidad Tipo de disponibilidad
     */
    public Disponibilidad(LocalDate comienzo, LocalDate finalPrevisto, TipoDeDisponibilidad disponibilidad) {
        this.comienzo = comienzo;
        this.finalPrevisto = finalPrevisto;
        this.tipoDeDisponibilidad = disponibilidad;
    }
    
    /**
     * Obtiene fecha de inicio
     * @return fecha de inicio
     */
    public LocalDate getComienzo() {
        return comienzo;
    }
    
    /**
     * Obtiene fecha de finalizacion
     * @return fecha de finalizacion
     */
    public LocalDate getFinalPrevisto() {
        return finalPrevisto;
    }
    
    /**
     * Obtiene tipo de disponibilidad
     * @return tipo de disponibilidad
     */
    public TipoDeDisponibilidad getTipoDeDisponibilidad(){
        return tipoDeDisponibilidad;
    }
    
}
