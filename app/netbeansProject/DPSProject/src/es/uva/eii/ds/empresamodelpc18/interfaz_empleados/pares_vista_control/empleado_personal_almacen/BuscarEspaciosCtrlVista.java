package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_personal_almacen;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.Controlador;
import es.uva.eii.ds.empresamodelpc18.negocio.controladores_casos_uso.ControladorCUBuscar;
import java.sql.SQLException;

/**
 *  Implementacion del controlador de la vista buscar espacios
 * 
 */
public class BuscarEspaciosCtrlVista extends Controlador{
    private BuscarEspaciosVista vistaBuscar;
    private ControladorCUBuscar controladorCU;
    
    /**
     * Constructor de la vista buscar espacios
     * @param vista Vista
     */
    public BuscarEspaciosCtrlVista (BuscarEspaciosVista vista) {
        super();
        vistaBuscar= vista;
        controladorCU = new ControladorCUBuscar();
    }
    
    /**
     * Procesar busqueda
     * @throws SQLException consulta no valida
     */
    public void procesarBusqueda() throws SQLException{
        String tipo = vistaBuscar.getTipos();
        int numero = vistaBuscar.getNumEspacios();
        if (numero<=0) {
            vistaBuscar.errorMessage();
        }
        else {
                getGestor().cambiarEspaciosDisponiblesVista(tipo,numero);
            }
    }
    /**
     * Cancelar busqueda
     */
    public void procesarCancelar(){
        getGestor().cambiarVistaIdentificarse("PERSONALALMACEN");
    }
}
