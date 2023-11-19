package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import java.time.LocalDate;

/**
 * Implementacion de la clase roles en empresa
 * 
 */
public class RolesEnEmpresa {
    private LocalDate comienzoEnRol;
    private TipoDeRol rol;
    
    /**
     * Constructor de roles en empresa
     * @param comienzoEnRol Fecha de inicio
     * @param rol Tipo de rol
     */
    public RolesEnEmpresa(LocalDate comienzoEnRol, TipoDeRol rol){
        this.comienzoEnRol = comienzoEnRol;
        this.rol = rol;
    }

    /**
     * Obtiene rol
     * @return rol obtenido
     */
    public TipoDeRol getRol(){
        return rol;
    }
    
    /**
     * Obtiene fecha de inicio
     * @return fecha de inicio
     */
    public LocalDate getComienzoEnRol() {
        return comienzoEnRol;
    }
}
