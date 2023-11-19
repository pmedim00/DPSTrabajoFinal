package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.Controlador;
import es.uva.eii.ds.empresamodelpc18.negocio.controladores_casos_uso.ControladorCUIdentificarse;
import es.uva.eii.ds.empresamodelpc18.registro.daos.DAOEmpleado;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Implementacion del controlador de identificarse
 * 
 */
public class IdentificarseCtrlVista extends Controlador{
    private IdentificarseVista vistaIdent;
    private ControladorCUIdentificarse controladorCU;
    
    /**
     * Constructor de vista identificarse
     * @param vista vista
     */
    public IdentificarseCtrlVista (IdentificarseVista vista) {
        super();
        vistaIdent = vista;
        controladorCU = new ControladorCUIdentificarse();
        
    }
    
    /**
     * Comprobacion de la identificacion del empleado
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     * @throws SQLException consulta no valida
     */
    public void procesarIdentificarEmpleado() throws ClassNotFoundException, IOException, SQLException {
        String dni = vistaIdent.getDni();
        String password = vistaIdent.getPassword();
        if (!compruebaStringNoVacio(dni) || !compruebaStringNoVacio(password)) {
            vistaIdent.errorMessage();
        }
        else {
            Boolean comprobacion = DAOEmpleado.compruebaCredenciales(dni, password);
            String rol = controladorCU.identificarEmpleado(dni);
            if (rol == null || !comprobacion) {
                vistaIdent.errorMessage();
                controladorCU.destroy();
            }
            else {
                getGestor().setDni(dni);
                getGestor().cambiarVistaIdentificarse(rol);
            }
        }
    }
    
    /**
     * Comprueba que no la cadena no sea vacia
     * @param cadena a comprobar
     * @return verdadero si no es vacia, falso en caso contrario
     */
    private boolean compruebaStringNoVacio(String cadena) {
        return !cadena.isEmpty();
    }
}
