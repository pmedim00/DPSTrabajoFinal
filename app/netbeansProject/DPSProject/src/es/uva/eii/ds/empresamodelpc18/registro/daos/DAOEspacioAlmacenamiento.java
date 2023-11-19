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
 * Implementacion del Espacio de Almacenamiento
 * 
 */
public class DAOEspacioAlmacenamiento {
    
    /**
     * Constructor de DAOEspacioAlmacenamiento
     */
     private DAOEspacioAlmacenamiento(){
    }
    
     /**
      * Consulta espacios disponibles segun el tipo de cpu
      * @param tipoCPU 1 amd, 2 intel
      * @return informacion de espacios
      * @throws SQLException consulta no valida
      */
    public static String consultarEspaciosDisponiblesPorTipoCPU(int tipoCPU) throws SQLException{     
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement stmt = db.getStatement("SELECT E.IDUBICACION, E.SECCION, E.ZONA, E.ESTANTERIA, E.OCUPADO "
                + "FROM ESPACIOALMACENAMIENTO E, PC P, CONFIGURACIONPC C "
                + "WHERE C.TIPOCPU=" + tipoCPU + " AND C.IDCONFIGURACION=P.IDCONFIGURACION "
                + "AND P.UBICACION=E.IDUBICACION AND E.OCUPADO='0'");
        ResultSet resultEspacios = stmt.executeQuery();
        String espaciosAlmacenamientoJson = obtenerEspacioAlmacenamientoJson(resultEspacios);
        
        db.closeConnection();
        return espaciosAlmacenamientoJson;
    }
    
    /**
     * Obtiene informacion de espacios de almacenamiento en JSON
     * @param datosEspacioAlmacenamiento informacion de espacios de almacenamiento
     * @return informacion en JSON
     */
     private static String obtenerEspacioAlmacenamientoJson(ResultSet datosEspacioAlmacenamiento) {
        int idUbicacion;
        short seccion;
        short zona;
        short estanteria;
        boolean ocupado;
        String espaciosjson = null;
        
         try {
             JsonArrayBuilder espaciosJsonB = Json.createArrayBuilder();
             StringWriter stringWriter = new StringWriter();
             JsonWriter writer = Json.createWriter(stringWriter);
             while (datosEspacioAlmacenamiento.next()) {
                 idUbicacion = datosEspacioAlmacenamiento.getInt("idUbicacion");
                 seccion = datosEspacioAlmacenamiento.getShort("seccion");
                 zona = datosEspacioAlmacenamiento.getShort("zona");
                 estanteria = datosEspacioAlmacenamiento.getShort("estanteria");
                 ocupado = datosEspacioAlmacenamiento.getBoolean("ocupado");

                 JsonObjectBuilder espacioJsonB = Json.createObjectBuilder()
                         .add("idUbicacion", idUbicacion)
                         .add("seccion", seccion)
                         .add("zona", zona)
                         .add("estanteria", estanteria)
                         .add("ocupado", ocupado);

                 JsonObject espacioJson = espacioJsonB.build();
                 espaciosJsonB = espaciosJsonB.add(espacioJson);

             }
             JsonArray espaciosJson = espaciosJsonB.build();
             writer.writeArray(espaciosJson);
             espaciosjson = stringWriter.toString();
         } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return espaciosjson;
    }
}