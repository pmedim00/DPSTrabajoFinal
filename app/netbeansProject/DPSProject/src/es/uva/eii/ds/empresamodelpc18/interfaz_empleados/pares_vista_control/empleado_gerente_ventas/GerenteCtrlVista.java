package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_gerente_ventas;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.Controlador;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Implementacion del controlador de vista de gerente
 * 
 */
public class GerenteCtrlVista extends Controlador{
    private GerenteVista vista;
    
    /**
     * Constructor de vista gerente
     * @param v vista
     */
    public GerenteCtrlVista (GerenteVista v){
        vista = v;
    }

    /**
     * Cambia vista
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    void procesarPedido() throws SQLException, ClassNotFoundException, IOException {
        getGestor().cambiarPedidosSolicitadosVista();
    }
}
