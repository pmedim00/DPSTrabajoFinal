package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 * Implementacion del pedido pc
 * 
 */
public class PedidoPC {
    private int idPedido;
    private int cantidadSolicitada;
    private LocalDate fechaPedido;
    private int estado;
    private int configuracionSolicitada;
    private String encargadoPor;

    /**
     * Constructor
     * @param json informacion del pedido
     */
    public PedidoPC(String json) {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(json);
        JsonReader reader = factory.createReader(sr);
        JsonObject pedidoComponentesJson = reader.readObject();

        idPedido = pedidoComponentesJson.getInt("idPedido");
        cantidadSolicitada = pedidoComponentesJson.getInt("cantidadSolicitada");
        fechaPedido = LocalDate.parse(pedidoComponentesJson.getString("fechaPedido"));
        estado = pedidoComponentesJson.getInt("estado");
        configuracionSolicitada = pedidoComponentesJson.getInt("configuracionSolicitada");
        encargadoPor = pedidoComponentesJson.getString("encargadoPor");
     
    }
    
    /**
     * Obtiene informacion del pedido
     * @return informacion
     */
     public String getJson() {
        
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        
        JsonObjectBuilder pedidoJsonB = Json.createObjectBuilder()
                .add("idPedido", idPedido)
                .add("cantidadSolicitada", cantidadSolicitada)
                .add("fechaPedido", fechaPedido.toString())
                .add("estado", estado)
                .add("configuracionSolicitada", configuracionSolicitada)
                .add("encargadoPor", encargadoPor);

        JsonObject pedidoJson = pedidoJsonB.build();
        writer.writeObject(pedidoJson);
        return stringWriter.toString();
    }

      /**
     * Obtener idPedido
     * @return idPedido
     */
    public int getIdPedido() {
        return idPedido;
    }
     
}
