package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_personal_almacen;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.Controlador;
import es.uva.eii.ds.empresamodelpc18.negocio.controladores_casos_uso.ControladorCUBuscar;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ListIterator;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 * Implementacion del controlador de la vista espacios disponibles
 * 
 */
public class EspaciosDisponiblesCtrlVista extends Controlador{
    private EspaciosDisponiblesVista vistaEspacios;
    private ControladorCUBuscar controladorCU;
    
    /**
     * Constructor de la vista espacios disponibles
     * @param vista vista
     */
    public  EspaciosDisponiblesCtrlVista  (EspaciosDisponiblesVista vista) {
        super();
        vistaEspacios = vista;
        controladorCU = new ControladorCUBuscar ();
    }
    
    /**
     * Cancelar
     */
    public void procesarCancelar(){
        getGestor().cambiarVistaBuscarEspacioPC();
    }
    
    /**
     * Busca espacios disponibles
     * @throws SQLException Consulta no valida
     */
    public void procesarEspacios() throws SQLException{
        String espaciosJSON = controladorCU.consultarEspaciosDisponibles(vistaEspacios.getTipoCPU());
      
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(espaciosJSON);
        JsonReader reader = factory.createReader(sr);
        JsonArray jsonArrayEspacios = reader.readArray();
        ListIterator iterator = jsonArrayEspacios.listIterator();
        String estado = "";
        int contador = 0;
        
        while (iterator.hasNext()) {
           
        JsonObject jsonEspacios = (JsonObject) iterator.next();
        
            int seccion = jsonEspacios.getInt("seccion");
            int zona = jsonEspacios.getInt("zona");
            int estanteria = jsonEspacios.getInt("estanteria");
            boolean ocupado = jsonEspacios.getBoolean("ocupado");
            if (ocupado == false) {
                estado = "No";
            }

            vistaEspacios.mostrarEspaciosDisponibles(seccion, zona, estanteria, estado);
            contador++;
        }
        if (vistaEspacios.getNumEspacios() > contador) {
            if (contador == 0) {
                vistaEspacios.errorMessage("No hay espacios disponibles de ese tipo de CPU");
            } else {
                int falta = vistaEspacios.getNumEspacios() - contador;
                if (falta > 1) {
                    vistaEspacios.errorMessage("No hay espacios suficientes, falta " + falta + " espacios");
                } else {
                    vistaEspacios.errorMessage("No hay espacios suficientes, falta " + falta + " espacio");
                }
            }
        }
 
    }
}
