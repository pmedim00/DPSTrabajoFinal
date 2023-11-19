package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_personal_almacen;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.Controlador;

/**
 * Implementacion del controlador de la vista de almacen
 * 
 */
public class AlmacenCtrlVista extends Controlador{
    private AlmacenVista vista;
    
    /**
     * Constructor de vista almacen
     * @param v vista
     */
    public AlmacenCtrlVista (AlmacenVista v){
        vista = v;
    }
    
    /**
     * Cambia vista a busacar espacios
     */
    public void procesarBuscarEspacioPc() {
        getGestor().cambiarVistaBuscarEspacioPC();
    } 
}
