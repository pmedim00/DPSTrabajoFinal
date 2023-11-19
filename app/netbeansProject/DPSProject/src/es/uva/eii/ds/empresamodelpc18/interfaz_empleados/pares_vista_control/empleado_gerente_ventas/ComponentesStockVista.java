package es.uva.eii.ds.empresamodelpc18.interfaz_empleados.pares_vista_control.empleado_gerente_ventas;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 * Vista de stock de componentes
 * 
 */
public class ComponentesStockVista extends javax.swing.JFrame {
    
    private ComponentesStockCtrlVista controlador;
    
    /**
     * Constructor vista
     * @param configuracion a mostrar
     * @throws SQLException consulta no valida
     * @throws ClassNotFoundException clase no valida
     * @throws IOException error
     */
    public ComponentesStockVista(int configuracion) throws SQLException, ClassNotFoundException, IOException {
        initComponents();
        controlador = new ComponentesStockCtrlVista(this);
        controlador.obtenerComponentesStock(configuracion);
        controlador.procesarListaDeConfiguracionesDisponibles();  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usuarioIdentificado = new javax.swing.JLabel();
        tituloProcesar = new javax.swing.JLabel();
        confirmarButton = new javax.swing.JToggleButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaConfiguracion = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        usuarioIdentificado.setText("Usuario identificado: Gerente ventas");

        tituloProcesar.setText("Procesar pedidos solicitados");

        confirmarButton.setText("Confirmar pedido");
        confirmarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarButtonActionPerformed(evt);
            }
        });

        tablaConfiguracion.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tablaConfiguracion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tarjeta gráfica", "Disco duro", "Placa base", "Caja", "Procesador", "RAM"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
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
        jScrollPane3.setViewportView(tablaConfiguracion);
        if (tablaConfiguracion.getColumnModel().getColumnCount() > 0) {
            tablaConfiguracion.getColumnModel().getColumn(0).setResizable(false);
            tablaConfiguracion.getColumnModel().getColumn(0).setPreferredWidth(10);
            tablaConfiguracion.getColumnModel().getColumn(1).setResizable(false);
            tablaConfiguracion.getColumnModel().getColumn(1).setPreferredWidth(10);
            tablaConfiguracion.getColumnModel().getColumn(2).setResizable(false);
            tablaConfiguracion.getColumnModel().getColumn(2).setPreferredWidth(10);
            tablaConfiguracion.getColumnModel().getColumn(3).setResizable(false);
            tablaConfiguracion.getColumnModel().getColumn(3).setPreferredWidth(10);
            tablaConfiguracion.getColumnModel().getColumn(4).setResizable(false);
            tablaConfiguracion.getColumnModel().getColumn(4).setPreferredWidth(10);
            tablaConfiguracion.getColumnModel().getColumn(5).setResizable(false);
            tablaConfiguracion.getColumnModel().getColumn(5).setPreferredWidth(10);
        }

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
                            .addComponent(tituloProcesar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(345, 345, 345)
                        .addComponent(confirmarButton)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(usuarioIdentificado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tituloProcesar)
                .addGap(33, 33, 33)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(confirmarButton)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarButtonActionPerformed
        controlador.confirmar();
    }//GEN-LAST:event_confirmarButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton confirmarButton;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaConfiguracion;
    private javax.swing.JLabel tituloProcesar;
    private javax.swing.JLabel usuarioIdentificado;
    // End of variables declaration//GEN-END:variables

    /**
     * Muestra cantidad disponible de los componentes
     * @param tarjetasGraficas cantidad
     * @param discosDuros cantidad
     * @param placasBase cantidad
     * @param cajas cantidad
     * @param procesadores cantidad
     * @param rams cantidad
     */
    public void mostrarCantidadesDisponiblesTiposConf(int tarjetasGraficas, int discosDuros, int placasBase, 
            int cajas, int procesadores, int rams) {
        DefaultTableModel model = (DefaultTableModel) tablaConfiguracion.getModel();
        model.addRow(new Object[]{tarjetasGraficas, discosDuros, placasBase, 
            cajas, procesadores, rams});
    }
    
}
