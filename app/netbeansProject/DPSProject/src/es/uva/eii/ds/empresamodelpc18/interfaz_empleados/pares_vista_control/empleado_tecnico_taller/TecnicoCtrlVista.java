package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_tecnico_taller;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.Controlador;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Implementacion del controlador de la vista de tecnico
 * 
 */
public class TecnicoCtrlVista extends Controlador{
    private TecnicoVista vista;
    
    /**
     * Constructor
     * @param v vista
     */
    public TecnicoCtrlVista (TecnicoVista v){
        vista = v;
    }

    /**
     * Cambia a registrar montaje PC
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException Clase no valida
     * @throws IOException Error
     */
    public void procesarRegistrarMontajePc() throws SQLException, ClassNotFoundException, IOException {
        getGestor().cambiarVistaRegistrarMontajePC();
    }
}

