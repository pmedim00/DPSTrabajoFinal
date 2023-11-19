package es.uva.eii.ds.empresamodelpc18.negocio.controladores_casos_uso;

import es.uva.eii.ds.empresamodelpc18.negocio.modelos.Empleado;
import es.uva.eii.ds.empresamodelpc18.negocio.modelos.Sesion;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Implementacion del contradolor de identificarse
 * 
 */
public class ControladorCUIdentificarse {
    
    private Empleado empleado;
    
    /**
     * Constructor del controlador del caso de uso identificarse
     */
    public ControladorCUIdentificarse(){
        empleado = null;
    }
    /**
     * Identifica al empleado
     * @param dni dni del empleado
     * @return empleado identificado
     * @throws ClassNotFoundException parametros no validos
     * @throws IOException parametros no validos
     * @throws SQLException parametros no validos
     */ 
    public String identificarEmpleado(String dni) throws ClassNotFoundException, IOException, SQLException{
       
       Sesion s = Sesion.getFirstInstance(dni);
       empleado = s.getEmpleado();
       if(empleado != null){
          boolean activo = empleado.estaActivo();
            if (activo) {
                return empleado.obtenerRolActual().getRol().name();
            }
       }
       Sesion.destroySesion();
       return null;
   }
    /**
     * Elimina la sesion
     */
    public void destroy(){
        Sesion.destroySesion();
    }
}
