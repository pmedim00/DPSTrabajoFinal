package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_gerente_ventas;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.Controlador;
import es.uva.eii.ds.empresamodelpc18.negocio.controladores_casos_uso.ControladorCUProcesarPedidoPC;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 * Implementacion del controlador de pedidos solicitados
 * 
 */
public class PedidosSolicitadosCtrlVista extends Controlador{
    private PedidosSolicitadosVista vistaPedidos;
    private ControladorCUProcesarPedidoPC controladorCU;
    
    /**
     * Constructor de vista pedidos solicitados
     * @param vista vista
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public  PedidosSolicitadosCtrlVista (PedidosSolicitadosVista vista) throws SQLException, ClassNotFoundException, IOException {
        super();
        vistaPedidos = vista;
        controladorCU = new ControladorCUProcesarPedidoPC();
        
    }
    
    /**
     * Cancelar busqueda
     */
    public void procesarCancelar(){
        getGestor().cambiarVistaIdentificarse("GERENTEVENTAS");
    }
    
    /**
     * Cambia a vista de stock de componentes 
     * @param configuracion que se va a mostrar
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
     public void procesaComponentesStock(int configuracion) throws SQLException, ClassNotFoundException, IOException {
        getGestor().cambiarComponentesStockVista(configuracion);
    }
    /**
     * Procesa lista de pedidos solicitados
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
   public void procesarListaDePedidos() throws SQLException, ClassNotFoundException, IOException {
        String pedidosJSON = controladorCU.consultarPedidosSolicitados();
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(pedidosJSON);
        JsonReader reader = factory.createReader(sr);
        JsonArray pedidos = reader.readArray();
        int idPedido=0;
        for(int i=0; i<pedidos.size(); i++){
            JsonObject pedidoObject = (JsonObject) pedidos.get(i);
            idPedido = pedidoObject.getInt("idPedido");
            vistaPedidos.agregarAListaPedidos(idPedido);
        }
        actualizarTabla(vistaPedidos.getPedidoSeleccionado());
      }

   /**
    * Actualiza la tabla
    * @param pedidoSeleccionado pedido sobre el que actualizar
    * @throws SQLException consulta no valida
    * @throws ClassNotFoundException clase no valida
    * @throws IOException error
    */
    public void actualizarTabla(String pedidoSeleccionado) throws SQLException, ClassNotFoundException, IOException {
        String pedidoJSON = controladorCU.consultarDatosPedido(String.valueOf(pedidoSeleccionado));
        
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(pedidoJSON);
        JsonReader reader = factory.createReader(sr);
        JsonArray pedidoArray = reader.readArray(); 
        
        JsonObject pedidoObject = (JsonObject) pedidoArray.get(0);
        
        int idPedido = pedidoObject.getInt("idPedido");
        int cantidad = pedidoObject.getInt("cantidadSolicitada");
        String fechaPedido = pedidoObject.getString("fechaPedido");
        int configuracion = pedidoObject.getInt("configuracionSolicitada");
        String encargadoPor = pedidoObject.getString("encargadoPor");
        String configuracionJSON = controladorCU.consultarDatosConfiguracion(configuracion);
        
        StringReader sr2 = new StringReader(configuracionJSON);
        JsonReader reader2 = factory.createReader(sr2);
        JsonObject configuracionObject= reader2.readObject(); 
        
        int tipoCPU = configuracionObject.getInt("tipoCPU");
        double velocidadCPU = configuracionObject.getJsonNumber("velocidadCPU").doubleValue();
        int capacidadRAM = configuracionObject.getInt("capacidadRAM");
        int capacidadDD = configuracionObject.getInt("capacidadDD");
        double velocidadTarjetaGrafica = configuracionObject.getJsonNumber("velocidadTarjetaGrafica").doubleValue();
        int memoriaTarjetaGrafica = configuracionObject.getInt("memoriaTarjetaGrafica");
        
        vistaPedidos.mostrarDatosPedido(cantidad, tipoCPU, velocidadCPU, capacidadRAM, capacidadDD, velocidadTarjetaGrafica, memoriaTarjetaGrafica, fechaPedido, encargadoPor); 
    }

    /**
     * Comprueba pcs disponibles 
     * @param configuracion sobre la que hacer la consulta
     * @return cont cantidad de pcs disponibles
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public int comprobarPCsDisponibles(int configuracion) throws SQLException, ClassNotFoundException, IOException {
        String pcJSON = controladorCU.consultarPCsDisponibles(configuracion);
        int cont=0;
        
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(pcJSON);
        JsonReader reader = factory.createReader(sr);
        JsonArray pcs = reader.readArray();
        
        for(int i=0; i<pcs.size(); i++){
            JsonObject pcObject = (JsonObject) pcs.get(i);
            if(configuracion == pcObject.getInt("idConfiguracion")){
                if (!pcObject.getBoolean("reservado")) {
                    cont++;
                }
            }
        }
     
     return cont;
    }
    /**
     * Obtiene la configuracion del pedido
     * @param pedido sobre el que se hace la consulta
     * @return configuracion
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public int obtenerConfiguracionPedido(String pedido) throws SQLException, ClassNotFoundException, IOException {
        String pedidosJSON = controladorCU.consultarPedidosSolicitados();
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(pedidosJSON);
        JsonReader reader = factory.createReader(sr);
        JsonArray pedidos = reader.readArray();
        int configuracion= 0;
        for(int i=0; i<pedidos.size(); i++){
            JsonObject pedidoObject = (JsonObject) pedidos.get(i);
            if(Integer.parseInt(pedido) == (pedidoObject.getInt("idPedido"))){
                configuracion = pedidoObject.getInt("configuracionSolicitada");
            }
        }
        return configuracion;
    }
    /**
     * Obtiene la cantidad del pedido
     * @param pedido que se va a consultar
     * @return cantidad
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public int obtenerCantidadPedido(String pedido) throws SQLException, ClassNotFoundException, IOException {
        String pedidosJSON = controladorCU.consultarPedidosSolicitados();
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(pedidosJSON);
        JsonReader reader = factory.createReader(sr);
        JsonArray pedidos = reader.readArray();
        int cantidad= 0;
        for(int i=0; i<pedidos.size(); i++){
            JsonObject pedidoObject = (JsonObject) pedidos.get(i);
            if(Integer.parseInt(pedido) == (pedidoObject.getInt("idPedido"))){
                cantidad = pedidoObject.getInt("cantidadSolicitada");
            }
        }
        return cantidad;
    }
    /**
     * Actualiza el estado del pedido
     * @param pedido que se actualiza
     * @param num valor del nuevo estado
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public void cambiarEstadoPedido(String pedido, int num) throws SQLException, ClassNotFoundException, IOException {
            controladorCU.actualizarEstadoPedidosPC(pedido, num);
    }
}
