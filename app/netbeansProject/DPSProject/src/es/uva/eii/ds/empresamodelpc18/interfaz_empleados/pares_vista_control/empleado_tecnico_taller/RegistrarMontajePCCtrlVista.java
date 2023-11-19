package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_tecnico_taller;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.Controlador;
import es.uva.eii.ds.empresamodelpc18.negocio.controladores_casos_uso.ControladorCURegistrarMontajePC;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 * Implementacion del controlador de la vista de montaje PC
 * 
 */
public class RegistrarMontajePCCtrlVista extends Controlador{
    
    private RegistrarMontajePCVista vistaRegistrar;
    private ControladorCURegistrarMontajePC controladorCU;
    private int conf;
    private String dni;
       
    /**
     * Constructor de la vista
     * @param vista vista
     */
    public RegistrarMontajePCCtrlVista (RegistrarMontajePCVista vista) {
        super();
        vistaRegistrar= vista;
        controladorCU = new ControladorCURegistrarMontajePC();
        dni=getGestor().getDni();
    }
    /**
     * Procesa cancelar
     */
    public void procesarCancelar() {
        getGestor().cambiarVistaIdentificarse("TECNICODELTALLER");
    }
    /**
     * Procesa confirmacion
     * @param row fila que se actualiza
     * @throws SQLException consulta no valida
     */
    public void procesarConfirmar(int row) throws SQLException {
        controladorCU.actualizarBD(row);
        getGestor().cambiarVistaIdentificarse("TECNICODELTALLER");
    }
    
    /**
     * Procesa las configuraciones
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public void procesarListaConfiguraciones() throws SQLException, ClassNotFoundException, IOException {
        String configuracionesJSON = controladorCU.consultarConfiguraciones();
        
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(configuracionesJSON);
        JsonReader reader = factory.createReader(sr);
        JsonArray configuracionesArray = reader.readArray();
        ListIterator iterator = configuracionesArray.listIterator();

        while (iterator.hasNext()) {

            JsonObject configuracionJson = (JsonObject) iterator.next();
           
            int tipoCPU = configuracionJson.getInt("tipoCPU");
            double velocidadCPU = configuracionJson.getJsonNumber("velocidadCPU").doubleValue();
            int capacidadRAM = configuracionJson.getInt("capacidadRAM");
            int capacidadDD = configuracionJson.getInt("capacidadDD");
            double velocidadTarjetaGrafica = configuracionJson.getJsonNumber("velocidadTarjetaGrafica").doubleValue();
            int memoriaTarjetaGrafica = configuracionJson.getInt("memoriaTarjetaGrafica");

            vistaRegistrar.mostrarDatosDeConfiguraciones(tipoCPU, velocidadCPU, capacidadRAM, capacidadDD, velocidadTarjetaGrafica, memoriaTarjetaGrafica);
        }
    }
    
    /**
     * Procesa lista de pedidos
     * @param selectRow fila a procesar
     * @param ped estado del pedido, true o false
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public void procesarListaPedidos(int selectRow, boolean ped) throws SQLException, ClassNotFoundException, IOException {
        String configuracionesJSON = controladorCU.consultarConfiguraciones();
      
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(configuracionesJSON);
        JsonReader reader = factory.createReader(sr);
        JsonArray configuracionesArray = reader.readArray();
        ListIterator iterator = configuracionesArray.listIterator();
        int cont=0;
        while (iterator.hasNext()) {
            JsonObject configuracionJson = (JsonObject) iterator.next();

            int idConfiguracion = configuracionJson.getInt("idConfiguracion");
            if(cont==selectRow){
                conf= idConfiguracion;
            }
            cont++;
        }
        if (ped) {
            String pedidosJSON = controladorCU.consultarPedidosPorConfiguracion(conf);
            sr = new StringReader(pedidosJSON);
            reader = factory.createReader(sr);
            JsonArray pedidosArray = reader.readArray();
            iterator = pedidosArray.listIterator();

            while (iterator.hasNext()) {

                JsonObject configuracionJson = (JsonObject) iterator.next();
                
                int cantidadSolicitada = configuracionJson.getInt("cantidadSolicitada");
                String fecha = configuracionJson.getString("fechaPedido");
                LocalDate fechaPedido = LocalDate.parse(fecha);
                vistaRegistrar.mostrarPedidosDeConfiguracionSolicitada(cantidadSolicitada, fechaPedido);
            }
        }
    }
    
    /**
     * Comprueba si la nueva etiqueta de PC introducida existe o no
     * @param etiqueta que se va a comprobar
     * @return true si no esta en uso, false en caso contrario
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public boolean comprobarQueNoExisteEsaEtiqueta(String etiqueta) throws SQLException, ClassNotFoundException, IOException {
        String pcsJSON = controladorCU.consultarPCsExistentes();
      
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(pcsJSON);
        JsonReader reader = factory.createReader(sr);
        JsonArray configuracionesArray = reader.readArray();
        ListIterator iterator = configuracionesArray.listIterator();
        boolean existe=false;
        while (iterator.hasNext()) {
            JsonObject pcJson = (JsonObject) iterator.next();

            int idEtiqueta= pcJson.getInt("idEtiqueta");
            if(String.valueOf(idEtiqueta).equals(etiqueta)){
                existe=true;
                return false;
            }
        }
        return true;
    }
    
    /**
     * Agrega la informacion a la base de datos
     * @param etiqueta etiqueta del nuevo pc
     * @param montadoPor dni del empleado
     */
      public void agregarEtiqueta(String etiqueta, String montadoPor){
            controladorCU.crearNuevoPC(Integer.parseInt(etiqueta), conf, montadoPor);
        }
    
