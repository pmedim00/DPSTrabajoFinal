package es.uva.eii.ds.empresamodelpc18.registro.daos;

import es.uva.eii.ds.empresamodelpc18.registro.dbaccess.DBConnection;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

/**
 * Implementacion de DAOComponente
 * 
 */
public class DAOComponente {
    
    private static final String IDDESCRIPCION = "idDescripcion";
    private static final String SELECT = "SELECT * ";
    
    /**
     * Constructor de DAOComponente
     */
    private DAOComponente(){
    }
    
    /**
     * Consulta informacion del componente
     * @return informacion
     * @throws SQLException consulta no valida
     */
    public static String consultarComponentes() throws SQLException{     
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement stmt = db.getStatement(SELECT
                + "FROM COMPONENTE C ");
        ResultSet resultComponentes = stmt.executeQuery();
        String componentesJson = obtenerComponenteJson(resultComponentes);
        
        db.closeConnection();
        return componentesJson;
    }
    
    /**
     * Actualiza informacion del componente
     * @param idEtiqueta etiqueta del componente
     * @param etiquetaPC etiqueta del pc
     * @throws SQLException consulta no valida
     */
    public static void registrarComponenteReservado(int idEtiqueta, int etiquetaPC) throws SQLException {
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement stmt = db.getStatement("UPDATE COMPONENTE SET RESERVADO=TRUE, ETIQUETAPC="+ etiquetaPC + "WHERE IDETIQUETA="+ idEtiqueta + " ");
        stmt.executeUpdate();
        
        db.closeConnection();
    }
    
    /**
     * Creacion del JSON de los componentes
     * @param datosComponentes informacion de los componentes
     * @return informacion en JSON
     */
    private static String obtenerComponenteJson(ResultSet datosComponentes) {
        String componentesjson = null;
        int idEtiqueta;
        Boolean reservado;
        int idDescripcion;
        int etiquetaPC;
        int recibidoEnCompra;
        int ubicacion;
    
        try {
             JsonArrayBuilder componentesJsonB = Json.createArrayBuilder();
             StringWriter stringWriter = new StringWriter();
             JsonWriter writer = Json.createWriter(stringWriter);
             while (datosComponentes.next()) {
                idEtiqueta = datosComponentes.getInt("idEtiqueta");
                String fechaAuxiliar = datosComponentes.getString("fechaEntrada");
                reservado = datosComponentes.getBoolean("reservado");
                idDescripcion = datosComponentes.getInt(IDDESCRIPCION );
                etiquetaPC = datosComponentes.getInt("etiquetaPC");
                recibidoEnCompra = datosComponentes.getInt("recibidoEnCompra");
                ubicacion = datosComponentes.getInt("ubicacion");

                 JsonObjectBuilder componenteJsonB = Json.createObjectBuilder()
                         .add("idEtiqueta", idEtiqueta)
                         .add("fechaEntrada", fechaAuxiliar)
                         .add("reservado", reservado)
                         .add(IDDESCRIPCION , idDescripcion)
                         .add("etiquetaPC", etiquetaPC)
                         .add("recibidoEnCompra", recibidoEnCompra)
                         .add("ubicacion", ubicacion);

                 JsonObject espacioJson = componenteJsonB.build();
                 componentesJsonB = componentesJsonB.add(espacioJson);

             }
             JsonArray espaciosJson = componentesJsonB.build();
             writer.writeArray(espaciosJson);
             componentesjson = stringWriter.toString();
         } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return componentesjson;
    } 
    
    /**
     * Consulta componentes en configuracion
     * @param idConfiguracion Configuracion a consultar
     * @return informacion de los componentes
     * @throws SQLException consulta no valida
     */
    public static String consultarComponentesEnConfiguracion(int idConfiguracion) throws SQLException {
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement stmt = db.getStatement(SELECT
                + "FROM COMPONENTESENCONFIGURACION C "
                + "WHERE IDCONFIGURACION="+ idConfiguracion +" ");
        ResultSet resultComponentes = stmt.executeQuery();
        String componentesJson = obtenerComponenteEnConfiguracionJson(resultComponentes);
        
        db.closeConnection();
        return componentesJson;
    }
    
     /**
     * Consulta componentes en configuracion
     * @param idConfiguracion Configuracion a consultar
     * @return informacion de los componentes
     * @throws SQLException consulta no valida
     */
    public static String consultarComponentes(int idConfiguracion) throws SQLException {
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement stmt = db.getStatement("SELECT DC.TIPO "
                + "FROM COMPONENTESENCONFIGURACION CC NATURAL JOIN DESCRIPCIONCOMPONENTE DC NATURAL JOIN COMPONENTE C "
                + "WHERE C.IDDESCRIPCION = DC.IDDESCRIPCION AND CC.IDCONFIGURACION="+ idConfiguracion +" ");
        ResultSet resultComponentes = stmt.executeQuery();
        String componentesJson = obtenerTipo(resultComponentes);
       
        db.closeConnection();
        return componentesJson;
    }
    
