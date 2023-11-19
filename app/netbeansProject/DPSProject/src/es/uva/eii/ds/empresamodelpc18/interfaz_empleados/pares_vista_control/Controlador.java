package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.GestorDeInterfazDeUsuario;

/**
 * Clase abstracta del controlador
 * 
 */
public abstract class Controlador {
    
    private GestorDeInterfazDeUsuario gestorInterfaz;
    /**
     * Constructor
     */
    public Controlador() {
        gestorInterfaz = GestorDeInterfazDeUsuario.getInstance();
    }
    
    /**
     * Obtiene el gestor de interfaces
     * @return gestor de interfaces
     */
    public GestorDeInterfazDeUsuario getGestor() {
        return gestorInterfaz;
    }
    
}
