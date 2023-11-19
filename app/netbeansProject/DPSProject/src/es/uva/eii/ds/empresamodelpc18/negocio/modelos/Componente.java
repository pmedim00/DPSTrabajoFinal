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
 * Implementacion de componente
 * 
 */
public class Componente {
    private int idEtiqueta;
    private LocalDate fechaEntrada;
    private Boolean reservado;
    private int idDescripcion;
    private int etiquetaPC;
    private int recibidoEnCompra;
    private int ubicacion;

    /**
     * Constructor
     * @param json Informacion del componente 
     */
    public Componente(String json) {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(json);
        JsonReader reader = factory.createReader(sr);
        JsonObject componenteJson = reader.readObject();

        idEtiqueta = componenteJson.getInt("idEtiqueta");
        String fecha = componenteJson.getString("fechaEntrada");
        fechaEntrada = LocalDate.parse(fecha);
        reservado = componenteJson.getBoolean("reservado");
        idDescripcion = componenteJson.getInt("idDescripcion");
        etiquetaPC = componenteJson.getInt("etiquetaPC");
        recibidoEnCompra = componenteJson.getInt("recibidoEnCompra");
        ubicacion = componenteJson.getInt("ubicacion");
    }

    /**
     * Obtener componente
     * @return informacion del componente
     */
    public String getJson() {
        
        String json = null;

        JsonObject empresa = Json.createObjectBuilder()
                .add("idEtiqueta", idEtiqueta)
                .add("fechaEntrada", fechaEntrada.toString())
                .add("reservado", reservado)
                .add("idDescripcion", idDescripcion)
                .add("etiquetaPC", etiquetaPC)
                .add("recibidoEnCompra", recibidoEnCompra)
                .add("ubicacion", ubicacion)
                .build();

        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        writer.writeObject(empresa);
        json = stringWriter.toString();

        return json;
    }

    /**
     * Obtener idDescripcion
     * @return idDescripcion
     */
    public int getIdDescripcion() {
        return idDescripcion;
    }

    /**
     * Obtener idEtiqueta
     * @return idEtiqueta
     */
    public int getIdEtiqueta() {
        return idEtiqueta;
    }

    /**
     * Obtener idEtiquetaPC
     * @return idEtiquetaPc
     */
    public int getEtiquetaPC() {
        return etiquetaPC;
    }

    /**
     * Almacena etiquetaPC
     * @param etiquetaPC que se almacena
     */
    public void setEtiquetaPC(int etiquetaPC) {
        this.etiquetaPC = etiquetaPC;
    }

    /**
     * Obtener estado
     * @return true reservado, false en caso contrario
     */
    public Boolean getReservado() {
        return reservado;
    }

    /**
     * Cambiar estado
     * @param reservado true reservado, false en caso contrario 
     */
    public void setReservado(Boolean reservado) {
        this.reservado = reservado;
    }
    
}
