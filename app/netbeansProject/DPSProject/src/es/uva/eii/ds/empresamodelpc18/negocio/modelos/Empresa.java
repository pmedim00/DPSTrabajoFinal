package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import java.io.StringReader;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 * Implementacion de la empresa
 * 
 */
public class Empresa {
    private String cif;
    private boolean esCliente;
    private boolean esProveedor;

    /**
     * Constructor
     * @param json informacion de la empresa 
     */
    public Empresa(String json) {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(json);
        JsonReader reader = factory.createReader(sr);
        JsonObject empresaJson = reader.readObject();

        cif = empresaJson.getString("cif");
        esCliente = empresaJson.getBoolean("esCliente");
        esProveedor = empresaJson.getBoolean("esProveedor");
    }

    /**
     * Obtener informacion de la empresa
     * @return informacion
     */
    public String getJson() {
        String json = null;

        JsonObject empresa = Json.createObjectBuilder()
                .add("cif", cif)
                .add("esCliente", esCliente)
                .add("esProveedor", esProveedor)
                .build();

        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        writer.writeObject(empresa);
        json = stringWriter.toString();

        return json;
    }
}
