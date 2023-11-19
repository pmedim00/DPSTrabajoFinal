package es.uva.eii.ds.empresamodelpc18.negocio.controladores_casos_uso;

import es.uva.eii.ds.empresamodelpc18.negocio.modelos.DescripcionComponente;
import es.uva.eii.ds.empresamodelpc18.negocio.modelos.PedidoPC;
import es.uva.eii.ds.empresamodelpc18.registro.daos.DAOComponente;
import es.uva.eii.ds.empresamodelpc18.registro.daos.DAOConfiguracionPC;
import es.uva.eii.ds.empresamodelpc18.registro.daos.DAOPedidoPC;
import es.uva.eii.ds.empresamodelpc18.registro.daos.DAOPC;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 * Implementacion del controlador del caso de uso procesar pedido
 * 
 */
public class ControladorCUProcesarPedidoPC {
     
    PedidoPC pedido;
    ArrayList<DescripcionComponente> descripciones;
    
    /**
     * Constructor del controlador del caso de uso procesar pedido pc
     */
    public ControladorCUProcesarPedidoPC(){
        pedido = null;
        descripciones = new ArrayList<>();
    }
    
    /**
     * Consulta pedidos solicitados
     * @return pedidos solcitados
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public String consultarPedidosSolicitados() throws SQLException, ClassNotFoundException, IOException {
        return DAOPedidoPC.consultarPedidosPCsSolicitados();
    }
    
    /**
     * Consulta datos de un pedido
     * @param pedido sobre el que se consulta
     * @return datos pedido
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public String consultarDatosPedido(String pedido) throws SQLException, ClassNotFoundException, IOException {
        return DAOPedidoPC.consultarDatosPedidoPorId(pedido);
    }
    
    /**
     * Consulta datos de configuracion
     * @param configuracion sobre la que se consulta
     * @return datos configuracion
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public String consultarDatosConfiguracion(int configuracion) throws SQLException, ClassNotFoundException, IOException {
        return DAOConfiguracionPC.consultarConfiguracionPedido(configuracion);
    }
    
    /**
     * Consulta PC disponibles
     * @param configuracion configuracion sobre la que se consulta
     * @return PCs
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public String consultarPCsDisponibles(int configuracion) throws SQLException, ClassNotFoundException, IOException {
        return DAOPC.consultarPCApartirDeConfiguracion(configuracion);
    }
    
    /**
     * Actualiza estado del pedido
     * @param pedido que se actualiza
     * @param num nuevo estado
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public void actualizarEstadoPedidosPC(String pedido, int num) throws SQLException, ClassNotFoundException, IOException {
        DAOPedidoPC.actualizarEstadoPedidosPC(pedido,num);
    }
     
    /**
     * Obtiene componentes en stock
     * @param configuracion para la que se buscan componentes
     * @return lista con el stock
     * @throws SQLException consulta no valida
     */
    public List<Integer> obtenerComponentesStock(int configuracion) throws SQLException {
        String componentesConfig = DAOComponente.consultarComponentes(configuracion);
        
        int cantidadTG = 0;
        int cantidadDiscosDuros = 0;
        int cantidadPlacasBases = 0;
        int cantidadCajas = 0;
        int cantidadProcesadores = 0;
        int cantidadRams = 0;
        
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(componentesConfig);
        JsonReader reader = factory.createReader(sr);
        JsonArray jsonArrayEspacios = reader.readArray();
        ListIterator iterator = jsonArrayEspacios.listIterator();
     
        while (iterator.hasNext()) {

            JsonObject json = (JsonObject) iterator.next();
            int seccion = json.getInt("tipo");

            switch (seccion) {
                case 1:
                    cantidadTG++;
                    break;
                case 2:
                    cantidadDiscosDuros++;
                    break;
                case 3:
                    cantidadPlacasBases++;
                    break;
                case 4:
                    cantidadCajas++;
                    break;
                case 5:
                    cantidadProcesadores++;
                    break;
                default:
                    cantidadRams++;
                    break;
            }
        }
        ArrayList<Integer> cantidades = new ArrayList<>();
        cantidades.add(cantidadTG);
        cantidades.add(cantidadDiscosDuros);
        cantidades.add(cantidadPlacasBases);
        cantidades.add(cantidadCajas);
        cantidades.add(cantidadProcesadores);
        cantidades.add(cantidadRams);
        return cantidades;
    }
    
    /**
     * Obtiene descripciones de los componentes
     * @throws SQLException consulta no valida
     */
    public void obtenerDescripciones() throws SQLException {
        String descripcionComp = DAOComponente.obtenerDescripcionComponentes();
        
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(descripcionComp);
        JsonReader reader = factory.createReader(sr);
        JsonArray jsonArray = reader.readArray();
        ListIterator iterator = jsonArray.listIterator();
        String json=null;
        DescripcionComponente descripcion;
        
        while ( iterator.hasNext() ) {
            try ( StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {

                JsonObject descripcionJsonObject = (JsonObject) iterator.next();
                writer.writeObject(descripcionJsonObject);
                json = stringWriter.toString();
                descripcion = new DescripcionComponente(json);
                descripciones.add(descripcion);
            }catch (Exception ex) {
                Logger.getLogger(ControladorCUProcesarPedidoPC.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
