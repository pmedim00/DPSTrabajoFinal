package es.uva.eii.ds.empresamodelpc18.registro.daos;

import es.uva.eii.ds.empresamodelpc18.registro.dbaccess.DBConnection;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 * Implementacion del DAO Empleado
 * 
 */
public class DAOEmpleado {
    
    private static final String NOMBRETIPO = "nombreTipo";
    
    /**
     * Constructor de DAOEmpleado
     */
    private DAOEmpleado(){ 
    }
    
    /**
     * Permite comprobar si los datos estan registrados en el sistema
     * @param l login del empleado
     * @param p password del empleado
     * @return exito true si el empleado esta registrado, false en caso contrario
     * @throws IllegalArgumentException parametros no validos
     * @throws java.sql.SQLException problemas en la conexion de base de datos
     */
    public static boolean compruebaCredenciales (String l, String p) throws SQLException{
        boolean exito = false;
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement stmt = db.getStatement("SELECT * FROM EMPLEADO");
        try (ResultSet rs = stmt.executeQuery()) {

            String log;
            String pass;
            while (rs.next()) {
                log = rs.getString("NIF");
                pass = rs.getString("PASSWORD");
                if (l.equals(log) && p.equals(pass)) {
                    exito = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.closeConnection();
        return exito;
    }
    
     /**
     * Permite obtener un empleado a partir del login
     * @param l login del empleado
     * @return empleado requerido
     * @throws java.lang.ClassNotFoundException no se encuentra la clase a la que se referencia
     * @throws java.sql.SQLException problemas en la conexion de base de datos
     */
    public static String obtenerEmpleado(String l) throws ClassNotFoundException, SQLException{
        String empleadoJSON = null;
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        DBConnection db = DBConnection.getInstance();
        db.openConnection();
        PreparedStatement stmt = db.getStatement("SELECT * FROM USUARIO");
        try (ResultSet rs = stmt.executeQuery()) {
            ResultSet resultRoles;
            ResultSet resultVinculacion;
            ResultSet resultDisponibilidad;
            JsonObject rol = null;
            JsonObject vinculacion = null;
            JsonObject disponibilidad = null;
            String fechaInicio = "";

            while (rs.next()) {
                String nifCif = rs.getString("NIFCIF");
                String nombre = rs.getString("NOMBRE");
                String direccionPostal = rs.getString("DIRECCIONPOSTAL");
                String direccionElectronica = rs.getString("DIRECCIONELECTRONICA");
                String telefono = rs.getString("TELEFONO");
                if (l.equals(nifCif)) {
                  
                        stmt = db.getStatement("SELECT V.INICIO, TV.NOMBRETIPO "
                                + "FROM VINCULACIONCONLAEMPRESA V NATURAL JOIN TIPODEVINCULACION TV "
                                + "WHERE V.VINCULO=TV.IDTIPO AND V.EMPLEADO=? ORDER BY(V.INICIO) DESC");
                        stmt.setString(1, l);
                        resultVinculacion = stmt.executeQuery();
                        vinculacion = obtenerVinculacionJson(resultVinculacion);
                   
                        
                        stmt = db.getStatement("SELECT D.COMIENZO, D.FINALPREVISTO, TD.NOMBRETIPO "
                                + "FROM DISPONIBILIDADEMPLEADO D NATURAL JOIN TIPODEDISPONIBILIDAD TD "
                                + "WHERE D.DISPONIBILIDAD=TD.IDTIPO AND D.EMPLEADO=? ORDER BY(D.COMIENZO) DESC");
                        stmt.setString(1, l);
                        resultDisponibilidad = stmt.executeQuery();
                        disponibilidad = obtenerDisponibilidadJson(resultDisponibilidad);
                 
                
                        stmt = db.getStatement("SELECT R.COMIENZOENROL, TR.NOMBRETIPO "
                                + "FROM ROLESENEMPRESA R NATURAL JOIN TIPODEROL TR "
                                + "WHERE R.ROL=TR.IDTIPO AND R.EMPLEADO=? ORDER BY(R.COMIENZOENROL) DESC");
                        stmt.setString(1, l);
                        resultRoles = stmt.executeQuery();
                        rol = obtenerRolJson(resultRoles);

                    if (l.equals(nifCif)) {
                        PreparedStatement stmt2 = db.getStatement("SELECT * FROM EMPLEADO WHERE NIF='" + nifCif + "'");
                        fechaInicio = obtenerFecha(stmt2);
                    }

                    JsonObject empleadoJson = Json.createObjectBuilder()
                            .add("nifcif", nifCif)
                            .add("nombre", nombre)
                            .add("direccionPostal", direccionPostal)
                            .add("direccionElectronica", direccionElectronica)
                            .add("telefono", telefono)
                            .add("fechaInicio", fechaInicio)
                            .add("estadoDeLaVinculacion", vinculacion)
                            .add("estadoDeDisponibilidad", disponibilidad)
                            .add("rolEnEmpresa", rol)
                            .build();
                    writer.writeObject(empleadoJson);
                    empleadoJSON = stringWriter.toString();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.closeConnection();
        return empleadoJSON;
    }
    
    /**
     * Obtiene fecha de inicio
     * @param stmt2 consulta
     * @return fecha
     */
    private static String obtenerFecha(PreparedStatement stmt2) {
        String fechaInicio = "";
        try (ResultSet rs2 = stmt2.executeQuery()) {
            while (rs2.next()) {
                fechaInicio = rs2.getString("FECHAINICIO");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaInicio;
    }

    
    /**
     * Obtiene los datos de vinculacion en JSON
     * @param datosVinculacion informacion sobre la vinculacion
     * @return informacion en JSON
     */
    private static JsonObject obtenerVinculacionJson(ResultSet datosVinculacion) {
        Date inicio;
        String vinculo;
        JsonObject vinculacionObject = null;
        try {
            if (datosVinculacion.next()) {
                inicio = datosVinculacion.getDate("inicio");
                vinculo = datosVinculacion.getString(NOMBRETIPO);
                

                vinculacionObject = Json.createObjectBuilder()
                        .add("inicio", inicio.toString())
                        .add("vinculo", vinculo)
                        .build();

            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vinculacionObject;
    }
    
    /**
     * Obtiene los datos de vinculacion en JSON
     * @param datosDisponibilidad informacion de disponibilidad
     * @return informacion en JSON
     */
    private static JsonObject obtenerDisponibilidadJson(ResultSet datosDisponibilidad) {
        Date comienzo;
        Date finalPrevisto;
        String disponibilidad;
        JsonObject disponibilidadObject = null;
        try {
            if (datosDisponibilidad.next()) {
                comienzo = datosDisponibilidad.getDate("comienzo");
                finalPrevisto = datosDisponibilidad.getDate("finalPrevisto");
                disponibilidad = datosDisponibilidad.getString(NOMBRETIPO);
           

                disponibilidadObject = Json.createObjectBuilder()
                        .add("comienzo", comienzo.toString())
                        .add("finalPrevisto", finalPrevisto.toString())
                        .add("disponibilidad", disponibilidad)
                        .build();

            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return disponibilidadObject;
    }
    
    /**
     * Obtiene informacion del rol en JSON
     * @param datosRol informacion del rol
     * @return informacion en JSON
     */
    private static JsonObject obtenerRolJson(ResultSet datosRol) {
        Date comienzoEnRol;
        String rol;
        JsonObject rolObject = null;
        try {
            if (datosRol.next()) {
                comienzoEnRol = datosRol.getDate("comienzoEnRol");
                rol = datosRol.getString(NOMBRETIPO);

                rolObject = Json.createObjectBuilder()
                        .add("comienzoEnRol", comienzoEnRol.toString())
                        .add("rol", rol)
                        .build();

            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rolObject;
    }
}
