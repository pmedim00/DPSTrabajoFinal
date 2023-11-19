package es.uva.eii.ds.empresamodelpc18.interfaz_empleados;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_personal_almacen.AlmacenVista;
import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_personal_almacen.BuscarEspaciosVista;
import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_gerente_ventas.ComponentesStockVista;
import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_personal_almacen.EspaciosDisponiblesVista;
import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_gerente_ventas.GerenteVista;
import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado.IdentificarseVista;
import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_gerente_ventas.PedidosSolicitadosVista;
import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_tecnico_taller.RegistrarMontajePCVista;
import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_tecnico_taller.TecnicoVista;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 * Implementacion del gestor de interfaces
 * 
 */
public class GestorDeInterfazDeUsuario {

    private static GestorDeInterfazDeUsuario gestor;
    private JFrame ventanaActual;
    private String dni;
    /**
     * Metodo para obtener la instancia de la clase
     * @return gestor Gestor de interfaces
     */
    public static GestorDeInterfazDeUsuario getInstance() {
        if (gestor == null) {
            gestor = new GestorDeInterfazDeUsuario();
        }
        return gestor;
    }
    /**
     * Constructor del gestor de interfaces
     */
    private GestorDeInterfazDeUsuario() {
        ventanaActual = null;
    }

    public void start() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException  | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IdentificarseVista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            ventanaActual = new IdentificarseVista();
            ventanaActual.setVisible(true);
        });
    }
    /**
     * Cambia a vista del personal identificado
     * @param rol Papel del personal
     */
    public void cambiarVistaIdentificarse(String rol) {
        ventanaActual.dispose();

        switch (rol) {
            case "PERSONALALMACEN":
                ventanaActual = new AlmacenVista();
                break;
            case "GERENTEVENTAS":
                ventanaActual = new GerenteVista();
                break;
            case "TECNICODELTALLER":
                ventanaActual = new TecnicoVista();
                break;
            default:
                break;
        }
        
        ventanaActual.setVisible(true);

    }
    
    /**
     * Cambia a vista de buscar espacios PC
     */
    public void cambiarVistaBuscarEspacioPC() {
        ventanaActual.dispose();
        ventanaActual = new BuscarEspaciosVista();
        ventanaActual.setVisible(true);
    }
    
    /**
     * Cambia a la vista de espacios disponibles
     * @param tipo Tipo de CPU a mostrar
     * @param num Cantidad de espacios necesarios
     * @throws SQLException consulta no valida
     */
     public void cambiarEspaciosDisponiblesVista(String tipo, int num) throws SQLException {
        ventanaActual.dispose();
        ventanaActual = new EspaciosDisponiblesVista(tipo,num);
        ventanaActual.setVisible(true);
     }

     /**
      * Cambia a la vista de pedidos solicitados
      * @throws SQLException consulta no valida
      * @throws ClassNotFoundException clase no valida
      * @throws IOException error
      */
    public void cambiarPedidosSolicitadosVista() throws SQLException, ClassNotFoundException, IOException {
        ventanaActual.dispose();
        ventanaActual = new PedidosSolicitadosVista();
        ventanaActual.setVisible(true);
    }
    
   /**
    * Cambia a la vista de stock de componentes
    * @param configuracion configuracion que se desea mostrar
    * @throws SQLException consulta no valida
    * @throws ClassNotFoundException clase no valida
    * @throws IOException error
    */
    public void cambiarComponentesStockVista(int configuracion) throws SQLException, ClassNotFoundException, IOException{
        ventanaActual.dispose();
        ventanaActual = new ComponentesStockVista(configuracion);
        ventanaActual.setVisible(true);
    }
    
    /**
     * Cambia a la vista de registrar montaje PC
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public void cambiarVistaRegistrarMontajePC() throws SQLException, ClassNotFoundException, IOException {
        ventanaActual.dispose();
        ventanaActual = new RegistrarMontajePCVista();
        ventanaActual.setVisible(true);
    }
    
    /**
     * Guarda dni del usuario
     * @param dni del usuario
     */
    public void setDni(String dni){
        this.dni=dni;
    }
    /**
     * Obtine dni del usuario
     * @return dni
     */
    public String getDni(){
        return dni;
    }
}