    /**
     * Obtiene tipo del componente
     * @param resultComponentes informacion de componente
     * @return 1 tarjeta grafica, 2 disco duro, 3 placa base, 4 caja, 5 procesador, 6 ram
     */
     private static String obtenerTipo(ResultSet resultComponentes) {
        String componentesjson = null;
        
        int tipo;
    
        try {
             JsonArrayBuilder componentesJsonB = Json.createArrayBuilder();
             StringWriter stringWriter = new StringWriter();
             JsonWriter writer = Json.createWriter(stringWriter);
             while (resultComponentes.next()) {
                tipo = resultComponentes.getInt("tipo");

                 JsonObjectBuilder componenteJsonB = Json.createObjectBuilder()
                         .add("tipo", tipo);

                 JsonObject espacioJson = componenteJsonB.build();
                 componentesJsonB = componentesJsonB.add(espacioJson);

             }
             JsonArray espaciosJson = componentesJsonB.build();
             writer.writeArray(espaciosJson);
             componentesjson = stringWriter.toString();
         } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return componentesjson;
    }
    
    /**
     * Creacion del JSON de los componentes en configuracion
     * @param resultComponentes informacion de los componentes
     * @return informacion en JSON
     */
    private static String obtenerComponenteEnConfiguracionJson(ResultSet resultComponentes) {
        String componentesjson = null;
        
        int idDescripcion;
        int idConfiguracion;
    
        try {
             JsonArrayBuilder componentesJsonB = Json.createArrayBuilder();
             StringWriter stringWriter = new StringWriter();
             JsonWriter writer = Json.createWriter(stringWriter);
             while (resultComponentes.next()) {
                idDescripcion = resultComponentes.getInt(IDDESCRIPCION );
                idConfiguracion = resultComponentes.getInt("idConfiguracion");

                 JsonObjectBuilder componenteJsonB = Json.createObjectBuilder()
                         .add(IDDESCRIPCION , idDescripcion)
                         .add("idConfiguracion", idConfiguracion);

                 JsonObject espacioJson = componenteJsonB.build();
                 componentesJsonB = componentesJsonB.add(espacioJson);

             }
             JsonArray espaciosJson = componentesJsonB.build();
             writer.writeArray(espaciosJson);
             componentesjson = stringWriter.toString();
         } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return componentesjson;
    }
    
    /**
     * Obtiene descripcion de los componentes
     * @return informacion 
     * @throws SQLException consulta no valida 
     */
    public static String obtenerDescripcionComponentes() throws SQLException {
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement stmt = db.getStatement(SELECT
                + "FROM DESCRIPCIONCOMPONENTE DC ");
        ResultSet resultComponentes = stmt.executeQuery();
        String componentesJson = obtenerDescripcionComponentesJson(resultComponentes);
        
        db.closeConnection();
        return componentesJson;
    }
    
    /**
     * Obtiene la descripcion de los componentes en json
     * @param resultComponentes informacion de los componentes
     * @return informacion en JSON
     */
    private static String obtenerDescripcionComponentesJson(ResultSet resultComponentes) {
        String descripcionJSON = null;
        
        int idDescripcion;
        int tipo;
        String marca;
        String modelo;
        String caracteristicasTecnicas;
    
        try {
             JsonArrayBuilder descripcionJsonB = Json.createArrayBuilder();
             StringWriter stringWriter = new StringWriter();
             JsonWriter writer = Json.createWriter(stringWriter);
             while (resultComponentes.next()) {
                idDescripcion = resultComponentes.getInt(IDDESCRIPCION);
                tipo = resultComponentes.getInt("tipo");
                marca = resultComponentes.getString("marca");
                modelo = resultComponentes.getString("modelo");
                caracteristicasTecnicas = resultComponentes.getString("caracteristicasTecnicas");

                 JsonObjectBuilder componenteJsonB = Json.createObjectBuilder()
                         .add(IDDESCRIPCION, idDescripcion)
                         .add("tipo", tipo)
                         .add("marca", marca)
                         .add("modelo", modelo)
                         .add("caracteristicasTecnicas", caracteristicasTecnicas);

                 JsonObject espacioJson = componenteJsonB.build();
                 descripcionJsonB = descripcionJsonB.add(espacioJson);

             }
             JsonArray descripcionJson = descripcionJsonB.build();
             writer.writeArray(descripcionJson);
             descripcionJSON  = stringWriter.toString();
         } catch (SQLException ex) {
            Logger.getLogger(DAOComponente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return descripcionJSON;
    }
}
