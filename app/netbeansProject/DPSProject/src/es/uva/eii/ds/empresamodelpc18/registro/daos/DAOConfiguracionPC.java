package es.uva.eii.ds.empresamodelpc18.registro.daos;

import es.uva.eii.ds.empresamodelpc18.registro.dbaccess.DBConnection;
import java.io.IOException;
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
 * Implementacion del DAO Configuracion PC
 * 
 */
public class DAOConfiguracionPC {
    
    private static final String IDCONFIGURACION = "idConfiguracion";
    private static final String TIPOCPU = "tipoCPU";
    private static final String VELOCIDADCPU = "velocidadCPU";
    private static final String CAPACIDADRAM = "capacidadRAM";
    private static final String CAPACIDADDD = "capacidadDD";
    private static final String VELOCIDADTARJETAGRAFICA = "velocidadTarjetaGrafica";
    private static final String MEMORIATARJETAGRAFICA = "memoriaTarjetaGrafica";
    
    /**
     * Constructor de DAOConfiguracionPC
     */
    private DAOConfiguracionPC(){   
    }
    
     /**
      * Consulta la configuracion de un pedido
      * @param configuracion a consultar
      * @return informacion de la configuracion
      * @throws SQLException consulta no valida
      * @throws ClassNotFoundException clase no valida
      * @throws IOException error
      */
    public static String consultarConfiguracionPedido(int configuracion) throws SQLException, ClassNotFoundException, IOException {
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement s = db.getStatement("SELECT CPC.IDCONFIGURACION, CPC.TIPOCPU, CPC.VELOCIDADCPU, "
                + "CPC.CAPACIDADRAM, CPC.CAPACIDADDD, CPC.VELOCIDADTARJETAGRAFICA, CPC.MEMORIATARJETAGRAFICA "
                                            + "FROM CONFIGURACIONPC CPC "
                                            + "WHERE CPC.IDCONFIGURACION="+ configuracion + " ");
        ResultSet resultConfiguracion = s.executeQuery();
        String pedidosPCsSolicitadosJson = obtenerConfiguracionJson(resultConfiguracion);
        
        db.closeConnection();
        return pedidosPCsSolicitadosJson;
    }
    
    /**
     * Creacion del JSON de la configuracion
     * @param resultConfiguracion informacion de la configuracion
     * @return informacion en JSON
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    private static String obtenerConfiguracionJson(ResultSet resultConfiguracion) throws SQLException, ClassNotFoundException, IOException {
        String configuracionjson = null;

        int idConfiguracion;
        int tipoCPU;
        double velocidadCPU;
        int capacidadRAM;
        int capacidadDD;
        double velocidadTarjetaGrafica;
        int memoriaTarjetaGrafica;
    

        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        JsonObject configuracionJson = null;
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        try {
            if(resultConfiguracion.next()){
                idConfiguracion = resultConfiguracion.getInt(IDCONFIGURACION);
                tipoCPU = resultConfiguracion.getInt(TIPOCPU);
                velocidadCPU = Double.parseDouble(resultConfiguracion.getString(VELOCIDADCPU));
                capacidadRAM = resultConfiguracion.getInt(CAPACIDADRAM);
                capacidadDD = resultConfiguracion.getInt(CAPACIDADDD);
                velocidadTarjetaGrafica = Double.parseDouble(resultConfiguracion.getString(VELOCIDADTARJETAGRAFICA));
                memoriaTarjetaGrafica = resultConfiguracion.getInt(MEMORIATARJETAGRAFICA);

               configuracionJson = Json.createObjectBuilder()
                    .add(IDCONFIGURACION, idConfiguracion)
                    .add(TIPOCPU, tipoCPU)
                    .add(VELOCIDADCPU, velocidadCPU)
                    .add(CAPACIDADRAM, capacidadRAM)
                    .add(CAPACIDADDD, capacidadDD)
                    .add(VELOCIDADTARJETAGRAFICA, velocidadTarjetaGrafica)
                    .add(MEMORIATARJETAGRAFICA, memoriaTarjetaGrafica)
                    .build();
               
                writer.writeObject(configuracionJson);
                configuracionjson = stringWriter.toString();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPedidoPC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return configuracionjson;
    }
    
    /**
     * Consulta informacion de la configuracion
     * @return informacion de la configuracion
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public static String consultarConfiguraciones() throws SQLException, ClassNotFoundException, IOException {
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement s = db.getStatement("SELECT CPC.IDCONFIGURACION, CPC.TIPOCPU, CPC.VELOCIDADCPU, "
                + "CPC.CAPACIDADRAM, CPC.CAPACIDADDD, CPC.VELOCIDADTARJETAGRAFICA, CPC.MEMORIATARJETAGRAFICA "
                                            + "FROM CONFIGURACIONPC CPC ");
        ResultSet resultConfiguracion = s.executeQuery();
        String pedidosPCsSolicitadosJson = obtenerConfiguracionesJson(resultConfiguracion);
        
        db.closeConnection();
        return pedidosPCsSolicitadosJson;
    }
    
    /**
     * Obtiene informacion de la configuracion en JSON
     * @param resultConfiguracion informacion de la configuracion
     * @return informacion en JSON
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    private static String obtenerConfiguracionesJson(ResultSet resultConfiguracion) throws SQLException, ClassNotFoundException, IOException {
        String configuracionesjson = null;

        int idConfiguracion;
        int tipoCPU;
        double velocidadCPU;
        int capacidadRAM;
        int capacidadDD;
        double velocidadTarjetaGrafica;
        int memoriaTarjetaGrafica;
    

        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        JsonArrayBuilder configuracionesJsonB = Json.createArrayBuilder();
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        try {
            while (resultConfiguracion.next()) {
                idConfiguracion = resultConfiguracion.getInt(IDCONFIGURACION);
                tipoCPU = resultConfiguracion.getInt(TIPOCPU);
                velocidadCPU = Double.parseDouble(resultConfiguracion.getString(VELOCIDADCPU));
                capacidadRAM = resultConfiguracion.getInt(CAPACIDADRAM);
                capacidadDD = resultConfiguracion.getInt(CAPACIDADDD);
                velocidadTarjetaGrafica = Double.parseDouble(resultConfiguracion.getString(VELOCIDADTARJETAGRAFICA));
                memoriaTarjetaGrafica = resultConfiguracion.getInt(MEMORIATARJETAGRAFICA);

                JsonObjectBuilder configuracionJsonB = Json.createObjectBuilder()
                        .add(IDCONFIGURACION, idConfiguracion)
                        .add(TIPOCPU, tipoCPU)
                        .add(VELOCIDADCPU, velocidadCPU)
                        .add(CAPACIDADRAM, capacidadRAM)
                        .add(CAPACIDADDD, capacidadDD)
                        .add(VELOCIDADTARJETAGRAFICA, velocidadTarjetaGrafica)
                        .add(MEMORIATARJETAGRAFICA, memoriaTarjetaGrafica);

                JsonObject configuracionJson = configuracionJsonB.build();
                configuracionesJsonB = configuracionesJsonB.add(configuracionJson);
            }
            JsonArray espaciosJson = configuracionesJsonB.build();
            writer.writeArray(espaciosJson);
            configuracionesjson = stringWriter.toString();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPedidoPC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return configuracionesjson;
    }
    
}
