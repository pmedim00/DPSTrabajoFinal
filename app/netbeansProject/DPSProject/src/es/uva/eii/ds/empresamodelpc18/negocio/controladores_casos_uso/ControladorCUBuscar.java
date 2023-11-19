package es.uva.eii.ds.empresamodelpc18.negocio.controladores_casos_uso;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.Controlador;
import es.uva.eii.ds.empresamodelpc18.registro.daos.DAOEspacioAlmacenamiento;
import java.sql.SQLException;

/**
 * Implementacion del controlador del caso de uso buscar
 * 
 */
public class ControladorCUBuscar extends Controlador{
    
     /**
      * Constructor del controlador del CU buscar espacios disponibles para pcs
      */
      public ControladorCUBuscar(){
        //No ha sido necesario inicializar datos
    }

    /**
     * Consulta espacios disponibles
     * @param tipo que se quiere buscar
     * @return informacion sobre el espacio
     * @throws SQLException consulta no valida
     */
    public String consultarEspaciosDisponibles(int tipo) throws SQLException {
        return DAOEspacioAlmacenamiento.consultarEspaciosDisponiblesPorTipoCPU(tipo);
    
    }

}
