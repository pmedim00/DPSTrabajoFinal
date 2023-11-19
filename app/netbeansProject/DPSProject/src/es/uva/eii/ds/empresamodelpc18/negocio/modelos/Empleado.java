package es.uva.eii.ds.empresamodelpc18.negocio.modelos;

import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 * Implementacion de empleado que permite manejar a los empleados
 * 
 */
public final class Empleado extends Usuario{ 
    
    private LocalDate fechaInicioEmpresa;
    private VinculacionConLaEmpresa vinculacion;
    private Disponibilidad disponibilidad;
    private RolesEnEmpresa rolEmpleado;
    private final ArrayList<RolesEnEmpresa> historialRoles;
    private final ArrayList<VinculacionConLaEmpresa> historialVinculaciones;
    private final ArrayList<Disponibilidad> historialDisponibilidades; 
    
    /**
     * Constructor de empleado
     * @param json datos para crear al empleado
     */
    public Empleado(String json){
    super();
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(json);
        JsonReader reader = factory.createReader(sr);
        JsonObject empleadoJson = reader.readObject();
        JsonObject rolObjeto = empleadoJson.getJsonObject("rolEnEmpresa");
        JsonObject vinculacionObjeto = empleadoJson.getJsonObject("estadoDeLaVinculacion");
        JsonObject disponibilidadObjeto = empleadoJson.getJsonObject("estadoDeDisponibilidad");
        
        super.setNifCif(empleadoJson.getString("nifcif"));
        super.setNombre(empleadoJson.getString("nombre"));
        super.setDireccionPostal(empleadoJson.getString("direccionPostal"));
        super.setDireccionElectronica(empleadoJson.getString("direccionElectronica"));
        super.setTelefono(empleadoJson.getString("telefono"));
       
        String fechaInicioEmpresaString= empleadoJson.getString("fechaInicio");
        setFechaInicioEmpresa(LocalDate.parse(fechaInicioEmpresaString));
        
        String inicioString = vinculacionObjeto.getString("inicio");
        LocalDate inicio = LocalDate.parse(inicioString);
        String vinculacionString =vinculacionObjeto.getString("vinculo");
        TipoDeVinculacion vinc = TipoDeVinculacion.valueOf(vinculacionString);
        setVinculacion(new VinculacionConLaEmpresa(inicio, vinc));
        
        String comienzoString = disponibilidadObjeto.getString("comienzo");
        LocalDate comienzo = LocalDate.parse(comienzoString);
        String finalPrevistoString = disponibilidadObjeto.getString("finalPrevisto");
        LocalDate finalPrevisto = LocalDate.parse(finalPrevistoString);
        String disponibilidadString = disponibilidadObjeto.getString("disponibilidad");
        TipoDeDisponibilidad disp = TipoDeDisponibilidad.valueOf(disponibilidadString);
        setDisponibilidad(new Disponibilidad(comienzo, finalPrevisto, disp));
        
        String comienzoEnRolString = rolObjeto.getString("comienzoEnRol");
        LocalDate comienzoEnRol = LocalDate.parse(comienzoEnRolString);
        String rolString = rolObjeto.getString("rol");
        TipoDeRol rol = TipoDeRol.valueOf(rolString);    
        setRolesEnEmpresa(new RolesEnEmpresa(comienzoEnRol, rol));
        
        historialVinculaciones = new ArrayList<>();
        historialVinculaciones.add(vinculacion);
        historialDisponibilidades = new ArrayList<>();
        historialDisponibilidades.add(disponibilidad);
        historialRoles = new ArrayList<>();
        historialRoles.add(rolEmpleado);
    }
    
    /**
     * Comprueba si el empleado esta activo
     * @return boolean True si esta activo, false en caso contrario
     */
    public boolean estaActivo() {
        boolean activo = false;
        if(vinculacion.getVinculacion() == TipoDeVinculacion.CONTRATADO && disponibilidad.getTipoDeDisponibilidad() == TipoDeDisponibilidad.TRABAJANDO){
            activo=true;
        }
        return activo;
    }

    /**
     * Obtiene fecha de inicio en la empresa
     * @return Fecha de inicio
     */
    public LocalDate getFechaInicioEmpresa() {
        return fechaInicioEmpresa;
    }
    /**
     * Modifica fecha de inicio en la empresa
     * @param fechaInicioEmpresa Nueva fecha de inicio
     */
    public void setFechaInicioEmpresa(LocalDate fechaInicioEmpresa) {
        this.fechaInicioEmpresa = fechaInicioEmpresa;
    }
    /**
     * Obtiene tipo de vinculacion del empleado
     * @return Vinculacion del empleado
     */
    public VinculacionConLaEmpresa getVinculacion() {
        return vinculacion;
    }
    
    /**
     * Modifica la vinculacion del empleado
     * @param vinculacion Nueva vinculacion
     */
    public void setVinculacion(VinculacionConLaEmpresa vinculacion) {
        this.vinculacion = vinculacion;
    }
    
    /**
     * Obtiene disponibillidad del empleado
     * @return Disponibilidad del empleado
     */
    public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }
    
    /**
     * Cambia disponibilidad del empleado
     * @param disponibilidad Nueva disponibilidad
     */
    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
    /**
     * Obtiene rol del empleado
     * @return rol del empleado
     */
    public RolesEnEmpresa obtenerRolActual(){
        return rolEmpleado;
    }
    
    /**
     * Cambia rol del empleado
     * @param rolEmpleado Nuevo rol del empleado
     */
    public void setRolesEnEmpresa(RolesEnEmpresa rolEmpleado) {
        this.rolEmpleado = rolEmpleado;
    }
    
}
