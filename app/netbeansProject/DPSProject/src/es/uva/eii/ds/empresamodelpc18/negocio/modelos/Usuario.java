package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 * Implementacion de la clase Usuario
 * 
 */
public abstract class Usuario {
    
    private String nifCif;
    private String nombre;
    private String direccionPostal;
    private String direccionElectronica;
    private String telefono;
            
    /**
     * Constructor de la clase Usuario
     */
    public Usuario(){
        this.nifCif = null;
        this.nombre = null;
        this.direccionPostal = null;
        this.direccionElectronica = null;
        this.telefono = null;
    }
    
    /**
     * Obtiene el nifcif del usuario
     * @return nifcif del usuario
     */
    public String getNifCif() {
        return nifCif;
    }
    
    /**
     * Modifica el nifcif del usuario
     * @param nifCif nifCif del usuario 
     */
    public void setNifCif(String nifCif) {
        this.nifCif = nifCif;
    }
    
    /**
     * Obtiene el nombre del usuario
     * @return nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Modifica el nombre del usuario
     * @param nombre nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene la direccion de residencia del usuario
     * @return direccion de residencia del usuario
     */
    public String getDireccionPostal() {
        return direccionPostal;
    }
    
    /**
     * Modifica la direccion de residencia del usuario
     * @param direccionPostal direccion de residencia del usuario
     */
    public void setDireccionPostal(String direccionPostal) {
        this.direccionPostal = direccionPostal;
    }
    
    /**
     * Obtiene la direccion de correo del usuario
     * @return direccion de correo del usuario
     */
    public String getDireccionElectronica() {
        return direccionElectronica;
    }
    
    /**
     * Modifica la direccion de correo del usuario
     * @param direccionElectronica direccion de correo del usuario
     */
    public void setDireccionElectronica(String direccionElectronica) {
        this.direccionElectronica = direccionElectronica;
    }

    /**
     * Obtiene el telefono del usuario
     * @return el telefono del usuario
     */
    public String getTelefono() {
        return telefono;
    }
    
    /**
     * Modifica el numero de telefono del usuario
     * @param telefono numero de telefono del usuario
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    /**
     * Genera y obtiene el usuario en formato JSON
     * @return el usuario en formato JSON
     */
    public String getJson() {
        String json;
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);

        JsonObject usuarioJson = Json.createObjectBuilder()
                .add("nifCif", nifCif)
                .add("nombre", nombre)
                .add("direccionPostal", direccionPostal)
                .add("direccionElectronica", direccionElectronica)
                .add("telefono", telefono)
                .build();
        writer.writeObject(usuarioJson);
        json = stringWriter.toString();
        return json;
    }
}