    /**
     * Comprueba etiqueta de componentes
     * @param etiqueta que se comprueba
     * @param idEtiquetaPc etiqueta del PC
     * @throws IOException error
     * @throws SQLException Consulta no valida
     */
    public void comprobarEtiquetaComponentes(String etiqueta, int idEtiquetaPc) throws IOException, SQLException {
        String componenteJson = controladorCU.consultarComponentesDisponibles();
        String componentesConfJson = controladorCU.obtenerIdDescripcion(conf);
        ArrayList<Integer> lista = new ArrayList<>();
        JsonReaderFactory factory2 = Json.createReaderFactory(null);
        StringReader sr2 = new StringReader(componentesConfJson);
        JsonReader reader2 = factory2.createReader(sr2);
        JsonArray configuracionesArray2 = reader2.readArray();
        ListIterator iterator2 = configuracionesArray2.listIterator();
        
        JsonReaderFactory factory = Json.createReaderFactory(null);
        StringReader sr = new StringReader(componenteJson);
        JsonReader reader = factory.createReader(sr);
        JsonArray configuracionesArray = reader.readArray();
        ListIterator iterator = configuracionesArray.listIterator();
        boolean existe=false;
        while (iterator2.hasNext()){
            JsonObject pcJson2 = (JsonObject) iterator2.next();
            lista.add(pcJson2.getInt("idDescripcion"));
        }
        while (iterator.hasNext()) {
            JsonObject pcJson = (JsonObject) iterator.next();
            
            int idEtiqueta= pcJson.getInt("idEtiqueta");
            int idDescripcion2= pcJson.getInt("idDescripcion");
            boolean reservado= pcJson.getBoolean("reservado");
            if(String.valueOf(idEtiqueta).equals(etiqueta)){
                existe=true;
                boolean repetida = controladorCU.comprobarIntroducidaEtiquetaRepetida(Integer.parseInt(etiqueta));
                if (repetida || reservado){
                   String mensaje= "La etiqueta identificativa pertenece a un componente ya añadido o que ya ha sido reservado.";
                   vistaRegistrar.mostrarMensajeError(mensaje); 
                }
                else{
                    boolean control=false;
                    for(int j=0;j<lista.size();j++){
                    if(lista.get(j)==idDescripcion2){
                    controladorCU.agregarComponente(idEtiqueta, idEtiquetaPc);
                    vistaRegistrar.limpiarEtiquetaComponentes();
                    control=true;
                    }
                    }
                    if(!control){
                       String mensaje= "La etiqueta identificativa no pertenece a un componente descrito en la configuracion.";
                       vistaRegistrar.mostrarMensajeError(mensaje);
                    }
                }
            }
        }
        
        if (!existe){
            String mensaje= "La etiqueta identificativa no pertenece a un componente existente.";
            vistaRegistrar.mostrarMensajeError(mensaje);
        }
        
    }

    /**
     * Comprueba la cantidad de componentes agregados
     * @return true si estan todos agregados, false en caso contrario
     * @throws SQLException consulta no valida
     */
    public boolean comprobarcantidad() throws SQLException {
        int cantidadComponentesConf = controladorCU.obtenerCantidadComponentesConfiguracion(conf);
        int cantidadComponentesAgregados = controladorCU.obtenerCantidadComponentesAgregados();
        if(cantidadComponentesAgregados < cantidadComponentesConf){
            return false;
        }
        return true;
    }
    /**
     * Obtiene el dni del empleado
     * @return dni
     */
    public String getDni() {
        return dni;
    }
}
