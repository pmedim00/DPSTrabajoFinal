package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_tecnico_taller;

import es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado.IdentificarseVista;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Vista Registrar Montaje
 * 
 */
public class RegistrarMontajePCVista extends javax.swing.JFrame {
    
    private RegistrarMontajePCCtrlVista controlador;
    
    public RegistrarMontajePCVista() throws SQLException, ClassNotFoundException, IOException {
        initComponents();
        etiquetaPC.setEnabled(false);
        aceptarButton.setEnabled(false);
        etiquetaComponente.setEnabled(false);
        comprobarEtiquetaComponente.setEnabled(false);
        confirmarButton.setEnabled(false);
        controlador = new RegistrarMontajePCCtrlVista(this);
        controlador.procesarListaConfiguraciones();
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tablaPedidos.getModel());
        tablaPedidos.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(2);
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usuarioIdentificado = new javax.swing.JLabel();
        tituloRegistrar = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaConfiguracion = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPedidos = new javax.swing.JTable();
        pedidosEnProcesoLabel = new javax.swing.JLabel();
        peticionEtiquetaLabel = new javax.swing.JLabel();
        peticionEtiquetasComponentesLabel = new javax.swing.JLabel();
        confirmarButton = new javax.swing.JButton();
        cancelarButton = new javax.swing.JButton();
        etiquetaPC = new javax.swing.JTextField();
        errorMessageLabel = new javax.swing.JLabel();
        aceptarButton = new javax.swing.JButton();
        etiquetaComponente = new javax.swing.JTextField();
        comprobarEtiquetaComponente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        usuarioIdentificado.setText("Usuario identificado: Tecnico de taller");

        tituloRegistrar.setText("Registrar montaje PC");

        tablaConfiguracion.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tablaConfiguracion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo CPU", "Velocidad CPU", "Capacidad RAM", "Capacidad DD", "Velocidad tarjeta gráfica", "Memoria tarjeta gráfica"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaConfiguracionMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaConfiguracion);
        if (tablaConfiguracion.getColumnModel().getColumnCount() > 0) {
            tablaConfiguracion.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaConfiguracion.getColumnModel().getColumn(1).setPreferredWidth(40);
            tablaConfiguracion.getColumnModel().getColumn(2).setPreferredWidth(40);
            tablaConfiguracion.getColumnModel().getColumn(3).setPreferredWidth(40);
            tablaConfiguracion.getColumnModel().getColumn(4).setResizable(false);
            tablaConfiguracion.getColumnModel().getColumn(4).setPreferredWidth(90);
            tablaConfiguracion.getColumnModel().getColumn(5).setResizable(false);
            tablaConfiguracion.getColumnModel().getColumn(5).setPreferredWidth(90);
        }

        tablaPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cantidad de PCs", "Fecha pedido"
            }
        ));
        tablaPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPedidosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaPedidos);

        pedidosEnProcesoLabel.setText("Pedidos en proceso con esa configuración:");

        peticionEtiquetaLabel.setText("Introduce etiqueta identificativa del nuevo PC:");

        peticionEtiquetasComponentesLabel.setText("Introduce las etiquetas identificativas de cada componente utilizado:");

        confirmarButton.setText("Confirmar registro");
        confirmarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarButtonActionPerformed(evt);
            }
        });

        cancelarButton.setText("Cancelar");
        cancelarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarButtonActionPerformed(evt);
            }
        });

        errorMessageLabel.setForeground(new java.awt.Color(255, 0, 0));

        aceptarButton.setText("Aceptar");
        aceptarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarButtonActionPerformed(evt);
            }
        });

        comprobarEtiquetaComponente.setText("Comprobar y añadir");
        comprobarEtiquetaComponente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comprobarEtiquetaComponenteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usuarioIdentificado)
                            .addComponent(tituloRegistrar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                            .addComponent(pedidosEnProcesoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(errorMessageLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(peticionEtiquetaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(etiquetaPC, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(aceptarButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(confirmarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelarButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(peticionEtiquetasComponentesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(etiquetaComponente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(comprobarEtiquetaComponente)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(usuarioIdentificado)
                .addGap(18, 18, 18)
                .addComponent(tituloRegistrar)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pedidosEnProcesoLabel)
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(peticionEtiquetaLabel)
                    .addComponent(etiquetaPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aceptarButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorMessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(peticionEtiquetasComponentesLabel)
                    .addComponent(etiquetaComponente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comprobarEtiquetaComponente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmarButton)
                    .addComponent(cancelarButton))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarButtonActionPerformed
        try {
            boolean cantidadCorrecta = controlador.comprobarcantidad();
            if(cantidadCorrecta){
                controlador.agregarEtiqueta(etiquetaPC.getText(), controlador.getDni());
                controlador.procesarConfirmar(tablaPedidos.getSelectedRow());
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarMontajePCVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_confirmarButtonActionPerformed

    private void cancelarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarButtonActionPerformed
        controlador.procesarCancelar();
    }//GEN-LAST:event_cancelarButtonActionPerformed

    private void tablaConfiguracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaConfiguracionMouseClicked
        while(tablaPedidos.getRowCount()>0){
            ((DefaultTableModel)tablaPedidos.getModel()).removeRow(0);
        }
        tablaConfiguracion.getSelectedRow();
        try {
            controlador.procesarListaPedidos(tablaConfiguracion.getSelectedRow(), true);
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Logger.getLogger(RegistrarMontajePCVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tablaConfiguracionMouseClicked

    private void tablaPedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPedidosMouseClicked
        etiquetaPC.setEnabled(true);
        aceptarButton.setEnabled(true);
    }//GEN-LAST:event_tablaPedidosMouseClicked

    private void aceptarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarButtonActionPerformed
        if(etiquetaPC.getText().isEmpty()){
            mostrarMensajeError("Etiqueta PC no valida");       
        }else{
            boolean noValido=etiquetaPC.getText().matches("[+-]?\\d*(\\.\\d+)?");
            if(!noValido){
                mostrarMensajeError("Etiqueta PC no valida");
            } else {
            try {
                boolean noExiste = controlador.comprobarQueNoExisteEsaEtiqueta(etiquetaPC.getText());
                if (noExiste){
                    etiquetaComponente.setEnabled(true);
                    comprobarEtiquetaComponente.setEnabled(true);
                }else{
                    String mensaje = "Esa etiqueta identificativa ya existe, introduce una nueva.";
                    mostrarMensajeError(mensaje);
                }
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                Logger.getLogger(RegistrarMontajePCVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
    }//GEN-LAST:event_aceptarButtonActionPerformed

    private void comprobarEtiquetaComponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comprobarEtiquetaComponenteActionPerformed
        try {
            controlador.comprobarEtiquetaComponentes(etiquetaComponente.getText(), Integer.parseInt(etiquetaPC.getText()));
            if(controlador.comprobarcantidad()){
                comprobarEtiquetaComponente.setEnabled(false);
                etiquetaComponente.setEnabled(false);
                confirmarButton.setEnabled(true);    
            }
            
        } catch (IOException | SQLException ex) {
            Logger.getLogger(RegistrarMontajePCVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comprobarEtiquetaComponenteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptarButton;
    private javax.swing.JButton cancelarButton;
    private javax.swing.JButton comprobarEtiquetaComponente;
    private javax.swing.JButton confirmarButton;
    private javax.swing.JLabel errorMessageLabel;
    private javax.swing.JTextField etiquetaComponente;
    private javax.swing.JTextField etiquetaPC;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel pedidosEnProcesoLabel;
    private javax.swing.JLabel peticionEtiquetaLabel;
    private javax.swing.JLabel peticionEtiquetasComponentesLabel;
    private javax.swing.JTable tablaConfiguracion;
    private javax.swing.JTable tablaPedidos;
    private javax.swing.JLabel tituloRegistrar;
    private javax.swing.JLabel usuarioIdentificado;
    // End of variables declaration//GEN-END:variables

    /**
     * Muestra los datos de la configuracion
     * @param tipoCPu 1 amd, 2 intel
     * @param velocidadCPU Velocidad de la PU
     * @param capacidadRAM Capadidad de la memoria RAM
     * @param capacidadDD Capacidad del disco duro
     * @param velocidadTarjetaGrafica Velocidad de la tarjeta grafica
     * @param memoriaTarjetaGrafica Capacidad de la tarjeta grafica
     */
    public void mostrarDatosDeConfiguraciones(int tipoCPu, double velocidadCPU, int capacidadRAM, 
            int capacidadDD, double velocidadTarjetaGrafica, int memoriaTarjetaGrafica) {
        DefaultTableModel model = (DefaultTableModel) tablaConfiguracion.getModel();
        model.addRow(new Object[]{tipoCPu, velocidadCPU, capacidadRAM, 
            capacidadDD, velocidadTarjetaGrafica, memoriaTarjetaGrafica});
    }
    
    /**
     * Muestra los pedidos de una configuracion
     * @param cantidad numero de pcs del pedido 
     * @param fecha fecha de realizacion del pedido
     */
    public void mostrarPedidosDeConfiguracionSolicitada(int cantidad, LocalDate fecha) {
        DefaultTableModel model = (DefaultTableModel) tablaPedidos.getModel();
        model.addRow(new Object[]{cantidad, fecha});
    }
    
    /**
     * Muestra mensaje de error
     * @param mensaje informacion extra sobre el error
     */
    public void mostrarMensajeError(String mensaje) {
       errorMessageLabel.setText(mensaje);
       new Thread(() -> {
            try {
                Thread.sleep(3000);
                errorMessageLabel.setText("");
            } catch (InterruptedException ex) {
                Logger.getLogger(IdentificarseVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }
    
    /**
     * Reset del campo etiqueta componentes
     */
    public void limpiarEtiquetaComponentes(){
        etiquetaComponente.setText("");
}
}
