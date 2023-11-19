package es.uva.eii.ds.empresamodelpc18.negocio.controladores_casos_uso;

import es.uva.eii.ds.empresamodelpc18.registro.daos.DAOComponente;
import es.uva.eii.ds.empresamodelpc18.negocio.modelos.Componente;
import es.uva.eii.ds.empresamodelpc18.negocio.modelos.PC;
import es.uva.eii.ds.empresamodelpc18.negocio.modelos.PedidoPC;
import es.uva.eii.ds.empresamodelpc18.registro.daos.DAOConfiguracionPC;
import es.uva.eii.ds.empresamodelpc18.registro.daos.DAOPC;
import es.uva.eii.ds.empresamodelpc18.registro.daos.DAOPedidoPC;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * Implementacion del contradolor de montaje PC
 * 
 */
public class ControladorCURegistrarMontajePC {
    
    ArrayList<PedidoPC> pedidos;
    ArrayList<Componente> componentes;
    ArrayList<Componente> componentesAgregados;
    PC pc;
    PedidoPC pedido;
    
    /**
     * Constructor del controlador del caso de uso registrar montaje pc
     */
    public ControladorCURegistrarMontajePC (){
        pedidos = new ArrayList<>();
        componentes = new ArrayList<>();
        componentesAgregados = new ArrayList<>();
        pc= null;
        pedido=null;
    }
    
    /**
     * Consulta configuraciones
     * @return configuracion
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
     public String consultarConfiguraciones() throws SQLException, ClassNotFoundException, IOException {
        return DAOConfiguracionPC.consultarConfiguraciones();
    }

     /**
      * Consultar pedidos de una configuracion
      * @param configuracion que se consulta
      * @return pedidos
      * @throws SQLException consula no valida
      * @throws ClassNotFoundException clase no valida
      * @throws IOException error
      */
    public String consultarPedidosPorConfiguracion(int configuracion) throws SQLException, ClassNotFoundException, IOException {
       String pedidosJson = DAOPedidoPC.consultarPedidosPorConfiguracion(configuracion);
       
       JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(pedidosJson);
        JsonReader reader = factory.createReader(sr);
        JsonArray jsonArray = reader.readArray();
        ListIterator iterator = jsonArray.listIterator();
        String json=null;
        
        while ( iterator.hasNext() ) {
            try ( StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {

                JsonObject pedidoJsonObject = (JsonObject) iterator.next();
                writer.writeObject(pedidoJsonObject);
                json = stringWriter.toString();
                pedido = new PedidoPC(json);
                pedidos.add(pedido);
            }catch (Exception ex) {
                Logger.getLogger(ControladorCURegistrarMontajePC.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pedidosJson;
    }
    /**
     * Consulta PCs existentes
     * @return pcs
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public String consultarPCsExistentes() throws SQLException, ClassNotFoundException, IOException {
        return DAOPC.consultarPCs();
    }

    /**
     * Crear un nuevo pc
     * @param etiqueta del nuevo pc
     * @param idConfiguracion configuracion del nuevo pc
     * @param tecnicoTaller dnni del empleado
     */
    public void crearNuevoPC(int etiqueta, int idConfiguracion, String tecnicoTaller) {
        pc = new PC(etiqueta, idConfiguracion, tecnicoTaller);
        pc.setReservado(true);
        String json = pc.getJson();
        DAOPC.registrarPC(json);
    }
    
    /**
     * Consulta componentes disponibles
     * @return componentes
     * @throws SQLException consulta no valida
     */
    public String consultarComponentesDisponibles() throws SQLException {
        return DAOComponente.consultarComponentes();
    }
    
    /**
     * Agregar componentes
     * @param idEtiquetaComponente etiqueta del componente que se agrega
     * @param idEtiquetaPC etiqueta del pc donde se agrega
     * @throws IOException error
     * @throws SQLException consulta no valida
     */
    public void agregarComponente(int idEtiquetaComponente, int idEtiquetaPC) throws IOException, SQLException{
        String componenteJson = consultarComponentesDisponibles();
        
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(componenteJson);
        JsonReader reader = factory.createReader(sr);
        JsonArray jsonArray = reader.readArray();
        ListIterator iterator = jsonArray.listIterator();
        String json=null;
        
        while ( iterator.hasNext() ) {
            try ( StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {

                JsonObject componenteJsonObject = (JsonObject) iterator.next();
                writer.writeObject(componenteJsonObject);
                json = stringWriter.toString();
                Componente componente = new Componente(json);
                componentes.add(componente);
                
                if(componente.getIdEtiqueta() == idEtiquetaComponente && !componente.getReservado()){
                    componente.setReservado(true);
                    componente.setEtiquetaPC(idEtiquetaPC);
                    componentesAgregados.add(componente);
                }
            } catch (Exception ex) {
                Logger.getLogger(ControladorCURegistrarMontajePC.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Comprueba etiqueta
     * @param etiqueta que se comprueba
     * @return true si la etiqueta ya esta agregada, false en caso contrario
     */
    public boolean comprobarIntroducidaEtiquetaRepetida(int etiqueta) {
        for(int i=0; i<componentesAgregados.size(); i++){
            if(componentesAgregados.get(i).getIdEtiqueta() == etiqueta){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Actualizar 
     * @param row Fila que se actualiza
     * @throws SQLException consulta no valida
     */
    public void actualizarBD(int row) throws SQLException {
        for(int i=0; i<componentesAgregados.size(); i++){
            DAOComponente.registrarComponenteReservado(componentesAgregados.get(i).getIdEtiqueta(), componentesAgregados.get(i).getEtiquetaPC()); 
        }
        
        pedido = pedidos.get(row);
        DAOPC.asociarPCAPedido(pc.getIdEtiqueta(), pedido.getIdPedido());
        DAOPedidoPC.actualizarEstadoCompletado(pedido.getIdPedido());
    }
    
    /**
     * Obtiene la cantidad de componentes de una configuracion
     * @param idConfiguracion que se consulta
     * @return cantidad
     * @throws SQLException consulta no valida
     */
    public int obtenerCantidadComponentesConfiguracion(int idConfiguracion) throws SQLException {
        String componentesEnConfiguracionJSON = DAOComponente.consultarComponentesEnConfiguracion(idConfiguracion);
      
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(componentesEnConfiguracionJSON);
        JsonReader reader = factory.createReader(sr);
        JsonArray configuracionesArray = reader.readArray();
        return configuracionesArray.size();
    }

    /**
     * Obtiene la cantidad de componentes agregados
     * @return cantidad
     */
    public int obtenerCantidadComponentesAgregados() {
       return componentesAgregados.size();
    }
    
    /**
     * Obtiene el identificador de la descripcion del componente
     * @param idConfiguracion sobre el cual se busca los componentes
     * @return idDescripcion del componente
     * @throws SQLException consulta no valida
     */
    public String obtenerIdDescripcion(int idConfiguracion) throws SQLException {
        return DAOComponente.consultarComponentesEnConfiguracion(idConfiguracion);    
    }
}
