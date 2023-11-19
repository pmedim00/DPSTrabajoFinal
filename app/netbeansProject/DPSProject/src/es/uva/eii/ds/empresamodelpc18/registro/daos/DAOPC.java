package es.uva.eii.ds.empresamodelpc18.registro.daos;

import es.uva.eii.ds.empresamodelpc18.registro.dbaccess.DBConnection;
import java.io.IOException;
import java.io.StringReader;
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
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 * Implementacion DAO PC
 * 
 */
public class DAOPC {
    
    private static final String IDETIQUETA = "idEtiqueta";
    private static final String RESERVADO = "reservado";
    private static final String FECHAMONTAJE = "fechaMontaje";
    private static final String IDCONFIGURACION = "idConfiguracion";
    private static final String MONTADOPOR = "montadoPor";
    private static final String UBICACION = "ubicacion";
    private static final String IDPEDIDO = "idPedido";
    
    /**
     * Constructor de DAOPC
     */
    private DAOPC(){        
    }
    
    /**
     * Obtiene informacion del PC
     * @return informacion del PC
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public static String consultarPCs() throws SQLException, ClassNotFoundException, IOException {
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement s = db.getStatement("SELECT P.IDETIQUETA, P.RESERVADO, P.FECHAMONTAJE, "
                + "P.IDCONFIGURACION, P.MONTADOPOR, P.IDPEDIDO, P.UBICACION "
                                            + "FROM PC P ");
        ResultSet resultPC = s.executeQuery();
        String pcsJson = obtenerPCJson(resultPC);
        
        db.closeConnection();
        return pcsJson;
    }
    
    /**
     * Consulta informacion del PC segun una configuracion
     * @param configuracion que se va a consultar
     * @return informacion PC
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public static String consultarPCApartirDeConfiguracion(int configuracion) throws SQLException, ClassNotFoundException, IOException {
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement s = db.getStatement("SELECT P.IDETIQUETA, P.RESERVADO, P.FECHAMONTAJE, "
                + "P.IDCONFIGURACION, P.MONTADOPOR, P.IDPEDIDO, P.UBICACION "
                                            + "FROM PC P "
                                            + "WHERE P.IDCONFIGURACION="+ configuracion + " ");
        ResultSet resultPC = s.executeQuery();
        String pcsJson = obtenerPCJson(resultPC);
        
        db.closeConnection();
        return pcsJson;
    }
    
    /**
     * Obtiene informacion del PC en JSON
     * @param resultPC informacion del PC
     * @return informacion en JSON
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    private static String obtenerPCJson(ResultSet resultPC) throws SQLException, ClassNotFoundException, IOException {
        String pcsjson = null;

        int idEtiqueta;
        boolean reservado;            
        LocalDate fechaMontaje;
        int idConfiguracion;
        String montadoPor;
        int ubicacion;
        int idPedido;
    
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        JsonObject pcJson = null;
        
        try {
            JsonArrayBuilder pedidosPCsJson = Json.createArrayBuilder();
            StringWriter stringWriter = new StringWriter();
            JsonWriter writer = Json.createWriter(stringWriter);
            while(resultPC.next()){
                idEtiqueta = resultPC.getInt(IDETIQUETA);
                reservado = resultPC.getBoolean(RESERVADO);
                String fechaAuxiliar = resultPC.getString(FECHAMONTAJE);
                fechaMontaje = LocalDate.parse(fechaAuxiliar);
                idConfiguracion = resultPC.getInt(IDCONFIGURACION);
                montadoPor = resultPC.getString(MONTADOPOR);
                ubicacion = resultPC.getInt(UBICACION);
                idPedido = resultPC.getInt(IDPEDIDO);

               pcJson = Json.createObjectBuilder()
                    .add(IDETIQUETA, idEtiqueta)
                    .add(RESERVADO, reservado)
                    .add(FECHAMONTAJE, fechaMontaje.toString())
                    .add(IDCONFIGURACION, idConfiguracion)
                    .add(MONTADOPOR, montadoPor)
                    .add(UBICACION, ubicacion)
                    .add(IDPEDIDO, idPedido)
                    .build();
               pedidosPCsJson.add(pcJson);
            }
                JsonArray pedidosJson = pedidosPCsJson.build();
                writer.writeArray(pedidosJson);
                pcsjson = stringWriter.toString();
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOPedidoPC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pcsjson;
    }
    
    /**
     * Agregar informacion del PC
     * @param json informacion del PC
     */
    public static void registrarPC(String json) {
       try {
            JsonReaderFactory factory = Json.createReaderFactory(null);
            StringReader sr = new StringReader(json);
            JsonReader reader = factory.createReader(sr);
            JsonObject pedidoJson = reader.readObject();

            int idEtiqueta = pedidoJson.getInt(IDETIQUETA);
            boolean reservado = pedidoJson.getBoolean(RESERVADO);
            String fechaAuxiliar = pedidoJson.getString(FECHAMONTAJE);
            int idConfiguracion = pedidoJson.getInt(IDCONFIGURACION);
            String montadoPor = pedidoJson.getString(MONTADOPOR);

            DBConnection db = DBConnection.getInstance();
            db.openConnection();
            PreparedStatement s = db.getStatement("INSERT INTO PC(idEtiqueta,reservado,fechaMontaje,idConfiguracion,montadoPor) "
                    + "VALUES (?, ?, ?, ?, ?)");
            s.setInt(1, idEtiqueta);
            s.setBoolean(2, reservado);
            s.setString(3, fechaAuxiliar);
            s.setInt(4, idConfiguracion);
            s.setString(5, montadoPor);
            s.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOPC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Agrega el PC al pedido
     * @param idEtiqueta del pedido
     * @param idPedido del PC
     * @throws SQLException consulta no valida
     */
    public static void asociarPCAPedido(int idEtiqueta, int idPedido) throws SQLException {
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement stmt = db.getStatement("UPDATE PC SET IDPEDIDO="+ idPedido + "WHERE IDETIQUETA="+ idEtiqueta + " ");
        stmt.executeUpdate();
        
        db.closeConnection();
    }
    
}
