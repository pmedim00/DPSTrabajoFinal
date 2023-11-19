package es.uva.eii.ds.empresamodelpc18.registro.daos;

import es.uva.eii.ds.empresamodelpc18.registro.dbaccess.DBConnection;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 * Implementacion del DAO Pedido PC
 * 
 */
public class DAOPedidoPC {

    /**
     * Constructor de DAOPedidoPC
     */
    private DAOPedidoPC(){
    }
    
    private static final String SELECCIONAR_DATOS_PEDIDO =
            "SELECT P.IDPEDIDO, P.CANTIDADSOLICITADA, P.FECHAPEDIDO, P.ESTADO, P.CONFIGURACIONSOLICITADA, P.ENCARGADOPOR ";
    private static final String TABLA_PEDIDOS = "FROM PEDIDOPC P ";
    
    /**
     * Consulta los pedido solcitados
     * @return informacion de los pedidos solicitados
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public static String consultarPedidosPCsSolicitados() throws SQLException, ClassNotFoundException, IOException{     
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement s = db.getStatement(SELECCIONAR_DATOS_PEDIDO 
                                            + TABLA_PEDIDOS
                                            + "WHERE P.ESTADO=1");
        ResultSet resultPedidosPCs = s.executeQuery();
        String pedidosPCsSolicitadosJson = obtenerPedidosPCsSolicitadosJson(resultPedidosPCs);
        
        db.closeConnection();
        return pedidosPCsSolicitadosJson;
    }
    
    /**
     * Consulta informacion del pedido segun su identificador
     * @param idPedido identificador del pedido
     * @return informacion del pedido
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public static String consultarDatosPedidoPorId(String idPedido) throws SQLException, ClassNotFoundException, IOException{     
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement s = db.getStatement(SELECCIONAR_DATOS_PEDIDO
                                            + TABLA_PEDIDOS
                                            + "WHERE P.IDPEDIDO=" + idPedido + " ");
        ResultSet resultPedido = s.executeQuery();
        String pedidoJson = obtenerPedidosPCsSolicitadosJson(resultPedido);
        
        db.closeConnection();
        return pedidoJson;
    }
    
    /**
     * Consulta informacion del pedido segun la configuracion
     * @param configuracion a consultar
     * @return informacion del pedido
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public static String consultarPedidosPorConfiguracion(int configuracion) throws SQLException, ClassNotFoundException, IOException {
       DBConnection db = DBConnection.getInstance();
       db.openConnection();
       PreparedStatement s = db.getStatement(SELECCIONAR_DATOS_PEDIDO
                                            + TABLA_PEDIDOS
                                            + "WHERE P.ESTADO=2 AND P.CONFIGURACIONSOLICITADA="+ configuracion + " ");
       ResultSet resultPedidosPCs = s.executeQuery();
       String pedidosPCsSolicitadosJson = obtenerPedidosPCsSolicitadosJson(resultPedidosPCs);
        
       db.closeConnection();
       return pedidosPCsSolicitadosJson;
    }
    
    /**
     * Obtiene datos del pedido en JSON
     * @param resultPedidosPCs informacion del pedido
     * @return informacion en JSON
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    private static String obtenerPedidosPCsSolicitadosJson(ResultSet resultPedidosPCs) throws SQLException, ClassNotFoundException, IOException {
        String pedidosSolicitadosjson = null;

        int idPedido;
        int cantidadSolicitada;
        LocalDate fechaPedido;
        int estado;
        int configuracionSolicitada;
        String encargadoPor;

        DBConnection db = DBConnection.getInstance();
        db.openConnection();

        try {
            JsonArrayBuilder pedidosPCsJsonB = Json.createArrayBuilder();
            StringWriter stringWriter = new StringWriter();
            JsonWriter writer = Json.createWriter(stringWriter);
            while (resultPedidosPCs.next()) {
                idPedido = resultPedidosPCs.getInt("idPedido");
                cantidadSolicitada = resultPedidosPCs.getInt("cantidadSolicitada");
                String fechaAuxiliar = resultPedidosPCs.getString("fechaPedido");
                fechaPedido = LocalDate.parse(fechaAuxiliar);
                estado = resultPedidosPCs.getInt("estado");
                configuracionSolicitada = resultPedidosPCs.getInt("configuracionSolicitada");
                encargadoPor = resultPedidosPCs.getString("encargadoPor");

                JsonObject pedidoJson = Json.createObjectBuilder()
                        .add("idPedido", idPedido)
                        .add("cantidadSolicitada", cantidadSolicitada)
                        .add("fechaPedido", fechaPedido.toString())
                        .add("estado", estado)
                        .add("configuracionSolicitada", configuracionSolicitada)
                        .add("encargadoPor", encargadoPor)
                        .build();
                pedidosPCsJsonB.add(pedidoJson);
            }
            JsonArray pedidosJson = pedidosPCsJsonB.build();
            writer.writeArray(pedidosJson);
            pedidosSolicitadosjson = stringWriter.toString();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPedidoPC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pedidosSolicitadosjson;
    }
    
    /**
     * Actualiza el estado del pedido
     * @param id identificador del pedido
     * @param num nuevo estado, 1 solicitado, 2 en proceso, 3 completado, 4 enviado, 5 entregado
     * @throws SQLException consulta no valida
     */
     public static void actualizarEstadoPedidosPC(String id, int num) throws SQLException{     
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement s = db.getStatement("UPDATE PEDIDOPC "
                                            + "SET ESTADO="+num+" "
                                            + "WHERE IDPEDIDO="+id+"");
        s.executeUpdate();
        db.closeConnection();
    }
     
     /**
      * Actualiza estado a completado
      * @param idPedido identificador del pedido
      * @throws SQLException consulta no valida
      */
    public static void actualizarEstadoCompletado(int idPedido) throws SQLException {
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement stmt = db.getStatement("UPDATE PEDIDOPC SET ESTADO=3 WHERE IDPEDIDO="+ idPedido + " ");
        stmt.executeUpdate();
        
        db.closeConnection();
    }
}
