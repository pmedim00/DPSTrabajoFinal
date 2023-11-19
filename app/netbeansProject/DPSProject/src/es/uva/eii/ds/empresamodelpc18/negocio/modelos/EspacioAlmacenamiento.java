package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 * Implementacion del espacio de almacenamiento
 * 
 */
public class EspacioAlmacenamiento {
    
    private int idUbicacion;
    private short seccion;
    private short zona;
    private short estanteria;
    private boolean ocupado;
    
    /**
     * Constructor
     * @param json informacion del espacio de almacenamiento
     */
    public EspacioAlmacenamiento(String json){
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(json);
        JsonReader reader = factory.createReader(sr);
        JsonObject espacioAlmacenamientoJson = reader.readObject();
        
        idUbicacion = espacioAlmacenamientoJson.getInt("idUbicacion");
        seccion = (short) espacioAlmacenamientoJson.getInt("seccion");
        zona = (short) espacioAlmacenamientoJson.getInt("zona");
        estanteria = (short) espacioAlmacenamientoJson.getInt("estanteria");
        ocupado = espacioAlmacenamientoJson.getBoolean("ocupado");
    }

     /**
     * Obtener idUbicacion
     * @return idUbicacion
     */
    public int getIdUbicacion() {
        return idUbicacion;
    }

     /**
     * Obtener seccion
     * @return seccion
     */
    public short getSeccion() {
        return seccion;
    }

     /**
     * Obtener zona
     * @return zona
     */
    public short getZona() {
        return zona;
    }
    
    /**
     * Obtener estanteria
     * @return estanteria
     */
    public short getEstanteria() {
        return estanteria;
    }
    
    /**
     * Obtiene la ocupacion
     * @return true si esta ocupado, false en caso contrario
     */
    public boolean isOcupado() {
        return ocupado;
    }
    
}
