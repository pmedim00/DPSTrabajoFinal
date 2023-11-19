package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import java.io.StringReader;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 * Implementacion de descripcion del componente
 * 
 */
public class DescripcionComponente {
    private int idDescripcion;
    private int tipo;
    private String marca;
    private String modelo;
    private double precio;
    private String caracteristicasTecnicas;

    /**
     * Constructor
     * @param json informacion de la descripcion
     */
    public DescripcionComponente(String json) {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(json);
        JsonReader reader = factory.createReader(sr);
        JsonObject descripcionJson = reader.readObject();

        idDescripcion = descripcionJson.getInt("idDescripcion");
        tipo = descripcionJson.getInt("tipo");
        marca = descripcionJson.getString("marca");
        modelo = descripcionJson.getString("modelo");
        caracteristicasTecnicas = descripcionJson.getString("caracteristicasTecnicas");
    }
    
    /**
     * Obtener descripcion del componente
     * @return informacion del componente
     */
     public String getJson() {
        String json = null;

        JsonObject empresa = Json.createObjectBuilder()
                .add("idDescripcion", idDescripcion)
                .add("tipo", tipo)
                .add("marca", marca)
                .add("modelo", modelo)
                .add("precio", precio)
                .add("caracteristicasTecnicas", caracteristicasTecnicas)
                .build();

        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        writer.writeObject(empresa);
        json = stringWriter.toString();

        return json;
    }
     
    /**
     * Obtiene el id de la descripcion
     * @return id
     */
    public int getIdDescripcion() {
        return idDescripcion;
    }
    
    /**
     * Obtiene el tipo del componente
     * @return 1 tarjeta grafica, 2 disco duro, 3 placa base, 4 caja, 5 procesador, 6 ram
     */
    public int getTipo() {
        return tipo;
    }
     
}

