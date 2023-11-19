package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import es.uva.eii.ds.empresamodelpc18.registro.daos.DAOEmpleado;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Implementacion de sesion, que permite guardar los datos del empleado que se ha identificado
 * 
 */
public class Sesion {
 
    private static Sesion sesion;
    private Empleado empleado;
    
    /**
     * Construye la sesion
     * @param dni Dni del usuario a identificar
     * @return sesion Sesion iniciada
     * @throws ClassNotFoundException No existe empleado
     * @throws IOException Parametros no validos
     * @throws SQLException Parametros no validos
     * @throws JSONException Parametros no validos
     */
    private Sesion(String dni) throws ClassNotFoundException, SQLException {
        String empleadoJSON = DAOEmpleado.obtenerEmpleado(dni);
        if (empleadoJSON == null) {
            setEmpleado(null);
        }
        else {
            setEmpleado(new Empleado(empleadoJSON));
        }
    }
      
    /**
     * Genera la sesion por primera vez
     * @param dni Dni del usuario a identificar
     * @return sesion Sesion iniciada
     * @throws ClassNotFoundException No existe empleado
     * @throws SQLException Parametros no validos
     */
    public static Sesion getFirstInstance(String dni) throws ClassNotFoundException, SQLException{
        if (sesion != null) {
            throw new IllegalStateException("Sesion ya abierta");
        }
        setSesion(new Sesion(dni));
        return sesion;
      }
    /**
     * Obtiene sesion iniciada
     * @return sesion Sesion iniciada
     */
    public static Sesion getInstance() {
        if (sesion == null){
            throw new IllegalStateException("Sesion no iniciada");
        }
        return sesion;
      }
    
    /**
     * Elimina sesion actual
     * 
     */
    public static void destroySesion() {
        setSesion(null);
    }
   
    /**
     * Establece sesion iniciada
     * @param sesion Sesion ha establecer
     */
    private static void setSesion(Sesion sesion) {
        Sesion.sesion = sesion;
    }
    
    /**
     * Obtiene empleado
     * @return empleado Empleado consultado
     */
    public Empleado getEmpleado() {
        return empleado;
    }
    
    /**
     * Asigna empleado
     * @return empleado Empleado actualizado
     */
    private void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
 
}
