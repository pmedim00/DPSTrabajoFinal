package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 * Implementacion de PC
 * 
 */
public class PC {        
    
    private int idEtiqueta;
    private boolean reservado;            
    private LocalDate fechaMontaje;
    private int idConfiguracion;
    private String montadoPor;
    private int ubicacion;
    private int idPedido;
    
    /**
     * Constructor a partir de json
     * @param json informacion del pc
     */
    public PC (String json){
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(json);
        JsonReader reader = factory.createReader(sr);
        JsonObject pcJson = reader.readObject();
        
        idEtiqueta = pcJson.getInt("idEtiqueta");
        reservado = pcJson.getBoolean("reservado");
        fechaMontaje = LocalDate.parse(pcJson.getString("fechaMontaje"));
        idConfiguracion = pcJson.getInt("idConfiguracion");
        montadoPor = String.valueOf(pcJson.getString("montadoPor"));
        ubicacion = pcJson.getInt("ubicacion");
        idPedido = pcJson.getInt("idPedido");
    }
    
    /**
     * Constructor a partir de parametros
     * @param etiqueta nueva etiqueta del pc creado
     * @param idConf configuracion del nuevo pc
     * @param tecnicoTaller identificador del empleado que lo ha montado
     */
    public PC(int etiqueta, int idConf, String tecnicoTaller) {
        idEtiqueta = etiqueta;
        reservado = false;            
        fechaMontaje = LocalDate.now();
        idConfiguracion = idConf;
        montadoPor = tecnicoTaller;
        ubicacion = 0;
        idPedido = 0;
    }

    /**
     * Cambia el valor de reservado
     * @param reservado true reservado, false caso contrario
     */
    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

     /**
     * Obtener idEtiqueta
     * @return idEtiqueta
     */
    public int getIdEtiqueta() {
        return idEtiqueta;
    }
    
    /**
     * Asigna un id al pedido
     * @param idPedido id nuevo
     */
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    /**
     * Obtine informacion del pc
     * @return informacion del pc
     */
    public String getJson(){
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        
        JsonObject pcJsonObject = Json.createObjectBuilder()
                .add("idEtiqueta", idEtiqueta)
                .add("reservado", reservado)
                .add("fechaMontaje", fechaMontaje.toString())
                .add("idConfiguracion", idConfiguracion)
                .add("montadoPor", montadoPor)
                .add("ubicacion", ubicacion)
                .add("idPedido", idPedido)
                .build();
        
        writer.writeObject(pcJsonObject);
        return stringWriter.toString();
    }
}
