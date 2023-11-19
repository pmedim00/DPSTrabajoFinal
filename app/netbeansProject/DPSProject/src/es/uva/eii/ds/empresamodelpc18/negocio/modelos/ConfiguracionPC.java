package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import java.io.StringReader;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 * Implementacion de configuracion PC
 * 
 */
public class ConfiguracionPC {
    
    private int idConfiguracion;
    private int tipoCPU;
    private double velocidadCPU;
    private int capacidadRAM;
    private int capacidadDD;
    private double velocidadTarjetaGrafica;
    private int memoriaTarjetaGrafica;
    
    /**
     * Constructor
     * @param json informacion del pc 
     */
    public ConfiguracionPC(String json){
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(json);
        JsonReader reader = factory.createReader(sr);
        JsonObject configuracionPCJson = reader.readObject();

        idConfiguracion = configuracionPCJson.getInt("idConfiguracion");
        tipoCPU = configuracionPCJson.getInt("tipoCPU");
        velocidadCPU = Double.parseDouble(configuracionPCJson.getString("velocidadCPU"));
        capacidadRAM = configuracionPCJson.getInt("capacidadRAM");
        capacidadDD = configuracionPCJson.getInt("capacidadDD");
        velocidadTarjetaGrafica = Double.parseDouble(configuracionPCJson.getString("velocidadTarjetaGrafica"));
        memoriaTarjetaGrafica = configuracionPCJson.getInt("memoriaTarjetaGrafica");
    }
    
    /**
     * Obtener informacion del pc
     * @return informacion
     */
    public String getJson() {
        String json = null;

        JsonObject empresa = Json.createObjectBuilder()
                .add("idConfiguracion", idConfiguracion)
                .add("tipoCPU", tipoCPU)
                .add("velocidadCPU", velocidadCPU)
                .add("capacidadRAM", capacidadRAM)
                .add("capacidadDD", capacidadDD)
                .add("velocidadTarjetaGrafica", velocidadTarjetaGrafica)
                .add("memoriaTarjetaGrafica", memoriaTarjetaGrafica)
                .build();

        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        writer.writeObject(empresa);
        json = stringWriter.toString();

        return json;
    }

     /**
     * Obtener idConfiguracion
     * @return idConfiguracion
     */
    public int getIdConfiguracion() {
        return idConfiguracion;
    }

}
