package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_gerente_ventas;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.Controlador;
import es.uva.eii.ds.empresamodelpc18.negocio.controladores_casos_uso.ControladorCUProcesarPedidoPC;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementacion del controlador de la vista stock de componentes
 * 
 */
public class ComponentesStockCtrlVista extends Controlador{
     private ComponentesStockVista vistaComponentes;
     private ControladorCUProcesarPedidoPC controladorCU;
     List<Integer> cantidades;
     /**
      * Constructor de vista stock de componentes
      * @param vista vista
      * @throws SQLException consulta no valida
      */
    public ComponentesStockCtrlVista (ComponentesStockVista vista) throws SQLException {
        super();
        vistaComponentes = vista;
        controladorCU = new ControladorCUProcesarPedidoPC();
    }
    
    /**
     * Cambia de vista
     */
    public void confirmar() {
        getGestor().cambiarVistaIdentificarse("GERENTEVENTAS");
    }
    /**
     * Procesa la lista de configuraciones
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public void procesarListaDeConfiguracionesDisponibles() throws SQLException, ClassNotFoundException, IOException {
       vistaComponentes.mostrarCantidadesDisponiblesTiposConf(cantidades.get(0), cantidades.get(1), cantidades.get(2),
               cantidades.get(3), cantidades.get(4), cantidades.get(5));
    }

    /**
     * Obtiene los componentes en stock
     * @param configuracion configuracion de la que se buscan componentes
     * @throws SQLException consulta no valida
     */
    public void obtenerComponentesStock(int configuracion) throws SQLException {
        cantidades = controladorCU.obtenerComponentesStock(configuracion);
    }
}
